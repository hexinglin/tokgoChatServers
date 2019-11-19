import Net.TCP.ClientBaseClass;
import Net.UDP.UdpReceiveClass;
import Net.UDP.UdpSendClass;
import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class test extends ClientBaseClass {


    public static void main(String[] args){
        try {
            new test();
            while (true);
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public test() throws Exception{
        this.Connect("hecao.pw",20001,this);
        byte[] dd = "action".getBytes("gbk");
        this.Send(dd,dd.length);
//        this.Send(dd,dd.length,8000, InetAddress.getByName("118.199.210.122"));
        Map<String,Object> k = new HashMap<>();
        k.put("mod","homeCamera");
        k.put("action","feedback");
        k.put("identity","viviter");
        k.put("NO",0);
        byte[] se= JSON.toJSONString(k).getBytes("gbk");
        UdpSendClass udpSendClass = new UdpSendClass(6000);

        udpSendClass.SendData(se,0,se.length,20001, InetAddress.getByName("hecao.pw"));

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        this.Send(se,se.length,8000, InetAddress.getByName("118.199.210.122"));
////        this.Send(se.getBytes("gbk"),se.length(),8000, InetAddress.getByName("192.168.1.102"));
//        System.out.println("fdgasdfadsds");
    }

    @Override
    protected void ServersBreakoff() {

    }


    @Override
    public void Receive(Socket socket, byte[] bytes, int i) {
        System.out.println(i);
    }
}
