package arm.davsoft.staffmanager.components;

import arm.davsoft.staffmanager.skin.StatusBarSkin;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 * @author David Shahbazyan
 * @since Jan 24, 2016
 */
public class StatusBar extends Control {
    private final StringProperty text = new SimpleStringProperty(this, "text", "Ready.");
    private final ObjectProperty<Node> graphic = new SimpleObjectProperty<>(this, "graphic");
    private final DoubleProperty progress = new SimpleDoubleProperty(this, "progress");
    private final ObservableList<Node> leftItems = FXCollections.observableArrayList();
    private final ObservableList<Node> rightItems = FXCollections.observableArrayList();

    private Task currentTask;

    public StatusBar() {
        getStyleClass().add("status-bar");
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new StatusBarSkin(this);
    }

    public String getText() {
        return text.get();
    }
    public StringProperty textProperty() {
        return text;
    }
    public void setText(String text) {
        this.text.set(text);
    }

    public final ObjectProperty<Node> graphicProperty() {
        return graphic;
    }
    public final Node getGraphic() {
        return graphicProperty().get();
    }
    public final void setGraphic(Node node) {
        graphicProperty().set(node);
    }

    public double getProgress() {
        return progress.get();
    }
    public DoubleProperty progressProperty() {
        return progress;
    }
    public void setProgress(double progress) {
        this.progress.set(progress);
    }

    public ObservableList<Node> getLeftItems() {
        return leftItems;
    }

    public ObservableList<Node> getRightItems() {
        return rightItems;
    }

    public void setTask(Task task) {
        this.currentTask = task;
        this.textProperty().bind(task.messageProperty());
        this.progressProperty().bind(task.progressProperty());
    }

    public void cancelTask() {
        if (this.currentTask != null && this.currentTask.isRunning()) {
            this.currentTask.cancel();
        }
        reset();
    }

    public void reset() {
        this.currentTask = null;
        this.textProperty().unbind();
        this.progressProperty().unbind();
        this.textProperty().setValue("Ready.");
        this.progressProperty().setValue(null);
    }
}
