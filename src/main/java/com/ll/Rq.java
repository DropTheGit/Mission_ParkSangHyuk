package com.ll;

import java.util.HashMap;
import java.util.Map;

public class Rq {
    String cmd;
    String action;
    Map<String, String> paramMaps = new HashMap<>();

    Rq(String cmd) {
        this.cmd = cmd;
        String[] cmdBits = cmd.split("\\?", 2);
        this.action = cmdBits[0].trim();
        if (cmdBits.length == 1) {
            return;
        }
        String query = cmdBits[1].trim();

        String[] queryBits = query.split("&");
        for (int i = 0; i < queryBits.length; i++) {
            String[] paramBits = queryBits[i].split("=", 2);

            String paramName = paramBits[0].trim();
            if(paramBits.length == 1){
                return;
            }
            String paramValue = paramBits[1].trim();

            paramMaps.put(paramName, paramValue);
        }
    }

    public int getValueByParamName(String paramName, int defaultValue) {
        try {
            return Integer.parseInt(paramMaps.get(paramName));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

}