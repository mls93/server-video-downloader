package regexutils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mls on 12.09.15.
 */
public class RegexUtils {



    public static String findByRegex(String src, String regex){
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(src);
        String result = "";
        if (m.find())
            result = m.group(1);

        return result;
    };

    public static String findNthByRegex(String src,String regex,int N){

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(src);
        for (int i=0;i<N;i++)
            m.find();
        return m.group(1);
    }
}
