package cn.hncj.grabbing.util;

import java.util.UUID;

/**
 * UUID工具类
 *
 * @author zhoubin
 * @since 1.0.0
 */
public class UUIDUtils {

   public static String uuid() {
      return UUID.randomUUID().toString().replace("-", "");
   }

}