package arm.davsoft.staffmanager.domain;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;

/**
 * Created by david on 8/10/16.
 */
public class AppSpecImage extends Image {
    private byte[] bytes;

    public AppSpecImage(byte[] bytes) {
        super(new ByteArrayInputStream(bytes));
        this.bytes = bytes;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
