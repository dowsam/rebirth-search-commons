/*
 * Copyright (c) 2005-2012 www.summall.com.cn All rights reserved
 * Info:summall-search-commons Digest.java 2012-3-29 15:15:15 l.xue.nong$$
 */


package cn.com.restart.search.commons;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * The Class Digest.
 *
 * @author l.xue.nong
 */
public class Digest {

    
    /** The Constant STREAM_BUFFER_LENGTH. */
    private static final int STREAM_BUFFER_LENGTH = 1024 * 16;

    
    /**
     * Digest.
     *
     * @param digest the digest
     * @param data the data
     * @return the byte[]
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private static byte[] digest(MessageDigest digest, InputStream data) throws IOException {
        byte[] buffer = new byte[STREAM_BUFFER_LENGTH];
        int read = data.read(buffer, 0, STREAM_BUFFER_LENGTH);

        while (read > -1) {
            digest.update(buffer, 0, read);
            read = data.read(buffer, 0, STREAM_BUFFER_LENGTH);
        }

        return digest.digest();
    }

    
    /** The Constant Charset_UTF8. */
    private static final Charset Charset_UTF8 = Charset.forName("UTF8");

    
    /**
     * Gets the bytes utf8.
     *
     * @param data the data
     * @return the bytes utf8
     */
    private static byte[] getBytesUtf8(String data) {
        return data.getBytes(Charset_UTF8);
    }

    
    /**
     * Gets the digest.
     *
     * @param algorithm the algorithm
     * @return the digest
     */
    static MessageDigest getDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    
    /**
     * Gets the md5 digest.
     *
     * @return the md5 digest
     */
    public static MessageDigest getMd5Digest() {
        return getDigest("MD5");
    }

    
    /**
     * Gets the sha256 digest.
     *
     * @return the sha256 digest
     */
    private static MessageDigest getSha256Digest() {
        return getDigest("SHA-256");
    }

    
    /**
     * Gets the sha384 digest.
     *
     * @return the sha384 digest
     */
    private static MessageDigest getSha384Digest() {
        return getDigest("SHA-384");
    }

    
    /**
     * Gets the sha512 digest.
     *
     * @return the sha512 digest
     */
    private static MessageDigest getSha512Digest() {
        return getDigest("SHA-512");
    }

    
    /**
     * Gets the sha digest.
     *
     * @return the sha digest
     */
    private static MessageDigest getShaDigest() {
        return getDigest("SHA");
    }

    
    /**
     * Md5.
     *
     * @param data the data
     * @return the byte[]
     */
    public static byte[] md5(byte[] data) {
        return getMd5Digest().digest(data);
    }

    
    /**
     * Md5.
     *
     * @param data the data
     * @return the byte[]
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static byte[] md5(InputStream data) throws IOException {
        return digest(getMd5Digest(), data);
    }

    
    /**
     * Md5.
     *
     * @param data the data
     * @return the byte[]
     */
    public static byte[] md5(String data) {
        return md5(getBytesUtf8(data));
    }

    
    /**
     * Md5 hex.
     *
     * @param data the data
     * @return the string
     */
    public static String md5Hex(byte[] data) {
        return Hex.encodeHexString(md5(data));
    }

    
    /**
     * Md5 hex.
     *
     * @param data the data
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static String md5Hex(InputStream data) throws IOException {
        return Hex.encodeHexString(md5(data));
    }

    
    /**
     * Md5 hex.
     *
     * @param data the data
     * @return the string
     */
    public static String md5Hex(String data) {
        return Hex.encodeHexString(md5(data));
    }

    
    /** The Constant US_ASCII. */
    final static private Charset US_ASCII = Charset.forName("US-ASCII");

    
    /**
     * Md5 hex to byte array.
     *
     * @param md5Hex the md5 hex
     * @return the byte[]
     */
    public static byte[] md5HexToByteArray(String md5Hex) {
        return md5Hex.getBytes(US_ASCII);
    }

    
    /**
     * Md5 hex from byte array.
     *
     * @param data the data
     * @return the string
     */
    public static String md5HexFromByteArray(byte[] data) {
        return new String(data, 0, 32, US_ASCII);
    }

    
    /**
     * Sha.
     *
     * @param data the data
     * @return the byte[]
     */
    public static byte[] sha(byte[] data) {
        return getShaDigest().digest(data);
    }

    
    /**
     * Sha.
     *
     * @param data the data
     * @return the byte[]
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static byte[] sha(InputStream data) throws IOException {
        return digest(getShaDigest(), data);
    }

    
    /**
     * Sha.
     *
     * @param data the data
     * @return the byte[]
     */
    public static byte[] sha(String data) {
        return sha(getBytesUtf8(data));
    }

    
    /**
     * Sha256.
     *
     * @param data the data
     * @return the byte[]
     */
    public static byte[] sha256(byte[] data) {
        return getSha256Digest().digest(data);
    }

    
    /**
     * Sha256.
     *
     * @param data the data
     * @return the byte[]
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static byte[] sha256(InputStream data) throws IOException {
        return digest(getSha256Digest(), data);
    }

    
    /**
     * Sha256.
     *
     * @param data the data
     * @return the byte[]
     */
    public static byte[] sha256(String data) {
        return sha256(getBytesUtf8(data));
    }

    
    /**
     * Sha256 hex.
     *
     * @param data the data
     * @return the string
     */
    public static String sha256Hex(byte[] data) {
        return Hex.encodeHexString(sha256(data));
    }

    
    /**
     * Sha256 hex.
     *
     * @param data the data
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static String sha256Hex(InputStream data) throws IOException {
        return Hex.encodeHexString(sha256(data));
    }

    
    /**
     * Sha256 hex.
     *
     * @param data the data
     * @return the string
     */
    public static String sha256Hex(String data) {
        return Hex.encodeHexString(sha256(data));
    }

    
    /**
     * Sha384.
     *
     * @param data the data
     * @return the byte[]
     */
    public static byte[] sha384(byte[] data) {
        return getSha384Digest().digest(data);
    }

    
    /**
     * Sha384.
     *
     * @param data the data
     * @return the byte[]
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static byte[] sha384(InputStream data) throws IOException {
        return digest(getSha384Digest(), data);
    }

    
    /**
     * Sha384.
     *
     * @param data the data
     * @return the byte[]
     */
    public static byte[] sha384(String data) {
        return sha384(getBytesUtf8(data));
    }

    
    /**
     * Sha384 hex.
     *
     * @param data the data
     * @return the string
     */
    public static String sha384Hex(byte[] data) {
        return Hex.encodeHexString(sha384(data));
    }

    
    /**
     * Sha384 hex.
     *
     * @param data the data
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static String sha384Hex(InputStream data) throws IOException {
        return Hex.encodeHexString(sha384(data));
    }

    
    /**
     * Sha384 hex.
     *
     * @param data the data
     * @return the string
     */
    public static String sha384Hex(String data) {
        return Hex.encodeHexString(sha384(data));
    }

    
    /**
     * Sha512.
     *
     * @param data the data
     * @return the byte[]
     */
    public static byte[] sha512(byte[] data) {
        return getSha512Digest().digest(data);
    }

    
    /**
     * Sha512.
     *
     * @param data the data
     * @return the byte[]
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static byte[] sha512(InputStream data) throws IOException {
        return digest(getSha512Digest(), data);
    }

    
    /**
     * Sha512.
     *
     * @param data the data
     * @return the byte[]
     */
    public static byte[] sha512(String data) {
        return sha512(getBytesUtf8(data));
    }

    
    /**
     * Sha512 hex.
     *
     * @param data the data
     * @return the string
     */
    public static String sha512Hex(byte[] data) {
        return Hex.encodeHexString(sha512(data));
    }

    
    /**
     * Sha512 hex.
     *
     * @param data the data
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static String sha512Hex(InputStream data) throws IOException {
        return Hex.encodeHexString(sha512(data));
    }

    
    /**
     * Sha512 hex.
     *
     * @param data the data
     * @return the string
     */
    public static String sha512Hex(String data) {
        return Hex.encodeHexString(sha512(data));
    }

    
    /**
     * Sha hex.
     *
     * @param data the data
     * @return the string
     */
    public static String shaHex(byte[] data) {
        return Hex.encodeHexString(sha(data));
    }

    
    /**
     * Sha hex.
     *
     * @param data the data
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static String shaHex(InputStream data) throws IOException {
        return Hex.encodeHexString(sha(data));
    }

    
    /**
     * Sha hex.
     *
     * @param data the data
     * @return the string
     */
    public static String shaHex(String data) {
        return Hex.encodeHexString(sha(data));
    }

    
    /** The Constant NULL_DIGEST. */
    public static final NullDigest NULL_DIGEST = new NullDigest("null");

    
    /**
     * The Class NullDigest.
     *
     * @author l.xue.nong
     */
    private static final class NullDigest extends MessageDigest {

        
        /**
         * Instantiates a new null digest.
         *
         * @param algorithm the algorithm
         */
        private NullDigest(String algorithm) {
            super(algorithm);
        }

        
        /* (non-Javadoc)
         * @see java.security.MessageDigestSpi#engineUpdate(byte)
         */
        @Override
        protected void engineUpdate(byte input) {
        }

        
        /* (non-Javadoc)
         * @see java.security.MessageDigestSpi#engineUpdate(byte[], int, int)
         */
        @Override
        protected void engineUpdate(byte[] input, int offset, int len) {
        }

        
        /* (non-Javadoc)
         * @see java.security.MessageDigestSpi#engineDigest()
         */
        @Override
        protected byte[] engineDigest() {
            return null;
        }

        
        /* (non-Javadoc)
         * @see java.security.MessageDigestSpi#engineReset()
         */
        @Override
        protected void engineReset() {
        }
    }
}
