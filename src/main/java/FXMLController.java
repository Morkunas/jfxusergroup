import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

public class FXMLController implements Initializable {

	@FXML
	private Button launchRockets;

	@FXML
	private ProgressBar progressBar;

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {

		final Timeline timeline = TimelineBuilder
				.create()
				.keyFrames(
						new KeyFrame(Duration.seconds(10), new KeyValue(
								progressBar.progressProperty(), 1.0)))
				.onFinished(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						launchRockets.setText("Now it is too late...");
						launchRockets.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent arg0) {
							}
						});
					}
				}).build();

		if (launchRockets != null) {
			launchRockets.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					launchRockets.setText("Oh oh!");
					timeline.play();
				}
			});
		}
	}

}
