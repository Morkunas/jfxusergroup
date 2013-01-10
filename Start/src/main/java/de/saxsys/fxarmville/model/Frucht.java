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

		public void wachse() {
			
		}

		public void ernten() {
			lebensZyklus.stop();
		}
	}
}
