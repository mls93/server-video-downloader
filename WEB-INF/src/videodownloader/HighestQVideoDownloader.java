package videodownloader;

import filedownloading.FileDownloader;
import filedownloading.SimpleFileDownloader;
import metadata.FFmpegMetadataAdder;
import metadata.MetadataAdder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by mls on 12.09.15.
 */
public class HighestQVideoDownloader extends BasicVideoDownloader {


    public HighestQVideoDownloader(String title, String artist, String extraInfo){
        super(title,artist,extraInfo);

    }
    @Override
    protected void downloadAppropriateVideoOnDevice(List<String> sources) {
        int currentSrc = 0;
        InputStream is = null;
        FileDownloader downloader = new SimpleFileDownloader(params);
        while (currentSrc < sources.size()) {
            currentSrc++;
            if (!downloader.setInputSteam(sources.get(currentSrc-1)))
                continue;
            break;
        }
        downloader.downloadFileFromStream();


        String fileName = downloader.getFileName();
        MetadataAdder adder = new FFmpegMetadataAdder(artist,title);
        try {
            adder.addMetadata(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
