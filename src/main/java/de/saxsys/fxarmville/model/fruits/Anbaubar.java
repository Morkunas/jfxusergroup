package de.saxsys.fxarmville.model.fruits;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.util.Duration;

/**
 * 1.
 * 
 * @author Michael
 * 
 */
public abstract class Anbaubar {

	private Random rnd = new Random();

	protected ReadOnlyIntegerProperty wachsdauerProperty;
	protected DoubleProperty reifegradProperty = new SimpleDoubleProperty();
	protected DoubleProperty geldwertProperty;
	protected BooleanProperty istReifProperty = new SimpleBooleanProperty();
	protected BooleanProperty istFauligProperty = new SimpleBooleanProperty();

	private Timeline reifung;
	private Timeline faulen;

	public abstract Image getBild();

	public void baueAn() {
		istReifProperty.bind(reifegradProperty.isEqualTo(wachsdauerProperty));
		double nextDouble = rnd.nextDouble() * 20;
		setReifegrad(0);
		reifung = TimelineBuilder
				.create()
				.delay(Duration.seconds(nextDouble))
				.keyFrames(
						new KeyFrame(
								Duration.seconds(getWachsdauer()),
								new KeyValue(reifegradProperty, getWachsdauer())))
				.build();
		faulen = TimelineBuilder
				.create()
				.delay(Duration.seconds(1))
				.keyFrames(
						new KeyFrame(Duration.millis(1), new KeyValue(
								istReifProperty, false), new KeyValue(
								istFauligProperty, true))).build();
		reifung.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				istReifProperty.unbind();
				faulen.play();
			}
		});
		reifung.play();
	}

	public void geerntet() {
		reifung.stop();
		faulen.stop();
	}

	public ReadOnlyIntegerProperty wachsdauerProperty() {
		return wachsdauerProperty;
	}

	public int getWachsdauer() {
		return wachsdauerProperty.get();
	}

	public double getReifegrad() {
		return reifegradProperty.get();
	}

	public void setReifegrad(double reifegrad) {
		this.reifegradProperty.set(reifegrad);
	}

	public DoubleProperty reifegradProperty() {
		return reifegradProperty;
	}

	public DoubleProperty geldwertProperty() {
		// TODO binding reifegrad <-> geldwert; faulige früchte sind nix
		// wert!
		return geldwertProperty;
	}

	public double getGeldwert() {
		return geldwertProperty().get();
	}

	public void setGeldWert(double geldWert) {
		geldwertProperty().set(geldWert);
	}

	public BooleanProperty istReifProperty() {
		return istReifProperty;
	}

	public BooleanProperty istFauligProperty() {
		return istFauligProperty;
	}
}
