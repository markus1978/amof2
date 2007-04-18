/***********************************************************************
 * State Automaton Language
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

package hub.sam.stateautomaton.model;

public class TransitionCustom extends TransitionDlg {

    @Override
    public void fire(StateRuntime context)  {
        AutomatonRuntime currentAutomatonRuntime = context.getAutomaton();

        System.out.println(AutomatonRuntimeCustom.getDebugName(currentAutomatonRuntime) + " consumes input '"
                + getInput() + "' in state " + context.getMetaClassifierState().getName() + ".");

        StateRuntime targetStateRuntime = context.getTargetStateRuntime(self);
        context.createInnerAutomatonRuntime();
        
        // make transition
        context.getAutomaton().setCurrentState(targetStateRuntime);

        System.out.println(AutomatonRuntimeCustom.getDebugName(currentAutomatonRuntime)
                + " transitions to state " + getTarget().getName() + ".");
        if (getTarget() instanceof FinalState) {
            System.out.println(AutomatonRuntimeCustom.getDebugName(currentAutomatonRuntime) + " reached a final state.");
        }
    }

}
