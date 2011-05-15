package com.twitstreet.main;

import java.io.FileReader;
import java.util.Map.Entry;
import java.util.Properties;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.twitstreet.session.SessionMgr;
import com.twitstreet.session.SessionMgrImpl;
import com.twitstreet.twitter.TwitterAuth;
import com.twitstreet.twitter.TwitterAuthImpl;
import com.twitstreet.twitter.TwitterProxy;
import com.twitstreet.twitter.TwitterProxyImpl;

public class TSModule extends AbstractModule {

	@Override
	protected void configure() {
		bindPropertiesFile(System.getProperty("user.home")+"/.twitstreet/app.properties");
		bind(TwitterProxy.class).to(TwitterProxyImpl.class);
		bind(TwitterAuth.class).to(TwitterAuthImpl.class);
		bind(SessionMgr.class).to(SessionMgrImpl.class);
	}

	private void bindPropertiesFile(String propFileName) {
		try {
			Properties props = new Properties();
			props.load(new FileReader(propFileName));
			for(Entry<Object, Object> entry:props.entrySet()) {
				String name = (String) entry.getKey();
				String value = (String) entry.getValue();
				bindConstant().annotatedWith(Names.named(name)).to(value);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
