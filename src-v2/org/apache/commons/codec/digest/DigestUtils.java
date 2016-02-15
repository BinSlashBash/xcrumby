/*
 * Decompiled with CFR 0_110.
 */
package org.apache.commons.codec.digest;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;

public class DigestUtils {
    private static final int STREAM_BUFFER_LENGTH = 1024;

    private static byte[] digest(MessageDigest messageDigest, InputStream inputStream) throws IOException {
        return DigestUtils.updateDigest(messageDigest, inputStream).digest();
    }

    public static MessageDigest getDigest(String object) {
        try {
            object = MessageDigest.getInstance((String)object);
            return object;
        }
        catch (NoSuchAlgorithmException var0_1) {
            throw new IllegalArgumentException(var0_1);
        }
    }

    public static MessageDigest getMd2Digest() {
        return DigestUtils.getDigest("MD2");
    }

    public static MessageDigest getMd5Digest() {
        return DigestUtils.getDigest("MD5");
    }

    public static MessageDigest getSha1Digest() {
        return DigestUtils.getDigest("SHA-1");
    }

    public static MessageDigest getSha256Digest() {
        return DigestUtils.getDigest("SHA-256");
    }

    public static MessageDigest getSha384Digest() {
        return DigestUtils.getDigest("SHA-384");
    }

    public static MessageDigest getSha512Digest() {
        return DigestUtils.getDigest("SHA-512");
    }

    @Deprecated
    public static MessageDigest getShaDigest() {
        return DigestUtils.getSha1Digest();
    }

    public static byte[] md2(InputStream inputStream) throws IOException {
        return DigestUtils.digest(DigestUtils.getMd2Digest(), inputStream);
    }

    public static byte[] md2(String string2) {
        return DigestUtils.md2(StringUtils.getBytesUtf8(string2));
    }

    public static byte[] md2(byte[] arrby) {
        return DigestUtils.getMd2Digest().digest(arrby);
    }

    public static String md2Hex(InputStream inputStream) throws IOException {
        return Hex.encodeHexString(DigestUtils.md2(inputStream));
    }

    public static String md2Hex(String string2) {
        return Hex.encodeHexString(DigestUtils.md2(string2));
    }

    public static String md2Hex(byte[] arrby) {
        return Hex.encodeHexString(DigestUtils.md2(arrby));
    }

    public static byte[] md5(InputStream inputStream) throws IOException {
        return DigestUtils.digest(DigestUtils.getMd5Digest(), inputStream);
    }

    public static byte[] md5(String string2) {
        return DigestUtils.md5(StringUtils.getBytesUtf8(string2));
    }

    public static byte[] md5(byte[] arrby) {
        return DigestUtils.getMd5Digest().digest(arrby);
    }

    public static String md5Hex(InputStream inputStream) throws IOException {
        return Hex.encodeHexString(DigestUtils.md5(inputStream));
    }

    public static String md5Hex(String string2) {
        return Hex.encodeHexString(DigestUtils.md5(string2));
    }

    public static String md5Hex(byte[] arrby) {
        return Hex.encodeHexString(DigestUtils.md5(arrby));
    }

    @Deprecated
    public static byte[] sha(InputStream inputStream) throws IOException {
        return DigestUtils.sha1(inputStream);
    }

    @Deprecated
    public static byte[] sha(String string2) {
        return DigestUtils.sha1(string2);
    }

    @Deprecated
    public static byte[] sha(byte[] arrby) {
        return DigestUtils.sha1(arrby);
    }

    public static byte[] sha1(InputStream inputStream) throws IOException {
        return DigestUtils.digest(DigestUtils.getSha1Digest(), inputStream);
    }

    public static byte[] sha1(String string2) {
        return DigestUtils.sha1(StringUtils.getBytesUtf8(string2));
    }

    public static byte[] sha1(byte[] arrby) {
        return DigestUtils.getSha1Digest().digest(arrby);
    }

