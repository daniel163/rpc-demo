package com.nickzhang.rpc.RpcExporter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * Created by nickzhang on 2017/3/31.
 * 发布者
 *
 */
public class ExporterTask implements Runnable {

    Socket client = null;

    public ExporterTask(Socket client){
        this.client = client;
    }


    public void run() {
        ObjectInputStream input = null;
        ObjectOutputStream output = null;

        try {
            input = new ObjectInputStream(client.getInputStream());
            String interfaceName = input.readUTF();
            Class<?> service = Class.forName(interfaceName);
            String methodName = input.readUTF();
            Class<?>[] parameterTypes = (Class<?>[])input.readObject();
            Object[] arguments = (Object[])input.readObject();
            Method method = service.getMethod(methodName,parameterTypes);
            Object result = method.invoke(service.newInstance(),arguments);
            output = new ObjectOutputStream(client.getOutputStream());
            output.writeObject(result);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (output != null){
                try {
                    output.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            if (input != null){
                try {
                    input.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            if (client != null){
                try {
                    client.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

    }
}
