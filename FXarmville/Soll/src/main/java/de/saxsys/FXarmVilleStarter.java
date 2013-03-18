package de.saxsys;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FXarmVilleStarter extends Application {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage stage) throws Exception {
		final Pane rootPane = (Pane) FXMLLoader.load(FXarmVilleStarter.class
				.getResource("/root.fxml"));

		final Scene rootScene = new Scene(rootPane, 1000, 500);
		rootScene.getStylesheets().add(
				ClassLoader.getSystemResource("style.css").toString());

		stage.setScene(rootScene);
		stage.setTitle("FXarmVille");
		stage.show();

		// final Pane rootPane = VBoxBuilder.create().spacing(10).build();
		// final Slider fxruchtSlider = SliderBuilder.create()
		// .blockIncrement(0.05).min(0).max(1).build();
		//
		// final Farm farm = new Farm();
		// final FXarm fxArm = new FXarm(farm);
		//
		// for (final Frucht frucht : farm.angebautProperty()) {
		// frucht.aktuelleLebenszeitProperty().bind(
		// fxruchtSlider.valueProperty());
		// }
		//
		// rootPane.getChildren().addAll(fxArm, fxruchtSlider);
		//
		// final Scene rootScene = new Scene(rootPane, 500, 530);
		// rootScene.getStylesheets().add(
		// ClassLoader.getSystemResource("style.css").toString());
		//
		// stage.setScene(rootScene);
		// stage.setTitle("FXarmVille");
		// stage.show();
	}

}
