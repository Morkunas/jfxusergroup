package de.saxsys.fxarmville.model.fruits;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import de.saxsys.fxarmville.model.util.FruchtBildLader;

/**
 * 4
 * 
 * @author Michael
 * 
 */
public class ApFXelsine extends Anbaubar {

	public ApFXelsine() {
		wachsdauerProperty = new SimpleIntegerProperty(10);
	}

	@Override
	public Image getBild() {
		return FruchtBildLader.getInstance().getBild("apfxelsine.png");
	}
}
