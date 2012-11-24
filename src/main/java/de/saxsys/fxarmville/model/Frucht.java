package de.saxsys.fxarmville.model;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.SequentialTransitionBuilder;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.util.Duration;
import de.saxsys.fxarmville.model.util.FruchtBildLader;

public class Frucht {

	private final DoubleProperty reifedauerProperty = new SimpleDoubleProperty();
	private final DoubleProperty reifegradProperty = new SimpleDoubleProperty();
	private final BooleanProperty istReifProperty = new SimpleBooleanProperty();
	private final BooleanProperty istFauligProperty = new SimpleBooleanProperty();

	private final LebensZyklus lebensZyklus = new LebensZyklus();
	private final String bildName;

	public Frucht(String bildName, double reifedauer) {
		this.bildName = bildName;
		reifedauerProperty.set(reifedauer);
	}

	public Image getBild() {
		return FruchtBildLader.getInstance().getBild(bildName);
	}

	public void baueAn() {
		istReifProperty.bind(Bindings.greaterThanOrEqual(reifegradProperty,
				reifedauerProperty).and(Bindings.not(istFauligProperty)));
		lebensZyklus.wachse();
	}

	public void ernten() {
		lebensZyklus.ernten();
	}

	/*
	 * WACHSDAUER
	 */
	public ReadOnlyDoubleProperty wachsdauerProperty() {
		return reifedauerProperty;
	}

	public double getWachsdauer() {
		return reifedauerProperty.get();
	}

	/*
	 * REIFEGRAD
	 */
	public double getReifegrad() {
		return reifegradProperty.get();
	}

	public ReadOnlyDoubleProperty reifegradProperty() {
		return reifegradProperty;
	}

	/*
	 * AKTUELLER ZUSTAND
	 */

	public ReadOnlyBooleanProperty istReifProperty() {
		return istReifProperty;
	}

	public ReadOnlyBooleanProperty istFauligProperty() {
		return istFauligProperty;
	}

	/**
	 * Private Klasse welche den Lebenszyklus einer Frucht abbildet.
	 * 
	 * @author sialcasa
	 * 
	 */
	private class LebensZyklus {

		private SequentialTransition lebensZyklus;

		public void wachse() {
			// **** BEGIN LIVE CODING ****

			// Wachstum
			final Random random = new Random();
			final double warteZeit = random.nextDouble() * 10;
			Timeline reifung = TimelineBuilder
					.create()
					.delay(Duration.seconds(warteZeit))
					.keyFrames(
							new KeyFrame(Duration.seconds(getWachsdauer()),
									new KeyValue(reifegradProperty,
											getWachsdauer()))).build();

			// Zeit, wann die Frucht reif ist
			PauseTransition istReif = new PauseTransition();
			double reifeZeit = random.nextDouble() * 3 + 1.0;
			istReif.setDuration(Duration.seconds(reifeZeit));

			// Leben starten
			lebensZyklus = SequentialTransitionBuilder.create()
					.children(reifung, istReif).build();

			lebensZyklus.setOnFinished(fruchtGehtEinEvent());
			lebensZyklus.play();
		}

		public EventHandler<ActionEvent> fruchtGehtEinEvent() {
			return new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					istFauligProperty.set(true);
				}
			};
		}

		// **** END LIVE CODING ****

		public void ernten() {
			lebensZyklus.stop();
		}
	}
}