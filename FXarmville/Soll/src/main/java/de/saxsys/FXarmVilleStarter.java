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
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Pane rootPane = (Pane) FXMLLoader.load(FXarmVilleStarter.class
				.getResource("/root.fxml"));

		Scene rootScene = new Scene(rootPane, 1000, 500);
		rootScene.getStylesheets().add(
				ClassLoader.getSystemResource("style.css").toString());

		stage.setScene(rootScene);
		stage.setTitle("FXarmVille");
		stage.show();
		
//		Pane rootPane = VBoxBuilder.create().spacing(10).build();
////		Slider fxruchtSlider = SliderBuilder.create().blockIncrement(0.05).min(0).max(1).build();
//		
//		Farm farm = new Farm();
//		FXarm fxArm = new FXarm(farm);
//		
////		for (Frucht frucht : farm.angebautProperty()) {
////			frucht.aktuelleLebenszeitProperty().bind(fxruchtSlider.valueProperty().multiply(frucht.getLebenszeit()));
////		}
//		
//		rootPane.getChildren().addAll(fxArm);
//
//		Scene rootScene = new Scene(rootPane, 500, 530);
//		rootScene.getStylesheets().add(
//				ClassLoader.getSystemResource("style.css").toString());
//
//		stage.setScene(rootScene);
//		stage.setTitle("FXarmVille");
//		stage.show();
	}

}
