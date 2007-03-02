/*
A MOF 2 Java -- The MOF Repository tool for Java
Copyright (C) 2005 Markus Scheidgen

    This library is free software; you can redistribute it and/or modify it
under the terms of the GNU Lesser General Public License as published by the
Free Software Foundation; either version 2.1 of the License, or any later
version.

    This library is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
details.

    You should have received a copy of the GNU Lesser General Public License
along with this library; if not, write to the Free Software Foundation, Inc.,
59 Temple Place, Suite 330, Boston, MA 02111-1307 USA 
*/

package cmof;

import cmof.common.ReflectiveCollection;
import hub.sam.mof.util.*;

public class UmlClassCustom extends cmof.UmlClassDlg {

    @Override
	public ReflectiveCollection<? extends NamedElement> getMember() {
        ReflectiveCollection<? extends NamedElement> members = new SetImpl<NamedElement>(getOwnedMember());             
        ReflectiveCollection<? extends NamedElement> inheritedMembers = getMyInheritedMember();     
        
        members.addAll(inheritedMembers);
        return members;
    }
        
	private ReflectiveCollection<? extends NamedElement> getMyInheritedMember() {
        ReflectiveCollection<? extends NamedElement> inheritedMembers = new SetImpl<NamedElement>();
        for(cmof.UmlClass superClass: getSuperClass()) {
            inheritedMembers.addAll(superClass.getMember());
        }        
        ReflectiveCollection<? extends NamedElement> result = new SetImpl<NamedElement>(inheritedMembers);
        for(NamedElement inheritedMember: inheritedMembers) {
            if (inheritedMember instanceof RedefinableElement) {      
                for (RedefinableElement redefinedInheritedMember: ((RedefinableElement)inheritedMember).getRedefinedElement()) {
                    if (redefinedInheritedMember.getName().equals(inheritedMember.getName())) {
                        result.remove(redefinedInheritedMember);
                    }
                }                
            }
        }
        for(NamedElement ownedMember: getOwnedMember()) {
            if (ownedMember instanceof RedefinableElement) {
                for (RedefinableElement redefinedOwnedElement: ((RedefinableElement)ownedMember).getRedefinedElement()) {
                    if (redefinedOwnedElement.getName().equals(ownedMember.getName())) {
                        result.remove(redefinedOwnedElement);
                    }
                }                
            }
        }
        return result;               
    }

	@Override
	public ReflectiveCollection<? extends NamedElement> getInheritedMember() {		
		ReflectiveCollection<? extends NamedElement> inheritedMembers = new SetImpl<NamedElement>();
        for(cmof.UmlClass superClass: getSuperClass()) {
            inheritedMembers.addAll(superClass.getOwnedMember());
            inheritedMembers.addAll(superClass.getInheritedMember());
        }  
        return inheritedMembers;        
	}	
}
