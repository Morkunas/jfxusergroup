package de.saxsys.fxarmville.presentation;

import javafx.animation.FadeTransitionBuilder;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransitionBuilder;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import de.saxsys.fxarmville.model.Frucht;

public class FXrucht extends Parent {

	private final ImageView imageView = ImageViewBuilder.create().fitHeight(50)
			.fitWidth(50).build();

	private final Frucht frucht;

	public FXrucht(final Frucht frucht) {

		this.frucht = frucht;

		// Init
		imageView.setImage(frucht.getBild());
		getChildren().add(imageView);

		// Erntelistener
		erzeugeMouseListenerZumErnten();

		// Starten
		starteWachstum();
	}

	private void erzeugeMouseListenerZumErnten() {
		setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(final MouseEvent event) {
				frucht.ernten();
			}
		});
	}

	private void starteWachstum() {
		final DoubleBinding scaleBinding = Bindings.min(1.0, frucht
				.aktuelleLebenszeitProperty()
				.divide(frucht.getLebenszeit() / 2));
		scaleXProperty().bind(scaleBinding);
		scaleYProperty().bind(scaleBinding);

		frucht.istReifProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(
					final ObservableValue<? extends Boolean> observable,
					final Boolean oldValue, final Boolean newValue) {
				if (newValue) {
					setEffect(new Glow(10));
				} else {
					setEffect(null);
				}
			}
		});

		// final DoubleBinding standDerFaulung = Bindings.max(
		// Bindings.subtract(2, frucht.aktuelleLebenszeitProperty()
		// .divide(frucht.lebenszeitProperty()).multiply(2)), 0d);
		// opacityProperty().bind(standDerFaulung);
		frucht.istFauligProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(
					final ObservableValue<? extends Boolean> observable,
					final Boolean oldValue, final Boolean newValue) {
				RotateTransitionBuilder.create().node(FXrucht.this)
						.toAngle(360).cycleCount(Timeline.INDEFINITE).rate(0.1)
						.interpolator(Interpolator.LINEAR).build().play();
				FadeTransitionBuilder.create().node(FXrucht.this).toValue(0)
						.duration(Duration.seconds(frucht.getLebenszeit() / 2))
						.build().play();
			}
		});

	}

}
