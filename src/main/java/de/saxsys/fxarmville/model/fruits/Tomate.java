package de.saxsys.fxarmville.model.fruits;

import de.saxsys.fxarmville.model.util.FruchtBildLader;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;

public class Tomate extends Anbaubar {

	public Tomate() {
		geldwertProperty = new SimpleDoubleProperty(0.13);
		wachsdauerProperty = new SimpleIntegerProperty(20);
	}
	
	@Override
	public Image getBild() {
		return FruchtBildLader.getInstance().getBild("tomate.png");
	}
}
