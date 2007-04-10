/*
A MOF 2 Java -- The MOF Repository tool for Java
Copyright (C) 2005 Markus Scheidgen

    This library is free software; you can redistribute it and/or modify it
under the terms of the GNU Lesser General Public License as published by the
Free Software Foundation; either version 2.1 of the License, or any later
version.

    This library is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
details.

    You should have received a copy of the GNU Lesser General Public License
along with this library; if not, write to the Free Software Foundation, Inc.,
59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/

package hub.sam.mof;

import hub.sam.mof.bootstrap.BootstrapExtent;
import hub.sam.mof.codegeneration.GenerationException;
import hub.sam.mof.codegeneration.PackageGenerator;
import hub.sam.mof.codegeneration.ResolveJavaCodeClashes;
import hub.sam.mof.codegeneration.StreamFactory;
import hub.sam.mof.instancemodel.MetaModelException;
import hub.sam.mof.reflection.ExtentImpl;
import hub.sam.mof.reflection.server.ejb.ServerRepositoryHome;
import hub.sam.mof.reflection.server.impl.ReflectionFactory;
import hub.sam.mof.xmi.Xmi1Reader;
import hub.sam.mof.xmi.XmiException;
import hub.sam.mof.xmi.XmiImportExport;
import hub.sam.mof.xmi.Xmi1Reader.XmiKind;
import hub.sam.util.AbstractClusterableException;
import hub.sam.util.Identity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import org.jdom.JDOMException;

import cmof.NamedElement;
import cmof.Package;
import cmof.PrimitiveType;
import cmof.UmlClass;
import cmof.cmofFactory;
import cmof.exception.ModelException;
import cmof.reflection.Extent;
import cmof.reflection.Factory;

/**
 * A repository manages a set of extents that can contain
 * models on all meta-modeling levels.
 */
public class Repository extends hub.sam.util.Identity {

	private static Configuration configuration = new Configuration();
	private Collection<RepositoryChangeListener> fRepositoryChangeListener = new Vector<RepositoryChangeListener>();
	
    /**
     * Extreeem dirty. This is needed for AS applications to communitcate the actual m2 boolean primitiv type to
     * {@link hub.sam.mof.as.actions.AsGuardExpression}.
     */
    public static PrimitiveType booleanType = null;

    @SuppressWarnings("unchecked")
    private static Context getInitialContext(String providerUrl) throws NamingException {
        Hashtable props = new Hashtable();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        props.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        props.put(Context.PROVIDER_URL, providerUrl);
        return new InitialContext(props);
    }

    public static hub.sam.mof.reflection.client.ClientRepository connectToRemoteRepository(String providerUrl)
            throws Exception {
        java.lang.Object o = getInitialContext(providerUrl).lookup(ServerRepositoryHome.JNDI_NAME);
        ServerRepositoryHome repositoryHome =
                (ServerRepositoryHome)PortableRemoteObject.narrow(o, ServerRepositoryHome.class);
        return new hub.sam.mof.reflection.client.impl.ClientRepositoryImpl(repositoryHome.create());
    }

    public static hub.sam.mof.reflection.client.ClientRepository connectToLocalRepository() {
        return new hub.sam.mof.reflection.client.impl.ClientRepositoryImpl(
                ReflectionFactory.getFactory().createRepository());
    }

    public static Repository getLocalRepository() {
        return getDefault();
    }

    private static final Repository defaultRepository = new Repository();

    private static Repository getDefault() {
        return defaultRepository;
    }

    private final Map<String, Extent> extents = new HashMap<String, Extent>();

    /**
     * The name of an extent that contains the CMOF model. This extent will be
     * create when this class is instanciated.
     *
     * @see #Repository()
     */
    public static final String CMOF_EXTENT_NAME = BootstrapExtent.CMOF_EXTENT_NAME;

