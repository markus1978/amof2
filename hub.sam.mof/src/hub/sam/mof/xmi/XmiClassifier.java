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

package hub.sam.mof.xmi;

public class XmiClassifier {
    private String name = null;
    private String namespacePrefix = null;
    private XmiClassifier contextClass = null;
    private String contextProperty = null;
    private final boolean isDefinedByContext;
    
    public XmiClassifier(String name, String namespacePrefix) {
        this.name = name;
        this.namespacePrefix = namespacePrefix;
        isDefinedByContext = false;
    }
    
    public XmiClassifier(XmiClassifier contextClass, String contextProperty) {
        this.contextClass = contextClass;
        this.contextProperty = contextProperty;
        isDefinedByContext = true;
    }

    public String getName() {
        return name;
    }

    public String getNamespacePrefix() {
        return namespacePrefix;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setNamespacePrefix(String prefix) {
        this.namespacePrefix = prefix;
    }
    
    @Override
	public String toString() {
        if (isDefinedByContext()) {
            return "(classified by context: " + contextClass + "::" + contextProperty + ")";
        } else {
            if (namespacePrefix != null) {
                return namespacePrefix + "." + name;
            } else {
                return name;
            }        
        }
    }
    
    public XmiClassifier getContextClass() {
        return contextClass;
    }
    
    public String getContextProperty() {
        return contextProperty;
    }
    
    public boolean isDefinedByContext() {
        return isDefinedByContext;
    }
}
