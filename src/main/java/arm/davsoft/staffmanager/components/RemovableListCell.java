package arm.davsoft.staffmanager.components;

import arm.davsoft.staffmanager.domain.Classifier;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by david on 8/25/16.
 */
public class RemovableListCell extends ListCell<Object> {
    @Override
    protected void updateItem(Object item, boolean empty) {
        super.updateItem(item, empty);
        String text = null;
        Node graphic = null;
        if (item != null && !empty) {
            text = item.toString();
            Button btn_remove = new Button();
            HBox hBox = new HBox();

            graphic = hBox;
        }
        setText(text);
        setGraphic(graphic);
    }
}
