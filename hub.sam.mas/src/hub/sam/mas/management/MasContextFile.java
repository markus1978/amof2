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

import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.core.resources.IResource;

/**
 * Use MasContextFile if you have the context file as an Eclipse IResource.
 * If you don't have it, use SimpleMasContextFile.
 * 
 */
public class MasContextFile extends SimpleMasContextFile implements IMasContextFileResource {
    
    private final IResource contextFileResource;
    
    public MasContextFile(IResource contextFileResource) throws FileNotFoundException, IOException {
        super(contextFileResource.getLocation().toOSString());
        this.contextFileResource = contextFileResource;
    }

    public MasContextFile(String contextFileLocation) throws FileNotFoundException, IOException {
        super(contextFileLocation);
        this.contextFileResource = null;
    }
    
    public IResource getResource() {
        return contextFileResource;
    }

}
