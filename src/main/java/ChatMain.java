import Chat.ChatServers;
import Message.SystemMessage;

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
            SystemMessage systemMessage = new SystemMessage(chatServers);
            System.out.println("开启系统消息监控成功");
            while (true){
                try {
                    Thread.sleep(100000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
