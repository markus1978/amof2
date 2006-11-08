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

package hub.sam.mof.mofinstancemodel;

import cmof.Property;
import cmof.UmlClass;
import hub.sam.mof.instancemodel.ValueSpecification;

import java.util.Collection;
import java.util.HashSet;

public class UpdateGraphNode {

    private final ValueSpecification<UmlClass,Property,java.lang.Object> value;
    private UpdateGraphNode adjacentReason = null;
    private Collection<UpdateGraphNode> adjacentReasonings = new HashSet<UpdateGraphNode>();
    private final MofValueSpecificationList owner;
    private int position = -1;

    public UpdateGraphNode(ValueSpecification<UmlClass,Property,java.lang.Object> value, MofValueSpecificationList owner) {
        super();
        this.value = value;
        this.owner = owner;
    }

    public void setPosition(int position) {
    	this.position = position;
    }

    public int getPosition() {
    	return position;
    }

    /**
     * Sets the node that represents the reason for this node. Reasons are subsettings or associations between
     * the slot of the given node and the slot of this node.
     * @param node
     */
    public void setAdjacentReason(UpdateGraphNode node) {
        if (adjacentReason != null) {
            throw new RuntimeException("assert");
        }
        adjacentReason = node;
    }

    /**
     * Adds a reasoning. That are nodes that this node is a reason for. Reasons are subsettings or associations between
     * the slot this node and the slot of the given node.
     * @param node
     */
    public void addAjacentReasoning(UpdateGraphNode node) {
        if (node != null) {
            adjacentReasonings.add(node);
            node.setAdjacentReason(this);
        }
    }

    public UpdateGraphNode getAdjacentReason() {
        return adjacentReason;
    }

    public Collection<UpdateGraphNode> getAdjacentReasonings() {
        return adjacentReasonings;
    }

    @Override
	public boolean equals(Object o) {
        if (o instanceof ValueSpecification) {
            return o.equals(value);
        } else if (o instanceof UpdateGraphNode) {
            return o == this;
        } else {
            return false;
        }
    }

    public ValueSpecification<UmlClass, Property, java.lang.Object> getValue() {
        return value;
    }

    public void setAdjacentReasonings(Collection<UpdateGraphNode> adjacentReasonings) {
        this.adjacentReasonings = adjacentReasonings;
    }

    public boolean primaryAdd() {
        boolean result = owner.primaryAdd(this);
        for (UpdateGraphNode adjacent: adjacentReasonings) {
            adjacent.secondaryAdd();
        }
        return result;
    }

    public void primaryAdd(int index) {
        owner.primaryAdd(index, this);
        for (UpdateGraphNode adjacent: adjacentReasonings) {
            adjacent.secondaryAdd();
        }
    }

    public void primaryRemove() {
        owner.primaryRemove(this);
        UpdateGraphNode root = this;
        while (root.adjacentReason != null) {
            root = root.adjacentReason;
        }
        root.secondaryRemove(this);
    }

    public void primaryRemove(int index) {
        owner.primaryRemove(index, this);
        UpdateGraphNode root = this;
        while (root.adjacentReason != null) {
            root = root.adjacentReason;
        }
        root.secondaryRemove(this);
    }

    public void secondaryAdd() {
        owner.secondaryAdd(this);
        for (UpdateGraphNode adjacent: adjacentReasonings) {
            adjacent.secondaryAdd();
        }
    }

    public void secondaryRemove(UpdateGraphNode except) {
        if (except != this) {
            // was already removed with primary remove
            owner.secondaryRemove(this);
        }
        for (UpdateGraphNode adjacent: adjacentReasonings) {
            adjacent.secondaryRemove(except);
        }
    }
}
