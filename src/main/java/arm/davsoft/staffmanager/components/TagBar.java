package arm.davsoft.staffmanager.components;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author David Shahbazyan
 * @since Sep 10, 2016
 */
public class TagBar<T> extends FlowPane {
    /**
     * Default value is <b>false</b>
     */
    private BooleanProperty readOnly;
    private final ListProperty<T> tags;

    public TagBar() {
        getStyleClass().setAll("tag-bar");
        getStylesheets().add(Application.getUserAgentStylesheet());

        readOnly = new BooleanPropertyBase(false) {
            @Override
            public Object getBean() {
                return TagBar.this;
            }

            @Override
            public String getName() {
                return "readOnly";
            }
        };

        tags = new SimpleListProperty<>(FXCollections.observableArrayList());
        tags.addListener((ListChangeListener.Change<? extends T> change) -> {
            while (change.next()) {
                if (change.wasPermutated()) {
                    List<Node> newSublist = new ArrayList<>(change.getTo() - change.getFrom());
                    for (int i = change.getFrom(), end = change.getTo(); i < end; i++) {
                        newSublist.add(null);
                    }
                    for (int i = change.getFrom(), end = change.getTo(); i < end; i++) {
                        newSublist.set(change.getPermutation(i), getChildren().get(i));
                    }
                    getChildren().subList(change.getFrom(), change.getTo()).clear();
                    getChildren().addAll(change.getFrom(), newSublist);
                } else {
                    if (change.wasRemoved()) {
                        getChildren().subList(change.getFrom(), change.getFrom() + change.getRemovedSize()).clear();
                    }
                    if (change.wasAdded()) {
                        getChildren().addAll(change.getFrom(), change.getAddedSubList().stream().map(Tag::new).collect(Collectors.toList()));
                    }
                }
            }
        });
    }

    private class Tag extends HBox {
        public Tag(T tag) {
            getStyleClass().setAll("tag");
//            setMargin(this, new Insets(5));
            setAlignment(Pos.CENTER_LEFT);
            setSpacing(5);
//            setMinHeight(30);
//            setMaxHeight(30);
//            setPrefHeight(30);
            Text text = new Text(tag.toString());
            text.getStyleClass().addAll("text");
            getChildren().add(text);
            if (!readOnly.get()) {
                Button removeButton = new Button();
                removeButton.getStyleClass().addAll("icn-remove");
                removeButton.setOnAction((evt) -> tags.remove(tag));
                getChildren().addAll(removeButton);
            }
        }
    }

    public boolean isReadOnly() {
        return readOnly.get();
    }

    public BooleanProperty readOnlyProperty() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly.set(readOnly);
    }

    public ObservableList getTags() {
        return tags.get();
    }

    public ListProperty<T> tagsProperty() {
        return tags;
    }

    public void setTags(ObservableList tags) {
        this.tags.set(tags);
    }
}
