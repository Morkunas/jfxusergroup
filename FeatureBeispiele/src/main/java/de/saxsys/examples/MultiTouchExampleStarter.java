package de.saxsys.examples;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import de.saxsys.examples.FXMultitouchExample;

public class MultiTouchExampleStarter extends Application {

    /**
     * @param args
     */
    public static void main(final String[] args) {
        launch(args);
    }

    // **** CODE FÜR FXML später hinzufügen ****
    @Override
    public void start(final Stage stage) throws Exception {
        // Pane rootPane = (Pane) FXMLLoader.load(Main.class
        // .getResource("/root.fxml"));
        final Pane rootPane = new FXMultitouchExample();

        final Scene rootScene = new Scene(rootPane, 1000, 500);

        stage.setScene(rootScene);

        stage.setTitle("FXarmVille");
        stage.setFullScreen(true);
        stage.show();
    }

}
