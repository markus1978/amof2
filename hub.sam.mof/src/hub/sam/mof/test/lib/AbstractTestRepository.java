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

import concretecore.*;
import hub.sam.mof.Repository;
import junit.framework.TestCase;
import cmof.reflection.Extent;


public class AbstractTestRepository extends TestCase {

    public AbstractTestRepository(String name) {
        super(name);
    }
    
    protected Repository repository;
    protected Extent metaModelExtent;
    protected Extent modelExtent;
    protected static final String ABSTRACTIONS_PACKAGE_NAME = "Package:concretecore/Package:concreteabstractions/";

        
    @Override
	public void setUp() throws Exception {
        repository = Repository.getLocalRepository();
        repository.reset();
        modelExtent = repository.createExtent("testExtent");
        metaModelExtent = StaticConcreteLibrary.createModel();
    }
}
