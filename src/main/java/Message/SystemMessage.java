package Message;

import Chat.ChatServers;
import Chat.ChatUser;
import Dao.MesaageData;
import Dao.MessageDao;

import java.sql.SQLException;
import java.util.List;

/**
 * @Description:
 * @Author: hxl
 * @Date: 2019/1/30 13:22
 * @Version 1.0
 */
public class SystemMessage extends Thread {

  private final ChatServers chatServers;
  private final MessageDao messageDao;


  public SystemMessage(ChatServers chatServers) throws SQLException {
    this.chatServers = chatServers;
    messageDao = new MessageDao();
    this.start();
  }


  @Override
  public void run() {
    while (true){
      //获取当前需要通知的消息
      try {
        List<MesaageData> mesaageDataList = messageDao.getQQMessages();
        for (MesaageData mesaageData : mesaageDataList){
          ChatUser chatUser =chatServers.getUserInfroMap().get(mesaageData.getReceiveuser());
          if (chatUser != null){
            if (chatServers.sendMessage(chatUser,mesaageData)){
              messageDao.deleteMessage(mesaageData.getId());
            }
          }
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
      try {
        sleep(300000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }






  }
}
