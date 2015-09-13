package videourlsretrieving;

import regexutils.RegexUtils;
import signaturemodifier.SignatureChanger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mls on 12.09.15.
 */
public class UrlsRetrieverImpl implements UrlsRetriever {

    private final String params;
    private String urlsString;
    private String player_url;

    public UrlsRetrieverImpl(String urlsString,String player_url,String params) {
        this.urlsString = urlsString;
        this.player_url = player_url;
        this.params = params;
    }



    private  List<Map<String,String>> getVideoParametersMap(String [] parameters){
        List<Map<String,String>> videoParams = new ArrayList<Map<String, String>>();
        int size = 0;
        videoParams.add(new HashMap<String, String>());
        Map<String,String> parameter = videoParams.get(0);
        for (int i=0;i < parameters.length;i++){
            String [] pair = parameters[i].split("=");
            if (!parameter.isEmpty()){
                if (parameter.containsKey(pair[0])){
                    videoParams.add(new HashMap<String, String>());
                    size++;
                    parameter = videoParams.get(size);

                }
            }
            parameter.put(pair[0],pair[1]);
            if (pair.length==3){
                params.replace(pair[0],pair[1]+pair[2]);
            }
            if (pair[0] =="url"){
                params.replace(pair[0], parameters[i].substring(parameters[i].indexOf('=') + 1));
            }
        }
        return videoParams;
    }

    private String getURLFromParameters(Map<String,String> video, SignatureChanger ch){
        String signatureRegex = "signature\\=(.*?)((?=\\&)|(?=$))";
        if (!video.containsKey("url"))
            return "";
        String vd_type ="",vd_size="";
        if (!video.containsKey("type"))
            vd_type = video.get("url").split("mime%3D")[1].split("%26")[0];
        else vd_type= video.get("type");
        if (!video.containsKey("size"))
            vd_size = "0";
        else vd_size = video.get("size");

        String url = video.get("url").replaceAll("%3A", ":").replaceAll("%2F","/").replaceAll("%3F", "\\?")
                .replaceAll("%26", "\\&").replaceAll("%3D", "=").replaceAll("(?<=%)25","")+"&type="+vd_type.replaceAll("\\=", "%3D").replaceAll("\"","%22");


        if (video.containsKey("s")) {
            String newSignature = ch.change(video.get("s"));
            url += "&signature="+newSignature;

        }
        else{
            String oldSignature = RegexUtils.findByRegex(url, signatureRegex);
            if (!oldSignature.equals("")){
                String newSignature = ch.change(oldSignature);
                url.replaceAll(signatureRegex, "");
                url +="&signature="+ newSignature;

            }
        }
        System.out.println("video"+": "+url+" "+vd_size+" "+vd_type);
        return url;
    }

    @Override
    public List<String> getUrls() {

        urlsString = urlsString.replaceAll("\\\\u0026", "<break>").replaceAll("\\,","<break>");
        String[] currentResult = urlsString.split("<break>");
        SignatureChanger ch = new SignatureChanger(player_url);
        List<Map<String,String>> videoParams = this.getVideoParametersMap(currentResult);
        List<String> videos = new ArrayList<String>();
        for (Map<String,String> video:videoParams){
            String url = this.getURLFromParameters(video,ch);
            videos.add(url);
        }
        return videos;
    }
}
