package HomeCamera;

import Net.UDP.UdpReceiveClass;

import java.net.DatagramPacket;

public class TransferUDP  extends UdpReceiveClass {

    private final PicHttpServer picHttpServer;

    public TransferUDP(PicHttpServer picHttpServer) throws Exception {
        this.picHttpServer = picHttpServer;
        this.CreatReceiveMonitor(8000);
    }

    @Override
    protected void Receive(DatagramPacket datagramPacket, byte[] bytes, int i) {
        if (i>100){
            this.picHttpServer.setPicByte(bytes,i);
        }

    }
}