    public static String sha1Hex(InputStream inputStream) throws IOException {
        return Hex.encodeHexString(DigestUtils.sha1(inputStream));
    }

    public static String sha1Hex(String string2) {
        return Hex.encodeHexString(DigestUtils.sha1(string2));
    }

    public static String sha1Hex(byte[] arrby) {
        return Hex.encodeHexString(DigestUtils.sha1(arrby));
    }

    public static byte[] sha256(InputStream inputStream) throws IOException {
        return DigestUtils.digest(DigestUtils.getSha256Digest(), inputStream);
    }

    public static byte[] sha256(String string2) {
        return DigestUtils.sha256(StringUtils.getBytesUtf8(string2));
    }

    public static byte[] sha256(byte[] arrby) {
        return DigestUtils.getSha256Digest().digest(arrby);
    }

    public static String sha256Hex(InputStream inputStream) throws IOException {
        return Hex.encodeHexString(DigestUtils.sha256(inputStream));
    }

    public static String sha256Hex(String string2) {
        return Hex.encodeHexString(DigestUtils.sha256(string2));
    }

    public static String sha256Hex(byte[] arrby) {
        return Hex.encodeHexString(DigestUtils.sha256(arrby));
    }

    public static byte[] sha384(InputStream inputStream) throws IOException {
        return DigestUtils.digest(DigestUtils.getSha384Digest(), inputStream);
    }

    public static byte[] sha384(String string2) {
        return DigestUtils.sha384(StringUtils.getBytesUtf8(string2));
    }

    public static byte[] sha384(byte[] arrby) {
        return DigestUtils.getSha384Digest().digest(arrby);
    }

    public static String sha384Hex(InputStream inputStream) throws IOException {
        return Hex.encodeHexString(DigestUtils.sha384(inputStream));
    }

    public static String sha384Hex(String string2) {
        return Hex.encodeHexString(DigestUtils.sha384(string2));
    }

    public static String sha384Hex(byte[] arrby) {
        return Hex.encodeHexString(DigestUtils.sha384(arrby));
    }

    public static byte[] sha512(InputStream inputStream) throws IOException {
        return DigestUtils.digest(DigestUtils.getSha512Digest(), inputStream);
    }

    public static byte[] sha512(String string2) {
        return DigestUtils.sha512(StringUtils.getBytesUtf8(string2));
    }

    public static byte[] sha512(byte[] arrby) {
        return DigestUtils.getSha512Digest().digest(arrby);
    }

    public static String sha512Hex(InputStream inputStream) throws IOException {
        return Hex.encodeHexString(DigestUtils.sha512(inputStream));
    }

    public static String sha512Hex(String string2) {
        return Hex.encodeHexString(DigestUtils.sha512(string2));
    }

    public static String sha512Hex(byte[] arrby) {
        return Hex.encodeHexString(DigestUtils.sha512(arrby));
    }

    @Deprecated
    public static String shaHex(InputStream inputStream) throws IOException {
        return DigestUtils.sha1Hex(inputStream);
    }

    @Deprecated
    public static String shaHex(String string2) {
        return DigestUtils.sha1Hex(string2);
    }

    @Deprecated
    public static String shaHex(byte[] arrby) {
        return DigestUtils.sha1Hex(arrby);
    }

    public static MessageDigest updateDigest(MessageDigest messageDigest, InputStream inputStream) throws IOException {
        byte[] arrby = new byte[1024];
        int n2 = inputStream.read(arrby, 0, 1024);
        while (n2 > -1) {
            messageDigest.update(arrby, 0, n2);
            n2 = inputStream.read(arrby, 0, 1024);
        }
        return messageDigest;
    }

    public static MessageDigest updateDigest(MessageDigest messageDigest, String string2) {
        messageDigest.update(StringUtils.getBytesUtf8(string2));
        return messageDigest;
    }

    public static MessageDigest updateDigest(MessageDigest messageDigest, byte[] arrby) {
        messageDigest.update(arrby);
        return messageDigest;
    }
}

