package config;

import com.alibaba.fastjson.JSONObject;

public class HomeCameraConfig {

    private final Integer port;


    public HomeCameraConfig(JSONObject baseconfig) {
        port = baseconfig.getInteger("port");
    }

    public Integer getPort() {
        return port;
    }
}
