package HomeCamera;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class PicHttpServer implements HttpHandler{
    private final  HttpServer server;
    private  byte[] picByte;
    private  int  len;

    public PicHttpServer() throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(6000), 0);
        this.server.createContext("/getpic", this);
        this.server.start();
        this.picByte=new byte[1];
        this.len =1;
    }

    public  void setPicByte(@org.jetbrains.annotations.NotNull byte[] picByte,int len) {
        this.picByte = picByte.clone();
        this.len = len;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    httpExchange.sendResponseHeaders(200,0);
                    OutputStream os = httpExchange.getResponseBody();
                    os.write(picByte,0,len);
                    os.close();
                }catch (IOException ie) {
                } catch (Exception e) {
                }
            }
        }).start();
    }

}