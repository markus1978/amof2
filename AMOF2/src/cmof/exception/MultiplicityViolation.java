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

package cmof.exception;

import hub.sam.mof.mofinstancemodel.MofStructureSlot;

/**
 * Denotes a violation of the multiplicity constraint of a slot. It is thrown
 * whenever the number of values in a slot does not fulfil the
 * mulitplicity given by the defining property of the slot.
 */
public class MultiplicityViolation extends ModelException {

	private static final long serialVersionUID = 1L;

	private final MofStructureSlot src;

	public MultiplicityViolation(MofStructureSlot src) {
		super("Multiplicity of property " + src.getDefiningFeature().getQualifiedName() +
				" on value " + src.getParentIdentity().getSecondaryIdentity() + " has been violated");
		this.src = src;
	}

	/**
	 * @return The slot that causes this vialation.
	 */
	public MofStructureSlot getSource() {
		return src;
	}
}
