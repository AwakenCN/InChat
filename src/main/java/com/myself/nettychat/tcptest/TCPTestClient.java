package com.myself.nettychat.tcptest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * 先去同包下CRC16myself下执行获取数据，数据格式有规定
 * TCP端测试模拟，建议放置在另一个项目
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 19:40 2018\9\20 0020
 */
public class TCPTestClient {

    public static void main(String[] args) throws IOException {
        //10万测试
        for (int i = 0;i<100000;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        runtest();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
            sleep(100);
        }
    }

    private static void runtest() throws IOException{
        //客户端请求与本机在18866端口建立TCP连接
        Socket client = new Socket("127.0.0.1", 8092);
        client.setSoTimeout(10000);
        //获取键盘输入
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        //获取Socket的输出流，用来发送数据到服务端
        PrintStream out = new PrintStream(client.getOutputStream());
        //获取Socket的输入流，用来接收从服务端发送过来的数据
        BufferedReader buf =  new BufferedReader(new InputStreamReader(client.getInputStream()));
        boolean flag = true;
        int i = 1;
        while(flag){
            //if (i == 1){
            //帧头+ID+数据类型+24把锁状态+crc校验+帧尾
            String str = "test";
            //发送数据到服务端
            out.println(str);
            if("bye".equals(str)){
                flag = false;
            }else{
                try{
                    //从服务器端接收数据有个时间限制（系统自设，也可以自己设置），超过了这个时间，便会抛出该异常
                    String echo = buf.readLine();
                    System.out.println(echo);
                }catch(SocketTimeoutException e){
                    System.out.println("Time out, No response");
                }
            }
            sleep(5000);
        }
        input.close();
        if(client != null){
            //如果构造函数建立起了连接，则关闭套接字，如果没有建立起连接，自然不用关闭
            client.close(); //只关闭socket，其关联的输入输出流也会被关闭
        }
    }

    private static void sleep(Integer time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
