package org.jml.myeis.services;

import java.util.*;

import org.jml.myeis.domain.DomainObject;

/**
 * Created by jeremiahlumontod on 4/23/17.
 */
public abstract class AbstractMapService {
    protected Map<Integer, DomainObject> domainMap;

    public AbstractMapService() {
        domainMap = new HashMap<>();
        loadDomainObjects();
    }

    public List<DomainObject> listAll() {
        return new ArrayList<>(domainMap.values());
    }

    public DomainObject getById(Integer id) {
        return domainMap.get(id);
    }

    public DomainObject saveOrUpdate(DomainObject domainObject) {
        if(domainObject!=null) {
            if(domainObject.getId()==null) {
                domainObject.setId(getNextKey());
            }
            domainMap.put(domainObject.getId(),domainObject);
            return domainObject;
        }else{
            System.out.println("Error in saveOrUpdate");
            return null;
        }
    }

    public void delete(Integer id) {
        domainMap.remove(id);
    }

    private Integer getNextKey() {
        if(domainMap==null || domainMap.size()<1) {
            return 1;
        }
        return Collections.max(domainMap.keySet()) + 1;
    }

    protected abstract void loadDomainObjects();
}
