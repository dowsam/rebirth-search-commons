/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons KeepFrameDecoder.java 2012-7-6 10:23:52 l.xue.nong$$
 */

package cn.com.rebirth.search.commons.netty;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;


/**
 * The Class KeepFrameDecoder.
 *
 * @author l.xue.nong
 */
public class KeepFrameDecoder extends FrameDecoder {

	
	/** The Constant decoder. */
	public static final KeepFrameDecoder decoder = new KeepFrameDecoder();

	
	/* (non-Javadoc)
	 * @see org.jboss.netty.handler.codec.frame.FrameDecoder#decode(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.Channel, org.jboss.netty.buffer.ChannelBuffer)
	 */
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
		return null;
	}
}
