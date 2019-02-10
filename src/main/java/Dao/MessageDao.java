package Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: hxl
 * @Date: 2019/1/30 12:08
 * @Version 1.0
 */
public class MessageDao {
  private static final String url="jdbc:mysql://cdb-p7b1ovok.cd.tencentcdb.com:10050/tokgo_prd";    //JDBC的URL
  private static final String user ="root";
  private static final String password ="141459awdfth";

  private static final String QQ_MESSAGES_SQL = "SELECT * FROM t_message where receiveuser='qq_message'";
  private static final String DELETE_MESSAGE = "DELETE FROM t_message WHERE id =";

  private final Connection conn;  //调用DriverManager对象的getConnection()方法，获得一个Connection对象

  static {
    try {
      //调用Class.forName()方法加载驱动程序
      Class.forName("com.mysql.cj.jdbc.Driver");
      System.out.println("成功加载MySQL驱动！");
    } catch (ClassNotFoundException e1) {
      System.out.println("找不到MySQL驱动!");
      e1.printStackTrace();
    }
  }

  public MessageDao() throws SQLException {
    conn = DriverManager.getConnection(url,user,password);
  }

  public List<MesaageData> getQQMessages() throws SQLException {
    List<MesaageData> mesaageDataList = new ArrayList<>();
    PreparedStatement statement = conn.prepareStatement(QQ_MESSAGES_SQL);
    ResultSet rs= statement.executeQuery();
    while(rs.next()){
      mesaageDataList.add(new MesaageData(rs));
    }
    return mesaageDataList;
  }

  public void deleteMessage(Integer id) throws SQLException {
    PreparedStatement statement = conn.prepareStatement(DELETE_MESSAGE+id);
    statement.executeUpdate();
  }

}

