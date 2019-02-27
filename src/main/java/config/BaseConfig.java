package config;

import com.alibaba.fastjson.JSONObject;

public class BaseConfig {

    private final Integer port;


    public BaseConfig(JSONObject baseconfig) {
        port = baseconfig.getInteger("port");
    }

    public Integer getPort() {
        return port;
    }
}
