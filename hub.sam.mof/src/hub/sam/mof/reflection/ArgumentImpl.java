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

package hub.sam.mof.reflection;

public class ArgumentImpl implements cmof.reflection.Argument {
	
	private final String name;
	private final Object value;
	public ArgumentImpl(String name, java.lang.Object value) {
		this.name = name;
		this.value = value;
	}
	
    public String getName() {
        return name;
    }

    public java.lang.Object getValue() {
        return value;
    }    
	
	@Override
	public boolean equals(java.lang.Object other) {
		if (other instanceof ArgumentImpl) {
			ArgumentImpl otherArgument = (ArgumentImpl)other;
			return (name.equals(otherArgument.name) && value.equals(otherArgument.value));
		} else {
			return false;
		}
	}
}
