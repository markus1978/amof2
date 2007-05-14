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
    public void fire(AutomatonRuntime context)  {
        System.out.println(AutomatonRuntimeCustom.getDebugName(context) + " consumes input '"
                + getInput() + "' in state " + context.getCurrentState().getName() + ".");

        // make transition
        context.setCurrentState(getTarget());
        
        // create runtime information if target is a composite state
        if (getTarget().getSubAutomaton() != null && context.getCompositeState(getTarget()) == null) {
            context.createCompositeState(getTarget());
        }

        System.out.println(AutomatonRuntimeCustom.getDebugName(context)
                + " transitions to state " + getTarget().getName() + ".");
        if (getTarget() instanceof FinalState) {
            System.out.println(AutomatonRuntimeCustom.getDebugName(context) + " reached a final state.");
        }
    }

}
