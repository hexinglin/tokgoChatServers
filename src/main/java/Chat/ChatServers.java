package Chat;

import Dao.MesaageData;
import Net.TCP.ServersBaseClass;
import Utils.ToolUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import config.SystemConfig;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: hxl
 * \* Date: 2019/1/28
 * \* Time: 19:33
 * \* Description:
 * \
 */
public class ChatServers extends ServersBaseClass {

    private static Map<Socket,String> userLinkMap = new HashMap<>();
    private static Map<String,ChatUser> userInfroMap = new HashMap<>();

    public ChatServers() throws IOException {
        this.CreatServer(SystemConfig.getBaseConfig().getPort(),this);
    }

    public static Map<String, ChatUser> getUserInfroMap() {
        return userInfroMap;
    }


    public Boolean sendMessage(ChatUser chatUser,MesaageData mesaageData){
        String sendstr  ="{\"action\": \"chat\",\"sendaccount\": \""+mesaageData.getSenduser()+"\"" +
          ",\"receiveaccount\": \""+mesaageData.getReceiveuser()+"\",\"message\": \""+mesaageData.getMessage()+"\"}";
        byte[] sendbytes  = sendstr.getBytes();
        return Send(chatUser.getSocket(),sendbytes,sendbytes.length);
    }



    private void ChatInfromation(JSONObject mesageJson, String omesage) throws IOException {
        //数据格式：{"action": "chat","sendaccount": "string","receiveaccount": "string","message": "string"}
        String receiveAccount = mesageJson.getString("receiveaccount");
        ChatUser recChatUser = userInfroMap.get(receiveAccount);
        if (recChatUser!=null){
            byte[] sendbytes  = omesage.getBytes();
            if (!this.Send(recChatUser.getSocket(),sendbytes,sendbytes.length)){
                ToolUtil.print("数据发送失败,用户:"+recChatUser.getAccount());
            }
        }else {
            ToolUtil.print("数据发送失败,用户 "+receiveAccount+" 未连接");
        }
    }

    private void UserLogin(JSONObject mesageJson, Socket client) {
        //数据格式：{"action": "login","account": "string"}
        String account = mesageJson.getString("account");
        userLinkMap.put(client,account);
        userInfroMap.put(account,new ChatUser(account,client));
        ToolUtil.print("客户端连接:"+userLinkMap.get(client)+",当前连接数量："+(userInfroMap.size()));
    }





    protected void ClientExit(Socket socket) {
        String userstr = userLinkMap.get(socket);
        try {
            userInfroMap.remove(userLinkMap.get(socket));
            userLinkMap.remove(socket);
            ToolUtil.print("客户端退出:"+userstr+",当前连接数量："+(userInfroMap.size()));
        }catch (Exception e){
            ToolUtil.print("客户端退出异常,用户:"+userstr);
        }
    }

    public void Receive(Socket socket, byte[] bytes, int i) {
        String Data = new String(bytes,0,i);
        ToolUtil.print("receive message:" + Data);
        //解析json数据
        try {
            JSONObject mesageJson =  JSON.parseObject(Data);
            String action = mesageJson.getString("action");
            switch (action){
                case "login":
                    UserLogin(mesageJson,socket);
                    break;
                case "chat":
                    ChatInfromation(mesageJson,Data);
                    break;
                case "heartbeat":
                    this.Send(socket,bytes,i);
                    break;
            }
        }catch (Exception e){
            ToolUtil.print("message decode failure:" + Data);
        }
    }
}
