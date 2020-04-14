package util;

import java.util.Objects;

/**
 * TODO
 *
 * @author zkk
 * @since 2020/4/13 12:43
 */
public class StringUtil {

    public static String doFirstCharLower(String str){
        if(Objects.nonNull(str) && str.length() > 0){
            return str.substring(0, 1).toLowerCase() + str.substring(1);
        }
        return "";
    }

    public static String doFirstCharUpper(String str){
        if(Objects.nonNull(str) && str.length() > 0){
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
        return "";
    }

}
