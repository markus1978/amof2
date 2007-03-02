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

import java.util.List;

import hub.sam.mof.codegeneration.wrapper.FactoryWrapper;
import hub.sam.mof.reflection.ExtentImpl;
import hub.sam.mof.reflection.FactoryImpl;

public class FactoryImplGenerator extends AbstractGenerator {

	public FactoryImplGenerator(StreamFactory streamFactory) {
		super(streamFactory);
	}

	public void init(List<String>myPackageName, FactoryWrapper wrapper) throws Throwable {
        init(myPackageName, wrapper.getJavaFactoryImpl());
        add("public class $javaFactoryImpl extends " + FactoryImpl.class.getCanonicalName() + " implements $javaFactory {");
        add("    public $javaFactoryImpl(" + ExtentImpl.class.getCanonicalName() + " extent, " + cmof.Package.class.getCanonicalName() + " path) {");
        add("        super(extent, path);");
        add("    }");
        print(wrapper);
	}
	
	public void addType(cmof.UmlClass umlClass) throws Throwable {
        add("    public " + getFullQualifiedJavaIdentifier(umlClass) + " create" + getJavaIdentifier(umlClass) + "() {");
        add("        return (" + getFullQualifiedJavaIdentifier(umlClass) + ") create(\"" + umlClass.getQualifiedName() + "\");");
        add("    }");
        print(null);
	}
	
	public void end(FactoryWrapper wrapper) throws Throwable {
		add("}");
        print(wrapper);
	}
}
