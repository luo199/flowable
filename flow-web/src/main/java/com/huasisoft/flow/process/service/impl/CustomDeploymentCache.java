package com.huasisoft.flow.process.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.flowable.common.engine.impl.persistence.deploy.DeploymentCache;
import org.flowable.engine.impl.persistence.deploy.ProcessDefinitionCacheEntry;
import org.springframework.stereotype.Component;

@Component
public class CustomDeploymentCache implements DeploymentCache<ProcessDefinitionCacheEntry> {
   /* @Autowired
    private CacheManager cacheManager;*/
    
    public static final Map<String,ProcessDefinitionCacheEntry> caches = new ConcurrentHashMap<>();

    @Override
    public ProcessDefinitionCacheEntry get(String s) {
        return caches.get(s);
    }

    @Override
    public boolean contains(String s) {
        boolean flag = false;
        if (caches.get(s) != null) {
            flag = true;
        }
        return flag;
    }

    @Override
    public void add(String s, ProcessDefinitionCacheEntry processDefinitionCacheEntry) {
        caches.put(s, processDefinitionCacheEntry);
    }

    @Override
    public void remove(String s) {
        caches.remove(s);
    }

    @Override
    public void clear() {
        caches.clear();
    }
}