/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons NetworkExceptionHelper.java 2012-7-6 10:23:41 l.xue.nong$$
 */
package cn.com.rebirth.search.commons.transport;

import java.net.ConnectException;
import java.nio.channels.ClosedChannelException;


/**
 * The Class NetworkExceptionHelper.
 *
 * @author l.xue.nong
 */
public class NetworkExceptionHelper {

	
	/**
	 * Checks if is connect exception.
	 *
	 * @param e the e
	 * @return true, if is connect exception
	 */
	public static boolean isConnectException(Throwable e) {
		if (e instanceof ConnectException) {
			return true;
		}
		return false;
	}

	
	/**
	 * Checks if is close connection exception.
	 *
	 * @param e the e
	 * @return true, if is close connection exception
	 */
	public static boolean isCloseConnectionException(Throwable e) {
		if (e instanceof ClosedChannelException) {
			return true;
		}
		if (e.getMessage() != null) {
			
			if (e.getMessage().contains("Connection reset by peer")) {
				return true;
			}
			if (e.getMessage().contains("connection was aborted")) {
				return true;
			}
			if (e.getMessage().contains("forcibly closed")) {
				return true;
			}
			if (e.getMessage().contains("Broken pipe")) {
				return true;
			}
			if (e.getMessage().contains("Connection timed out")) {
				return true;
			}
		}
		return false;
	}
}
