package de.saxsys.fxarmville.model;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import javafx.util.Duration;
import de.saxsys.fxarmville.model.util.FruchtBildLader;

public class Frucht {

	// Zeit für Reifung + Faulen
	private final DoubleProperty lebenszeit = new SimpleDoubleProperty();

	// Aktueller Reife- /Faulgrad
	private final DoubleProperty aktuelleLebenszeit = new SimpleDoubleProperty();

	/*
	 * Util: Lebensabschnitte
	 */
	private final BooleanProperty istReif = new SimpleBooleanProperty();
	private final BooleanProperty istFaulig = new SimpleBooleanProperty();
	private final BooleanProperty istEingegangen = new SimpleBooleanProperty();
	private final BooleanProperty istGeerntetWorden = new SimpleBooleanProperty();

	private final LebensZyklus lebensZyklus = new LebensZyklus();
	private final String bildName;

	public Frucht(final String bildName, final double lebenszeit) {
		this.bildName = bildName;
		this.lebenszeit.set(lebenszeit);
	}

	public Image getBild() {
		return FruchtBildLader.getInstance().getBild(bildName);
	}

	/*
	 * ANBAU
	 */
	public void baueAn() {
		// TODO LIVE
		final DoubleBinding mitteDesLebens = lebenszeit.divide(2);
		istReif.bind(aktuelleLebenszeit.greaterThan(
				mitteDesLebens.subtract(getReifedauer()))
				.and(aktuelleLebenszeit.lessThan(mitteDesLebens
						.add(getReifedauer()))));

		// END LIVE

		istEingegangen.bind(aktuelleLebenszeit.greaterThanOrEqualTo(lebenszeit)
				.and(istGeerntetWorden.not()));

		istFaulig.bind(aktuelleLebenszeit.greaterThan(mitteDesLebens).and(
				istReif.not()));

		lebensZyklus.wachse();
	}

	public void ernten() {
		lebensZyklus.ernten();
		istGeerntetWorden.set(true);
	}

	/*
	 * LEBENSZEIT
	 */
	public ReadOnlyDoubleProperty lebenszeitProperty() {
		return lebenszeit;
	}

	public double getLebenszeit() {
		return lebenszeit.get();
	}

	/*
	 * REIFEGRAD
	 */
	public double getAktuelleLebenszeit() {
		return aktuelleLebenszeit.get();
	}

	public DoubleProperty aktuelleLebenszeitProperty() {
		return aktuelleLebenszeit;
	}

	/*
	 * AKTUELLER ZUSTAND
	 */

	public ReadOnlyBooleanProperty istReifProperty() {
		return istReif;
	}

	public ReadOnlyBooleanProperty istFauligProperty() {
		return istFaulig;
	}

	public ReadOnlyBooleanProperty istEingegangenProperty() {
		return istEingegangen;
	}

	public ReadOnlyBooleanProperty istGeerntetWordenProperty() {
		return istGeerntetWorden;
	}

	private double getReifedauer() {
		return getLebenszeit() / 16d;
	}

	/**
	 * Private Klasse welche den Lebenszyklus einer Frucht abbildet.
	 * 
	 * @author sialcasa
	 * 
	 */
	private class LebensZyklus {

		private Timeline lebensZyklus;
		private final Random random = new Random();

		public void wachse() {
			// TODO LIVE
			lebensZyklus = TimelineBuilder
					.create()
					.delay(Duration.seconds(random.nextInt(10)))
					.keyFrames(
							new KeyFrame(Duration.seconds(getLebenszeit()),
									new KeyValue(aktuelleLebenszeit,
											getLebenszeit()))).build();
			lebensZyklus.play();
		}

		public void ernten() {
			lebensZyklus.stop();
		}
	}
}
