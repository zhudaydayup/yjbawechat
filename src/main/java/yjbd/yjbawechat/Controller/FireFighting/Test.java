package yjbd.yjbawechat.Controller.FireFighting;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Test {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String url = "%E7%BD%91%E6%A0%BC%E5%B7%A1%E6%9F%A5";
        String decode = URLDecoder.decode(url, "UTF-8");
        System.out.println(decode);
    }
}
