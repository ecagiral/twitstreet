package com.twitstreet.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.twitstreet.config.ConfigMgr;
import com.twitstreet.db.data.User;
import com.twitstreet.session.UserMgrImpl;

@SuppressWarnings("serial")
@Singleton
public class SigninServlet extends TwitStreetServlet {
	private static Logger logger = Logger.getLogger(SigninServlet.class);
	@Inject
	ConfigMgr configMgr;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
		setPageAttributes();
		response.setContentType("text/html");
		
		if (user == null) {
			Twitter twitter = new TwitterFactory().getInstance();
			try {
				StringBuffer callbackURL = request.getRequestURL();
				int index = callbackURL.lastIndexOf("/");
				callbackURL.replace(index, callbackURL.length(), "").append(
						"/callback");
				logger.debug("Callback url is: " + callbackURL.toString());
				twitter.setOAuthConsumer(configMgr.getConsumerKey(),
						configMgr.getConsumerSecret());
				logger.debug("Consumer Key: " + configMgr.getConsumerKey() + ", Consumer Secret: " + configMgr.getConsumerSecret());
				RequestToken requestToken = twitter
						.getOAuthRequestToken(callbackURL.toString());
				configMgr.setRequestToken(requestToken);
				response.sendRedirect(requestToken.getAuthenticationURL());
				logger.debug("Redirect sent to authentication URL: " + requestToken.getAuthenticationURL());

			} catch (TwitterException e) {
				throw new ServletException(e);
			}
		} else {
			response.sendRedirect(request.getContextPath() + "/");
		}
	}
}
