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

import hub.sam.mof.codegeneration.wrapper.UmlClassWrapper;
import hub.sam.mof.remote.LocalObjectImpl;
import hub.sam.mof.remote.RemoteObjectImpl;

public class ObjectLocalProxyImplementationGenerator extends
        ObjectProxyImplementationGenerator {

    public ObjectLocalProxyImplementationGenerator(StreamFactory streamFactory, String classNameExtension) {
        super(streamFactory, classNameExtension);
    }

    @Override
    protected void addClassSignature(UmlClassWrapper umlClass) throws Throwable {
        add("public class " + getClassName(umlClass) + " extends " + LocalObjectImpl.class.getName() + " $implements");
    }

    @Override
    protected void addGeneralClassBodyCode(UmlClassWrapper umlClass) throws Throwable {
        add("public " + getClassName(umlClass) + "(" + RemoteObjectImpl.class.getName() + " remote) {");
        add("    super(remote);");
        add("}");
        //if (CodeGenerationConfiguration.getActualConfig().isGenerateOcl()) {
        //   add("public $oclModelElement ocl$name() {");
        //    add("    return null; // no ocl support for remote access");
        //    add("}");
        //}
    }

    @Override
    protected void addGeneralClassBodyCodeForParent(UmlClassWrapper umlClass) throws Throwable {
        //if (CodeGenerationConfiguration.getActualConfig().isGenerateOcl()) {
        //    add("public $oclModelElement ocl$name() {");
        //    add("    return null; // no ocl support for remote access");
        //    add("}");
        //}
    }
}