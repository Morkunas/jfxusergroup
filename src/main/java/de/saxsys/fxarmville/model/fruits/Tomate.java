package de.saxsys.fxarmville.model.fruits;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import de.saxsys.fxarmville.model.util.FruchtBildLader;

public class Tomate extends Anbaubar {

	public Tomate() {
		wachsdauerProperty = new SimpleIntegerProperty(20);
	}

	@Override
	public Image getBild() {
		return FruchtBildLader.getInstance().getBild("tomate.png");
	}
}
