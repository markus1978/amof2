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

import static java.lang.System.out;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Vector;

import hub.sam.util.Clusterable;

public class XmiException extends Exception implements Clusterable<XmiException> {

	private static final long serialVersionUID = 1L;
	private Collection<XmiException> exceptions = new Vector<XmiException>();
	
	public XmiException() {
        super();
    }

    public XmiException(String message, Throwable cause) {
        super(message, cause);
    }

    public XmiException(String message) {
        super(message);
    }

    public XmiException(Throwable cause) {
        super(cause);
    }

	public Collection<XmiException> getExceptions() {
		return exceptions;
	} 
	
	public void add(XmiException ex) {
		exceptions.add(ex);
	}
}
