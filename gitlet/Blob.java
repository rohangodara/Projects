package gitlet;
import java.io.File;
import java.io.Serializable;

import static gitlet.Utils.join;

public class Blob implements Serializable {

    private String name;
    private byte[] inside;
    private String SHA1Name;

    public Blob(String fileName) {
        name = fileName;
        inside = Utils.readContents(join(Repository.CWD, fileName)); //how do we get the contents from StagingArea of fileName into this new file inside
        SHA1Name = Utils.sha1(inside);
    }

    public String getSHA1Name() {
        return SHA1Name;
    }

    public byte[] getInside() {
        return inside;
    }
}
