package filedownloading;

import java.io.InputStream;

/**
 * Created by mls on 10.09.15.
 */
public interface FileDownloader {
    boolean setInputSteam(String sourceUrl);
    void downloadFileFromStream();
    String getFileName();

}
