package com.notedpath.linode.domain;

import java.util.ArrayList;
import java.util.List;

import com.notedpath.linode.API_ACTION;
import com.notedpath.linode.LinodeDNSContext;
import com.notedpath.linode.LinodeObject;

/**
 * 
 * @author amedeomantica
 *
 */

public class LinodeDomain extends LinodeObject {
		
	public static interface Keys {
		
		public String DOMAINID = "DOMAINID";
		public String DESCRIPTION = "DESCRIPTION";
		public String TYPE = "TYPE";
		public String STATUS = "STATUS";
		public String SOA_EMAIL = "SOA_EMAIL";
		public String DOMAIN = "DOMAIN";
		public String RETRY_SEC = "RETRY_SEC";
		public String MASTER_IPS = "MASTER_IPS";
		public String EXPIRE_SEC = "EXPIRE_SEC";
		public String REFRESH_SEC = "REFRESH_SEC";
		public String TTL_SEC = "TTL_SEC";
		
	}

	
	private List<LinodeDomainResource> _domainResources;
	
	public LinodeDomain(LinodeDNSContext context) {
		super(context);
		this._type = Type.DOMAIN;
	}
	
	
	public Integer getDomainId() {
		return (Integer) values.get(Keys.DOMAINID);
	}
	public void setDomainId(Integer domainId) {
		values.put(Keys.DOMAINID, domainId);
	}
	
	public String getDescription() {
		return (String) values.get(Keys.DESCRIPTION);
	}
	public void setDescription(String description) {
		values.put(Keys.DESCRIPTION, description);
	}
	
	public String getType() {
		return (String) values.get(Keys.TYPE);
	}
	public void setType(String type) {
		values.put(Keys.TYPE, type);
	}
	
	public Integer getStatus() {
		return (Integer) values.get(Keys.STATUS);
	}
	public void setStatus(Integer status) {
		values.put(Keys.STATUS, status);
	}
	
	public String getSoaEmail() {
		return (String) values.get(Keys.SOA_EMAIL);
	}
	public void setSoaEmail(String soaEmail) {
		values.put(Keys.SOA_EMAIL, soaEmail);
	}
	
	public String getDomain() {
		return (String) values.get(Keys.DOMAIN);
	}
	public void setDomain(String domain) {
		values.put(Keys.DOMAIN, domain);
	}
	
	public Integer getRetrySec() {
		return (Integer) values.get(Keys.RETRY_SEC);
	}
	public void setRetrySec(Integer retrySec) {
		values.put(Keys.RETRY_SEC, retrySec);
	}
	
	public String getMasterIPs() {
		return (String) values.get(Keys.MASTER_IPS);
	}
	public void setMasterIPs(String masterIPs) {
		values.put(Keys.MASTER_IPS, masterIPs);
	}
	
	public Integer getExpireSec() {
		return (Integer) values.get(Keys.EXPIRE_SEC);
	}	
	public void setExpireSec(Integer expireSec) {
		values.put(Keys.EXPIRE_SEC, expireSec);
	}
	
	public Integer getRefreshSec() {
		return (Integer) values.get(Keys.REFRESH_SEC);
	}
	public void setRefreshSec(Integer refreshSec) {
		values.put(Keys.REFRESH_SEC, refreshSec);
	}
	
	public Integer getTtlSec() {
		return (Integer) values.get(Keys.TTL_SEC);
	}
	public void setTtlSec(Integer ttlSec) {
		values.put(Keys.TTL_SEC, ttlSec);
	}
	
	public void addResource(LinodeDomainResource aResource) {
		if(_domainResources == null) {
			_domainResources = new ArrayList<>();
		}
		_domainResources.add(aResource);
	}
	
	public List<LinodeDomainResource> resources() {
		if(_domainResources == null) {
			_context.fetchDomainResources(this);
		}
		return _domainResources;
	}


	@Override
	public API_ACTION deleteAction() {
		return API_ACTION.DOMAIN_DELETE;
	}


	@Override
	public String primaryKey() {
		return LinodeDomain.Keys.DOMAINID;
	}


	@Override
	public Integer primaryKeyValue() {
		return getDomainId();
	}


	@Override
	public API_ACTION updateAction() {
		return API_ACTION.DOMAIN_UPDATE;
	}


	@Override
	public API_ACTION createAction() {
		return API_ACTION.DOMAIN_CREATE;
	}


	

}
