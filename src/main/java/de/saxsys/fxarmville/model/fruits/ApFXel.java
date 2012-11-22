package de.saxsys.fxarmville.model.fruits;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import de.saxsys.fxarmville.model.util.FruchtBildLader;

/**
 * 3
 * 
 * @author Michael
 * 
 */
public class ApFXel extends Anbaubar {

	public ApFXel() {
		wachsdauerProperty = new SimpleIntegerProperty(5);
	}

	@Override
	public Image getBild() {
		return FruchtBildLader.getInstance().getBild("apfxel.png");
	}
}
