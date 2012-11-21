package de.saxsys.fxarmville.model.fruits;

import javafx.scene.image.Image;

public class Tomate extends Anbaubar {

	private Image tomatenBild;

	@Override
	public Image getBild() {
		if (tomatenBild == null) {
			if (tomatenBild == null) {
				tomatenBild = new Image(
						ClassLoader.getSystemResourceAsStream("tomate.png"));
			}
			return tomatenBild;
		}
		return null;
	}

	@Override
	public int getWachsdauer() {
		return 20;
	}

}
