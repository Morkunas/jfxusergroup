package de.saxsys.fxarmville.presentation;

import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.ScaleTransitionBuilder;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
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

		setOpacity(0.0);

		// Reset Opacity
		ParallelTransition scaleAndFadeIn = new ParallelTransition();

		// Scaling + FadeIn
		ScaleTransition scaling = ScaleTransitionBuilder.create().node(this)
				.fromX(0).fromY(0).toX(1.0).toY(1.0)
				.duration(Duration.seconds(wachsdauer)).build();
		FadeTransition fadeIn = FadeTransitionBuilder.create().node(this)
				.toValue(1.0).duration(Duration.seconds(0.3)).build();
		scaleAndFadeIn.getChildren().addAll(scaling, fadeIn);

		// Sequenziell FadeIn+scale und danach Fadeout
		FadeTransition fadeOut = FadeTransitionBuilder.create().node(this)
				.toValue(0.0).duration(Duration.seconds(1)).build();

		SequentialTransition scaleFadeInAndFadeOut = new SequentialTransition();
		scaleFadeInAndFadeOut.getChildren().addAll(scaleAndFadeIn, fadeOut);
		scaleFadeInAndFadeOut.setOnFinished(onFadeOutFinished);

		scaleFadeInAndFadeOut.play();
	}

	private EventHandler<ActionEvent> onFadeOutFinished = new EventHandler<ActionEvent>() {
		@Override
		public void handle(ActionEvent arg0) {
			initAltern();
		}
	};
}
