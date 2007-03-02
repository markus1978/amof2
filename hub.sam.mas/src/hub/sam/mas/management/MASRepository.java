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

public class MASRepository {

    private static MASRepository instance;
    private Map<Extent, MASContext> contexts = new HashMap<Extent, MASContext>();
    
    private MASRepository() {
        // private constructor
    }
    
    public static MASRepository getInstance() {
        if (instance == null) {
            instance = new MASRepository();
        }
        return instance;
    }
    
    public MASContext createMASContext(MOFModelFactory factory) throws MOFModelException {
        MASContext context = new MASContext(factory);
        contexts.put(context.getContextId(), context);
        return context;
    }
    
    public void closeMASContext(MASContext context) {
        context.close();
        contexts.remove(context.getContextId());
    }
    
    public MASContext getMASContext(Extent syntaxExtent) {
        return contexts.get(syntaxExtent);
    }
    
    public Collection<MASContext> getMASContexts() {
        return contexts.values();
    }
    
}
