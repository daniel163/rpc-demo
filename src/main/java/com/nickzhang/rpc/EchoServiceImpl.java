package com.nickzhang.rpc;

/**
 * Created by nickzhang on 2017/3/31.
 */
public class EchoServiceImpl implements EchoService {

    public String echo(String ping) {
        return ping + "---- >>> 接口被调用!";
    }
}
