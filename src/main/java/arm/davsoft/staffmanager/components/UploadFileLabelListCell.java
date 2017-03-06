package arm.davsoft.staffmanager.components;

import arm.davsoft.staffmanager.domain.UploadFile;
import javafx.scene.control.ListCell;

/**
 * Created by david on 8/25/16.
 */
public class UploadFileLabelListCell extends ListCell<UploadFile> {
    @Override
    protected void updateItem(UploadFile item, boolean empty) {
        super.updateItem(item, empty);
        String text = null;
        if (item != null && !empty) {
            text = item.getName();
        }
        setText(text);
        setGraphic(null);
    }
}
