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

package cmof.reflection;

import cmof.*;
import cmof.common.ReflectiveCollection;
import cmof.exception.*;

public interface Extent {

    public ReflectiveCollection<? extends Object> getObject();

    public ReflectiveCollection<? extends Object> objectsOfType(UmlClass type,
            boolean includeSubtypes);

    public ReflectiveCollection<? extends Link> linksOfType(Association type);

    public ReflectiveCollection<? extends Object> linkedObjects(
            Association association, Object endObject, boolean end1to2direction);

    public boolean linkExists(Association association, Object firstObject,
            Object secondObject);
    
    public ReflectiveCollection<? extends Object> outermostComposites();

    /**
     * Querys a object in the extent. This is not part of standard MOF 2.0.
     * Query has to be of format:
     * <pre>
     *     meta-classifier-id ":" element-id ("/" meta-classifier-id ":" element-id)*
     * </pre>
     * Example:
     * <pre>
     * cmofExtent.query("Package:cmof/Class:Property");
     * </pre>
     * @throws Exception when the query has a wrong format. 
     * @return the queried object or null.
     */
    public cmof.reflection.Object query(java.lang.String queryString) throws QueryParseException;
}
