package config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.InputStream;

public class SystemConfig {
    private static  BaseConfig baseConfig =null;
    private static DBConfig dbConfig =null;

    static {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("resoures/config.json");
            String text = IOUtils.toString(inputStream,"utf8");
            JSONObject systemconfig =  JSON.parseObject(text);
            baseConfig = new BaseConfig(systemconfig.getJSONObject("base"));
            dbConfig = new DBConfig(systemconfig.getJSONObject("database"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static BaseConfig getBaseConfig() {
        return baseConfig;
    }

    public static DBConfig getDbConfig() {
        return dbConfig;
    }

    //私有化构造函数
    private SystemConfig(){}


}
