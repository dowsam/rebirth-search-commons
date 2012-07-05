/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons SmileXContentParser.java 2012-3-29 15:15:16 l.xue.nong$$
 */
package cn.com.restart.search.commons.xcontent.smile;

import cn.com.restart.search.commons.xcontent.XContentType;
import cn.com.restart.search.commons.xcontent.json.JsonXContentParser;

import com.fasterxml.jackson.core.JsonParser;


/**
 * The Class SmileXContentParser.
 *
 * @author l.xue.nong
 */
public class SmileXContentParser extends JsonXContentParser {

    
    /**
     * Instantiates a new smile x content parser.
     *
     * @param parser the parser
     */
    public SmileXContentParser(JsonParser parser) {
        super(parser);
    }

    
    /* (non-Javadoc)
     * @see cn.com.summall.search.commons.xcontent.json.JsonXContentParser#contentType()
     */
    @Override
    public XContentType contentType() {
        return XContentType.SMILE;
    }
}
