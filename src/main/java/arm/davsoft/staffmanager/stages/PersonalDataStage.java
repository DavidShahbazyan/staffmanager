package arm.davsoft.staffmanager.stages;

import arm.davsoft.staffmanager.components.ApplicationTitleBar;
import arm.davsoft.staffmanager.controllers.AppSpecController;
import arm.davsoft.staffmanager.controllers.PersonalDataController;
import arm.davsoft.staffmanager.domain.PersonalData;
import arm.davsoft.staffmanager.enums.UIMode;
import arm.davsoft.staffmanager.service.PersonalDataService;
import arm.davsoft.staffmanager.utils.IDGenerator;
import arm.davsoft.staffmanager.utils.ResourceManager;
import arm.davsoft.staffmanager.utils.Utils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Parent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by david on 8/1/16.
 */
public class PersonalDataStage extends CustomStage {
    private ObjectProperty<UIMode> mode = new SimpleObjectProperty<>();
    private BooleanProperty editMode = new SimpleBooleanProperty(true);
    private PersonalData personalData;

    public PersonalDataStage(PersonalData personalData) throws Exception {
        this(personalData, null);
    }

    public PersonalDataStage(PersonalData personalData, Stage parentStage) throws Exception {
        super(parentStage);

        this.mode.addListener((observable, oldValue, newValue) -> setEditMode(UIMode.NEW_MODE.equals(newValue) || UIMode.EDIT_MODE.equals(newValue)));
        if (personalData.getId() == null) {
            setMode(UIMode.NEW_MODE);
            this.personalData = personalData;
            this.personalData.setId(IDGenerator.getNextTempId());
        } else {
            setMode(UIMode.EDIT_MODE);
            this.personalData = PersonalDataService.loadPersonalDataById(personalData.getId());
        }

        initStyle(StageStyle.UNDECORATED);
        initStage(Utils.concatObjects(Arrays.asList(ResourceManager.getParam("APPLICATION.NAME"), personalData.getFullName().isEmpty() ? ResourceManager.getMessage("label.newMember") : personalData.getFullName()), " - "), ResourceManager.getAppLogo());
        Parent parent = fxmlLoader.load(getClass().getResourceAsStream("/screens/personalDataScreen.fxml"));
        ((VBox) getScene().getRoot()).getChildren().addAll(new ApplicationTitleBar(this, true, false, true), parent);
        VBox.setVgrow(parent, Priority.ALWAYS);
        fixAllDimensionsTo(560, 540);

        ((AppSpecController) fxmlLoader.getController()).setCurrentStage(this);
        ((AppSpecController) fxmlLoader.getController()).prepareForm();
    }


    public UIMode getMode() {
        return mode.get();
    }

    public ObjectProperty<UIMode> modeProperty() {
        return mode;
    }

    public void setMode(UIMode mode) {
        this.mode.set(mode);
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    // There is no need of setter for this field, yet.
    private void setPersonalData(PersonalData personalData) {
        this.personalData = personalData;
    }

    public boolean getEditMode() {
        return editMode.get();
    }

    public BooleanProperty editModeProperty() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode.set(editMode);
    }
}
