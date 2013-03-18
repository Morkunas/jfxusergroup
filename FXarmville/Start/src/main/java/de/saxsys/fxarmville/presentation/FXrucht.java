package de.saxsys.fxarmville.presentation;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.event.EventHandler;
import javafx.scene.Parent;
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

	private void starteWachstum() {
		// **** BEGIN LIVE CODING ****

		faulen();
	}

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	private void faulen() {
		final DoubleBinding standDerFaulung = Bindings.min(Bindings.subtract(2,
				frucht.aktuelleLebenszeitProperty().multiply(2)), 1.0);
		opacityProperty().bind(standDerFaulung);
	}
}
