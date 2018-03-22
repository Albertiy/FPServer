package com.albertiy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

@Controller
public class MainController {

    @RequestMapping("/GetData")
    @ResponseBody
    public void GetDataFromAndroid(Map<String,String> map) throws UnsupportedEncodingException {
        String sb = "";
        if(!map.isEmpty()){
            for (Map.Entry<String, String>entry:map.entrySet()) {
                sb = sb.concat("&");
                sb = sb.concat(entry.getKey()+"=");
                sb = sb.concat(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            sb.replaceFirst("&","");
        }
        System.out.println("get info from Android: " + sb);
        return ;
    }
}
