package HomeCamera;

import Net.TCP.ServersBaseClass;
import Net.UDP.UdpReceiveClass;

import java.net.DatagramPacket;
import java.net.Socket;
import java.net.SocketException;

public class TransferUDP extends ServersBaseClass {

    private Socket sendPacket=null;
    private int count = 0;


    public TransferUDP() throws Exception {
        this.CreatServer(6000,this);

    }

    @Override
    protected void ClientExit(Socket socket) {

    }

    @Override
    public void Receive(Socket socket, byte[] bytes, int i) {
        System.out.println(i);
        if (this.sendPacket!=null){
            if (this.count>0){
                this.Send(this.sendPacket,bytes,i);
                this.count--;
                System.out.println("转发");
            }
        }
        if (i == 6){
            sendPacket = socket;
            this.count =100;
        }
    }
}
