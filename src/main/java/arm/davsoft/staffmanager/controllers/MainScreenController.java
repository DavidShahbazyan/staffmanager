package arm.davsoft.staffmanager.controllers;

import arm.davsoft.staffmanager.Main;
import arm.davsoft.staffmanager.components.MemberListCell;
import arm.davsoft.staffmanager.components.ProcessIndicator;
import arm.davsoft.staffmanager.components.TagBar;
import arm.davsoft.staffmanager.components.UploadFileLabelListCell;
import arm.davsoft.staffmanager.domain.Classifier;
import arm.davsoft.staffmanager.domain.PersonalData;
import arm.davsoft.staffmanager.domain.UploadFile;
import arm.davsoft.staffmanager.service.PersonalDataService;
import arm.davsoft.staffmanager.stages.LoginStage;
import arm.davsoft.staffmanager.stages.PersonalDataStage;
import arm.davsoft.staffmanager.utils.Dialogs;
import arm.davsoft.staffmanager.utils.ResourceManager;
import arm.davsoft.staffmanager.utils.Utils;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by david on 7/20/16.
 */
public class MainScreenController extends AppSpecController {
    @FXML private VBox rootContainer;
    @FXML private MenuItem appSettingsMenuItem, signOutMenuItem, appRestartMenuItem, exitAppMenuItem, aboutAppMenuItem;
    @FXML private ListView<PersonalData> membersListView;
    @FXML private ListView<UploadFile> attachmentsListView;
    @FXML private ImageView img_avatar;
    @FXML private TextField txt_search;
    @FXML private Label label_firstName, label_secondName, label_middleName, label_passport, label_gender, label_birthDate,
            label_email, label_phone, label_birthPlace, label_nationality, label_regAddress, label_resAddress;
    @FXML private Button btn_add, btn_edit, btn_delete, btn_refresh;
    @FXML private TagBar<Classifier> participationTagBar;

