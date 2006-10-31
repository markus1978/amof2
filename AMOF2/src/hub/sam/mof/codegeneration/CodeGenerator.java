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

import java.io.*;
import java.util.regex.*;
import java.lang.reflect.*;

public class CodeGenerator {
    private int indentWidth = 4;
    private StringBuffer code = new StringBuffer();
    private Pattern fieldPattern = Pattern.compile("\\$[a-zA-Z0-9_]+");
    protected PrintStream out;
    
    public CodeGenerator(OutputStream out) {
        this.out = new PrintStream(out);
    }
    
    public void add(int depth, String line) {
        code.append(getIndent(depth));
		code.append(line);
		code.append("\n");
    }
    
    public void add(String line) {
        add(0, line);
    }
    
    public void print(Object data) throws Throwable {
        String formatedCode = formatCode(data);
        out.println(formatedCode);
        code = new StringBuffer();
    }
    
    private String getIndent(int depth) {
        StringBuffer indent = new StringBuffer();
        for (int i = 0; i < depth; i++) {
            for (int ii = 0; ii < indentWidth; ii++) {
                indent.append(' ');
            }
        }
        return indent.toString();
    }
	
    private String formatCode(Object data) throws Throwable {
		String codeToFormat = code.toString();
        Matcher matcher = fieldPattern.matcher(codeToFormat);
		StringBuffer formatedCode = new StringBuffer(codeToFormat.length()*2);
        while(matcher.find()) {
            String fieldName = codeToFormat.substring(matcher.start()+1, matcher.end());
            
			StringBuffer methodName = new StringBuffer();
            methodName.append("get"); 
			methodName.append(fieldName.toUpperCase().charAt(0));
			methodName.append(fieldName.substring(1, fieldName.length()));
            
			Method method = data.getClass().getMethod(methodName.toString(), new Class[]{});
            Object objectValue = null;
            try {
                objectValue = method.invoke(data, new Object[]{});
            } catch (InvocationTargetException ex) {
                throw ex.getTargetException();
            }
            if (objectValue == null) {
                throw new NullPointerException("the property " + fieldName + " is null");
            }
            String value = objectValue.toString();
           
			formatedCode.append(codeToFormat.substring(0, matcher.start()));
			formatedCode.append(value);
			codeToFormat = codeToFormat.substring(matcher.end(), codeToFormat.length());
            matcher = fieldPattern.matcher(codeToFormat);
        }
		formatedCode.append(codeToFormat);
		return formatedCode.toString();
    }
    
    public void setIndentWidth(int indentWidth) {
        this.indentWidth = indentWidth;
    }
    
    private class TestBean {
        String testValue = "Hello";
        public String getTestValue() {
            return testValue;
        }
    }
    
    public static final void main(String args[]) throws Throwable {
        // test
        CodeGenerator cg = new CodeGenerator(System.out);
        cg.add(1, "$testValue World");
        cg.print(cg.new TestBean());
    }
}
