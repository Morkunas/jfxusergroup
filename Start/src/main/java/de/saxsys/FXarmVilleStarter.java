package de.saxsys;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import de.saxsys.fxarmville.model.Farm;
import de.saxsys.fxarmville.presentation.FXarm;

public class FXarmVilleStarter extends Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// Pane rootPane = (Pane) FXMLLoader.load(FXarmVilleStarter.class
		// .getResource("/root.fxml"));

		final Farm farm = new Farm();
		final FXarm fxFarm = new FXarm(farm);

		Scene rootScene = new Scene(fxFarm, 1000, 500);

		stage.setScene(rootScene);

		stage.setTitle("FXarmVille");
		stage.show();
	}

}
