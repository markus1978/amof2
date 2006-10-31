package hub.sam.util;

import hub.sam.mof.util.AssertionException;

import java.util.*;

import cmof.reflection.Extent;

public class Identity {
	private static final boolean ejb = false;
	
	private final Object id;		
	private Identity baseId = null;
	private Identity parentId = null;
	private Identity basedIds = null;
	
	private Map<Object, Identity> childIds = null;
	private static Map<Object, Object> externalIds = new HashMap<Object, Object>();
				
	public Identity(Object id) {
		if (ejb) {
			childIds = new HashMap<Object, Identity>();
		}
		if (id != null) {
			if (id instanceof Identity) {
				this.id = ((Identity)id).getId();
			} else {
				this.id = getIdForExternalId(id);
			}
		} else {
			this.id = Unique.getUnique();
		}
	}
	
	private static Object getIdForExternalId(Object externalId) {
		Object id = externalIds.get(externalId);
		if (id == null) {
			id = Unique.getUnique();
			externalIds.put(externalId, id);
		}
		return id;
	}
	
	public Identity() {
		this(null);
	}
	
	public final Identity getSecondaryIdentity() {
		return basedIds;
	}
	
	public final void setPrimaryIdentity(Identity base) {
		setPrimaryIdentity(base, false);
	}
			
	public final void setPrimaryIdentity(Identity base, boolean first) {
		if (base != null) {
			this.baseId = base;
			// only the first secondaryIdentity for base is attached to base
			if (base.basedIds == null) {					
				base.basedIds = this;
			} else {
				if (!first) {								
					base.basedIds = this;
				}
			}
		}
	}
	
	public final Identity getParentIdentity() {
		return parentId;
	}
	
	public final void setParentIdentity(Identity parent) {
		if (parent != null) {
			if (baseId != null) {
				baseId.setParentIdentity(parent);
			} else {
				if (parent.parentId == this) {
					throw new AssertionException();
				}
				this.parentId = parent;
				if (ejb) {
					parent.childIds.put(id, this);
				}
			}
		}
	}
	
	public final List<Object> getFullId() {
		if (baseId != null) {
			return baseId.getFullId();
		} else {
			List<Object> fullId = null;			
			if (parentId == null) {
				fullId = new ArrayList<Object>(1);
				fullId.add(id);
			} else {
				fullId = new ArrayList<Object>(5);										
				fullId.addAll(parentId.getFullId());
				fullId.add(id);
			}			
			return fullId;
		}
	}
	
	public Object getId() {
		if (baseId != null) {
			return baseId.getId();
		} else {
			return id;
		}
	}
		
	public final boolean idEquals(Identity e) {
		return e.getFullId().equals(e);
	}		
	
	public final Identity resolveId(Object id) {
		Identity result = null;
		if (ejb) {
			result = childIds.get(id);
		}
		if (result == null) {
			Identity secondary = basedIds;
			result = secondary.resolveId(id);
			if (result != null) {
				return result;
			}			
		}
		return result;
	}
	
	private Identity resolveFullIdWOFirst(List<Object> fullId) {
		Identity next = resolveId(fullId.get(0));
		if (fullId.size() == 1) {
			return next;
		} else {
			return next.resolveFullIdWOFirst(fullId.subList(1,fullId.size()));
		}
	}
	
	protected Identity resolveFirstId(Object id) {
		if (getId().equals(id)) {
			return this;
		} else {
			return null;
		}
	}
	
	public Identity resolveFullId(List<Object> fullId) {
		Identity firstMatch = resolveFirstId(fullId.get(0));
		if (firstMatch == null) {
			return null;
		} else {				
			if (fullId.size() > 1) {
				return firstMatch.resolveFullIdWOFirst(fullId.subList(1,fullId.size()));
			} else {
				return firstMatch;
			}
		}	
	}
}
