package songwordsretrieving;

import myhttprequests.JavaCurl;
import regexutils.RegexUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mls on 12.09.15.
 */
public class FirstWordsRetriever implements WordsRetriever{



    private String request;

    public FirstWordsRetriever(String params) {
        this.request = params;
    }



    @Override
    public String retrieveWords(){
        List<String> params = new ArrayList<String>();
        params.add("q");
        params.add(request);
        String findResponse = "",pageWithWordsSource = "";

        try {
            findResponse = JavaCurl.sendPost("http://www.elyrics.net/find.php", params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String wordsDivRegex = "<div class=\"inner_right\">(.*?)</div>";
        String wordsDivInSrc = RegexUtils.findByRegex(findResponse,wordsDivRegex);

        String wordsLinkRegex = "href=\"(.*?)\"";
        String wordsLink = RegexUtils.findByRegex(wordsDivInSrc,wordsLinkRegex);

        try {
            pageWithWordsSource = JavaCurl.sendGet("http://www.elyrics.net" + wordsLink);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String lyricsRegex= "<div id=\'inlyr\'.*?>(.*?)<p>";

        String lyrics = RegexUtils.findByRegex(pageWithWordsSource,lyricsRegex);
        return lyrics;
    }



}
