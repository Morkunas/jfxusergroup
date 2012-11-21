package de.saxsys.fxarmville;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
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
		Pane rootPane = new Pane();
		// TODO rootpane background style zeugs in FXML
		rootPane.setStyle("-fx-background-image:url(\"erde.jpg\");");

		FXFarm farm = new FXFarm(DataMock.getData());
		rootPane.getChildren().add(farm);

		Scene rootScene = new Scene(rootPane, 700, 500);

		stage.setScene(rootScene);

		stage.show();

	}

}
