package com.notedpath.linode.domain;

import java.util.logging.Logger;

import com.notedpath.linode.API_ACTION;
import com.notedpath.linode.LinodeDNSContext;
import com.notedpath.linode.LinodeObject;

public class LinodeDomainResource  extends LinodeObject {
		
	public static enum ResouceType {
		NS, MX, A, AAAA, CNAME, TXT, SRV
	}

	public static interface Keys {
		
		public String PROTOCOL = "PROTOCOL";
		public String TTL_SEC = "TTL_SEC";
		public String PRIORITY = "PRIORITY";
		public String TYPE = "TYPE";
		public String TARGET = "TARGET";
		public String WEIGHT = "WEIGHT";
		public String RESOURCEID = "RESOURCEID";
		public String PORT = "PORT";
		public String DOMAINID = "DOMAINID";
		public String NAME = "NAME";
		
	}
	
	public LinodeDomainResource(LinodeDNSContext context) {
		super(context);
		this._type = Type.DOMAINRESOURCE;
	}


	public String getProtocol() {
		return (String) values.get(Keys.PROTOCOL);
	}
	public void setProtocol(String protocol) {
		values.put(Keys.PROTOCOL, protocol);
	}
	
	public Integer getTtlSec() {
		return (Integer) values.get(Keys.TTL_SEC);
	}
	public void setTtlSec(Integer ttlSec) {
		values.put(Keys.TTL_SEC, ttlSec);
	}
	
	public Integer getPriority() {
		return (Integer) values.get(Keys.PRIORITY);
	}
	public void setPriority(Integer priority) {
		values.put(Keys.PRIORITY, priority);
	}
	
	public ResouceType getResourceType() {
		return (ResouceType) values.get(Keys.TYPE);
	}
	public void setResourceType(String rerourceType) {
		try {
			values.put(Keys.TYPE, ResouceType.valueOf(rerourceType));
		} catch (java.lang.IllegalArgumentException e) {
			System.err.println("Wrong resource type");
		}
	}
	public void setResourceType(ResouceType rerourceType) {
		values.put(Keys.TYPE, rerourceType);
	}
	
	public String getTarget() {
		return (String) values.get(Keys.TARGET);
	}
	public void setTarget(String target) {
		values.put(Keys.TARGET, target);
	}
	
	public Integer getWeight() {
		return (Integer) values.get(Keys.WEIGHT);
	}
	public void setWeight(Integer weight) {
		values.put(Keys.TTL_SEC, weight);
	}
	
	public Integer getResourceId() {
		return (Integer) values.get(Keys.RESOURCEID);
	}
	public void setResourceId(Integer resourceId) {
		values.put(Keys.RESOURCEID, resourceId);
	}
	
	public Integer getPort() {
		return (Integer) values.get(Keys.PORT);
	}
	public void setPort(Integer port) {
		values.put(Keys.PORT, port);
	}
	
	public Integer getDomainId() {
		return (Integer) values.get(Keys.DOMAINID);
	}
	public void setDomainID(Integer domainId) {
		values.put(Keys.DOMAINID, domainId);
	}
	
	public String getName() {
		return (String) values.get(Keys.NAME);
	}
	public void setName(String name) {
		values.put(Keys.NAME, name);
	}


	@Override
	public API_ACTION deleteAction() {
		return API_ACTION.DOMAIN_RESOURCE_DELETE;
	}


	@Override
	public String primaryKey() {
		return LinodeDomainResource.Keys.RESOURCEID;
	}


	@Override
	public Integer primaryKeyValue() {
		return getResourceId();
	}


	@Override
	public API_ACTION updateAction() {
		return API_ACTION.DOMAIN_RESOURCE_UPDATE;
	}


	@Override
	public API_ACTION createAction() {
		return API_ACTION.DOMAIN_RESOURCE_CREATE;
	}

	
}
