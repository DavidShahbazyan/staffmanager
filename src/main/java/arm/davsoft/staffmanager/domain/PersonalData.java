package arm.davsoft.staffmanager.domain;

import arm.davsoft.staffmanager.utils.ResourceManager;
import arm.davsoft.staffmanager.utils.Utils;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by david on 8/4/16.
 */
public class PersonalData implements Cloneable {
    private Integer id;
    private StringProperty firstName;
    private StringProperty secondName;
    private StringProperty middleName;
    private StringProperty passport;
    private ObjectProperty<Classifier> gender;
    private ObjectProperty<LocalDate> birthDate;
    private StringProperty birthPlace;
    private StringProperty nationality;
    private StringProperty regAddress;
    private StringProperty resAddress;
    private StringProperty email;
    private StringProperty phone;
    private ObjectProperty<AppSpecImage> avatar;
    private ListProperty<Classifier> participations;
    private ListProperty<UploadFile> attachments;

    public PersonalData() {
        this(null);
        try {
            avatar.setValue(new AppSpecImage(Utils.inputStreamToByteArray(ResourceManager.getResource("/images/no_avatar-xlarge.jpg").openStream())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PersonalData(Integer id) {
        this.id = id;
        initFields();
    }

    private void initFields() {
        firstName = new SimpleStringProperty();
        secondName = new SimpleStringProperty();
        middleName = new SimpleStringProperty();
        passport = new SimpleStringProperty();
        gender = new SimpleObjectProperty<>();
        birthDate = new SimpleObjectProperty<>();
        birthPlace = new SimpleStringProperty();
        nationality = new SimpleStringProperty();
        regAddress = new SimpleStringProperty();
        resAddress = new SimpleStringProperty();
        email = new SimpleStringProperty();
        phone = new SimpleStringProperty();
        avatar = new SimpleObjectProperty<>();
        participations = new SimpleListProperty<>(FXCollections.observableArrayList());
        attachments = new SimpleListProperty<>(FXCollections.observableArrayList());
    }

    public boolean isNew() {
        return id == null || id < 0;
    }

    public boolean isValid() {
        return (firstName != null && !firstName.getValueSafe().isEmpty())
                && (secondName != null && !secondName.getValueSafe().isEmpty())
                && (middleName != null && !middleName.getValueSafe().isEmpty())
                && (passport != null && !passport.getValueSafe().isEmpty())
                && (gender != null && gender.getValue() != null)
                && (birthDate != null && birthDate.getValue() != null)
                && (birthPlace != null && !birthPlace.getValueSafe().isEmpty())
                && (nationality != null && !nationality.getValueSafe().isEmpty())
                && (regAddress != null && !regAddress.getValueSafe().isEmpty())
//                && (resAddress != null && !resAddress.getValueSafe().isEmpty())
                && (email != null && !email.getValueSafe().isEmpty())
                && (phone != null && !phone.getValueSafe().isEmpty())
//                && (avatar != null && avatar.getValue() != null)
                ;
    }

    @Override
    public PersonalData clone() throws CloneNotSupportedException {
//        PersonalData retVal = new PersonalData(id);
        PersonalData retVal = new PersonalData(id);
        retVal.setFirstName(getFirstName());
        retVal.setSecondName(getSecondName());
        retVal.setMiddleName(getMiddleName());
        retVal.setPassport(getPassport());
        retVal.setGender(getGender());
        retVal.setBirthDate(getBirthDate());
        retVal.setBirthPlace(getBirthPlace());
        retVal.setNationality(getNationality());
        retVal.setRegAddress(getRegAddress());
        retVal.setResAddress(getResAddress());
        retVal.setEmail(getEmail());
        retVal.setPhone(getPhone());
        retVal.setAvatar(getAvatar());
        retVal.setParticipations(FXCollections.observableList(getParticipations()));
        retVal.setAttachments(FXCollections.observableList(getAttachments()));
        return retVal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getSecondName() {
        return secondName.get();
    }

    public StringProperty secondNameProperty() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName.set(secondName);
    }

    public String getMiddleName() {
        return middleName.get();
    }

    public StringProperty middleNameProperty() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName.set(middleName);
    }

    public String getFullName() {
        return Utils.concatObjects(Arrays.asList(getFirstName(), getSecondName(), getMiddleName()), " ");
    }

    public String getPassport() {
        return passport.get();
    }

    public StringProperty passportProperty() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport.set(passport);
    }

    public Classifier getGender() {
        return gender.get();
    }

    public ObjectProperty<Classifier> genderProperty() {
        return gender;
    }

    public void setGender(Classifier gender) {
        this.gender.set(gender);
    }

    public LocalDate getBirthDate() {
        return birthDate.get();
    }

    public ObjectProperty<LocalDate> birthDateProperty() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate.set(birthDate);
    }

    public Integer getAge() {
        return getBirthDate().until(LocalDate.now()).getYears();
    }

    public String getBirthPlace() {
        return birthPlace.get();
    }

    public StringProperty birthPlaceProperty() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace.set(birthPlace);
    }

    public String getNationality() {
        return nationality.get();
    }

    public StringProperty nationalityProperty() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality.set(nationality);
    }

    public String getRegAddress() {
        return regAddress.get();
    }

    public StringProperty regAddressProperty() {
        return regAddress;
    }

    public void setRegAddress(String regAddress) {
        this.regAddress.set(regAddress);
    }

    public String getResAddress() {
        return resAddress.get();
    }

    public StringProperty resAddressProperty() {
        return resAddress;
    }

    public void setResAddress(String resAddress) {
        this.resAddress.set(resAddress);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public AppSpecImage getAvatar() {
        return avatar.get();
    }

    public ObjectProperty<AppSpecImage> avatarProperty() {
        return avatar;
    }

    public void setAvatar(AppSpecImage avatar) {
        this.avatar.set(avatar);
    }

    public ObservableList<Classifier> getParticipations() {
        return participations.get();
    }

    public ListProperty<Classifier> participationsProperty() {
        return participations;
    }

    public void setParticipations(ObservableList<Classifier> participations) {
        this.participations.set(participations);
    }

    public ObservableList<UploadFile> getAttachments() {
        return attachments.get();
    }

    public ListProperty<UploadFile> attachmentsProperty() {
        return attachments;
    }

    public void setAttachments(ObservableList<UploadFile> attachments) {
        this.attachments.set(attachments);
    }
}
