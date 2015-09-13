package metadata;

import java.io.IOException;

/**
 * Created by mls on 10.09.15.
 */
public interface MetadataAdder {
    void addMetadata(String fileName) throws IOException;
}
