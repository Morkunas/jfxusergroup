package de.saxsys.fxarmville.presentation;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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

	// **** BEGIN LIVE CODING ****
	private void erzeugeMouseListenerZumErnten() {
		setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(final MouseEvent mouseEvent) {
				if (mouseEvent.getButton() == MouseButton.PRIMARY) {
					frucht.ernten();
				}
			}
		});
	}

	// END

	private void starteWachstum() {
		// **** BEGIN LIVE CODING ****

		final DoubleBinding standDerReifung = Bindings.min(
				frucht.aktuelleLebenszeitProperty().divide(
						frucht.lebenszeitProperty().divide(2)), 1.0);

		scaleXProperty().bind(standDerReifung);
		scaleYProperty().bind(standDerReifung);

		final DoubleBinding standDerFaulung = Bindings.max(
				Bindings.subtract(2, frucht.aktuelleLebenszeitProperty()
						.divide(frucht.lebenszeitProperty()).multiply(2)), 0d);
		opacityProperty().bind(standDerFaulung);

		// Wenn Frucht reif ist, bekommt sie glow
		frucht.istReifProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(final ObservableValue<? extends Boolean> arg0,
					final Boolean alterWert, final Boolean neuerWert) {
				if (neuerWert) {
					setEffect(new Glow(10));
				} else {
					setEffect(null);
				}
			}
		});

		// Wenn sie faulig wird
		// 2 * (1 - akt/gesamt)

		// **** END LIVE CODING ****
	}

}
