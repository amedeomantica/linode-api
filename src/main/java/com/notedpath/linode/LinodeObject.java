package com.notedpath.linode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public abstract class LinodeObject {
		
	public static enum Type {
		DOMAIN, DOMAINRESOURCE
	}
	
	protected HashMap<String, Object> values;
	protected HashMap<String, Object> pristine;
	
	private Set<String> _changedKeys;
	
	private boolean _deleted;
	
	protected Type _type;
	
	protected LinodeDNSContext _context;
	
	public abstract API_ACTION deleteAction();
	public abstract API_ACTION updateAction();
	public abstract API_ACTION createAction();
	public abstract String primaryKey();
	public abstract Integer primaryKeyValue();
	
	public LinodeObject(LinodeDNSContext context) {
		_context = context;
		_deleted = false;
		_changedKeys = new HashSet<>();
		values = new HashMap<String, Object>();
	}
	
	public Type type() {
		return _type;
	}
	
	public void setPristine() {
		pristine = new HashMap<>();
		values.keySet().stream().forEach(r -> {
			pristine.put(r,values.get(r));
		});
	}
	
	public void revert() {
		_deleted = false;
		pristine.keySet().stream().forEach(r -> {
			values.put(r,pristine.get(r));
		});
	}
	
	public void delete() {
		_deleted = true;
	}

	
	public boolean isDeleted() {
		return _deleted;
	}
	
	private void _checkChanges() {
		_changedKeys = new HashSet<>();
		values.keySet().forEach(r-> {
			if(values.get(r)!=null) {
				if(!(values.get(r).equals(pristine.get(r))) ) {
					_changedKeys.add(r);
				}
			} else {
				if(pristine.get(r)!=null) {
					_changedKeys.add(r);
				}
			}
			
		});
	}
	
	public Set<String> changedKeys() {
		_checkChanges();
		return _changedKeys;
	}
	
	public boolean hasChanges() {
		if(!isDeleted()) {
			if(changedKeys().size() > 0) {
				return true;
			}
		}	
		return false;
	}
	
}
