package metadata;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by mls on 10.09.15.
 */
public class FFmpegMetadataAdder implements MetadataAdder{

    private final String artist;
    private final String title;

    public  FFmpegMetadataAdder(String artist,String title){
        this.artist = artist;
        this.title = title;
    }

    @Override
    public void addMetadata(String fileName) throws IOException {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String ffmpeg = "ffmpeg";
        String base = System.getProperty("catalina.base");
        if (base==null)
            base = "/run/media/mls/Data/distr/apache-tomcat-8.0.24";
        String dir = base + System.getProperty("videoplayer.videos");
        System.out.println("res="+fileName);
        String ext = fileName.split("\\.")[fileName.split("\\.").length-1];

        ProcessBuilder pb = new ProcessBuilder(ffmpeg,"-i",""+fileName+"","-metadata",
                "artist='"+artist+"'","-metadata","title='"+title+"'","-acodec","copy",
                "-vcodec","copy",""+dir+title.replaceAll(" ","\\ ")+"."+ext+"");

        pb.directory(new File(dir));
        for (String s:pb.command())
            System.out.println(s);
        Process process = pb.start();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(process.getErrorStream()));

        String line = "";
        while ((line = reader.readLine())!= null) {
            System.out.println(line);
        }

        try {
            process.waitFor();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(process.exitValue());
        System.out.println(new File(fileName).delete());
    }
}
