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

package hub.sam.mof.instancemodel;

public class InstanceValue<C,P,DataValue> extends ValueSpecificationImpl<C,P,DataValue> {
    
    private final ClassInstance<C,P,DataValue> instance;
    
    @Override
	public int hashCode() {
        if (instance != null) {
            return instance.hashCode();
        } else {
            return super.hashCode();
        }
    }
    
    @Override
	public boolean equals(Object theOther) {
        if (theOther instanceof InstanceValue) {
            if (instance != null) {
                return instance.equals(((InstanceValue)theOther).getInstance());
            } else {
                return super.equals(theOther);
            }
        } else {
            return false;
        }
    }
    
    public InstanceValue(ClassInstance<C,P,DataValue> instance) {         
        this.instance = instance;
    }
    
    public ClassInstance<C,P,DataValue> getInstance() {
        return instance;
    } 
    
    @Override
	public InstanceValue<C,P,DataValue> asInstanceValue() {
        return this;
    }

    @Override
	public String toString() {
        return instance.toString();
    }
}
