package arm.davsoft.staffmanager.service;

import arm.davsoft.staffmanager.domain.Classifier;
import arm.davsoft.staffmanager.domain.ClassifierImpl;
import arm.davsoft.staffmanager.enums.MetaCategory;
import arm.davsoft.staffmanager.helpers.ConnectionProvider;

import javax.annotation.Nonnull;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by david on 8/4/16.
 */
public final class ClassifierService {
    private static final String LOAD_CLASSIFIER_BY_ID = "SELECT * FROM @@TABLE_NAME WHERE id=?;";
    private static final String LOAD_CLASSIFIERS = "SELECT * FROM @@TABLE_NAME;";

    private ClassifierService() { /* DO NOTHING */ }

    public static Classifier loadClassifierById(@Nonnull MetaCategory metaCategory, Integer selfId) throws SQLException {
        Objects.requireNonNull(metaCategory);
        Classifier retVal = null;
        PreparedStatement pst = ConnectionProvider.getInstance().getConnection().prepareStatement(LOAD_CLASSIFIER_BY_ID.replaceAll("@@TABLE_NAME", metaCategory.getMetaCategoryId()));
        pst.setInt(1, selfId);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            retVal = new ClassifierImpl(rs.getInt("id"), rs.getString("name"));
        }
        rs.close();
        pst.close();
        return retVal;
    }

    public static List<Classifier> loadClassifiers(@Nonnull MetaCategory metaCategory) throws SQLException {
        Objects.requireNonNull(metaCategory);
        List<Classifier> retVal = new ArrayList<>();
        PreparedStatement pst = ConnectionProvider.getInstance().getConnection().prepareStatement(LOAD_CLASSIFIERS.replaceAll("@@TABLE_NAME", metaCategory.getMetaCategoryId()));
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            retVal.add(new ClassifierImpl(rs.getInt("id"), rs.getString("name")));
        }
        rs.close();
        pst.close();
        return retVal;
    }

}
