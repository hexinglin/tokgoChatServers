/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HomeCamera;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import config.SystemConfig;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * 原理：https://www.cnblogs.com/GO-NO-1/p/7241556.html
 * @author TOKGO
 */
public class TransferServer extends Thread {
    private static final int MAXBUF = 65500;
    private final DatagramSocket dataSocket;
    private final Map<Integer,DatagramPacket> cameraHost;
    private final TransferUDP transferUDP;
    
    public TransferServer() throws Exception {
        this.dataSocket = new DatagramSocket(SystemConfig.getHomeCameraConfig().getPort());
        this.start();
        this.cameraHost = new HashMap<>();
        this.transferUDP = new TransferUDP();
    }


    /**
     * 解析的数据报
     * {
     *     "mod":"homeCamera",
     *     "action":"regiter",
     *     "identity":"camera",
     *     "NO":1,
     *     "data":{}
     * }
     * */
     private void DealReciveData(DatagramPacket receivePacket, JSONObject dataJson) throws Exception{

         if ("homeCamera".equals(dataJson.getString("mod"))){
             if ("regiter".equals(dataJson.getString("action"))){
                 Registration(receivePacket,dataJson);

             }else if ("feedback".equals(dataJson.getString("action"))){
                 //观看者的反馈包
                 DatagramPacket packet =  cameraHost.get(dataJson.getInteger("NO"));
                 if (packet == null){
                     sendNotFound(dataJson,receivePacket);
                 }else {
                     SendAddress(dataJson, packet, receivePacket);
                     this.transferUDP.setSendPacket(receivePacket);
                 }

             }else if ("regiterList".equals(dataJson.getString("action"))){
                 SendCameraList(dataJson,receivePacket);
             }
         }
    }

    private void SendCameraList(JSONObject dataJson, DatagramPacket receivePacket) {
//        for ()
    }

    private void SendAddress(JSONObject dataJson, DatagramPacket sendPacket, DatagramPacket contentPacket) throws IOException {
        JSONObject sendjson = new JSONObject();
        sendjson.put("ip", contentPacket.getAddress());
        sendjson.put("port", contentPacket.getPort());
        dataJson.put("data", sendjson);
        SendData(sendPacket.getAddress(), sendPacket.getPort(), dataJson);
    }

    private void sendNotFound(JSONObject dataJson, DatagramPacket sendPacket) throws IOException {
        JSONObject sendjson = new JSONObject();
        dataJson.put("data", sendjson);
        dataJson.put("msg", "not found");
        SendData(sendPacket.getAddress(), sendPacket.getPort(), dataJson);
    }

    private void Registration(DatagramPacket receivePacket, JSONObject dataJson) throws Exception {
        //相机注册

        if ("camera".equals(dataJson.getString("identity"))){
            cameraHost.put(dataJson.getInteger("NO"), receivePacket);
        }else {
            //观看者注册
            DatagramPacket packet =  cameraHost.get(dataJson.getInteger("NO"));
            if (packet == null){
                sendNotFound(dataJson, receivePacket);
            }else {
                SendAddress(dataJson, receivePacket, packet);
            }
        }
    }

     private void SendData(InetAddress ip,Integer port,Object data) throws IOException{
         String sendStr = JSON.toJSONString(data);
         byte[] sendByte = sendStr.getBytes("utf-8");
         DatagramPacket sendpacket = new DatagramPacket(sendByte,sendByte.length,ip,port);
         this.dataSocket.send(sendpacket); 
     }

     
    @Override
    public void run(){
        byte[] receiveByte = new byte[MAXBUF];


        while(true){
            try {
                DatagramPacket receivePacket = new DatagramPacket(receiveByte,MAXBUF);
                this.dataSocket.receive(receivePacket);
                if(receivePacket.getLength()>0){
                    JSONObject json = JSON.parseObject(new String(receivePacket.getData(),0,receivePacket.getLength()));
                    System.out.println(json.toJSONString());
                    DealReciveData(receivePacket,json);
                }
            } catch (Exception ex) {}
        }
    }
    
}
