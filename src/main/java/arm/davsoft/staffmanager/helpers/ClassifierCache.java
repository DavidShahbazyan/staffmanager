package arm.davsoft.staffmanager.helpers;

import arm.davsoft.staffmanager.domain.Classifier;
import arm.davsoft.staffmanager.enums.MetaCategory;
import arm.davsoft.staffmanager.service.ClassifierService;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by david on 8/4/16.
 */
public final class ClassifierCache {
    private final Map<String, List<Classifier>> cache = new HashMap<>();

    private ClassifierCache() {
        if (ClassifierCacheInstanceHolder.INSTANCE != null) {
            throw new IllegalStateException("The Classifier Cache is already initialised.");
        }
    }

    public static ClassifierCache getInstance() {
        return ClassifierCacheInstanceHolder.INSTANCE;
    }

    private static final class ClassifierCacheInstanceHolder {
        private static final ClassifierCache INSTANCE = new ClassifierCache();
    }

    private Classifier loadClassifier(MetaCategory metaCategory, Integer selfId) {
        Classifier retVal = null;
        try {
            retVal = ClassifierService.loadClassifierById(metaCategory, selfId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retVal;
    }

    private List<Classifier> loadClassifiers(MetaCategory metaCategory) {
        List<Classifier> retVal = null;
        try {
            retVal = ClassifierService.loadClassifiers(metaCategory);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retVal;
    }

    private List<Classifier> getClassifiersFromCacheOrLoad(MetaCategory metaCategory) {
        if (!cache.containsKey(metaCategory.getMetaCategoryId())) {
            cache.put(metaCategory.getMetaCategoryId(), loadClassifiers(metaCategory));
        }
        return cache.get(metaCategory.getMetaCategoryId());
    }

    public Classifier getGender(Integer selfId) {
        return loadClassifier(MetaCategory.GENDER, selfId);
    }
    public List<Classifier> getGenders() {
        return getClassifiersFromCacheOrLoad(MetaCategory.GENDER);
    }
    public List<Classifier> getParticipations() {
        return getClassifiersFromCacheOrLoad(MetaCategory.PARTICIPATION);
    }
}
