package hub.sam.mof.ant;

import org.apache.tools.ant.types.DataType;

import java.io.File;

public class XmiFile extends DataType {
    private File file = null;
    private String key = null;

    public void setFile(File file) {
        this.file = file;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public File getFile() {
        return file;
    }

    public String getKey() {
        return key;
    }
}
