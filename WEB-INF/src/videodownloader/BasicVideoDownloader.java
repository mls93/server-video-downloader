package videodownloader;

import myhttprequests.JavaCurl;
import regexutils.RegexUtils;
import videourlsretrieving.UrlsRetriever;
import videourlsretrieving.UrlsRetrieverImpl;

import java.util.List;


/**
 * Created by mls on 12.09.15.
 */
public abstract class BasicVideoDownloader implements VideoDownloader {


    protected String params;
    protected String title;
    protected String artist;


    public BasicVideoDownloader(String title, String artist, String extraInfo) {
        this.title = title;
        this.artist = artist;
        params = artist+" "+title+" "+extraInfo;
    }

    @Override
    public void downloadVideo() {
        String searchPage = requestVideoSearch();
        String link = findVideoLink(searchPage);
        String videoSource = getVideoSource(link);
        List<String> rawData = getVideoUrls(videoSource);
        downloadAppropriateVideoOnDevice(rawData);

    }



    public String requestVideoSearch(){

        String requestUrl = "https://youtube.com/results?search_query=";
        int count = 0;
        for (String s: params.split("\\s+")){
            count++;
            if(count>1)
                requestUrl+="+";
            requestUrl += s;

        }
        System.out.println(requestUrl);
        String searchPage = "";
        try {
            searchPage = JavaCurl.sendGet(requestUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return searchPage;

    }

    public String findVideoLink(String source){
        String videoLinkRegex = "h3 class=\"yt-lockup-title \"><.*?href=\"(.*?)\"";
        String videoLink = RegexUtils.findNthByRegex(source,videoLinkRegex,3);
        return videoLink;
    }

    public String getVideoSource(String link){
        String url = "https://youtube.com"+link;
        String source = "";
        try {
            source = JavaCurl.sendGet(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return source;
    }




    public List<String> getVideoUrls(String source){
        String videoInfoRegex = "\"url_encoded_fmt_stream_map\"\\:\"(.*?)(?=\"\\,)";
        String playerUrlRegex = "\"assets\"\\:.*?\"js\"\\:\"(.*?)\"";

        String videoInfoString = RegexUtils.findByRegex(source, videoInfoRegex);
        String player_url = RegexUtils.findByRegex(source,playerUrlRegex).replaceAll("\\\\","");

        UrlsRetriever retriever = new UrlsRetrieverImpl(videoInfoString,player_url,params);
        List<String> videos = retriever.getUrls();
        return videos;
    }

    protected abstract void downloadAppropriateVideoOnDevice(List<String> sources);

}
