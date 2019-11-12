import Chat.ChatServers;
import HomeCamera.TransferServer;
import Message.SystemMessage;
import Utils.ToolUtil;

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
        //  开启聊天服务器系统

        try {
            ChatServers chatServers = new ChatServers();
            ToolUtil.print("开启聊天服务器成功");
            SystemMessage systemMessage = new SystemMessage(chatServers);
            ToolUtil.print("开启系统消息监控成功");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //  开启家庭监控转发服务器系统
        try {
            TransferServer transferServer = new TransferServer();
            ToolUtil.print("开启家庭服务器成功");
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (true){
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
