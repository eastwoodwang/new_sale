package com.ns.warlock.service.impl;

import com.ns.warlock.util.SettingUtils;
import freemarker.template.TemplateModelException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import com.ns.warlock.service.CacheService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;

@Service("cacheServiceImpl")
public class CacheServiceImpl implements CacheService {

    @Resource(name = "ehCacheManager")
    private CacheManager cacheManager;
    @Resource(name = "messageSource")
    private ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource;
    @Resource(name = "freeMarkerConfigurer")
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Override
    public String getDiskStorePath() {
        return null;
    }

    @Override
    public int getCacheSize() {
        return 0;
    }

    @CacheEvict(value = { "setting", "authorization"}, allEntries = true)
    public void clear() {
        reloadableResourceBundleMessageSource.clearCache();
        try {
            freeMarkerConfigurer.getConfiguration().setSharedVariable("setting", SettingUtils.get());
        } catch (TemplateModelException e) {
            e.printStackTrace();
        }
        freeMarkerConfigurer.getConfiguration().clearTemplateCache();
    }
}