    private ObservableList<PersonalData> membersListViewItems = FXCollections.observableArrayList();


    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.LOGGER.info("Main controller instantiation started.");
        prepareForm();
        Main.LOGGER.info("Main controller instantiated successfully.");
    }

    @Override
    public void prepareForm() {
        super.prepareForm();
        img_avatar.setPreserveRatio(false);

        initFormBindings();
        initMembersListViewSearch();
        initMembersListView();
        initAttachmentsListView();
        refreshMembersListView();
    }

    @Override
    protected void initIcons() {
        super.initIcons();
//        appSettingsMenuItem.setGraphic(new ImageView("images/icons/general/settings.png"));
        signOutMenuItem.setGraphic(new ImageView("images/icons/general/lock.png"));
        appRestartMenuItem.setGraphic(new ImageView("images/icons/general/refresh.png"));
        exitAppMenuItem.setGraphic(new ImageView("images/icons/general/exit.png"));
        aboutAppMenuItem.setGraphic(new ImageView("images/icons/general/informationGreen.png"));
    }

    private void initFormBindings() {
        btn_edit.disableProperty().bind(Bindings.isNull(membersListView.getSelectionModel().selectedItemProperty()));
        btn_delete.disableProperty().bind(Bindings.isNull(membersListView.getSelectionModel().selectedItemProperty()));
    }

    private void initMembersListViewSearch() {
        txt_search.textProperty().addListener((observable, oldVal, newVal) -> filterMembersList(oldVal, newVal));
    }

    private void initMembersListView() {
        membersListView.setCellFactory(param -> new MemberListCell());
        membersListView.setItems(membersListViewItems);
        membersListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            PersonalData selectedPersonalData = membersListView.getSelectionModel().getSelectedItem();
            if (selectedPersonalData != null) {
                updatePersonalDetailsSection();
            }
        });
    }

    private void initAttachmentsListView() {
        attachmentsListView.setCellFactory(param -> new UploadFileLabelListCell());
        attachmentsListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Utils.showAttachment(attachmentsListView.getSelectionModel().getSelectedItem());
            }
        });
    }

    private void updatePersonalDetailsSection() {
        PersonalData selectedItem = membersListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                PersonalData data = PersonalDataService.loadPersonalDataById(selectedItem.getId());
                if (data != null) {
                    label_firstName.setText(data.getFirstName());
                    label_secondName.setText(data.getSecondName());
                    label_middleName.setText(data.getMiddleName());
                    label_passport.setText(data.getPassport());
                    label_gender.setText(data.getGender().getName());
                    label_birthDate.setText(data.getBirthDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
                    label_email.setText(data.getEmail());
                    label_phone.setText(data.getPhone());
                    label_birthPlace.setText(data.getBirthPlace());
                    label_nationality.setText(data.getNationality());
                    label_regAddress.setText(data.getRegAddress());
                    label_resAddress.setText(data.getResAddress());
                    img_avatar.setImage(data.getAvatar());
                    participationTagBar.setTags(data.getParticipations());
                    attachmentsListView.setItems(data.getAttachments());
                }
            } catch (SQLException e) {
                e.printStackTrace();
                Main.LOGGER.error("Error occurred in updatePersonalDetailsSection method: ", e);
            }
        }
    }

    private void filterMembersList(String oldVal, String newVal) {
        if (oldVal != null && (newVal.length() < oldVal.length())) {
            membersListView.setItems(membersListViewItems);
        }
        String value = newVal.toUpperCase();
        ObservableList<PersonalData> subentries = FXCollections.observableArrayList();
        for (PersonalData personalData : membersListView.getItems()) {
            boolean match = true;
            String entryText = personalData.getFullName();
            if (!entryText.toUpperCase().contains(value)) {
                match = false;
            }
            if (match) {
                subentries.add(personalData);
            }
        }
        membersListView.setItems(subentries);
    }

    @FXML
    private void openAppSettings(ActionEvent event) {
        Dialogs.showSettingsDialog(rootContainer.getScene().getWindow());
    }

    @FXML
    private void aboutApp(ActionEvent event) {
        Dialogs.showAboutAppDialog(rootContainer.getScene().getWindow());
    }

    @FXML
    private void logOut(ActionEvent event) throws Exception {
        new LoginStage().showAndFocus();
        currentStage.hide();
    }

    @FXML
    private void restartApp(ActionEvent event) throws Exception {
        ProcessIndicator graphic = new ProcessIndicator("images/icons/process/fs/step_1@2x.png", true);
        if (Dialogs.showConfirmDialog(graphic, ResourceManager.getMessage("title.dialog.restarting"), null, ResourceManager.getMessage("label.confirmation.restartTheApplication"))) {
            Main.restart();
        }
    }

    @FXML
    private void exitApp(ActionEvent event) {
        Main.exit();
    }

    @FXML
    private void createNewPersonalData(ActionEvent event) throws Exception {
        PersonalDataStage personalDataStage = new PersonalDataStage(new PersonalData(), ((Stage) rootContainer.getScene().getWindow()));
        personalDataStage.addBeforeStageCloseAction(o -> refreshMembersListView());
        personalDataStage.showAndFocus();
    }

    @FXML
    private void editPersonalData(ActionEvent event) throws Exception {
        PersonalData selectedMember = membersListView.getSelectionModel().getSelectedItem();
        if (selectedMember != null) {
            PersonalDataStage personalDataStage = new PersonalDataStage(selectedMember, ((Stage) rootContainer.getScene().getWindow()));
            personalDataStage.addBeforeStageCloseAction(o -> refreshMembersListView());
            personalDataStage.showAndFocus();
        } else {
            Dialogs.showWarningPopup("Attention!", "There is nothing selected in the table below. Please select a row first.");
        }
    }

    @FXML
    private void deletePersonalData(ActionEvent event) throws SQLException {
        PersonalData selectedMember = membersListView.getSelectionModel().getSelectedItem();
        if (selectedMember != null) {
            String content = String.format(ResourceManager.getMessage("label.confirmation.delete.item"), selectedMember.getFullName());
            if (Dialogs.showConfirmDialog(ResourceManager.getMessage("title.dialog.delete"), null, content)) {
                PersonalDataService.deletePersonalData(selectedMember);
                refreshMembersListView();
            }
        } else {
            Dialogs.showWarningPopup("Attention!", "There is nothing selected in the table below. Please select a row first.");
        }
    }

    @FXML
    private void refreshMembersListView() {
        int selectedIndex = membersListView.getSelectionModel().getSelectedIndex();
        try {
            membersListViewItems.clear();
            membersListViewItems.addAll(PersonalDataService.loadAllMembers());
            filterMembersList("", "");
            if (membersListViewItems.size() > 0) {
                if (selectedIndex < 0) {
                    membersListView.getSelectionModel().select(0);
                } else {
                    membersListView.getSelectionModel().select(selectedIndex);
                }
            }
            membersListView.requestFocus();
        } catch (SQLException e) {
            Main.LOGGER.error("Error during refreshing members table: ", e);
        }
    }
}
