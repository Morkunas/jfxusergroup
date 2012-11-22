package de.saxsys.fxarmville.presentation;

import javafx.animation.Animation.Status;
import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.BooleanProperty;
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
import de.saxsys.fxarmville.model.fruits.Anbaubar;

public class FXAnbaubar extends Parent {

	private ImageView imageView = ImageViewBuilder.create().fitHeight(50)
			.fitWidth(50).build();

	private Anbaubar anbaubar;

	private BooleanProperty geerntetProperty = new SimpleBooleanProperty();
	private BooleanProperty eingegangenProperty = new SimpleBooleanProperty();

	public FXAnbaubar(Anbaubar anbaubar) {

		this.anbaubar = anbaubar;

		// Init
		imageView.setImage(anbaubar.getBild());
		getChildren().add(imageView);

		// Erntelistener
		createMouseListenerZumErnten();

		// Starten
		startWachstum();
	}

	private void startWachstum() {
		// Prozentsatz der Reifung = Größe der Frucht
		final DoubleBinding standDerReifung = anbaubar.reifegradProperty()
				.divide(anbaubar.wachsdauerProperty());
		scaleXProperty().bind(standDerReifung);
		scaleYProperty().bind(standDerReifung);

		// Wenn Frucht reif ist, bekommt sie glow
		anbaubar.istReifProperty().addListener(new ChangeListener<Boolean>() {
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
		anbaubar.istFauligProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean arg1, Boolean arg2) {
				FadeTransition eingehen = FadeTransitionBuilder.create()
						.node(FXAnbaubar.this).toValue(0.0)
						.duration(Duration.seconds(1)).build();
				eingehen.play();
				// TRICK - HOHOHO , kein event on finisht nötig
				eingegangenProperty.bind(Bindings.equal(
						eingehen.statusProperty(), Status.STOPPED));
			}

		});
	}

	// Statistiken

	private void createMouseListenerZumErnten() {
		setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				anbaubar.ernten();
				neuerAnbau();
			}
		});
	}

	private void neuerAnbau() {
		geerntetProperty.set(true);
	}

	public BooleanProperty geerntetProperty() {
		return geerntetProperty;
	}

	public BooleanProperty eingegangenProperty() {
		return eingegangenProperty;
	}

}
