package de.saxsys.fxarmville.model;

import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import de.saxsys.fxarmville.model.util.FruchtBildLader;

public class Frucht {

	// wie lange ist eine Frucht reif
	private static final double REIFEDAUER = 0.05;

	// Zeit für Reifung / Faulen
	private final DoubleProperty lebenszeit = new SimpleDoubleProperty();

	// Aktueller Reifegrad
	private final DoubleProperty aktuelleLebenszeit = new SimpleDoubleProperty();

	/*
	 * Lebensabschnitte
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

	public void baueAn() {
		final double haelfteDerLebenszeit = 0.5;
		istReif.bind(aktuelleLebenszeit.greaterThan(
				haelfteDerLebenszeit - REIFEDAUER).and(
				aktuelleLebenszeit.lessThan(haelfteDerLebenszeit + REIFEDAUER)));

		istEingegangen.bind(aktuelleLebenszeit.greaterThanOrEqualTo(1.0).and(
				istGeerntetWorden.not()));

		istFaulig.bind(aktuelleLebenszeit.greaterThan(haelfteDerLebenszeit)
				.and(istReif.not()));

		lebensZyklus.wachsen();
	}

	/**
	 * Private Klasse welche den Lebenszyklus einer Frucht abbildet.
	 * 
	 * @author sialcasa
	 * 
	 */
	private class LebensZyklus {

		private Timeline lebensZyklus;

		public void wachsen() {
			// **** BEGIN LIVE CODING ****
		}

		public void ernten() {
			// **** BEGIN LIVE CODING ****
		}
	}

	public void ernten() {
		lebensZyklus.ernten();
		istGeerntetWorden.set(true);
	}

	public Image getBild() {
		return FruchtBildLader.getInstance().getBild(bildName);
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
	 * AKTUELLE LEBENSZEIT
	 */

	public DoubleProperty aktuelleLebenszeitProperty() {
		return aktuelleLebenszeit;
	}

	public double getAktuelleLebenszeit() {
		return aktuelleLebenszeit.get();
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
}
