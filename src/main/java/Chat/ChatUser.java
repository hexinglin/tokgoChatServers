package Chat;

import java.net.Socket;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: hxl
 * \* Date: 2019/1/22
 * \* Time: 20:21
 * \* Description:
 * \
 */
public class ChatUser {
    private String account;
    private Socket socket;

    public ChatUser(String account, Socket socket) {
        this.account = account;
        this.socket = socket;
    }

    public String getAccount() {
        return account;
    }

    public Socket getSocket() {
        return socket;
    }
}
