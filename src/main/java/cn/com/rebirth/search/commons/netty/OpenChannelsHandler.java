/*
 * Copyright (c) 2005-2012 www.china-cti.com All rights reserved
 * Info:rebirth-search-commons OpenChannelsHandler.java 2012-7-6 10:23:46 l.xue.nong$$
 */


package cn.com.rebirth.search.commons.netty;

import java.util.Set;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelState;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ChannelUpstreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.rebirth.commons.concurrent.ConcurrentCollections;
import cn.com.rebirth.search.commons.metrics.CounterMetric;


/**
 * The Class OpenChannelsHandler.
 *
 * @author l.xue.nong
 */
@ChannelHandler.Sharable
public class OpenChannelsHandler implements ChannelUpstreamHandler {

	
	/** The open channels. */
	final Set<Channel> openChannels = ConcurrentCollections.newConcurrentSet();

	
	/** The open channels metric. */
	final CounterMetric openChannelsMetric = new CounterMetric();

	
	/** The total channels metric. */
	final CounterMetric totalChannelsMetric = new CounterMetric();

	
	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(getClass());

	
	/**
	 * Instantiates a new open channels handler.
	 */
	public OpenChannelsHandler() {
		super();
	}

	
	/** The remover. */
	final ChannelFutureListener remover = new ChannelFutureListener() {
		public void operationComplete(ChannelFuture future) throws Exception {
			boolean removed = openChannels.remove(future.getChannel());
			if (removed) {
				openChannelsMetric.dec();
			}
			if (logger.isTraceEnabled()) {
				logger.trace("channel closed: {}", future.getChannel());
			}
		}
	};

	
	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.ChannelUpstreamHandler#handleUpstream(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelEvent)
	 */
	@Override
	public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
		if (e instanceof ChannelStateEvent) {
			ChannelStateEvent evt = (ChannelStateEvent) e;
			
			if (evt.getState() == ChannelState.OPEN && Boolean.TRUE.equals(evt.getValue())) {
				if (logger.isTraceEnabled()) {
					logger.trace("channel opened: {}", ctx.getChannel());
				}
				boolean added = openChannels.add(ctx.getChannel());
				if (added) {
					openChannelsMetric.inc();
					totalChannelsMetric.inc();
					ctx.getChannel().getCloseFuture().addListener(remover);
				}
			}
		}
		ctx.sendUpstream(e);
	}

	
	/**
	 * Number of open channels.
	 *
	 * @return the long
	 */
	public long numberOfOpenChannels() {
		return openChannelsMetric.count();
	}

	
	/**
	 * Total channels.
	 *
	 * @return the long
	 */
	public long totalChannels() {
		return totalChannelsMetric.count();
	}

	
	/**
	 * Close.
	 */
	public void close() {
		for (Channel channel : openChannels) {
			channel.close().awaitUninterruptibly();
		}
	}
}
