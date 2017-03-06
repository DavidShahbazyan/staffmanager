package arm.davsoft.staffmanager.service;

import arm.davsoft.staffmanager.domain.*;
import arm.davsoft.staffmanager.helpers.ConnectionProvider;
import javafx.collections.FXCollections;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by david on 8/4/16.
 */
public final class PersonalDataService {
    private static final String LOAD_ALL_MEMBERS = "SELECT mmbr.memberId, mmbr.firstName, mmbr.lastName, mmbr.middleName, mmbr.birthDate, mmbr.birthPlace, mmbr.nationlity, mmbr.passport, mmbr.addressRegistration, mmbr.addressResidential, mmbr.phone, mmbr.email, mmbr.avatar, gndr.id AS genderId, gndr.name AS genderName FROM Members AS mmbr INNER JOIN C_Gender AS gndr ON gndr.id = mmbr.genderId";
    private static final String LOAD_MEMBER_DATA_BY_ID = LOAD_ALL_MEMBERS + " WHERE mmbr.memberId = ?";
    private static final String LOAD_MEMBER_ATTACHMENTS = "SELECT * FROM Attachments WHERE memberId=?";
    private static final String LOAD_MEMBER_PARTICIPATIONS = "SELECT p.id AS participationId, p.name AS participationName FROM MemberParticipations AS mp INNER JOIN C_Participation AS p ON p.id = mp.participationId WHERE memberId=?";
    private static final String ADD_MEMBER = "INSERT INTO Members (firstName, lastName, middleName, birthDate, birthPlace, nationlity, passport, addressRegistration, addressResidential, email, genderId, phone, avatar) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_MEMBER = "UPDATE Members SET firstName=?, lastName=?, middleName=?, birthDate=?, birthPlace=?, nationlity=?, passport=?, addressRegistration=?, addressResidential=?, email=?, genderId=?, phone=?, avatar=? WHERE memberId=?";
    private static final String DELETE_MEMBER = "DELETE FROM Members WHERE memberId=?";
    private static final String ADD_MEMBER_ATTACHMENT = "INSERT INTO Attachments (memberId, data, name) VALUES (?, ?, ?)";
    private static final String ADD_MEMBER_PARTICIPATION = "INSERT INTO MemberParticipations (memberId, participationId) VALUES (?, ?)";
    private static final String DELETE_ALL_ATTACHMENTS = "DELETE FROM Attachments WHERE memberId=?";
    private static final String DELETE_ALL_PARTICIPATIONS = "DELETE FROM MemberParticipations WHERE memberId=?";

    private PersonalDataService() { /* DO NOTHING */ }

    public static PersonalData loadPersonalDataById(Integer selfId) throws SQLException {
        PersonalData retVal = null;
        PreparedStatement pst = ConnectionProvider.getInstance().getConnection().prepareStatement(LOAD_MEMBER_DATA_BY_ID);
        pst.setInt(1, selfId);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            retVal = new PersonalData(rs.getInt("memberId"));
            retVal.setFirstName(rs.getString("firstName"));
            retVal.setSecondName(rs.getString("lastName"));
            retVal.setMiddleName(rs.getString("middleName"));
            retVal.setPassport(rs.getString("passport"));
            retVal.setGender(new ClassifierImpl(rs.getInt("genderId"), rs.getString("genderName")));
            retVal.setBirthDate(new Date(rs.getLong("birthDate")).toLocalDate());
            retVal.setBirthPlace(rs.getString("birthPlace"));
            retVal.setNationality(rs.getString("nationlity"));
            retVal.setRegAddress(rs.getString("addressRegistration"));
            retVal.setResAddress(rs.getString("addressResidential"));
            retVal.setEmail(rs.getString("email"));
            retVal.setPhone(rs.getString("phone"));
            retVal.setAvatar(new AppSpecImage(rs.getBytes("avatar")));
        }
        rs.close();
        pst.close();

        pst = ConnectionProvider.getInstance().getConnection().prepareStatement(LOAD_MEMBER_ATTACHMENTS);
        pst.setInt(1, selfId);
        rs = pst.executeQuery();
        List<UploadFile> attachments = new ArrayList<>();
        while (rs.next()) {
            attachments.add(new UploadFile(rs.getInt("attachmentId"), rs.getBytes("data"), rs.getString("name")));
        }
        retVal.setAttachments(FXCollections.observableList(attachments));
        rs.close();
        pst.close();

