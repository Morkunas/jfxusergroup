package de.saxsys.fxarmville.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;

/**
 * 1.
 * 
 * @author Michael
 * 
 */
public abstract class Anbaubar {

	private ReadOnlyIntegerProperty wachsdauerProperty;
	protected DoubleProperty geldwertProperty;

	public abstract Image getBild();

	public ReadOnlyIntegerProperty wachsdauerProperty() {
		if (wachsdauerProperty == null) {
			wachsdauerProperty = new SimpleIntegerProperty(getWachsdauer());
		}
		return wachsdauerProperty;
	}

	public abstract int getWachsdauer();
	
	public DoubleProperty geldwertProperty() {
		if (geldwertProperty == null) {
			geldwertProperty = new SimpleDoubleProperty();
			// TODO binding reifegrad <-> geldwert; faulige früchte sind nix
			// wert!
		}
		return geldwertProperty;
	}

	public double getGeldwert() {
		return geldwertProperty().get();
	}

	public void setGeldWert(double allesWirdAnders) {
		geldwertProperty().set(allesWirdAnders);
	}
}
