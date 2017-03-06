package arm.davsoft.staffmanager.domain;

import javax.annotation.Nonnull;

/**
 * Created by david on 8/4/16.
 */
public class ClassifierImpl implements Classifier {
    Integer id;
    String name;

    public ClassifierImpl(@Nonnull Integer id) {
        this.id = id;
    }

    public ClassifierImpl(@Nonnull Integer id, @Nonnull String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClassifierImpl that = (ClassifierImpl) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return getName();
    }

    @Nonnull
    public Integer getId() {
        return id;
    }

    @Nonnull
    public String getName() {
        return name;
    }

}
