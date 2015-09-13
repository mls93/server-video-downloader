package filedownloading;

import regexutils.RegexUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by mls on 10.09.15.
 */
public class SimpleFileDownloader implements FileDownloader{

    private InputStream is;
    private String params;
    private String url;

    public String getFileName() {
        System.out.println(fileName);
        return fileName;
    }

    private String fileName;

    public SimpleFileDownloader(String params){
        this.params = params;
    }

    @Override
    public boolean setInputSteam(String sourceUrl) {

        is = null;
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(sourceUrl).openConnection();
            int status = conn.getResponseCode();
            if (status ==403){
                System.out.println("403 code ");

                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }

        try {
            is = conn.getInputStream();
        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
            return false;
        }
        url = sourceUrl;
        return true;


    }

    @Override
    public void downloadFileFromStream() {
        setFileName();
        System.out.println(fileName);
        File fileToDownload = new File(fileName);
        OutputStream outstream = null;
        //if (fileToDownload.exists())
        //    return;

        try {
            outstream = new FileOutputStream(fileToDownload);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] buffer = new byte[4096];
        int len;
        try {
            while ((len = is.read(buffer)) > 0) {
                outstream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e){
            System.out.println("Missing some of files");
            return;
        }
        try {
            outstream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setFileName(){
        String resultName = "";
        for (String s : params.split("\\s+")){
            resultName += s;
        }
        String base = System.getProperty("catalina.base");
        fileName = base + System.getProperty("videoplayer.videos") + resultName;
        String extensionRegex = "type=.*?%2F(.*?)%3B";
        String ext = RegexUtils.findByRegex(url,extensionRegex);
        fileName+="."+ext;


    }
}
