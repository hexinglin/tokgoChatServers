package Dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Author: hxl
 * @Date: 2019/1/30 12:08
 * @Version 1.0
 */
public class test {
  private static final String url="jdbc:mysql://tp-forum-test.c3lq138brhlg.eu-central-1.rds.amazonaws.com:3306/ultrax_qa?verifyServerCertificate=false&useSSL=false&requireSSL=false";    //JDBC的URL
  private static final String user ="root";
  private static final String password ="2IUx8V8otpvdq8t22l";

  private static final String QQ_MESSAGES_SQL = "SELECT * FROM pre_common_word";
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

  public test() throws SQLException {
    conn = DriverManager.getConnection(url,user,password);
  }

  public void getQQMessages() throws SQLException {
    String originText ="Usually, being put out of service is punishment enough as you're going to lose pay. With my company, if DOT puts you out of service, they will put you out of service for another day as an extended punishment. But I doubt you'd be fired unless you did it repeatedly.";
    PreparedStatement statement = conn.prepareStatement(QQ_MESSAGES_SQL);
    ResultSet rs= statement.executeQuery();
    while(rs.next()){
      String o = rs.getString("find");
      if ("/\\bcompany\\b/".equals(o))
        continue;
      String re = o.substring(1,o.length()-1);
      Pattern compilePattern = Pattern.compile(re);
      Matcher m = compilePattern.matcher(originText);
      if (m.find()) {
        System.out.println(rs.getString("find"));
        return;
      }
    }

  }
  public static void main(String[] args){
    try {
      new test().getQQMessages();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  public void deleteMessage(Integer id) throws SQLException {
    PreparedStatement statement = conn.prepareStatement(DELETE_MESSAGE+id);
    statement.executeUpdate();
  }

}

