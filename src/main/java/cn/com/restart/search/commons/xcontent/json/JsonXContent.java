/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons JsonXContent.java 2012-3-29 15:15:08 l.xue.nong$$
 */


package cn.com.restart.search.commons.xcontent.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import cn.com.restart.search.commons.io.FastStringReader;
import cn.com.restart.search.commons.xcontent.XContent;
import cn.com.restart.search.commons.xcontent.XContentBuilder;
import cn.com.restart.search.commons.xcontent.XContentGenerator;
import cn.com.restart.search.commons.xcontent.XContentParser;
import cn.com.restart.search.commons.xcontent.XContentType;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;




/**
 * The Class JsonXContent.
 *
 * @author l.xue.nong
 */
public class JsonXContent implements XContent {

    
    /**
     * Content builder.
     *
     * @return the x content builder
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static XContentBuilder contentBuilder() throws IOException {
        return XContentBuilder.builder(jsonXContent);
    }

    
    /** The Constant jsonFactory. */
    private final static JsonFactory jsonFactory;
    
    
    /** The Constant jsonXContent. */
    public final static JsonXContent jsonXContent;

    static {
        jsonFactory = new JsonFactory();
        jsonFactory.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        jsonFactory.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, true);
        jsonXContent = new JsonXContent();
    }

    
    /**
     * Instantiates a new json x content.
     */
    private JsonXContent() {
    }

    
    /* (non-Javadoc)
     * @see cn.com.summall.search.commons.xcontent.XContent#type()
     */
    @Override
    public XContentType type() {
        return XContentType.JSON;
    }

    
    /* (non-Javadoc)
     * @see cn.com.summall.search.commons.xcontent.XContent#streamSeparator()
     */
    @Override
    public byte streamSeparator() {
        return '\n';
    }

    
    /* (non-Javadoc)
     * @see cn.com.summall.search.commons.xcontent.XContent#createGenerator(java.io.OutputStream)
     */
    @Override
    public XContentGenerator createGenerator(OutputStream os) throws IOException {
        return new JsonXContentGenerator(jsonFactory.createJsonGenerator(os, JsonEncoding.UTF8));
    }

    
    /* (non-Javadoc)
     * @see cn.com.summall.search.commons.xcontent.XContent#createGenerator(java.io.Writer)
     */
    @Override
    public XContentGenerator createGenerator(Writer writer) throws IOException {
        return new JsonXContentGenerator(jsonFactory.createJsonGenerator(writer));
    }

    
    /* (non-Javadoc)
     * @see cn.com.summall.search.commons.xcontent.XContent#createParser(java.lang.String)
     */
    @Override
    public XContentParser createParser(String content) throws IOException {
        return new JsonXContentParser(jsonFactory.createJsonParser(new FastStringReader(content)));
    }

    
    /* (non-Javadoc)
     * @see cn.com.summall.search.commons.xcontent.XContent#createParser(java.io.InputStream)
     */
    @Override
    public XContentParser createParser(InputStream is) throws IOException {
        return new JsonXContentParser(jsonFactory.createJsonParser(is));
    }

    
    /* (non-Javadoc)
     * @see cn.com.summall.search.commons.xcontent.XContent#createParser(byte[])
     */
    @Override
    public XContentParser createParser(byte[] data) throws IOException {
        return new JsonXContentParser(jsonFactory.createJsonParser(data));
    }

    
    /* (non-Javadoc)
     * @see cn.com.summall.search.commons.xcontent.XContent#createParser(byte[], int, int)
     */
    @Override
    public XContentParser createParser(byte[] data, int offset, int length) throws IOException {
        return new JsonXContentParser(jsonFactory.createJsonParser(data, offset, length));
    }

    
    /* (non-Javadoc)
     * @see cn.com.summall.search.commons.xcontent.XContent#createParser(java.io.Reader)
     */
    @Override
    public XContentParser createParser(Reader reader) throws IOException {
        return new JsonXContentParser(jsonFactory.createJsonParser(reader));
    }
}
