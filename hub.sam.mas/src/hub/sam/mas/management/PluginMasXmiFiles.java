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

import hub.sam.mas.MasPlugin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

public class PluginMasXmiFiles implements MasXmiFiles {
    
    private String syntaxFile;
    private String semanticFile;
    private String semanticMetaFile;
    
    public PluginMasXmiFiles(String syntaxFile, String semanticFile) {
        this.syntaxFile = syntaxFile;
        this.semanticFile = semanticFile;
    }

    public PluginMasXmiFiles(IPath pathToContextFile, String contextFile) throws FileNotFoundException, IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream( pathToContextFile.append(contextFile).makeAbsolute().toOSString() ));
        
        syntaxFile = pathToContextFile.append( (String) properties.get("syntax") ).makeAbsolute().toOSString();
        semanticFile = pathToContextFile.append( (String) properties.get("semantic") ).makeAbsolute().toOSString();
        
        String urlPath = FileLocator.toFileURL(MasPlugin.getDefault().getBundle().getEntry(masMetaFileRelative)).getPath();
        semanticMetaFile = new Path(urlPath).makeAbsolute().toOSString();
    }

    public String getMasFile() {
        return semanticFile;
    }

    public String getSyntaxFile() {
        return syntaxFile;
    }

    public String getMasMetaFile() {
        return semanticMetaFile;
    }

}
