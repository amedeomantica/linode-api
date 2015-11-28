package com.notedpath.linode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.notedpath.linode.domain.LinodeDomain;
import com.notedpath.linode.domain.LinodeDomainResource;

public class LinodeDNSContext {
	
	private static Logger log = Logger.getLogger(LinodeDNSContext.class.getName());

	private Linode _linode;
	
	private Set<LinodeObject> _fetchedObjects;
	
	public LinodeDNSContext(String apiKey) {
		_linode = new Linode(apiKey, true);
		_fetchedObjects = new HashSet<>();
	}
	
	public Linode linode() {
		return _linode;
	}
	
	private List<LinodeDomain> fetchDomains(Integer domainId) {
		
		List<LinodeDomain> response = new ArrayList<>();
				
		try {
			LinodeResponse r;
			if(domainId!=null) {
				r = _linode.execute(API_ACTION.DOMAIN_LIST, "DomainID", String.valueOf(domainId));
			} else {
				r = _linode.execute(API_ACTION.DOMAIN_LIST);
			}
						
			JSONArray array = r.getDataAsJSONArray();
			
			for(int i = 0 ; i < array.length() ; i++) {
				LinodeDomain domain = loadDomain((JSONObject) array.get(i));
				if(!domain.isDeleted()) {
					response.add(domain);
				}
				
			}
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return response;
		
	}
	
	private LinodeDomain loadDomain(JSONObject linodeDomain) throws JSONException {
		
		Integer domainId = linodeDomain.getInt("DOMAINID");
		
		Optional<LinodeObject> existing = _fetchedObjects.stream().filter(r -> {
			return r._type.equals(LinodeObject.Type.DOMAIN);
		}).filter(d -> {
			return ((LinodeDomain)d).getDomainId().equals(domainId);
		}).findFirst();
		
		if(!existing.isPresent()) {
			LinodeDomain newDomain = new LinodeDomain(this);
			newDomain.setDomainId(domainId);
			newDomain.setDescription(linodeDomain.getString("DESCRIPTION"));
			newDomain.setType(linodeDomain.getString("TYPE"));
			newDomain.setStatus(getInteger(linodeDomain.get("STATUS")));
			newDomain.setSoaEmail(linodeDomain.getString("SOA_EMAIL"));
			newDomain.setDomain(linodeDomain.getString("DOMAIN"));
			newDomain.setRetrySec(getInteger(linodeDomain.get("RETRY_SEC")));
			newDomain.setMasterIPs(linodeDomain.getString("MASTER_IPS"));
			newDomain.setExpireSec(getInteger(linodeDomain.get("EXPIRE_SEC")));
			newDomain.setRefreshSec(getInteger(linodeDomain.get("REFRESH_SEC")));
			newDomain.setTtlSec(getInteger(linodeDomain.get("TTL_SEC")));
			newDomain.setPristine();
			_fetchedObjects.add(newDomain);
			return newDomain;
		} else {
			return (LinodeDomain)existing.get();
		}
		
	}
	
	public List<LinodeDomain> fetchAllDomains() {
		return fetchDomains(null);
	}
	
	public LinodeDomain fetchDomain(Integer domainId) {
		List<LinodeDomain> list = fetchDomains(domainId);
		if(list!=null && list.size() > 0) {
			return fetchDomains(domainId).get(0);
		}
		return null;
	}
	
	public LinodeDomain fetchDomain(String domainName) {
		List<LinodeDomain> list = fetchDomains(null);
		if(list!=null && list.size() > 0) {
			_fetchedObjects.stream().filter(r -> {
				return r._type.equals(LinodeObject.Type.DOMAIN) && ((LinodeDomain)r).getDomain().equals(domainName);
			});
		}
		return null;
	}
	
	public void deleteDomain(LinodeDomain aDomain) {
		aDomain.delete();
	}

	
	/**/
	
	public List<LinodeDomainResource> fetchDomainResources(LinodeDomain domain) {
		
		List<LinodeDomainResource> response = new ArrayList<>();
		
		try {
			LinodeResponse r;
			r = _linode.execute(API_ACTION.DOMAIN_RESOURCE_LIST, "DomainID", String.valueOf(domain.getDomainId()));
			JSONArray array = r.getDataAsJSONArray();
			
			for(int i = 0 ; i < array.length() ; i++) {
				LinodeDomainResource resource = loadDomainResource((JSONObject) array.get(i));
				response.add(resource);
				if(!resource.isDeleted()) {
					domain.addResource(resource);
				}		
			}
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return response;
		
		
	}
	
	private LinodeDomainResource loadDomainResource(JSONObject linodeDomainResource) throws JSONException {
		
		Integer resourceId = linodeDomainResource.getInt("RESOURCEID");
		
		Optional<LinodeObject> existing = _fetchedObjects.stream().filter(r -> {
			return r._type.equals(LinodeObject.Type.DOMAINRESOURCE);
		}).filter(d -> {
			return ((LinodeDomainResource)d).getResourceId().equals(resourceId);
		}).findFirst();
		
		if(!existing.isPresent()) {
			LinodeDomainResource newDomainResource = new LinodeDomainResource(this);
			
			newDomainResource.setDomainID(getInteger(linodeDomainResource.get("DOMAINID")));
			newDomainResource.setResourceId(getInteger(linodeDomainResource.get("RESOURCEID")));
			
			newDomainResource.setProtocol(linodeDomainResource.getString("PROTOCOL"));
			newDomainResource.setTtlSec(getInteger(linodeDomainResource.get("TTL_SEC")));
			newDomainResource.setPriority(getInteger(linodeDomainResource.get("PRIORITY")));
			newDomainResource.setResourceType(linodeDomainResource.getString("TYPE"));
			newDomainResource.setTarget(linodeDomainResource.getString("TARGET"));
			newDomainResource.setWeight(getInteger(linodeDomainResource.get("WEIGHT")));
			newDomainResource.setPort(getInteger(linodeDomainResource.get("PORT")));
			newDomainResource.setName(linodeDomainResource.getString("NAME"));
			newDomainResource.setPristine();
			_fetchedObjects.add(newDomainResource);
			
			return newDomainResource;
		} else {
			return (LinodeDomainResource)existing.get();
		}
		
	}
	
	private Integer getInteger(Object v) {
		Integer result = null;
		if(v!=null) {
			String s = String.valueOf(v);
			if(s!=null) {
				try {
					result = Integer.valueOf(s);
				} catch (NumberFormatException e) {
					//
				}
			}
		}
		
		return result;
	}
	
	public Set<LinodeObject> deletedObjects() {		
		return _fetchedObjects.stream().filter(r-> {
			return r.isDeleted();
		}).collect(Collectors.toSet());
	}
	
	public Set<LinodeObject> changedObjects() {		
		return _fetchedObjects.stream().filter(r-> {
			return r.hasChanges();
		}).collect(Collectors.toSet());
	}
	
	
	public void saveChanges() {
		
		List<LinodeRequest> requests = new ArrayList<>();
		
		Set<LinodeObject> deleteObjects = deletedObjects();
		
		deleteObjects.stream().forEach(r-> {
			LinodeRequest request = new LinodeRequest(r.deleteAction(), r.primaryKey(), String.valueOf(r.primaryKeyValue()));
			requests.add(request);
		});
		
		Set<LinodeObject> changedObjects = changedObjects();
		
		changedObjects.stream().forEach(r-> {
			List<String> params = new ArrayList<String>();
			
			params.add(r.primaryKey());
			params.add(String.valueOf(r.primaryKeyValue()));
			
			Set<String> changes = r.changedKeys();
			changes.stream().forEach(aKey-> {
				params.add(aKey);
				params.add(String.valueOf(r.values.get(aKey)));
			});
			LinodeRequest request = new LinodeRequest(r.updateAction(), params.toArray(new String[params.size()]));
			requests.add(request);
		});
		
		boolean error = false;
		try {
			
			Object response = linode().batchExecute(requests);
			if(response instanceof JSONObject) {
				JSONArray errors = ((JSONObject) response).getJSONArray("ERRORARRAY");
				if(errors.length() > 0) {
					error = true;
				}
			}
			if(response instanceof JSONArray) {
				JSONArray jsonArrayResponse = (JSONArray) response;
				for(int i = 0 ; i < jsonArrayResponse.length() ; i++) {
					JSONObject singleResponse = jsonArrayResponse.getJSONObject(i);
					JSONArray errors = singleResponse.getJSONArray("ERRORARRAY");
					if(errors.length() > 0) {
						error = true;
						break;
					}
				}
				
			}
			
			if(!error) {
				
				Set<LinodeObject> cleanedUpSet = new HashSet<>();
				_fetchedObjects.stream().forEach(r-> {
					if(!r.isDeleted()) {
						r.setPristine();
						cleanedUpSet.add(r);
					}			
				});
				_fetchedObjects = cleanedUpSet;
				
			} else {
				throw new SaveException("Failed to push changes to Linode");
			}
			
		} catch (IOException | JSONException | SaveException e) {
			e.printStackTrace();
		}
		

	
	}
	
	public void revert() {
		_fetchedObjects.stream().forEach(r-> {
			r.revert();
		});
	}
	
	public static class SaveException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = -4019950598998652926L;
		
		public SaveException(String message) {
			super(message);
		}
		
		
	}
	
}
