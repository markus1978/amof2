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

import hub.sam.stateautomaton.StateAutomaton;

public class AutomatonRuntimeCustom extends AutomatonRuntimeDlg {

    /**
     * fire initial transition.
     * 
     */
    @Override
    public void initialise() {
        StateAutomaton.evaluateInvariant("self.state->size() > 0 and initialState->size() = 1 and finalState->size() > 0",
                getMetaClassifierAutomaton());
        StateAutomaton.evaluateInvariant("initialState.outgoing->size() = 1",
                self.getMetaClassifierAutomaton());

        InitialState initialState = (InitialState) getCurrentState().getMetaClassifierState();
        Transition initialTransition = initialState.getOutgoing().iterator().next();
        initialTransition.fire(getCurrentState());
    }
    
    @Override
    public void run(java.lang.String input) {
        initialise();
        
        while (input.length() > 0) {
            StateRuntime stateRuntime = getCurrentState();
            
            java.lang.String chr = input.substring(0, 1);
            boolean consumed = stateRuntime.consume(chr);
            input = input.substring(1);
            if (!consumed) {
                // ignore token
                System.out.println("ignoring input token '" + chr + "'");
            }
        }
    }
    
    @Override
    public void remove()  {
        System.out.println(getDebugName(self) + ": deleting instances ...");
        for (State state: getMetaClassifierAutomaton().getState()) {
            StateRuntime stateRuntime = getState(state);
            if (stateRuntime != null) {
                if (stateRuntime.getSubAutomaton() != null) {
                    stateRuntime.getSubAutomaton().remove();
                }
                stateRuntime.metaDelete();
                System.out.println(getDebugName(self) + ": deleted instance of state '" + state.getName() + "'");
            }
        }
        System.out.println("deleted: " + getDebugName(self));
        metaDelete();
    }
    
    public static java.lang.String getDebugName(AutomatonRuntime automatonRuntime) {
        return "automaton " + automatonRuntime.getMetaClassifierAutomaton().getName()
                + " (" + ((AutomatonRuntimeImpl) automatonRuntime).getId() + ")";
    }
    
}