        pst = ConnectionProvider.getInstance().getConnection().prepareStatement(LOAD_MEMBER_PARTICIPATIONS);
        pst.setInt(1, selfId);
        rs = pst.executeQuery();
        List<Classifier> participations = new ArrayList<>();
        while (rs.next()) {
            participations.add(new ClassifierImpl(rs.getInt("participationId"), rs.getString("participationName")));
        }
        retVal.setParticipations(FXCollections.observableList(participations));
        rs.close();
        pst.close();

        return retVal;
    }

    public static List<PersonalData> loadAllMembers() throws SQLException {
        List<PersonalData> retVal = new ArrayList<>();
        PreparedStatement pst = ConnectionProvider.getInstance().getConnection().prepareStatement(LOAD_ALL_MEMBERS);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            PersonalData data = new PersonalData(rs.getInt("memberId"));
            data.setFirstName(rs.getString("firstName"));
            data.setSecondName(rs.getString("lastName"));
            data.setMiddleName(rs.getString("middleName"));
            data.setPassport(rs.getString("passport"));
            data.setGender(new ClassifierImpl(rs.getInt("genderId"), rs.getString("genderName")));
            data.setBirthDate(new Date(rs.getLong("birthDate")).toLocalDate());
            data.setBirthPlace(rs.getString("birthPlace"));
            data.setNationality(rs.getString("nationlity"));
            data.setRegAddress(rs.getString("addressRegistration"));
            data.setResAddress(rs.getString("addressResidential"));
            data.setEmail(rs.getString("email"));
            data.setPhone(rs.getString("phone"));
            data.setAvatar(new AppSpecImage(rs.getBytes("avatar")));
            retVal.add(data);
        }
        rs.close();
        pst.close();
        return retVal;
    }

    public static void savePersonalData(PersonalData personalData) throws SQLException {
        PreparedStatement pst;

        if (personalData.isNew()) {
            pst = ConnectionProvider.getInstance().getConnection().prepareStatement(ADD_MEMBER, Statement.RETURN_GENERATED_KEYS);
        } else {
            pst = ConnectionProvider.getInstance().getConnection().prepareStatement(UPDATE_MEMBER);
            pst.setInt(14, personalData.getId());
        }

        pst.setString(1, personalData.getFirstName());   // firstName
        pst.setString(2, personalData.getSecondName());   // lastName
        pst.setString(3, personalData.getMiddleName());   // middleName
        pst.setDate(4, Date.valueOf(personalData.getBirthDate()));     // birthDate
        pst.setString(5, personalData.getBirthPlace());   // birthPlace
        pst.setString(6, personalData.getNationality());   // nationality
        pst.setString(7, personalData.getPassport());   // passport
        pst.setString(8, personalData.getRegAddress());   // addressRegistration
        pst.setString(9, personalData.getResAddress());   // addressResidential
        pst.setString(10, personalData.getEmail());  // email
        pst.setInt(11, personalData.getGender().getId());     // genderId
        pst.setString(12, personalData.getPhone());  // phone2
        pst.setBytes(13, personalData.getAvatar().getBytes());  // avatar

        pst.executeUpdate();
        if (personalData.isNew()) {
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                personalData.setId(rs.getInt(1));
            }
            rs.close();
        }
        pst.close();

        clearAllMemberFunctionalData(personalData);

        pst = ConnectionProvider.getInstance().getConnection().prepareStatement(ADD_MEMBER_ATTACHMENT);
        for (UploadFile item : personalData.getAttachments()) {
            pst.setInt(1, personalData.getId());
            pst.setBytes(2, item.getBytes());
            pst.setString(3, item.getName());
            pst.execute();
        }
        pst.close();

        pst = ConnectionProvider.getInstance().getConnection().prepareStatement(ADD_MEMBER_PARTICIPATION);
        for (Classifier item : personalData.getParticipations()) {
            pst.setInt(1, personalData.getId());
            pst.setInt(2, item.getId());
            pst.execute();
        }
        pst.close();
    }

    public static void deletePersonalData(PersonalData personalData) throws SQLException {
        clearAllMemberFunctionalData(personalData);
        PreparedStatement pst = ConnectionProvider.getInstance().getConnection().prepareStatement(DELETE_MEMBER);
        pst.setInt(1, personalData.getId());
        pst.execute();
        pst.close();
    }

    private static void clearAllMemberFunctionalData(PersonalData personalData) throws SQLException {
        for (String sql : Arrays.asList(DELETE_ALL_ATTACHMENTS, DELETE_ALL_PARTICIPATIONS)) {
            PreparedStatement pst = ConnectionProvider.getInstance().getConnection().prepareStatement(sql);
            pst.setInt(1, personalData.getId());
            pst.execute();
            pst.close();
        }
    }
}
