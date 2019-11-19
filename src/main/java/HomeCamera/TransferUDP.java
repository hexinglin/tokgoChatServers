package HomeCamera;

import Net.UDP.UdpReceiveClass;

import java.net.DatagramPacket;
import java.net.SocketException;

public class TransferUDP extends UdpReceiveClass {

    private DatagramPacket sendPacket=null;
    private int count = 0;


    public TransferUDP() throws SocketException {
        this.CreatReceiveMonitor(6000);

    }

    public void setSendPacket(DatagramPacket sendPacket) {
        this.sendPacket = sendPacket;
        this.count = 100;
    }

    @Override
    protected void Receive(DatagramPacket datagramPacket, byte[] bytes, int i) {
        System.out.println(i);
        if (this.sendPacket==null){
            if (this.count>0){
                this.Send(this.sendPacket,bytes,i);
                this.count--;
                System.out.println("转发");
            }
        }
    }
}
