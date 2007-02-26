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

package hub.sam.mas.management;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.jdom.JDOMException;

import hub.sam.mof.Repository;
import hub.sam.mof.instancemodel.MetaModelException;
import hub.sam.mof.xmi.XmiException;
import cmof.Package;
import cmof.reflection.Extent;

public abstract class AbstractMOFModelFactory implements MOFModelFactory {
    
    private String syntaxXmiFile;
    private String syntaxMetaXmiFile;
    private String semanticXmiFile;
    private String semanticMetaXmiFile;
    
    private static MOFModel syntaxMetaModel;
    
    public AbstractMOFModelFactory(String syntaxXmiFile, String syntaxMetaXmiFile, String semanticXmiFile, String semanticMetaXmiFile) {
        this.syntaxXmiFile = syntaxXmiFile;
        this.syntaxMetaXmiFile = syntaxMetaXmiFile;
        this.semanticXmiFile = semanticXmiFile;
        this.semanticMetaXmiFile = semanticMetaXmiFile;
    }
    
    public AbstractMOFModelFactory(String pathToContextFile, String contextFile) throws FileNotFoundException, IOException {
        IPath eclipsePathToContextFile = new Path(pathToContextFile);
        String completeContextFile = eclipsePathToContextFile.append(contextFile).makeAbsolute().toOSString();
        
        Properties properties = new Properties();
        properties.load(new FileInputStream(completeContextFile));
        
        this.syntaxXmiFile = eclipsePathToContextFile.append((String) properties.get("syntax")).makeAbsolute().toOSString();
        this.semanticXmiFile = eclipsePathToContextFile.append((String) properties.get("semantic")).makeAbsolute().toOSString();
    }
    
    protected String getSemanticMetaXmiFile() {
        return semanticMetaXmiFile;
    }

    protected String getSemanticXmiFile() {
        return semanticXmiFile;
    }

    protected String getSyntaxMetaXmiFile() {
        return syntaxMetaXmiFile;
    }

    protected String getSyntaxXmiFile() {
        return syntaxXmiFile;
    }

    protected MOFModel createSyntaxMetaModel() {
        if (syntaxMetaModel == null) {
            Extent cmofExtent = Repository.getLocalRepository().getExtent(Repository.CMOF_EXTENT_NAME);
            Package cmofPackage = (Package) cmofExtent.query("Package:cmof");
            syntaxMetaModel = new MOFModel(null, cmofExtent, Repository.CMOF_EXTENT_NAME, cmofPackage);
        }
        return syntaxMetaModel;
    }
    
    public MOFModel createSyntaxModel() throws MOFModelException {
        MOFModel metaModel = createSyntaxMetaModel();
        return createModel(getSyntaxXmiFile(), metaModel);
    }
    
    protected MOFModel createModel(String xmiFile, MOFModel metaModel) throws MOFModelException {
        Repository repository = Repository.getLocalRepository();
        Extent modelExtent = repository.createExtent(xmiFile);
        
        try {
            if (xmiFile.endsWith(".xml")) {
                    repository.loadXmiIntoExtent(modelExtent, metaModel.getPackage(), new FileInputStream(xmiFile));
            }
            else if (xmiFile.endsWith(".mdxml")) {
                repository.loadMagicDrawXmiIntoExtent(modelExtent, metaModel.getPackage(), new FileInputStream(xmiFile));
            }
        }
        catch (FileNotFoundException e) {
            throw new MOFModelException(e);
        }
        catch (IOException e) {
            throw new MOFModelException(e);
        }
        catch (JDOMException e) {
            throw new MOFModelException(e);
        }
        catch (XmiException e) {
            throw new MOFModelException(e);
        }
        catch (MetaModelException e) {
            throw new MOFModelException(e);
        }
        
        return new MOFModel(metaModel, xmiFile, modelExtent, xmiFile, null);
    }

    public MOFModel createSemanticModel() throws MOFModelException {
        MOFModel metaModel = createSemanticMetaModel();
        return createModel(getSemanticXmiFile(), metaModel);
    }

    protected abstract MOFModel createSemanticMetaModel();

}
