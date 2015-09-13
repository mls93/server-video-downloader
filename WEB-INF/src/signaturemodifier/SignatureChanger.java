package signaturemodifier;

import java.util.List;

/**
 * Created by mls on 10.09.15.
 */
public class SignatureChanger{

    private String player_url;
    private String type;
    private JSInterpreter interpreter;
    private List<String> types;

    public SignatureChanger(String player_url) {

        this.player_url = player_url;
        interpreter = new JSInterpreter(player_url);
        types = interpreter.getFunctions();
    }

    public String change(String sig){
        String result = "";
        String[] arr = sig.split("");
        List<List<String>> operations = interpreter.getOperations();
        for (List<String> operation:operations) {
            String type = operation.get(0);
            int val = Integer.parseInt(operation.get(1));
            arr = doOperation(arr,val,type);
        }
        for (String s : arr) {
            result += s;
        }

        return result;
    }

    private String[] doOperation(String[] a,int b,String type){
        int ind = types.indexOf(type);
        switch (ind){
            case 2: return f3(a, b);
            case 1: return f2(a);
            default:return f1(a,b);
        }
    }


    private String[] f1 (String[] a, int b) {
        //var c=a[0];a[0]=a[b%a.length];a[b]=c
        String c = a[0];
        a[0] = a[b%a.length];
        a[b] = c;
        return a;
    }

    private String[] f2(String[] a){
        //a.reverse()
        String [] res = new String[a.length];
        for (int i=0;i<a.length; i++)
            res[a.length-i-1] = a[i];
        return res;
    }
    private String[] f3(String[] a,int b){
        //a.splice(0,b)
        String [] res = new String[a.length-b];
        for (int i=0;i<a.length-b; i++)
            res[i] = a[i+b];
        return res;

    }
}
