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

package hub.sam.mof.test.lib;

import hub.sam.mof.Repository;
import cmof.Element;
import cmof.Package;
import cmof.PackageableElement;
import cmof.Property;
import cmof.UmlClass;
import cmof.cmofFactory;
import cmof.reflection.Extent;

public class GenerateConcreteLibrary  {
	
	
	private cmofFactory factory = null;
	private Repository repository;
	
	public GenerateConcreteLibrary() throws Exception {
		repository = Repository.getLocalRepository();
	}
	
	public void generate() throws Exception {
		Extent cmofExtent = repository.getExtent(Repository.CMOF_EXTENT_NAME);
		Package cmofPackage = (Package)cmofExtent.query("Package:cmof"); 
		
		Extent extent= repository.createExtent("concreteLibrary");
		repository.loadXmiIntoExtent(extent, cmofPackage, "resources/models/user/core.xml");
		
		factory = (cmofFactory)repository.createFactory(extent, cmofPackage);
		Package core = (Package)extent.query("Package:core");
		browsePackage(core, null);
		repository.generateCode(extent, "resources/test/generated-src", false);
		repository.generateStaticModel(extent,"concretecore.StaticConcreteLibrary", "resources/test/generated-src");
		repository.writeExtentToXmi("resources/models/work/concretecore.xml", cmofPackage, extent);
	}
	
	private void browsePackage(Package abstractPackage, Package nestingConcretePackage) {
		Package concretePackage = factory.createPackage();
		// TODO first letter of Name in upper case
		concretePackage.setName("concrete" + abstractPackage.getName());
		if (nestingConcretePackage != null) {
			concretePackage.setNestingPackage(nestingConcretePackage);
		}
		for (PackageableElement abstractClass: abstractPackage.getPackagedElement()) {
			if (abstractClass instanceof UmlClass) {
				UmlClass concreteClass = factory.createUmlClass();
				concreteClass.setName(abstractClass.getName());
				concreteClass.setPackage(concretePackage);
				concreteClass.getSuperClass().add(abstractClass);
				for (Element member: ((UmlClass)abstractClass).getMember()) {
					if (member instanceof Property) {
						Property abstractAttribute = (Property)member;
						if (abstractAttribute.isDerivedUnion()) {
							Property concreteAttribute = factory.createProperty();
							concreteAttribute.setName(abstractAttribute.getName());
							concreteAttribute.setLower(abstractAttribute.getLower());
							concreteAttribute.setUpper(abstractAttribute.getUpper());
							concreteAttribute.setIsOrdered(abstractAttribute.isOrdered());
							concreteAttribute.setIsUnique(abstractAttribute.isUnique());
							concreteAttribute.setType(abstractAttribute.getType());
							concreteAttribute.setIsDerivedUnion(false);
							if (abstractAttribute.getOpposite() != null) {
								concreteAttribute.setOpposite(abstractAttribute.getOpposite());							
							}
							concreteAttribute.getRedefinedProperty().add(abstractAttribute);
							concreteClass.getOwnedAttribute().add(concreteAttribute);
						}
					}
				}
			}
		}
		for (Package nestedPackage: abstractPackage.getNestedPackage()) {
			browsePackage(nestedPackage, concretePackage);
		}
	}
	
	public static void main(String[] args) throws Exception {
		new GenerateConcreteLibrary().generate();
	}	
}
