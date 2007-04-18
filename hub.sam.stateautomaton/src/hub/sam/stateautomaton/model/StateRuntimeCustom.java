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

public class StateRuntimeCustom extends StateRuntimeDlg {

    @Override
    public void createInnerAutomatonRuntime() {
        if (getMetaClassifierState().getSubAutomaton() != null) {
            AutomatonRuntime innerAutomatonRuntime = getSubAutomaton();
            if (innerAutomatonRuntime == null) {
                innerAutomatonRuntime = getMetaClassifierState().getSubAutomaton().instantiate();
                innerAutomatonRuntime.initialise();
                setSubAutomaton(innerAutomatonRuntime);
            }
        }
    }

    @Override
    public StateRuntime getTargetStateRuntime(Transition transition) {
        State target = transition.getTarget();
        StateRuntime targetRuntime = getAutomaton().getState(target);
        if (targetRuntime == null) {
            targetRuntime = target.metaCreateStateRuntime();
            getAutomaton().setState(target, targetRuntime);
        }
        return targetRuntime;
    }

    @Override
    public boolean consume(java.lang.String input)  {
        Transition transition = getMetaClassifierState().getEnabledTransition(input);
        if (transition != null) {
            transition.fire(self);
            return true;
        }
        else {
            if (getSubAutomaton() != null) {
                return getSubAutomaton().getCurrentState().consume(input);
            }
        }
        return false;
    }
    
}
