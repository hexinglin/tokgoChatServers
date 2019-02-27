package config;

import com.alibaba.fastjson.JSONObject;

public class DBConfig {

    private final String url;
    private final String user;
    private final String password;



     public DBConfig(JSONObject dbonfig) {
         url = dbonfig.getString("url");
         user = dbonfig.getString("user");
         password = dbonfig.getString("password");
     }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
