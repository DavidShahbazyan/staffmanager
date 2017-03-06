package arm.davsoft.staffmanager.controllers;

import arm.davsoft.staffmanager.Main;
import arm.davsoft.staffmanager.components.TagBar;
import arm.davsoft.staffmanager.components.UploadFileLabelListCell;
import arm.davsoft.staffmanager.domain.AppSpecImage;
import arm.davsoft.staffmanager.domain.Classifier;
import arm.davsoft.staffmanager.domain.UploadFile;
import arm.davsoft.staffmanager.helpers.ClassifierCache;
import arm.davsoft.staffmanager.service.PersonalDataService;
import arm.davsoft.staffmanager.stages.PersonalDataStage;
import arm.davsoft.staffmanager.utils.Dialogs;
import arm.davsoft.staffmanager.utils.IDGenerator;
import arm.davsoft.staffmanager.utils.Utils;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by david on 7/23/16.
 */
public class PersonalDataController extends AppSpecController<PersonalDataStage> {

    @FXML private VBox rootContainer;
    @FXML private ImageView img_avatar;
    @FXML private ListView<UploadFile> attachmentListView;
    @FXML private Button btn_browseAvatar, btn_cancel, btn_ok, btn_addAttachment, btn_removeAttachment, btn_addParticipation;
    @FXML private ComboBox<Classifier> combo_gender, combo_participation;
    @FXML private DatePicker date_birthDate;
    @FXML private ToolBar attachmentToolBar;
    @FXML private TextField txtField_firstName, txtField_secondName, txtField_middleName, txtField_passport, txtField_email, txtField_phone, txtField_birthPlace, txtField_nationality, txtField_regAddress, txtField_resAddress;
    @FXML private TagBar<Classifier> participationTagBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        Main.LOGGER.info("Login controller instantiation started.");
//        prepareForm();
        Main.LOGGER.info("Login controller instantiated successfully.");
    }

    public void prepareForm() {
        initVisibilityBindings();
        initEditabilityBindings();
        initValueBindings();
        initParticipationTagBar();
        initAttachmentListView();

        img_avatar.setPreserveRatio(false);
        combo_gender.getItems().addAll(ClassifierCache.getInstance().getGenders());
        combo_participation.getItems().addAll(ClassifierCache.getInstance().getParticipations());
    }

    private void initVisibilityBindings() {
        btn_browseAvatar.visibleProperty().bind(currentStage.editModeProperty());
        btn_ok.visibleProperty().bind(currentStage.editModeProperty());
        btn_cancel.visibleProperty().bind(currentStage.editModeProperty());
        btn_addAttachment.visibleProperty().bind(currentStage.editModeProperty());
        btn_removeAttachment.visibleProperty().bind(currentStage.editModeProperty());
    }

    private void initEditabilityBindings() {
        combo_gender.disableProperty().bind(Bindings.not(currentStage.editModeProperty()));
        date_birthDate.disableProperty().bind(Bindings.not(currentStage.editModeProperty()));
        txtField_firstName.disableProperty().bind(Bindings.not(currentStage.editModeProperty()));
        txtField_secondName.disableProperty().bind(Bindings.not(currentStage.editModeProperty()));
        txtField_middleName.disableProperty().bind(Bindings.not(currentStage.editModeProperty()));
        txtField_passport.disableProperty().bind(Bindings.not(currentStage.editModeProperty()));
        txtField_email.disableProperty().bind(Bindings.not(currentStage.editModeProperty()));
        txtField_phone.disableProperty().bind(Bindings.not(currentStage.editModeProperty()));
        txtField_birthPlace.disableProperty().bind(Bindings.not(currentStage.editModeProperty()));
        txtField_nationality.disableProperty().bind(Bindings.not(currentStage.editModeProperty()));
        txtField_regAddress.disableProperty().bind(Bindings.not(currentStage.editModeProperty()));
        txtField_resAddress.disableProperty().bind(Bindings.not(currentStage.editModeProperty()));

        btn_removeAttachment.disableProperty().bind(Bindings.or(Bindings.not(currentStage.editModeProperty()), Bindings.isEmpty(attachmentListView.getSelectionModel().getSelectedItems())));
    }

    private void initValueBindings() {
        combo_gender.valueProperty().bindBidirectional(currentStage.getPersonalData().genderProperty());
        date_birthDate.valueProperty().bindBidirectional(currentStage.getPersonalData().birthDateProperty());
        txtField_firstName.textProperty().bindBidirectional(currentStage.getPersonalData().firstNameProperty());
        txtField_secondName.textProperty().bindBidirectional(currentStage.getPersonalData().secondNameProperty());
        txtField_middleName.textProperty().bindBidirectional(currentStage.getPersonalData().middleNameProperty());
        txtField_passport.textProperty().bindBidirectional(currentStage.getPersonalData().passportProperty());
        txtField_email.textProperty().bindBidirectional(currentStage.getPersonalData().emailProperty());
        txtField_phone.textProperty().bindBidirectional(currentStage.getPersonalData().phoneProperty());
        txtField_birthPlace.textProperty().bindBidirectional(currentStage.getPersonalData().birthPlaceProperty());
        txtField_nationality.textProperty().bindBidirectional(currentStage.getPersonalData().nationalityProperty());
        txtField_regAddress.textProperty().bindBidirectional(currentStage.getPersonalData().regAddressProperty());
        txtField_resAddress.textProperty().bindBidirectional(currentStage.getPersonalData().resAddressProperty());
        img_avatar.imageProperty().bind(currentStage.getPersonalData().avatarProperty());
    }

    private void initParticipationTagBar() {
        participationTagBar.tagsProperty().bindBidirectional(currentStage.getPersonalData().participationsProperty());
    }

    private void initAttachmentListView() {
        attachmentListView.setCellFactory(param -> new UploadFileLabelListCell());
        attachmentListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Utils.showAttachment(attachmentListView.getSelectionModel().getSelectedItem());
            }
        });
        attachmentListView.setItems(currentStage.getPersonalData().getAttachments());
    }

    @FXML
    private void browseAvatar(ActionEvent event) {
        File file = Dialogs.openFileChooser(currentStage.getOwner(), "Select an image.", new FileChooser.ExtensionFilter("Images", "png", "jpg", "jpeg", "bmp"), null);
        if (file != null) {
            currentStage.getPersonalData().setAvatar(new AppSpecImage(Utils.fileToByteArray(file)));
        }
    }

    @FXML
    private void okAction(ActionEvent event) throws SQLException {
        if (currentStage.getPersonalData().isValid()) {
            switch (currentStage.getMode()) {
                case NEW_MODE:
                    System.out.println("Creates new record in DB.");
                    PersonalDataService.savePersonalData(currentStage.getPersonalData());
                    break;
                case EDIT_MODE:
                    System.out.println("Saves current changes in DB.");
                    PersonalDataService.savePersonalData(currentStage.getPersonalData());
                    break;
                default:
                    System.out.println("The stage is not supported!");
            }
            currentStage.close();
        } else {
            Dialogs.showWarningPopup("The form is not valid!", "All fields in the form are required!");
        }
    }

    @FXML
    private void cancelAction(ActionEvent event) {
        currentStage.close();
    }

    @FXML
    private void addAttachment(ActionEvent event) {
        File file = Dialogs.openFileChooser(currentStage.getOwner(), "Select an attachment.", new FileChooser.ExtensionFilter("Images", "png", "jpg", "jpeg", "bmp"), null);
        if (file != null) {
            currentStage.getPersonalData().getAttachments().add(new UploadFile(IDGenerator.getNextTempId(), file));
        }
    }

    @FXML
    private void removeAttachment(ActionEvent event) {
        UploadFile selectedItem = attachmentListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            currentStage.getPersonalData().getAttachments().remove(selectedItem);
        }
    }

    @FXML
    private void addParticipation(ActionEvent event) {
        Classifier selectedItem = combo_participation.getSelectionModel().getSelectedItem();
        if (selectedItem != null && !participationTagBar.getTags().contains(selectedItem)) {
            participationTagBar.getTags().add(selectedItem);
            combo_participation.getSelectionModel().clearSelection();
        }
    }

}
