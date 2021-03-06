package Chat;

import Net.TCP.ServersBaseClass;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

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

    private Integer port = 1315;
    private static Map<Socket,String> userLinkMap = new HashMap<>();
    private static Map<String,ChatUser> userInfroMap = new HashMap<>();

    public ChatServers() throws IOException {
        this.CreatServer(port,this);
        this.SetUTF8Mode();
    }

    private void ChatInfromation(JSONObject mesageJson, String omesage) throws IOException {
        //数据格式：{"action": "chat","sendaccount": "string","receiveaccount": "string","message": "string"}
        String receiveAccount = mesageJson.getString("receiveaccount");
        ChatUser recChatUser = userInfroMap.get(receiveAccount);
        if (recChatUser!=null){
            if (!this.Send(recChatUser.getSocket(),omesage)){
                System.out.println("数据发送失败,用户:"+recChatUser.getAccount());
            }
        }else {
            System.out.println("数据发送失败,用户 "+receiveAccount+" 未连接");
        }
    }

    private void UserLogin(JSONObject mesageJson, Socket client) {
        //数据格式：{"action": "login","account": "string"}
        String account = mesageJson.getString("account");
        userLinkMap.put(client,account);
        userInfroMap.put(account,new ChatUser(account,client));
        System.out.println("客户端连接:"+userLinkMap.get(socket)+",当前连接数量："+(userInfroMap.size()));
    }





    protected void ClientExit(Socket socket) {
        try {
            userInfroMap.remove(userLinkMap.get(socket));
            userLinkMap.remove(socket);
            System.out.println("客户端退出:"+userLinkMap.get(socket)+",当前连接数量："+(userInfroMap.size()));
        }catch (Exception e){
            System.out.println("客户端退出异常,用户:"+userLinkMap.get(socket));
        }
    }

    @Override
    public void Receive(Socket client, String Data) {
        System.out.println("receive message:" + Data);
        //解析json数据
        try {
            JSONObject mesageJson =  JSON.parseObject(Data);
            String action = mesageJson.getString("action");
            switch (action){
                case "login":
                    UserLogin(mesageJson,client);
                    break;
                case "chat":
                    ChatInfromation(mesageJson,Data);
                    break;
            }
        }catch (Exception e){
            System.out.println("message decode failure:" + Data);
        }

    }

    public void Receive(Socket socket, byte[] bytes, int i) {

    }
}
