import Chat.ChatServers;
import java.io.IOException;


/**
 * \* Created with IntelliJ IDEA.
 * \* User: hxl
 * \* Date: 2019/1/28
 * \* Time: 19:31
 * \* Description:
 * \
 */
public class ChatMain {

    public static void main(String[] args){
        try {
            ChatServers chatServers = new ChatServers();
            System.out.println("开启服务器成功");
            chatServers.setHeartCheckTimeI(1000000);
            while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
