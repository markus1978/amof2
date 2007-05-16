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

public class AutomatonCustom extends AutomatonDlg {
    
    @Override
    public void run(java.lang.String input) {
        AutomatonRuntime runtime = self.metaCreateAutomatonRuntime();
        runtime.initialise();

        while (input.length() > 0) {
            java.lang.String chr = input.substring(0, 1);
            boolean consumed = runtime.consume(chr);            
            input = input.substring(1);
            if (!consumed) {
                // ignore token
                System.out.println("ignoring token '" + chr + "'");
            }
        }
        
        runtime.destroy();
    }
    
}
