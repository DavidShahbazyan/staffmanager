package arm.davsoft.staffmanager.stages;

import arm.davsoft.staffmanager.Main;
import arm.davsoft.staffmanager.utils.ResourceManager;
import com.sun.org.apache.xerces.internal.impl.io.UTF8Reader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by david on 8/1/16.
 */
public class CustomStage extends Stage {
    protected final Stage parentStage;
    protected FXMLLoader fxmlLoader;
    protected Queue<Consumer> beforeStageCloseActions;

    public CustomStage() {
        this(null);
    }

    public CustomStage(Stage parentStage) {
        Main.LOGGER.info(String.format("Loading stage %s.", getClass().getSimpleName()));
        this.parentStage = parentStage;
        this.beforeStageCloseActions = new ArrayDeque<>();
    }

    @Override
    public void close() {
        beforeStageClose();
        if (parentStage == null) {
            Main.exit();
        } else {
            super.close();
            this.parentStage.requestFocus();
        }
    }

    @Override
    public void hide() {
        Main.LOGGER.info(String.format("Unloading stage %s.", getClass().getSimpleName()));
        super.hide();
    }

    void initStage(String title, Image icon) throws Exception {
        setTitle(title);
        getIcons().add(icon);
        setScene(new Scene(new VBox()));
        getScene().getStylesheets().add(ResourceManager.getUIThemeStyle());
        initFXMLLoader();
    }

    private void initFXMLLoader() throws Exception {
        fxmlLoader = new FXMLLoader();
//        fxmlLoader.setResources(ResourceManager.getBundle("properties/messages"));
        fxmlLoader.setResources(ResourceBundle.getBundle("properties/messages"));
//        fxmlLoader.setResources(ResourceBundle.getBundle("properties/messages", Locale.CHINESE));
    }

    public void fixAllDimensionsTo(double width, double height) {
        setResizable(false);
        fixMinDimensionsTo(width, height);
        fixMaxDimensionsTo(width, height);
    }

    public void fixMinDimensionsTo(double width, double height) {
        setMinWidth(width);
        setMinHeight(height);
    }

    public void fixMaxDimensionsTo(double width, double height) {
        setMaxWidth(width);
        setMaxHeight(height);
    }

    public final void showAndFocus() {
        show();
        requestFocus();
    }

    protected void beforeStageClose() {
        while (!this.beforeStageCloseActions.isEmpty()) {
            this.beforeStageCloseActions.poll().accept(null);
        }
    }

    public Queue<Consumer> getBeforeStageCloseActions() {
        return beforeStageCloseActions;
    }

    public void addBeforeStageCloseAction(Consumer consumer) {
        this.beforeStageCloseActions.offer(consumer);
    }
}
