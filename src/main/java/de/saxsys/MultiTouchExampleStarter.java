package de.saxsys;

import de.saxsys.examples.FXMultitouchExample;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MultiTouchExampleStarter extends Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	// **** CODE FÜR FXML später hinzufügen ****
	@Override
	public void start(Stage stage) throws Exception {
		// Pane rootPane = (Pane) FXMLLoader.load(Main.class
		// .getResource("/root.fxml"));
		Pane rootPane = new FXMultitouchExample();

		Scene rootScene = new Scene(rootPane, 1000, 500);

		stage.setScene(rootScene);

		stage.setTitle("FXarmVille");
		stage.show();
	}

}
