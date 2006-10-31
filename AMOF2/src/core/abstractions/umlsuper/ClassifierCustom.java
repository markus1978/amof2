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

package core.abstractions.umlsuper;

import hub.sam.mof.util.SetImpl;
import cmof.common.ReflectiveCollection;
import core.abstractions.namespaces.NamedElement;

public class ClassifierCustom extends ClassifierDlg {

   /**
    * <b>parents</b>, multiplicity=(0,*)
    * 
    * The query parents() gives all of the immediate ancestors of a generalized Classifier.
    */
   @Override
   public cmof.common.ReflectiveCollection<? extends core.abstractions.umlsuper.Classifier> parents() {
	   return self.getGeneral();
   }

   /**
    * <b>allParents</b>, multiplicity=(0,*)
    * 
    * The query allParents() gives all of the direct and indirect ancestors of a generalized Classifier.
    */
   @Override
   public cmof.common.ReflectiveCollection<? extends core.abstractions.umlsuper.Classifier> allParents() {
	   ReflectiveCollection<Classifier> allParents = new SetImpl<Classifier>();
	   allParents.addAll(self.parents());
	   for (Classifier general : self.parents()) {
		   allParents.addAll(general.allParents());
	   }
	   return allParents;
   }

   /**
    * <b>inheritableMembers</b>, multiplicity=(0,*)
    * 
    * The query inheritableMembers() gives all of the members of a classifier that may be inherited in one of its descendants,
    * subject to whatever visibility restrictions apply.
    */
   @Override
   public cmof.common.ReflectiveCollection<? extends core.abstractions.namespaces.NamedElement> inheritableMembers(core.abstractions.umlsuper.Classifier c) {
	   if (c.allParents().contains(self)) {
		ReflectiveCollection<NamedElement> inheritableMembers = new SetImpl<NamedElement>();
		for (NamedElement n : getMember()) {
			if (self.hasVisibilityOf(n)) {
				inheritableMembers.add(n);
			}
		}
		return inheritableMembers;
	   }
	   return null;
   }

   /**
    * <b>hasVisibilityOf</b>, multiplicity=(1,1)
    * 
    * The query hasVisibilityOf() determines whether a named element is visible in the classifier. By default all are visible. It is
    * only called when the argument is something owned by a parent.
    */
   @Override
   public boolean hasVisibilityOf(core.abstractions.namespaces.NamedElement n) {
	   return true;
   }

   /**
    * <b>maySpecializeType</b>, multiplicity=(1,1)
    * 
    * The query maySpecializeType() determines whether this classifier may have a generalization relationship to classifiers of
    * the specified type. By default a classifier may specialize classifiers of the same or a more general type. It is intended to be
    * redefined by classifiers that have different specialization constraints.
    */
   @Override
   public boolean maySpecializeType(core.abstractions.umlsuper.Classifier c) {
	   return self.	getClass().isAssignableFrom(c.getClass());
   }

}
   