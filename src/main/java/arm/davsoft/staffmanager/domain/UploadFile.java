package arm.davsoft.staffmanager.domain;

import arm.davsoft.staffmanager.utils.Utils;

import java.io.File;

/**
 * Created by david on 8/22/16.
 */
public class UploadFile {
    private int id;
    private byte[] bytes;
    private String name;

    public UploadFile(int id, File file) {
        this(id, Utils.fileToByteArray(file), file.getName());
    }

    public UploadFile(int id, byte[] bytes, String name) {
        this.id = id;
        this.bytes = bytes;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
