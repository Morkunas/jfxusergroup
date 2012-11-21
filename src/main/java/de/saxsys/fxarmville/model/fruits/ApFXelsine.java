package de.saxsys.fxarmville.model.fruits;

import de.saxsys.fxarmville.model.util.FruchtBildLader;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;

/**
 * 4
 * 
 * @author Michael
 * 
 */
public class ApFXelsine extends Anbaubar {

	public ApFXelsine() {
		geldwertProperty = new SimpleDoubleProperty(0.80);
		wachsdauerProperty = new SimpleIntegerProperty(10);
	}

	@Override
	public Image getBild() {
		return FruchtBildLader.getInstance().getBild("apfxelsine.png");
	}
}
