package com.twitstreet.task;

import java.util.List;

import org.apache.log4j.Logger;

import twitter4j.Twitter;
import twitter4j.TwitterException;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.twitstreet.config.ConfigMgr;
import com.twitstreet.db.data.Stock;
import com.twitstreet.db.data.User;
import com.twitstreet.market.StockMgr;
import com.twitstreet.session.UserMgr;
import com.twitstreet.twitter.TwitterProxy;
import com.twitstreet.twitter.TwitterProxyFactory;

@Singleton
public class StockUpdateTask implements Runnable {
	private static final long TEN_MINUTES = 10 * 60 * 1000;
	@Inject
	StockMgr stockMgr;
	@Inject
	ConfigMgr configMgr;
	@Inject
	UserMgr userMgr;
	@Inject
	TwitterProxyFactory twitterProxyFactory = null;
	private static Logger logger = Logger.getLogger(StockUpdateTask.class);

	@Override
	public void run() {

		while (true) {
			long startTime = System.currentTimeMillis();
			List<Stock> stockList = stockMgr.getUpdateRequiredStocks();

			for (Stock stock : stockList) {
				User user = userMgr.random();
				TwitterProxy twitterProxy = user == null ? null
						: twitterProxyFactory.create(user.getOauthToken(),
								user.getOauthTokenSecret());
				twitter4j.User twUser = null;

				try {
					twUser = twitterProxy.getTwUser(stock.getId());
				} catch (Exception ex) {
				}
				if (twUser != null) {
					stockMgr.updateTwitterData(stock.getId(), twUser
							.getFollowersCount(), twUser.getProfileImageURL()
							.toExternalForm(), twUser.getScreenName());
				}

			}

			long endTime = System.currentTimeMillis();
			long diff = endTime - startTime;

			if (diff < TEN_MINUTES) {
				try {
					Thread.sleep(TEN_MINUTES - diff);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}