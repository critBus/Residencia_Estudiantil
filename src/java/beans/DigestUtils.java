
package beans;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

class DigestUtils {
    private static final int STREAM_BUFFER_LENGTH = 1024;

    public DigestUtils() {
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokespecial java/lang/Object."<init>":()V
         * 4: return
         *  */
        // </editor-fold>
    }

    private static byte[] digest(MessageDigest digest, InputStream data) throws IOException {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: aload_1
         * 2: invokestatic  org/apache/commons/codec/digest/DigestUtils.updateDigest:(Ljava/security/MessageDigest;Ljava/io/InputStream;)Ljava/security/MessageDigest;
         * 5: invokevirtual java/security/MessageDigest.digest:()[B
         * 8: areturn
         *  */
        // </editor-fold>
    }

    public static MessageDigest getDigest(String algorithm) {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokestatic  java/security/MessageDigest.getInstance:(Ljava/lang/String;)Ljava/security/MessageDigest;
         * 4: areturn
         * 5: astore_1
         * 6: new           java/lang/IllegalArgumentException
         * 9: dup
         * 10: aload_1
         * 11: invokespecial java/lang/IllegalArgumentException."<init>":(Ljava/lang/Throwable;)V
         * 14: athrow
         * Exception table:
         * from    to  target type
         * 0     4     5   Class java/security/NoSuchAlgorithmException
         *  */
        // </editor-fold>
    }

    public static MessageDigest getMd2Digest() {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: ldc           MD2
         * 2: invokestatic  org/apache/commons/codec/digest/DigestUtils.getDigest:(Ljava/lang/String;)Ljava/security/MessageDigest;
         * 5: areturn
         *  */
        // </editor-fold>
    }

    public static MessageDigest getMd5Digest() {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: ldc           MD5
         * 2: invokestatic  org/apache/commons/codec/digest/DigestUtils.getDigest:(Ljava/lang/String;)Ljava/security/MessageDigest;
         * 5: areturn
         *  */
        // </editor-fold>
    }

    public static MessageDigest getSha1Digest() {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: ldc           SHA-1
         * 2: invokestatic  org/apache/commons/codec/digest/DigestUtils.getDigest:(Ljava/lang/String;)Ljava/security/MessageDigest;
         * 5: areturn
         *  */
        // </editor-fold>
    }

    public static MessageDigest getSha256Digest() {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: ldc           SHA-256
         * 2: invokestatic  org/apache/commons/codec/digest/DigestUtils.getDigest:(Ljava/lang/String;)Ljava/security/MessageDigest;
         * 5: areturn
         *  */
        // </editor-fold>
    }

    public static MessageDigest getSha384Digest() {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: ldc           SHA-384
         * 2: invokestatic  org/apache/commons/codec/digest/DigestUtils.getDigest:(Ljava/lang/String;)Ljava/security/MessageDigest;
         * 5: areturn
         *  */
        // </editor-fold>
    }

    public static MessageDigest getSha512Digest() {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: ldc           SHA-512
         * 2: invokestatic  org/apache/commons/codec/digest/DigestUtils.getDigest:(Ljava/lang/String;)Ljava/security/MessageDigest;
         * 5: areturn
         *  */
        // </editor-fold>
    }

    @Deprecated
    public static MessageDigest getShaDigest() {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: invokestatic  org/apache/commons/codec/digest/DigestUtils.getSha1Digest:()Ljava/security/MessageDigest;
         * 3: areturn
         *  */
        // </editor-fold>
    }

    public static byte[] md2(byte[] data) {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: invokestatic  org/apache/commons/codec/digest/DigestUtils.getMd2Digest:()Ljava/security/MessageDigest;
         * 3: aload_0
         * 4: invokevirtual java/security/MessageDigest.digest:([B)[B
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static byte[] md2(InputStream data) throws IOException {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: invokestatic  org/apache/commons/codec/digest/DigestUtils.getMd2Digest:()Ljava/security/MessageDigest;
         * 3: aload_0
         * 4: invokestatic  org/apache/commons/codec/digest/DigestUtils.digest:(Ljava/security/MessageDigest;Ljava/io/InputStream;)[B
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static byte[] md2(String data) {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokestatic  org/apache/commons/codec/binary/StringUtils.getBytesUtf8:(Ljava/lang/String;)[B
         * 4: invokestatic  org/apache/commons/codec/digest/DigestUtils.md2:([B)[B
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static String md2Hex(byte[] data) {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokestatic  org/apache/commons/codec/digest/DigestUtils.md2:([B)[B
         * 4: invokestatic  org/apache/commons/codec/binary/Hex.encodeHexString:([B)Ljava/lang/String;
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static String md2Hex(InputStream data) throws IOException {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokestatic  org/apache/commons/codec/digest/DigestUtils.md2:(Ljava/io/InputStream;)[B
         * 4: invokestatic  org/apache/commons/codec/binary/Hex.encodeHexString:([B)Ljava/lang/String;
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static String md2Hex(String data) {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokestatic  org/apache/commons/codec/digest/DigestUtils.md2:(Ljava/lang/String;)[B
         * 4: invokestatic  org/apache/commons/codec/binary/Hex.encodeHexString:([B)Ljava/lang/String;
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static byte[] md5(byte[] data) {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: invokestatic  org/apache/commons/codec/digest/DigestUtils.getMd5Digest:()Ljava/security/MessageDigest;
         * 3: aload_0
         * 4: invokevirtual java/security/MessageDigest.digest:([B)[B
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static byte[] md5(InputStream data) throws IOException {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: invokestatic  org/apache/commons/codec/digest/DigestUtils.getMd5Digest:()Ljava/security/MessageDigest;
         * 3: aload_0
         * 4: invokestatic  org/apache/commons/codec/digest/DigestUtils.digest:(Ljava/security/MessageDigest;Ljava/io/InputStream;)[B
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static byte[] md5(String data) {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokestatic  org/apache/commons/codec/binary/StringUtils.getBytesUtf8:(Ljava/lang/String;)[B
         * 4: invokestatic  org/apache/commons/codec/digest/DigestUtils.md5:([B)[B
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static String md5Hex(byte[] data) {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokestatic  org/apache/commons/codec/digest/DigestUtils.md5:([B)[B
         * 4: invokestatic  org/apache/commons/codec/binary/Hex.encodeHexString:([B)Ljava/lang/String;
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static String md5Hex(InputStream data) throws IOException {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokestatic  org/apache/commons/codec/digest/DigestUtils.md5:(Ljava/io/InputStream;)[B
         * 4: invokestatic  org/apache/commons/codec/binary/Hex.encodeHexString:([B)Ljava/lang/String;
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static String md5Hex(String data) {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokestatic  org/apache/commons/codec/digest/DigestUtils.md5:(Ljava/lang/String;)[B
         * 4: invokestatic  org/apache/commons/codec/binary/Hex.encodeHexString:([B)Ljava/lang/String;
         * 7: areturn
         *  */
        // </editor-fold>
    }

    @Deprecated
    public static byte[] sha(byte[] data) {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokestatic  org/apache/commons/codec/digest/DigestUtils.sha1:([B)[B
         * 4: areturn
         *  */
        // </editor-fold>
    }

    @Deprecated
    public static byte[] sha(InputStream data) throws IOException {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokestatic  org/apache/commons/codec/digest/DigestUtils.sha1:(Ljava/io/InputStream;)[B
         * 4: areturn
         *  */
        // </editor-fold>
    }

    @Deprecated
    public static byte[] sha(String data) {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokestatic  org/apache/commons/codec/digest/DigestUtils.sha1:(Ljava/lang/String;)[B
         * 4: areturn
         *  */
        // </editor-fold>
    }

    public static byte[] sha1(byte[] data) {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: invokestatic  org/apache/commons/codec/digest/DigestUtils.getSha1Digest:()Ljava/security/MessageDigest;
         * 3: aload_0
         * 4: invokevirtual java/security/MessageDigest.digest:([B)[B
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static byte[] sha1(InputStream data) throws IOException {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: invokestatic  org/apache/commons/codec/digest/DigestUtils.getSha1Digest:()Ljava/security/MessageDigest;
         * 3: aload_0
         * 4: invokestatic  org/apache/commons/codec/digest/DigestUtils.digest:(Ljava/security/MessageDigest;Ljava/io/InputStream;)[B
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static byte[] sha1(String data) {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokestatic  org/apache/commons/codec/binary/StringUtils.getBytesUtf8:(Ljava/lang/String;)[B
         * 4: invokestatic  org/apache/commons/codec/digest/DigestUtils.sha1:([B)[B
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static String sha1Hex(byte[] data) {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokestatic  org/apache/commons/codec/digest/DigestUtils.sha1:([B)[B
         * 4: invokestatic  org/apache/commons/codec/binary/Hex.encodeHexString:([B)Ljava/lang/String;
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static String sha1Hex(InputStream data) throws IOException {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokestatic  org/apache/commons/codec/digest/DigestUtils.sha1:(Ljava/io/InputStream;)[B
         * 4: invokestatic  org/apache/commons/codec/binary/Hex.encodeHexString:([B)Ljava/lang/String;
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static String sha1Hex(String data) {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokestatic  org/apache/commons/codec/digest/DigestUtils.sha1:(Ljava/lang/String;)[B
         * 4: invokestatic  org/apache/commons/codec/binary/Hex.encodeHexString:([B)Ljava/lang/String;
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static byte[] sha256(byte[] data) {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: invokestatic  org/apache/commons/codec/digest/DigestUtils.getSha256Digest:()Ljava/security/MessageDigest;
         * 3: aload_0
         * 4: invokevirtual java/security/MessageDigest.digest:([B)[B
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static byte[] sha256(InputStream data) throws IOException {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: invokestatic  org/apache/commons/codec/digest/DigestUtils.getSha256Digest:()Ljava/security/MessageDigest;
         * 3: aload_0
         * 4: invokestatic  org/apache/commons/codec/digest/DigestUtils.digest:(Ljava/security/MessageDigest;Ljava/io/InputStream;)[B
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static byte[] sha256(String data) {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokestatic  org/apache/commons/codec/binary/StringUtils.getBytesUtf8:(Ljava/lang/String;)[B
         * 4: invokestatic  org/apache/commons/codec/digest/DigestUtils.sha256:([B)[B
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static String sha256Hex(byte[] data) {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokestatic  org/apache/commons/codec/digest/DigestUtils.sha256:([B)[B
         * 4: invokestatic  org/apache/commons/codec/binary/Hex.encodeHexString:([B)Ljava/lang/String;
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static String sha256Hex(InputStream data) throws IOException {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokestatic  org/apache/commons/codec/digest/DigestUtils.sha256:(Ljava/io/InputStream;)[B
         * 4: invokestatic  org/apache/commons/codec/binary/Hex.encodeHexString:([B)Ljava/lang/String;
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static String sha256Hex(String data) {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokestatic  org/apache/commons/codec/digest/DigestUtils.sha256:(Ljava/lang/String;)[B
         * 4: invokestatic  org/apache/commons/codec/binary/Hex.encodeHexString:([B)Ljava/lang/String;
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static byte[] sha384(byte[] data) {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: invokestatic  org/apache/commons/codec/digest/DigestUtils.getSha384Digest:()Ljava/security/MessageDigest;
         * 3: aload_0
         * 4: invokevirtual java/security/MessageDigest.digest:([B)[B
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static byte[] sha384(InputStream data) throws IOException {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: invokestatic  org/apache/commons/codec/digest/DigestUtils.getSha384Digest:()Ljava/security/MessageDigest;
         * 3: aload_0
         * 4: invokestatic  org/apache/commons/codec/digest/DigestUtils.digest:(Ljava/security/MessageDigest;Ljava/io/InputStream;)[B
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static byte[] sha384(String data) {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokestatic  org/apache/commons/codec/binary/StringUtils.getBytesUtf8:(Ljava/lang/String;)[B
         * 4: invokestatic  org/apache/commons/codec/digest/DigestUtils.sha384:([B)[B
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static String sha384Hex(byte[] data) {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokestatic  org/apache/commons/codec/digest/DigestUtils.sha384:([B)[B
         * 4: invokestatic  org/apache/commons/codec/binary/Hex.encodeHexString:([B)Ljava/lang/String;
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static String sha384Hex(InputStream data) throws IOException {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokestatic  org/apache/commons/codec/digest/DigestUtils.sha384:(Ljava/io/InputStream;)[B
         * 4: invokestatic  org/apache/commons/codec/binary/Hex.encodeHexString:([B)Ljava/lang/String;
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static String sha384Hex(String data) {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokestatic  org/apache/commons/codec/digest/DigestUtils.sha384:(Ljava/lang/String;)[B
         * 4: invokestatic  org/apache/commons/codec/binary/Hex.encodeHexString:([B)Ljava/lang/String;
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static byte[] sha512(byte[] data) {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: invokestatic  org/apache/commons/codec/digest/DigestUtils.getSha512Digest:()Ljava/security/MessageDigest;
         * 3: aload_0
         * 4: invokevirtual java/security/MessageDigest.digest:([B)[B
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static byte[] sha512(InputStream data) throws IOException {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: invokestatic  org/apache/commons/codec/digest/DigestUtils.getSha512Digest:()Ljava/security/MessageDigest;
         * 3: aload_0
         * 4: invokestatic  org/apache/commons/codec/digest/DigestUtils.digest:(Ljava/security/MessageDigest;Ljava/io/InputStream;)[B
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static byte[] sha512(String data) {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokestatic  org/apache/commons/codec/binary/StringUtils.getBytesUtf8:(Ljava/lang/String;)[B
         * 4: invokestatic  org/apache/commons/codec/digest/DigestUtils.sha512:([B)[B
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static String sha512Hex(byte[] data) {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokestatic  org/apache/commons/codec/digest/DigestUtils.sha512:([B)[B
         * 4: invokestatic  org/apache/commons/codec/binary/Hex.encodeHexString:([B)Ljava/lang/String;
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static String sha512Hex(InputStream data) throws IOException {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokestatic  org/apache/commons/codec/digest/DigestUtils.sha512:(Ljava/io/InputStream;)[B
         * 4: invokestatic  org/apache/commons/codec/binary/Hex.encodeHexString:([B)Ljava/lang/String;
         * 7: areturn
         *  */
        // </editor-fold>
    }

    public static String sha512Hex(String data) {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokestatic  org/apache/commons/codec/digest/DigestUtils.sha512:(Ljava/lang/String;)[B
         * 4: invokestatic  org/apache/commons/codec/binary/Hex.encodeHexString:([B)Ljava/lang/String;
         * 7: areturn
         *  */
        // </editor-fold>
    }

    @Deprecated
    public static String shaHex(byte[] data) {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokestatic  org/apache/commons/codec/digest/DigestUtils.sha1Hex:([B)Ljava/lang/String;
         * 4: areturn
         *  */
        // </editor-fold>
    }

    @Deprecated
    public static String shaHex(InputStream data) throws IOException {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokestatic  org/apache/commons/codec/digest/DigestUtils.sha1Hex:(Ljava/io/InputStream;)Ljava/lang/String;
         * 4: areturn
         *  */
        // </editor-fold>
    }

    @Deprecated
    public static String shaHex(String data) {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: invokestatic  org/apache/commons/codec/digest/DigestUtils.sha1Hex:(Ljava/lang/String;)Ljava/lang/String;
         * 4: areturn
         *  */
        // </editor-fold>
    }

    public static MessageDigest updateDigest(MessageDigest messageDigest, byte[] valueToDigest) {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: aload_1
         * 2: invokevirtual java/security/MessageDigest.update:([B)V
         * 5: aload_0
         * 6: areturn
         *  */
        // </editor-fold>
    }

    public static MessageDigest updateDigest(MessageDigest digest, InputStream data) throws IOException {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: sipush        1024
         * 3: newarray       byte
         * 5: astore_2
         * 6: aload_1
         * 7: aload_2
         * 8: iconst_0
         * 9: sipush        1024
         * 12: invokevirtual java/io/InputStream.read:([BII)I
         * 15: istore_3
         * 16: iload_3
         * 17: iconst_m1
         * 18: if_icmple     41
         * 21: aload_0
         * 22: aload_2
         * 23: iconst_0
         * 24: iload_3
         * 25: invokevirtual java/security/MessageDigest.update:([BII)V
         * 28: aload_1
         * 29: aload_2
         * 30: iconst_0
         * 31: sipush        1024
         * 34: invokevirtual java/io/InputStream.read:([BII)I
         * 37: istore_3
         * 38: goto          16
         * 41: aload_0
         * 42: areturn
         *  */
        // </editor-fold>
    }

    public static MessageDigest updateDigest(MessageDigest messageDigest, String valueToDigest) {
        return null;
        // <editor-fold defaultstate="collapsed" desc="Compiled Code">
        /* 0: aload_0
         * 1: aload_1
         * 2: invokestatic  org/apache/commons/codec/binary/StringUtils.getBytesUtf8:(Ljava/lang/String;)[B
         * 5: invokevirtual java/security/MessageDigest.update:([B)V
         * 8: aload_0
         * 9: areturn
         *  */
        // </editor-fold>
    }
}
