package de.saxsys.fxarmville.presentation;

import javafx.animation.Animation.Status;
import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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

// TODO: geerntet + eingegangen evtl. ins Frucht Modell? 
public class FXrucht extends Parent {

	private ImageView imageView = ImageViewBuilder.create().fitHeight(50)
			.fitWidth(50).build();

	private Frucht frucht;

	private BooleanProperty geerntetProperty = new SimpleBooleanProperty();
	private BooleanProperty eingegangenProperty = new SimpleBooleanProperty();

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

	private void starteWachstum() {
		// Prozentsatz der Reifung = Größe der Frucht
		final DoubleBinding standDerReifung = frucht.reifegradProperty()
				.divide(frucht.wachsdauerProperty());
		scaleXProperty().bind(standDerReifung);
		scaleYProperty().bind(standDerReifung);

		// Wenn Frucht reif ist, bekommt sie glow
		frucht.istReifProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> bean,
					Boolean istReifAlt, Boolean istReifNeu) {
				if (istReifNeu) {
					setEffect(new Glow(10));
				} else {
					setEffect(null);
				}
			}
		});

		// Wenn frucht faulig ist, geht sie ein
		frucht.istFauligProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean arg1, Boolean arg2) {
				FadeTransition eingehen = FadeTransitionBuilder.create()
						.node(FXrucht.this).toValue(0.0)
						.duration(Duration.seconds(1)).build();
				eingehen.play();
				// TRICK - HOHOHO , kein event on finisht nötig
				eingegangenProperty.bind(Bindings.equal(
						eingehen.statusProperty(), Status.STOPPED));
			}

		});
	}

	// Statistiken

	private void erzeugeMouseListenerZumErnten() {
		setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				frucht.ernten();
				geerntetProperty.set(true);
			}
		});
	}

	public ReadOnlyBooleanProperty geerntetProperty() {
		return geerntetProperty;
	}

	public ReadOnlyBooleanProperty eingegangenProperty() {
		return eingegangenProperty;
	}

}
