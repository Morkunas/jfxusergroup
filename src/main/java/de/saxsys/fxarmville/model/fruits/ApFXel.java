package de.saxsys.fxarmville.model.fruits;

import de.saxsys.fxarmville.model.util.FruchtBildLader;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;

/**
 * 3
 * 
 * @author Michael
 * 
 */
public class ApFXel extends Anbaubar {

	public ApFXel() {
		geldwertProperty = new SimpleDoubleProperty(0.42);
		wachsdauerProperty = new SimpleIntegerProperty(5);
	}

	@Override
	public Image getBild() {
		return FruchtBildLader.getInstance().getBild("apfxel.png");
	}
}
