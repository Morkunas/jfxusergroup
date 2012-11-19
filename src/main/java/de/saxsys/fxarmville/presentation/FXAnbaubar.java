package de.saxsys.fxarmville.presentation;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.ScaleTransitionBuilder;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import de.saxsys.fxarmville.model.fruits.Anbaubar;

public class FXAnbaubar extends Parent {

	private ImageView imageView;
	private Anbaubar anbaubar;

	public FXAnbaubar(Anbaubar anbaubar) {
		this.anbaubar = anbaubar;
		imageView = ImageViewBuilder.create().image(anbaubar.getBild())
				.fitHeight(50).fitWidth(50).build();
		getChildren().add(imageView);
		initAltern();
	}

	private void initAltern() {
		int wachsdauer = anbaubar.getWachsdauer();
		ScaleTransition scaling = ScaleTransitionBuilder.create().node(this)
				.fromX(0).fromY(0).toX(1.0).toY(1.0)
				.duration(Duration.seconds(wachsdauer))
				.onFinished(onScalingFinished).build();
		scaling.play();
	}

	private void startFadeOut() {
		Timeline timeline = TimelineBuilder
				.create()
				.keyFrames(
						new KeyFrame(Duration.seconds(1), new KeyValue(
								imageView.opacityProperty(), 0)))
				.onFinished(onFadeOutFinished).build();

		timeline.play();
	}

	private EventHandler<ActionEvent> onScalingFinished = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent arg0) {
			startFadeOut();
		}
	};

	private EventHandler<ActionEvent> onFadeOutFinished = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent arg0) {
			// TODO
		}
	};
}
