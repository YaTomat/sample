package ais.sample.com.common;

/**
 * Created by YaTomat on 25.09.2017.
 */

public class PhoneUtil {

    public static boolean isValidPhone(String number){
        return number.startsWith("7")&&number.length()==11;
    }
}
