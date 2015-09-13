package parsing;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.*;
import com.coremedia.iso.boxes.apple.AppleItemListBox;
import com.googlecode.mp4parser.FileDataSourceImpl;
import com.googlecode.mp4parser.boxes.apple.AppleArtistBox;
import com.googlecode.mp4parser.boxes.apple.AppleNameBox;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * Created by MLS on 22.08.2015.
 */
public class MyMP4Parser {
    private String videoLocation;
    public MyMP4Parser(String videoLocation){
        this.videoLocation = videoLocation;
    }

    public String getVideoLocation() {
        return videoLocation;
    }

    public String getFileDtSource() throws IOException {
        FileDataSourceImpl fileDataSource = new FileDataSourceImpl(videoLocation);
        IsoFile isoFile = new IsoFile(fileDataSource);
        return isoFile.toString();
    }

    public String getDetails() throws IOException {
        FileDataSourceImpl fileDataSource = new FileDataSourceImpl(videoLocation);
        IsoFile isoFile = new IsoFile(fileDataSource);
        MovieBox moov = isoFile.getMovieBox();




        UserDataBox udta = moov.getBoxes(UserDataBox.class).get(0);

        MetaBox b1 = (MetaBox) udta.getBoxes().get(0);

        AppleItemListBox b2 = (AppleItemListBox) b1.getBoxes().get(1);

        AppleArtistBox b3 = b2.getBoxes(AppleArtistBox.class).get(0);
        String res1 = b3.getValue();
        AppleNameBox b4 = b2.getBoxes(AppleNameBox.class).get(0);







        String res2 =b4.getValue();
        return (res1+" "+res2).replaceAll("'","");
    }


}

