package com.nickzhang.rpc.RpcTest;

import com.nickzhang.rpc.EchoService;
import com.nickzhang.rpc.EchoServiceImpl;
import com.nickzhang.rpc.RpcExporter.RpcExporter;
import com.nickzhang.rpc.RpcImporter.RpcImporter;

import java.net.InetSocketAddress;

/**
 * Created by nickzhang on 2017/3/31.
 */
public class RpcTest {

    public static void main(String[] args) throws Exception{
        new Thread(new Runnable() {
            public void run() {
                try {
                    RpcExporter.exporter("localhost",8088);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();


        RpcImporter<EchoService> importer = new RpcImporter<EchoService>();
        EchoService echo = importer.importer(EchoServiceImpl.class , new InetSocketAddress("localhost" , 8088));
        System.out.println(echo.echo("调用远程rpc接口 ."));

    }
}
