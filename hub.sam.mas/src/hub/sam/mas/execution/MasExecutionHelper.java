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

package hub.sam.mas.execution;

import hub.sam.mas.management.MasContext;
import hub.sam.mof.Repository;
import hub.sam.mof.management.MofModel;
import hub.sam.mof.ocl.OclImplementationsManager;
import hub.sam.mof.reflection.ExtentImpl;
import hub.sam.mof.reflection.ImplementationsManager;
import hub.sam.mof.reflection.ImplementationsManagerContainer;
import hub.sam.mof.reflection.ImplementationsManagerImpl;
import hub.sam.mof.runtimelayer.MultiLevelImplementationsManager;
import cmof.reflection.Extent;

public class MasExecutionHelper {

    public static void prepareRun(Repository repository, MasContext masContext, MofModel m1Model) {
        ExecutionEnvironment env = new ExecutionEnvironment(m1Model.getExtent(),
        		m1Model.getMetaModel().getExtent(), repository);
                //masContext.getMasModel().getMetaModel().getExtent(), repository);
        
        Extent semanticExtent = masContext.getMasModel().getExtent();
        ((ExtentImpl) semanticExtent).setCustomImplementationsManager(new ImplementationsManagerContainer(
                new ImplementationsManager[] {
                        new MultiLevelImplementationsManager(masContext.getMasModel().getFactory()),
                        new ImplementationsManagerImpl() }
        ));
        
        // MultiLevelImplementationsManager creates runtime instances
        ((ExtentImpl) m1Model.getExtent()).setCustomImplementationsManager(new ImplementationsManagerContainer(
                new ImplementationsManager[] {                		
                        new MASImplementationsManager(masContext, env),
                        new MultiLevelImplementationsManager(m1Model.getFactory()),
                        new OclImplementationsManager(),
                        new ImplementationsManagerImpl() }
        ));               
    }
    
}
