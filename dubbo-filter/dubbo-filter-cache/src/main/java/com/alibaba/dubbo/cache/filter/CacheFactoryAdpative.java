package com.alibaba.dubbo.cache.filter;

import com.alibaba.dubbo.cache.Cache;
import com.alibaba.dubbo.cache.CacheFactory;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;

/**
 * fixed bug.
 */
public class CacheFactoryAdpative implements CacheFactory {

	public Cache getCache(URL url) {
		if (url == null)
			throw new IllegalArgumentException("url == null");
		String extName = url.getMethodParameter(url.getParameter("method"), "cache");
		if (extName == null) {
			extName = url.getParameter("cache", "lru");
		}
		if (extName == null)
			throw new IllegalStateException("Fail to get extension(CacheFactory) name from url(" + url.toString() + ") use keys([cache])");
		CacheFactory extension = (CacheFactory) ExtensionLoader.getExtensionLoader(CacheFactory.class).getExtension(extName);
		return extension.getCache(url);
	}
}
