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

package core.abstractions.redefinitions;

import core.abstractions.umlsuper.Classifier;

public class RedefinableElementCustom extends RedefinableElementDlg {
	/**
    * <b>isConsistentWith</b>, multiplicity=(1,1)
    * 
    * The query isConsistentWith() specifies, for any two RedefinableElements in a context in which redefinition is possible,
    * whether redefinition would be logically consistent. By default, this is false; this operation must be overridden for sub- 
    * classes of RedefinableElement to define the consistency conditions.
    */
   @Override
public boolean isConsistentWith(core.abstractions.redefinitions.RedefinableElement redefinee) {	  
	   return false;
   }

   /**
    * <b>isRedefinitionContextValid</b>, multiplicity=(1,1)
    * 
    * The query isRedefinitionContextValid() specifies whether the redefinition contexts of this RedefinableElement are prop-
    * erly related to the redefinition contexts of the specified RedefinableElement to allow this element to redefine the other
    * By default at least one of the redefinition contexts of this element must be a specialization of at least one of the redefini-
    * tion contexts of the specified element. 
    */
   @Override
public boolean isRedefinitionContextValid(core.abstractions.redefinitions.RedefinableElement redefinable) {
	   for (Classifier context : self.getRedefinitionContext()) {
		   for (Classifier redefContext : redefinable.getRedefinitionContext()) {
			   		if (context.allParents().contains(redefContext)) {
			   			return true;
			   }
		   }
	   }
	   return false;
   }

}
