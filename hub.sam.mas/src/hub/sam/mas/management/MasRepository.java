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
    private Map<String, MasContext> contextFileToMasContext = new HashMap<String, MasContext>();
    
    private MasRepository() {
        // private constructor
    }
    
    public static MasRepository getInstance() {
        if (instance == null) {
            instance = new MasRepository();
        }
        return instance;
    }
    
    /**
     * Creates a MAS Context in this MAS Repository.
     * 
     * @param modelContainer
     * @return
     */
    public MasContext createMasContext(IMasModelContainer modelContainer, IMasContextFile contextFile) {
        MasContext context = new MasContext(modelContainer);
        contexts.put(context.getContextId(), context);
        contextFileToMasContext.put(contextFile.getLocation(), context);
        return context;
    }
    
    /**
     * Creates a MAS Context in this MAS Repository and keeps a reference to the MAS Context File.
     * Use this method if you need this reference later.
     * 
     * @param modelContainer
     * @param contextFile
     * @return
     */
    public MasContext createMasContext(IMasModelContainer modelContainer, IMasContextFileResource contextFileResource) {
        MasContext context = new MasContext(modelContainer, contextFileResource);
        contexts.put(context.getContextId(), context);
        contextFileToMasContext.put(contextFileResource.getLocation(), context);
        return context;
    }
    
    public void closeMasContext(MasContext context) {
        context.close();
        contexts.remove(context.getContextId());
    }
    
    /**
     * retrieve the MasContext for the supplied MasContextFile
     * 
     * @param contextFile
     * @return
     */
    public MasContext getMasContext(String contextFile) {
        return contextFileToMasContext.get(contextFile);
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
