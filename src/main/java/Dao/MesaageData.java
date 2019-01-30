package Dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * @Description:
 * @Author: hxl
 * @Date: 2019/1/30 11:25
 * @Version 1.0
 */
public class MesaageData {

  private Integer id;
  private Integer sendid;
  private String senduser;
  private Integer receiveid;
  private String receiveuser;
  private String message;
  private String remark;
  private Date createTime;

  public MesaageData(ResultSet rs) throws SQLException {
    this.id = rs.getInt("id");
    this.sendid = rs.getInt("sendid");
    this.receiveid = rs.getInt("receiveid");
    this.senduser = rs.getString("senduser");
    this.receiveuser = rs.getString("receiveuser");
    this.message = rs.getString("message");
    this.remark = rs.getString("remark");
    this.createTime = rs.getTime("create_time");
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Integer getSendid() {
    return sendid;
  }

  public void setSendid(Integer sendid) {
    this.sendid = sendid;
  }

  public String getSenduser() {
    return senduser;
  }

  public void setSenduser(String senduser) {
    this.senduser = senduser;
  }

  public Integer getReceiveid() {
    return receiveid;
  }

  public void setReceiveid(Integer receiveid) {
    this.receiveid = receiveid;
  }

  public String getReceiveuser() {
    return receiveuser;
  }

  public void setReceiveuser(String receiveuser) {
    this.receiveuser = receiveuser;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }
}
