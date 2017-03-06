package arm.davsoft.staffmanager.components;

import arm.davsoft.staffmanager.domain.PersonalData;
import arm.davsoft.staffmanager.utils.ResourceManager;
import arm.davsoft.staffmanager.utils.Utils;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Arrays;

/**
 * Created by david on 8/25/16.
 */
public class MemberListCell extends ListCell<PersonalData> {

    @Override
    protected void updateItem(PersonalData item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
            ImageView avatar = new ImageView(item.getAvatar());
            avatar.setFitHeight(64);
            avatar.setFitWidth(135.0 / 150.0 * 64);
            Label fName = new Label(item.getFirstName());
            Label sName = new Label(item.getSecondName());
            Label mName = new Label(item.getMiddleName());
            Label age = new Label(Utils.concatObjects(Arrays.asList(String.valueOf(item.getAge()), ResourceManager.getMessage("label.years.old.short")), " "));
            VBox vBox = new VBox(fName, mName, sName, age);
            HBox hBox = new HBox(5, avatar, vBox);
            HBox.setHgrow(avatar, Priority.NEVER);
            HBox.setHgrow(vBox, Priority.ALWAYS);
            setGraphic(hBox);
        } else {
            setGraphic(null);
        }
    }
}
