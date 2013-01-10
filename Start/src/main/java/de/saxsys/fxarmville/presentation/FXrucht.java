package de.saxsys.fxarmville.presentation;

import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
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
		
	}

	private void starteWachstum() {

	}

}
