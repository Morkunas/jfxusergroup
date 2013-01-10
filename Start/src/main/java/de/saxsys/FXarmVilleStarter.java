package de.saxsys;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.SliderBuilder;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBoxBuilder;
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
				
		Pane rootPane = VBoxBuilder.create().spacing(10).build();
		Slider fxruchtSlider = SliderBuilder.create().blockIncrement(0.05).min(0).max(1).build();
		
		Farm farm = new Farm();
		FXarm fxArm = new FXarm(farm);
		
		// Slider an Lebenszeit binden
		
		rootPane.getChildren().addAll(fxArm, fxruchtSlider);

		Scene rootScene = new Scene(rootPane, 500, 530);
		rootScene.getStylesheets().add(
				ClassLoader.getSystemResource("style.css").toString());

		stage.setScene(rootScene);
		stage.setTitle("FXarmVille");
		stage.show();
	}

}