    /**
     * Creates a new repository. The repository will already contain a extent with
     * the CMOF model in it.
     *
     * @see #CMOF_EXTENT_NAME
     */
    private Repository() {
        super("repository");
        Extent m3 = cmof.CMOF.createModel();
        extents.put(CMOF_EXTENT_NAME, m3);
        if (m3 instanceof hub.sam.util.Identity) {
            ((hub.sam.util.Identity)m3).setParentIdentity(this);
        }
        booleanType = (PrimitiveType)m3.query("Package:core/Package:primitivetypes/PrimitiveType:Boolean");
    }

    public Extent addStaticModel(Class staticModel) throws SecurityException, NoSuchMethodException {
        Extent result;
        java.lang.reflect.Method createMethod = staticModel.getDeclaredMethod("createModel", new Class[]{});
        try {
            result = (Extent)createMethod.invoke(null, new java.lang.Object[]{});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (result != null) {
            extents.put(staticModel.getName(), result);
        }
        fireExtentAddedRepositoryChange(result);
        return result;
    }

    public static final int UNISYS = 1;
    public static final int XMI2 = 2;
    public static final int MD = 3;

    public Extent addXmiModel(InputStream xmi, String name, int type)
            throws JDOMException, IOException, XmiException, MetaModelException {
        Extent result;
        result = createExtent(name);
        if (type == XMI2) {
            hub.sam.mof.xmi.Xmi2Reader.readMofXmi(prepareXmiFileMap(xmi), result,
                    (cmof.Package)getExtent(CMOF_EXTENT_NAME).query("Package:cmof"), XmiKind.mof);
        } else if (type == UNISYS) {
            Xmi1Reader.readMofXmi(
                    xmi, result, (cmof.Package)getExtent(CMOF_EXTENT_NAME).query("Package:cmof"), XmiKind.unisys);
        } else if (type == MD) {
            hub.sam.mof.xmi.Xmi2Reader.readMofXmi(prepareXmiFileMap(xmi), result,
                    (cmof.Package)getExtent(CMOF_EXTENT_NAME).query("Package:cmof"), XmiKind.md);
        }
        return result;
    }

    public void loadXmiModel(Extent extent, Package metaModel, InputStream xmi, int type)
            throws JDOMException, IOException, XmiException, MetaModelException {
        if (type == XMI2) {
            loadXmiIntoExtent(extent, metaModel, xmi);
        } else if (type == UNISYS) {
            loadUnisysXmiIntoExtent(extent, metaModel, xmi);
        } else if (type == MD) {
            loadMagicDrawXmiIntoExtent(extent, metaModel, xmi);
        }
    }

    /**
     * @param name the name of an extent.
     * @return The extent with the given name or Null if no such extent exists in the repository.
     */
    public Extent getExtent(String name) {
        return extents.get(name);
    }

    /**
     * @return The names of all extents in the repository.
     */
    public Collection<String> getExtentNames() {
        return extents.keySet();
    }

    /**
     * Creates an extent for a given meta model.
     *
     * @param name a name for the extent.
     * @return The extent created.
     */
    public Extent createExtent(String name) {
        Extent newextent = new hub.sam.mof.reflection.ExtentImpl(name);
        ((hub.sam.util.Identity)newextent).setParentIdentity(this);
        extents.put(name, newextent);
        fireExtentAddedRepositoryChange(newextent);
        return newextent;
    }
    
    public Extent createExtent(String name, Iterable<? extends Package> metaModel) {
    	Extent result = createExtent(name);
    	((ExtentImpl)result).configureExtent(metaModel);
    	return result;
    }
    
    public Extent createExtent(String name, Package metaModel) {
        Extent result = createExtent(name);
        ((ExtentImpl)result).configureExtent(metaModel);
        return result;
    }

    public Extent createExtent(String name, Extent metaExtent) {
    	return createExtent(name, (Iterable)metaExtent.objectsOfType(
    			(UmlClass)getExtent(CMOF_EXTENT_NAME).query("Package:cmof/Class:Package"), false));
    }

    public void deleteExtent(String name) {    	
        Extent extent = getExtent(name);
        if (extent == null) {
        	throw new ModelException("Extent with name " + name + " does not exist.");
        }
        fireExtentRemovedRepositoryChange(extent);
        extents.remove(name);
        if (extent != null) {
        	((ExtentImpl)extent).myFinalize();
        }
        System.gc();
    }

    /**
     * Gives a factory for the meta-elements in a meta-package. The actual
     * type of the factory depends on the given package.
     *
     * Is deprecated use createExtent with metaModel or metaExtent and retrieve the
     * factory as adaptor from the extent.
     * 
     * @param forExtent  the extent in that the factory will put all elements that it will create
     * @param forPackage the package that contains the meta-elements that the factory will instantiate
     * @return The factory.
     */
    @Deprecated
    public Factory createFactory(Extent forExtent, cmof.Package forPackage) {
        return hub.sam.mof.reflection.FactoryImpl.createFactory(forExtent, forPackage);
    }
    
    public void configureExtent(Extent extent, Extent metaExtent) {
    	configureExtent(extent, (Iterable)metaExtent.objectsOfType(
    			(UmlClass)getExtent(CMOF_EXTENT_NAME).query("Package:cmof/Class:Package"), false));
    }
    
    public void configureExtent(Extent extent, Iterable<? extends Package> packages) {
    	((ExtentImpl)extent).configureExtent(packages);
    }
    
    public void configureExtent(Extent extent, Package metaModel) {
        ((ExtentImpl)extent).configureExtent(metaModel);
    }

    /**
     * Loads model elements from a XMI file into an extent.
     *
     * @param extent      the extent for the elements to load
     * @param metaModel   a meta model for the elemnts to load
     * @param xmiFileName the name of a file that contains xmi
     * @throws java.io.IOException
     * @throws hub.sam.mof.xmi.XmiException
     */
    public void loadXmiIntoExtent(Extent extent, cmof.Package metaModel, String xmiFileName) throws java.io.IOException,
            org.jdom.JDOMException, hub.sam.mof.xmi.XmiException, hub.sam.mof.instancemodel.MetaModelException {
        hub.sam.mof.xmi.Xmi2Reader
                .readMofXmi(prepareXmiFileMap(new java.io.File(xmiFileName)), extent, metaModel, XmiKind.mof);
    }

    public void loadXmiIntoExtent(Extent extent, cmof.Package metaModel, InputStream in) throws java.io.IOException,
            org.jdom.JDOMException, hub.sam.mof.xmi.XmiException, hub.sam.mof.instancemodel.MetaModelException {
        hub.sam.mof.xmi.Xmi2Reader.readMofXmi(prepareXmiFileMap(in), extent, metaModel, XmiKind.mof);
    }

    public XmiImportExport loadMagicDrawXmiIntoExtent(Extent extent, cmof.Package metaModel, String xmiFileName)
            throws java.io.IOException,
            org.jdom.JDOMException, hub.sam.mof.xmi.XmiException, hub.sam.mof.instancemodel.MetaModelException {
    	XmiImportExport result = new XmiImportExport();
        hub.sam.mof.xmi.Xmi2Reader.readMofXmi(prepareXmiFileMap(new java.io.File(xmiFileName)), extent, metaModel,
                XmiKind.md, result);
        return result;
    }

    public XmiImportExport loadMagicDrawXmiIntoExtent(Extent extent, cmof.Package metaModel, InputStream in)
            throws java.io.IOException,
            org.jdom.JDOMException, hub.sam.mof.xmi.XmiException, hub.sam.mof.instancemodel.MetaModelException {
    	XmiImportExport result = new XmiImportExport();
        hub.sam.mof.xmi.Xmi2Reader.readMofXmi(prepareXmiFileMap(in), extent, metaModel, XmiKind.md, result);
        return result;
    }

    public void loadXmiIntoExtent(Extent extent, Package metaModel, String xmiFileName, XmiKind type)
            throws JDOMException, IOException, XmiException, MetaModelException {
        if ((type == XmiKind.ea) || (type == XmiKind.unisys)) {
            hub.sam.mof.xmi.Xmi1Reader.readMofXmi(new File(xmiFileName), extent, metaModel, type);
        } else {
            hub.sam.mof.xmi.Xmi2Reader.readMofXmi(prepareXmiFileMap(new File(xmiFileName)), extent, metaModel, type);
        }
    }

    /**
     * Loads model elements from a XMI file into an extent. This is only for M2 models created by the Unisys Rose plugin for UML1.x.
     *
     * @param extent      the extent for the elements to load
     * @param metaModel   a meta model for the elemnts to load
     * @param xmiFileName the name of a file that contains xmi
     * @throws java.io.IOException
     * @throws hub.sam.mof.xmi.XmiException
     */
    public void loadUnisysXmiIntoExtent(Extent extent, cmof.Package metaModel, String xmiFileName)
            throws java.io.IOException,
            org.jdom.JDOMException, hub.sam.mof.xmi.XmiException, hub.sam.mof.instancemodel.MetaModelException {
        hub.sam.mof.xmi.Xmi1Reader.readMofXmi(new java.io.File(xmiFileName), extent, metaModel, XmiKind.unisys);
    }

    public void loadUnisysXmiIntoExtent(Extent extent, cmof.Package metaModel, InputStream in)
            throws java.io.IOException,
            org.jdom.JDOMException, hub.sam.mof.xmi.XmiException, hub.sam.mof.instancemodel.MetaModelException {
        hub.sam.mof.xmi.Xmi1Reader.readMofXmi(in, extent, metaModel, XmiKind.unisys);
    }

    public void loadEAXmiIntoExtent(Extent extent, cmof.Package metaModel, String xmiFileName)
            throws java.io.IOException,
            org.jdom.JDOMException, hub.sam.mof.xmi.XmiException, hub.sam.mof.instancemodel.MetaModelException {
        hub.sam.mof.xmi.Xmi1Reader.readMofXmi(new java.io.File(xmiFileName), extent, metaModel, XmiKind.ea);
    }

    public void loadEAXmiIntoExtent(Extent extent, cmof.Package metaModel, InputStream in) throws java.io.IOException,
            org.jdom.JDOMException, hub.sam.mof.xmi.XmiException, hub.sam.mof.instancemodel.MetaModelException {
        hub.sam.mof.xmi.Xmi1Reader.readMofXmi(in, extent, metaModel, XmiKind.ea);
    }

    /**
     * Writes elements to a file as XMI.
     *
     * @param xmiFileName the name of the file to write
     * @param metamodel   is a package containing the metamodel of the extent to be exported.
     * @param model       is the extent to be exported.
     * @throws java.io.IOException
     */
    public void writeExtentToXmi(String xmiFileName, cmof.Package metamodel, Extent model)
            throws java.io.IOException, hub.sam.mof.instancemodel.MetaModelException, hub.sam.mof.xmi.XmiException,
            org.jdom.JDOMException {
        hub.sam.mof.xmi.Xmi2Writer.writeMofXmi(new java.io.File(xmiFileName), model, metamodel, XmiKind.mof);
    }
    
    public void writeExtentToXmi(OutputStream out, cmof.Package metamodel, Extent model)
    		throws java.io.IOException, hub.sam.mof.instancemodel.MetaModelException, hub.sam.mof.xmi.XmiException, org.jdom.JDOMException {
    	hub.sam.mof.xmi.Xmi2Writer.writeMofXmi(out, model, metamodel, XmiKind.mof);
    }

    public void writeExtentToMagicDrawXmi(String xmiFileName, cmof.Package metamodel, Extent model)
            throws java.io.IOException, hub.sam.mof.instancemodel.MetaModelException, hub.sam.mof.xmi.XmiException,
            org.jdom.JDOMException {
        hub.sam.mof.xmi.Xmi2Writer.writeMofXmi(new java.io.File(xmiFileName), model, metamodel, XmiKind.md);
    }

    public void writeExtentToMagicDrawXmi(String xmiFileName, cmof.Package metamodel, Extent model, XmiImportExport importExport)
    		throws java.io.IOException, hub.sam.mof.instancemodel.MetaModelException, hub.sam.mof.xmi.XmiException,
    		org.jdom.JDOMException {
    	hub.sam.mof.xmi.Xmi2Writer.writeMofXmi(new FileOutputStream(new java.io.File(xmiFileName)), model, 
    			metamodel, XmiKind.md, importExport);
    }
    
    public void writeExtentToXmi(String xmiFileName, cmof.Package metamodel, Extent model, XmiKind kind)
            throws java.io.IOException, hub.sam.mof.instancemodel.MetaModelException, hub.sam.mof.xmi.XmiException,
            org.jdom.JDOMException {
        hub.sam.mof.xmi.Xmi2Writer.writeMofXmi(new java.io.File(xmiFileName), model, metamodel, kind);
    }

    /**
     * Creates the repository code for a meta model. The repository code will consist of
     * interfaces, implementations, and user-code delegators for all proxy objects, factorys, enumerations.
     * It also creates a static version of the model.
     *
     * @param extent The extent that contains the meta-model. This extent must be an instance of the CMOF-model.
     * @param path   The path to an existing directory in that the generated code will be placed.
     * @throws java.io.IOException
     * @throws GenerationException
     */
    public void generateCode(Extent extent, String path) throws IOException, GenerationException {
        generateCode(extent, path, false);
    }

    public void generateCode(Extent extent, String path, boolean generateCore) throws IOException, GenerationException {
        //new Analyser().analyse(extent);

        Collection<cmof.reflection.Object> outermostContainer = new HashSet<cmof.reflection.Object>();
        for (cmof.reflection.Object obj : extent.objectsOfType(null, true)) {
            outermostContainer.add(obj.getOutermostContainer());
        }

        new ResolveJavaCodeClashes().resolveJavaCodeClashes(
                outermostContainer,
                (cmof.cmofFactory)createFactory(
                        extent, (cmof.Package)getExtent(CMOF_EXTENT_NAME).
                        query("Package:cmof")));

        StreamFactory streamFactory = new StreamFactory(path);
        for (cmof.reflection.Object element : extent.objectsOfType(null, true)) {
            if (element instanceof cmof.Package) {
                if (((cmof.Package)element).getOwner() == null) {
                    if (generateCore || !"core".equals(((NamedElement)element).getName())) {
                        try {
                            new PackageGenerator(streamFactory)
                                    .generate(new java.util.Vector<String>(), (cmof.Package)element);
                        } catch (GenerationException ex) {
                            if (ex.getExceptions().size() > 0) {
                                AbstractClusterableException.printReport(ex, System.err);
                            }
                            throw new GenerationException("Errors during code generation.");
                        }
                    }
                }
            }
        }
    }
    
    public void generateCode(Extent extent, String path, Collection<String> forPackages) {
        Collection<cmof.reflection.Object> outermostContainer = new HashSet<cmof.reflection.Object>();
        for (cmof.reflection.Object obj : extent.objectsOfType(null, true)) {
            outermostContainer.add(obj.getOutermostContainer());
        }

        new ResolveJavaCodeClashes().resolveJavaCodeClashes(
                outermostContainer,
                (cmof.cmofFactory)createFactory(
                        extent, (cmof.Package)getExtent(CMOF_EXTENT_NAME).
                        query("Package:cmof")));

        StreamFactory streamFactory = new StreamFactory(path);
        for (cmof.reflection.Object element : extent.objectsOfType(null, true)) {
            if (element instanceof cmof.Package) {
                if (((cmof.Package)element).getOwner() == null) {
                    if (forPackages.contains((((NamedElement)element).getName()))) {
                        try {
                            new PackageGenerator(streamFactory)
                                    .generate(new java.util.Vector<String>(), (cmof.Package)element);
                        } catch (GenerationException ex) {
                            if (ex.getExceptions().size() > 0) {
                                AbstractClusterableException.printReport(ex, System.err);
                            }
                            throw new GenerationException("Errors during code generation.");
                        }
                    }
                }
            }
        }    	
    }

    public void generateStaticModel(Extent extent, String className, String path)
            throws java.io.IOException, GenerationException {
        String fileName = path + "/" + className.replace(".", "/") + ".java";
        String packageName = null;
        if (className.lastIndexOf(".") != -1) {
            packageName = className.substring(0, className.lastIndexOf("."));
            className = className.substring(className.lastIndexOf(".") + 1, className.length());
        }
        ExtentImpl.writeStaticModel(fileName, packageName, className, extent);
    }

    public static Configuration getConfiguration() {
    	return configuration;
    }
    
    public static void setConfiguration(Configuration configuration) {
    	Repository.configuration = configuration;
    }
    
    /**
     * Generates repository code for a meta-model kept in a XMI file.
     *
     * @param args a string array of size 2 with the name of a file, containing XMI as the first value and a
     *             path to a directory, where the generated code will be placed, as
     *             the second value
     */
    public static void main(String[] args) throws Exception {
        Repository repository = new Repository();
        getConfiguration().setAllowMutuableDerivedUnions(true);
        getConfiguration().setXmlNSPrefixForXsiType("xmi");
        cmof.Package m3Model = (cmof.Package)repository.getExtent(CMOF_EXTENT_NAME).query("Package:cmof");
        
        int i = 0;
        while(true) {
        	Extent theExtent = repository.createExtent("theExtent");
            cmofFactory theFactory = (cmofFactory)repository.createFactory(theExtent, m3Model);
        	Package myPackage = theFactory.createPackage();
        	UmlClass classOne = theFactory.createUmlClass();
        	UmlClass classTwo = theFactory.createUmlClass();
        	myPackage.getOwnedType().add(classOne);
        	myPackage.getOwnedType().add(classTwo);
        	classTwo.getPackage().getOwnedType();
        	
        	repository.deleteExtent("theExtent");        	
        	System.out.println(i++);
        }
    }

    @Override
    protected Identity resolveFirstId(Object id) {
        Identity firstMatch = null;
        if (!getId().equals(id)) {
            for (Extent extent : extents.values()) {
                if (((Identity)extent).getId().equals(id)) {
                    firstMatch = (Identity)extent;
                }
            }
        } else {
            firstMatch = this;
        }
        return firstMatch;
    }

    @Override
    public Identity resolveFullId(List<java.lang.Object> fullId) {
        Identity primary = super.resolveFullId(fullId);
        if (primary == null) {
            return null;
        }
        Identity secondary = primary.getSecondaryIdentity();
        if (secondary != null) {
            return secondary;
        } else {
            return primary;
        }
    }

    public void reset() {
    	fRepositoryChangeListener.clear();
    	for (String extentName : extents.keySet()) {
            Extent extent = extents.get(extentName);
            if (!extentName.equals(CMOF_EXTENT_NAME)) {
                extents.remove(extent);
                if (extent instanceof ExtentImpl) {
                    ((ExtentImpl)extent).myFinalize();
                }
            }
        }        
        System.gc();
    }

    private Map<String, InputStream> prepareXmiFileMap(java.io.File file) throws IOException {
        Map<String, InputStream> result = new HashMap<String,  InputStream>();
        result.put("", new FileInputStream(file));
        return result;
    }

    private Map<String, InputStream> prepareXmiFileMap(InputStream in) {
        Map<String, InputStream> result = new HashMap<String,  InputStream>();
        result.put("", in);
        return result;
    }
    
    public void addRepositoryChangeListener(RepositoryChangeListener listener) {
    	this.fRepositoryChangeListener.add(listener);
    }
    
    public void removeRepositoryChangeListener(RepositoryChangeListener listener) {
    	this.fRepositoryChangeListener.remove(listener);
    }
    
    private void fireExtentAddedRepositoryChange(Extent extent) {
    	for (RepositoryChangeListener listener: fRepositoryChangeListener) {
    		listener.extendAdded(extent);
    	}
    }
    
    private void fireExtentRemovedRepositoryChange(Extent extent) {
    	for (RepositoryChangeListener listener: fRepositoryChangeListener) {
    		listener.extendRemoved(extent);
    	}
    }
}
