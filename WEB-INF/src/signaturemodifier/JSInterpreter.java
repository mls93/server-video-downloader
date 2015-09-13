package signaturemodifier;

import myhttprequests.JavaCurl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mls on 10.09.15.
 */
public class JSInterpreter{
    private String player_url;
    private String type;
    private String source;
    public JSInterpreter(String player_url) {
        this.player_url = player_url;
        source = "";
        try {
            source = JavaCurl.sendGet("https:" + player_url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getType();

    }


    private void getType(){
        Pattern p = Pattern.compile("\\=[a-zA-Z]\\.sig\\|\\|([a-zA-Z0-9$]+?)\\(");
        Matcher m = p.matcher(source);
        if (m.find())
            type = m.group(1);
        else{
            p = Pattern.compile("e\\.sig\\|\\|e\\.s\\)\\{.*?e\\.sig\\|\\|(.*?)\\(e\\.s\\)");
            m = p.matcher(source);
            if (m.find())
                type = m.group(1);


        }


        System.out.println(type);
    }
    public List<String> getFunctions(){
        List<String> functions = new ArrayList<String>();
        Pattern p = Pattern.compile("function " + type.replaceAll("\\$",Matcher.quoteReplacement("\\$")) + "\\(a\\)\\{a\\=a\\.split\\(\"\"\\);(.*?)\\.");
        Matcher m = p.matcher(source);
        String var="";
        if(m.find())
            var = m.group(1);
        System.out.println(var);
        //System.out.println("var "+var.replaceAll("\\$",Matcher.quoteReplacement("\\$"))+"\\=\\{([a-zA-Z]+?)\\:.+?\\}\\,([a-zA-Z]+?)\\:.+?\\}\\,([a-zA-Z]+?)\\:");
        Pattern p1 = Pattern.compile("var "+var.replaceAll("\\$",Matcher.quoteReplacement("\\$"))+"\\=\\{.*?([a-zA-Z0-9]+?)\\:function\\(a\\,b\\)\\{[^{]*?c\\=a\\[0\\].*?\\}");
        Pattern p2 = Pattern.compile("var "+var.replaceAll("\\$",Matcher.quoteReplacement("\\$"))+"\\=\\{.*?([a-zA-Z0-9]+?)\\:function\\(a\\)\\{a\\.reverse.*?\\}");
        Pattern p3 = Pattern.compile("var "+var.replaceAll("\\$",Matcher.quoteReplacement("\\$"))+"\\=\\{.*?([a-zA-Z0-9]+?)\\:function\\(a\\,b\\)\\{a\\.splice.*?\\}");
        System.out.println("var " + var.replaceAll("\\$", Matcher.quoteReplacement("\\$")) + "\\=\\{.*?([a-zA-Z0-9]+?)\\:function\\(a\\,b\\)\\{.*?c\\=a\\[0\\].*?\\}");
        m = p1.matcher(source);
        if (m.find()) {
            System.out.println(m.group(1));
            functions.add(m.group(1));
        }
        m = p2.matcher(source);
        if (m.find()) {
            System.out.println(m.group(1));
            functions.add(m.group(1));
        }
        m = p3.matcher(source);
        if (m.find()) {
            System.out.println(m.group(1));
            functions.add(m.group(1));
        }

        return functions;
    }
    public List<List<String> > getOperations(){
        List< List <String> > operations = new ArrayList<List<String> >();
        Pattern p = Pattern.compile("function "+type.replaceAll("\\$",Matcher.quoteReplacement("\\$"))+"\\(a\\)\\{a\\=a\\.split\\(\"\"\\)(;.*?);return");
        Matcher m = p.matcher(source);
        String commands ="";
        if (m.find())
            commands = m.group(1);
        p = Pattern.compile(";[$a-zA-Z]+?\\.([a-zA-Z0-9]+?)\\(a\\,([0-9]+?)\\)");
        m = p.matcher(commands);

        while (m.find()) {
            List<String> command = new ArrayList<String>();
            command.add(m.group(1));
            command.add(m.group(2));

            operations.add(command);
            System.out.println(command);
        }
        return operations;
    }


}