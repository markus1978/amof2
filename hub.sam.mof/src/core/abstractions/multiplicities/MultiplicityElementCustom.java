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

package core.abstractions.multiplicities;

public class MultiplicityElementCustom extends MultiplicityElementDlg {
	 /**
     * <b>lowerBound</b>, multiplicity=(0,1)
     *
     * The query lowerBound() returns the lower bound of the multiplicity as an integer.
     */
    @Override
	public int lowerBound() {
    	return self.getLower();
    }

    /**
     * <b>upperBound</b>, multiplicity=(0,1)
     *
     *  The query upperBound() returns the upper bound of the multiplicity for a bounded multiplicity
     *   as an unlimited natural.
     */
    @Override
	public long upperBound() {
    	return self.getUpper();
    }

    /**
     * <b>isMultivalued</b>, multiplicity=(1,1)
     *
     * The query isMultivalued() checks whether this multiplicity has an upper bound greater than one.
     */
    @Override
	public boolean isMultivalued() {
    	return self.upperBound() >1 || self.upperBound() < 0;
    }

    /**
     * <b>includesCardinality</b>, multiplicity=(1,1)
     *
     * The query includesCardinality() checks whether the specified cardinality is valid for this multiplicity.
     */
    @Override
	public boolean includesCardinality(int C) {
    	return self.lowerBound() <= C && C <= self.upperBound();
    }

    /**
     * <b>includesMultiplicity</b>, multiplicity=(1,1)
     *
     * The query includesMultiplicity() checks whether this multiplicity includes all the cardinalities allowed by the specified
     * multiplicity.
     */
    @Override
	public boolean includesMultiplicity(core.abstractions.multiplicities.MultiplicityElement M) {
    	return self.lowerBound() <= M.lowerBound() && self.upperBound() >= M.upperBound();
    }


}
