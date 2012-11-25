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

		Farm farm = new Farm();
		FXarm fxArm = new FXarm(farm);

		Scene rootScene = new Scene(fxArm, 500, 500);
		rootScene.getStylesheets().add(
				ClassLoader.getSystemResource("style.css").toString());

		stage.setScene(rootScene);
		stage.setTitle("FXarmVille");
		stage.show();
	}

}
