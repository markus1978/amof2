/***********************************************************************
 * MASE -- MOF Action Semantics Editor
 * Copyright (C) 2007 Andreas Blunk
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301  USA
 ***********************************************************************/

package hub.sam.mase.m2model;

import java.io.InputStream;
import cmof.Package;
import cmof.cmofFactory;
import cmof.reflection.Extent;
import hub.sam.mof.Repository;
import hub.sam.mase.editor.MaseEditDomain;
import hub.sam.mase.m2model.Activity;
import hub.sam.mase.m2model.m2modelFactory;
import hub.sam.mase.m2model.ModelGarbageCollector;
//import hub.sam.mase.StaticM2Model;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.ui.IFileEditorInput;

public class MaseRepository {
    
    private static Repository repository;
    private static Package cmofPackage;
    private static Package m2Package;
    private static Extent m2Extent;
    public static final java.lang.String relativeXmiPath = "resources/models/bootstrap_merged.xml";
    
    private final Logger logger = Logger.getLogger(MaseRepository.class.getName());
    private static boolean initialized = false;
    private final Extent m1Extent;
    private final m2modelFactory factory;
    private final IFile editorFile;
    private final java.lang.String m1ExtentName;
    private final MaseEditDomain editDomain;
    
    public MaseRepository(MaseEditDomain editDomain, IFileEditorInput editorInput) throws IllegalStateException {
        if (!initialized) {
            throw new IllegalStateException("initialize MaseRepository first");
        }
        this.editDomain = editDomain;
        editorFile = editorInput.getFile();
        m1ExtentName = editorFile.toString();
        m1Extent = repository.createExtent(m1ExtentName);
        factory = (m2modelFactory) repository.createFactory(m1Extent, m2Package);
        logger.debug("created model extent: " + m1ExtentName);
    }
    
    public static void init(InputStream m2InputStream) {
        if (!initialized) {
            repository = Repository.getLocalRepository();
            Extent cmofExtent = repository.getExtent(Repository.CMOF_EXTENT_NAME);
            cmofPackage = (Package) cmofExtent.query("Package:cmof");
    
            m2Extent = repository.createExtent("m2Extent");
            //m2Extent = StaticM2Model.createModel();
            cmofFactory cmofFactory = (cmofFactory) repository.createFactory(m2Extent, cmofPackage);
    
            try {
                repository.loadXmiIntoExtent(m2Extent, cmofPackage, m2InputStream);
                m2Package = (Package) m2Extent.query("Package:masgraphics");
                
                cmof.Tag tag = cmofFactory.createTag();
                tag.setValue("Mase");
                tag.setName("org.omg.xmi.nsPrefix");
                m2Package.getTag().add(tag);
                
                initialized = true;
            }
            catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }
    
    public static boolean isInitialized() {
        return initialized;
    }
    
    public void deleteModelExtent() {
        repository.deleteExtent(m1ExtentName);
        logger.debug("deleted model extent: " + m1ExtentName);
    }
    
    public Activity loadModel() {
        java.io.File ioFile = new java.io.File(getEditorFileLocation());
        if (ioFile.length() > 0) {
            try {
                repository.loadXmiIntoExtent(m1Extent, m2Package, getEditorFileLocation());
                
                for (java.lang.Object outermostObject : m1Extent.outermostComposites()) {
                    if (outermostObject instanceof hub.sam.mase.m2model.Activity) {
                        return (Activity) outermostObject;
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        else {
            MaseCreationFactory factory = new MaseCreationFactory(editDomain, Activity.class);
            return (Activity) factory.getNewObject();
        }
        return null;
    }
    
    public void saveModel() {
        ModelGarbageCollector.getInstance().cleanUp();
        try {
            repository.writeExtentToXmi(getEditorFileLocation(), m2Package, m1Extent);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    
    public m2modelFactory getFactory() {
        return factory;
    }
    
    private java.lang.String getEditorFileLocation() {
        return editorFile.getLocation().toOSString();
    }

}
