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

package hub.sam.mof.codegeneration;

public class AbstractGenerator {
    private CodeGenerator cg = null;    
    private final StreamFactory streamFactory; 
    protected int indent = 0;
    protected hub.sam.mof.javamapping.JavaMapping javaMapping = hub.sam.mof.javamapping.JavaMapping.mapping;
    public AbstractGenerator(StreamFactory streamFactory) {
        this.streamFactory = streamFactory;
    }
    
    protected void print(Object o) throws Throwable {
        cg.print(o);
    }
    
    protected void add(String code) {
        cg.add(indent, code);
    }
    
    protected void init(java.util.List<String> packageName, String className) throws Throwable {
        cg = new CodeGenerator(streamFactory.createStream(packageName, className, "java"));
        if (packageName.size() != 0) {
            String packageIdentifier = "";           
            for (String singleName: packageName) {
                packageIdentifier += singleName + ".";
            }
            packageIdentifier = packageIdentifier.substring(0, packageIdentifier.length()-1);
            add("package " + packageIdentifier + ";");
            add("");
            print(this);
        }        
    }
    
    protected String getJavaIdentifier(cmof.NamedElement element) {
        return javaMapping.getJavaIdentifier(element);
    }
    
    protected String getFullQualifiedJavaIdentifier(cmof.NamedElement element) {
        return javaMapping.getFullQualifiedJavaIdentifier(element);
    }
}
