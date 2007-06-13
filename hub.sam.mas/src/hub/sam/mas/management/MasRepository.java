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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import cmof.reflection.Extent;

/**
 * Manages many MasContexts. The repository is a singleton. It allows creating a MasContext
 * by providing a MofModelManager.
 *
 */
public class MasRepository {

    private static MasRepository instance;
    private Map<Extent, MasContext> contexts = new HashMap<Extent, MasContext>();
    private Map<String, MasContext> xmiFiles = new HashMap<String, MasContext>();
    
    private MasRepository() {
        // private constructor
    }
    
    public static MasRepository getInstance() {
        if (instance == null) {
            instance = new MasRepository();
        }
        return instance;
    }
    
    public MasContext createMasContext(IMasModelContainer modelContainer) {
        MasContext context = new MasContext(modelContainer);
        contexts.put(context.getContextId(), context);
        xmiFiles.put(context.getSyntaxModel().getXmiFile(), context);
        return context;
    }
    
    public void closeMasContext(MasContext context) {
        context.close();
        contexts.remove(context.getContextId());
    }
    
    /**
     * retrieve MAS Context from Syntax XMI File
     * 
     * @param syntaxXmiFile
     * @return
     */
    public MasContext getMasContext(String syntaxXmiFile) {
        return xmiFiles.get(syntaxXmiFile);
    }

    /**
     * retrieve MAS Context from Syntax Extent
     * 
     * @param syntaxExtent
     * @return
     */
    public MasContext getMasContext(Extent syntaxExtent) {
        return contexts.get(syntaxExtent);
    }
    
    public Collection<MasContext> getMasContexts() {
        return contexts.values();
    }
    
}
