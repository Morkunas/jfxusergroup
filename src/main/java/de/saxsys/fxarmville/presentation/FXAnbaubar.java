package de.saxsys.fxarmville.presentation;

import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.PauseTransitionBuilder;
import javafx.animation.ScaleTransition;
import javafx.animation.ScaleTransitionBuilder;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.effect.Glow;
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
		startWachstum();
	}

	private void startWachstum() {
		int wachsdauer = anbaubar.getWachsdauer();

		setOpacity(0.0);

		// Schritt 1 Wachsen - Einscalen + einfaden
		ScaleTransition scaling = ScaleTransitionBuilder.create().node(this)
				.fromX(0).fromY(0).toX(1.0).toY(1.0)
				.duration(Duration.seconds(wachsdauer)).build();
		FadeTransition fadeIn = FadeTransitionBuilder.create().node(this)
				.toValue(1.0).duration(Duration.seconds(0.3)).build();

		// Schritt 2 Reif - Pause
		PauseTransition pause = PauseTransitionBuilder.create()
				.duration(Duration.seconds(1)).build();

		// Schritt 3 Eingehen - Fade out
		FadeTransition eingehen = FadeTransitionBuilder.create().node(this)
				.toValue(0.0).duration(Duration.seconds(1)).build();

		// Reset Opacity
		ParallelTransition wachstum = new ParallelTransition();
		wachstum.getChildren().addAll(scaling, fadeIn);
		// Wenn die Frucht reif ist, bekommt sie glow
		wachstum.setOnFinished(createAnbaubarIstReifHandler());

		// Wenn pause vorbei ist, geht frucht ein und verliert glow
		pause.setOnFinished(createAnbaubarIstWelkHandler());

		// Eingehen
		SequentialTransition scaleFadeInAndFadeOut = new SequentialTransition();
		scaleFadeInAndFadeOut.getChildren().addAll(wachstum, pause, eingehen);
		scaleFadeInAndFadeOut.setOnFinished(createAnbaubarWaechstHandler());

		scaleFadeInAndFadeOut.play();
	}

	private EventHandler<ActionEvent> createAnbaubarIstReifHandler() {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				setEffect(new Glow(10));
			}
		};
	}

	private EventHandler<ActionEvent> createAnbaubarIstWelkHandler() {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				setEffect(null);
			}
		};
	}

	private EventHandler<ActionEvent> createAnbaubarWaechstHandler() {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				startWachstum();
			}
		};
	}
}
