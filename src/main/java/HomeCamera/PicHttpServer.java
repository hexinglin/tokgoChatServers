package HomeCamera;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Map;

public class PicHttpServer{
    private  HttpServer server;
    private static byte[] picByte;
    private  static int  len;

    public PicHttpServer(int port) throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(port), 0);
        this.server.createContext("/getpic", new PicHandler());
        this.server.start();
        picByte=new byte[1];
        len =1;
    }

    public  void setPicByte(@org.jetbrains.annotations.NotNull byte[] picByte,int len) {
        PicHttpServer.picByte = picByte.clone();
        PicHttpServer.len = len;
    }

    static class PicHandler implements HttpHandler{
        @Override
        public void handle(HttpExchange exchange) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        exchange.sendResponseHeaders(200,0);
                        exchange.getResponseHeaders().add("Content-Type","image/jpeg");
                        OutputStream os = exchange.getResponseBody();
                        os.write(picByte,0,len);
                        os.close();
                    }catch (IOException ie) {
                        ie.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }


}