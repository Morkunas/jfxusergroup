package de.saxsys.fxarmville.presentation;

import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.animation.PauseTransition;
import javafx.animation.PauseTransitionBuilder;
import javafx.animation.SequentialTransition;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import de.saxsys.fxarmville.model.Korb;
import de.saxsys.fxarmville.model.fruits.Anbaubar;

public class FXAnbaubar extends Parent {

	private ImageView imageView;
	private Anbaubar anbaubar;
	private Korb korb;

	private SequentialTransition lebenszyklus;
	private BooleanProperty geerntetProperty = new SimpleBooleanProperty();

	public FXAnbaubar(Anbaubar anbaubar, Korb korb) {
		this.anbaubar = anbaubar;
		this.korb = korb;
		imageView = ImageViewBuilder.create().image(anbaubar.getBild())
				.fitHeight(50).fitWidth(50).build();
		getChildren().add(imageView);
		startWachstum();
		createMouseListener();
	}

	private void startWachstum() {
		// Schritt 1 Wachsen - Einscalen + Einfaden
		final DoubleBinding volleReifung = anbaubar.reifegradProperty().divide(
				anbaubar.wachsdauerProperty());
		scaleXProperty().bind(volleReifung);
		scaleYProperty().bind(volleReifung);
		opacityProperty().bind(volleReifung.add(0.5));

		anbaubar.istReifProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean arg1, Boolean arg2) {
				if (arg2) {
					anbaubar.istReifProperty().removeListener(this);

					scaleXProperty().unbind();
					scaleYProperty().unbind();
					opacityProperty().unbind();

					// Reife
					setEffect(new Glow(10));

					// Schritt 2 Reif - Pause
					PauseTransition pause = PauseTransitionBuilder.create()
							.duration(Duration.seconds(1)).build();

					// Schritt 3 Eingehen - Fade out
					FadeTransition eingehen = FadeTransitionBuilder.create()
							.node(FXAnbaubar.this).toValue(0.0)
							.duration(Duration.seconds(1)).build();

					// Wenn pause vorbei ist, geht frucht ein und verliert glow
					pause.setOnFinished(createAnbaubarIstWelkHandler());

					lebenszyklus = new SequentialTransition();
					lebenszyklus.getChildren().addAll(pause, eingehen);
					lebenszyklus.setOnFinished(createAnbaubarWaechstHandler());
					lebenszyklus.play();
				}
			}
		});
	}

	// Statistiken
	// Highscore aus backend (service)

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
				neuerAnbau();
			}
		};
	}

	private void createMouseListener() {
		setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				korb.gesammeltProperty().add(anbaubar);
				anbaubar.geerntet();
				if (lebenszyklus != null) {
					lebenszyklus.stop();
				}
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
}
