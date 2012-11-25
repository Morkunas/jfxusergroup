package de.saxsys.fxarmville.presentation;

import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import de.saxsys.fxarmville.model.Frucht;

public class FXrucht extends Parent {

	private ImageView imageView = ImageViewBuilder.create().fitHeight(50)
			.fitWidth(50).build();

	private Frucht frucht;

	public FXrucht(Frucht frucht) {

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
		// FIXME
	}

	private void starteWachstum() {
		// FIXME
		// Wachstum visualisieren

		// Wenn Frucht reif ist, bekommt sie glow

		// Wenn sie faulig wird
	}
}
