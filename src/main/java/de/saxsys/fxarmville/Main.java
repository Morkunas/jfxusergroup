package de.saxsys.fxarmville;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import de.saxsys.fxarmville.presentation.FXFarm;

public class Main extends Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		FXFarm farm = new FXFarm(DataMock.getData());

		Scene rootScene = new Scene(farm, 700, 500);

		stage.setScene(rootScene);

		stage.show();

	}

}
