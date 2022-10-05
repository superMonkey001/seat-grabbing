package cn.hncj.grabbing.util;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Author FanJian
 * @Date 2022/10/4
 */

public class MD5Utils {

    /**
     * 为了方便起见，就将所有的用户的盐设置成同一个，实际上，每个用户的盐应该是不同的。
     */

    /**
     * @param  src : 前端将用户的密码经过md5加密后传给后端的字符串
     * @return 前端传给后端的字符串和从数据库中获取的盐混合之后再进行md5加密后的结果
     */
    public static String dbPassword(String src,String salt) {
        String dbPassword = DigestUtils.md5Hex(src + salt);
        return dbPassword;
    }

}
