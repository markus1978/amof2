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

public class SimpleMasXmiFiles implements IMasXmiFiles {
    
    private String syntaxFile;
    private String semanticFile;
    
    public SimpleMasXmiFiles(String pathToContextFile, String contextFile) throws FileNotFoundException, IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream( pathToContextFile.concat(contextFile) ));
        
        syntaxFile = pathToContextFile.concat( (String) properties.get("syntax") );
        semanticFile = pathToContextFile.concat( (String) properties.get("semantic") );
    }

    public String getMasFile() {
        return semanticFile;
    }

    public String getSyntaxFile() {
        return syntaxFile;
    }

}
