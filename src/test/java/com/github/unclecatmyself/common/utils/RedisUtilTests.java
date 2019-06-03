package com.github.unclecatmyself.common.utils;

import org.junit.Assert;
import org.junit.Test;

public class RedisUtilTests {

    @Test
    public void testString2MD5() {
        Assert.assertEquals("d41d8cd98f00b204e9800998ecf8427e",
                RedisUtil.string2MD5(""));
        Assert.assertEquals("202cb962ac59075b964b07152d234b70",
                RedisUtil.string2MD5("123"));
        Assert.assertEquals("acbd18db4cc2f85cedef654fccc4a4d8",
                RedisUtil.string2MD5("foo"));
        Assert.assertEquals("8a6551f0a3e52a89dbf095f3ea1fe59f",
                RedisUtil.string2MD5("£$%^&*"));
    }

    @Test
    public void testConvertMD5() {
        Assert.assertEquals("", RedisUtil.convertMD5(""));
        Assert.assertEquals("EFG", RedisUtil.convertMD5("123"));
        Assert.assertEquals("\u0012\u001B\u001B", RedisUtil.convertMD5("foo"));
        Assert.assertEquals("PQ*R^", RedisUtil.convertMD5("$%^&*"));
    }

    @Test
    public void testConvertMD52() {
        Assert.assertEquals("\u001A\u0001\u0018\u0018R\u001A\u0001\u0018" +
                "\u0018Q=\u001A7\u001C\u0015\u0000",
                RedisUtil.convertMD5(null, null));
        Assert.assertEquals("\u0012\u001B\u001BR\u001A\u0001\u0018\u0018Q=" +
                "\u001A7\u001C\u0015\u0000", RedisUtil.convertMD5("foo", null));
        Assert.assertEquals("\u001A\u0001\u0018\u0018R\u0012\u001B\u001BQ=" +
                "\u001A7\u001C\u0015\u0000", RedisUtil.convertMD5(null, "foo"));
        Assert.assertEquals("RQ=\u001A7\u001C\u0015\u0000",
                RedisUtil.convertMD5("", ""));
        Assert.assertEquals("\u0012\u001B\u001BR\u0012\u001B\u001BQ=\u001A7" +
                "\u001C\u0015\u0000", RedisUtil.convertMD5("foo", "foo"));
        Assert.assertEquals("EF×PQGR\u0012\u001B\u001BQ=\u001A7\u001C\u0015" +
                "\u0000", RedisUtil.convertMD5("12£$%3", "foo"));
    }

    @Test
    public void testGetAddress() {
        Assert.assertEquals("", RedisUtil.getAddress(""));
        Assert.assertEquals("foobarbaz", RedisUtil.getAddress("foobarbaz"));
        Assert.assertEquals("foo", RedisUtil.getAddress("foo&bar&baz"));
    }
}
