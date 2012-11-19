package de.saxsys.fxarmville.model.fruits;

import javafx.scene.image.Image;

/**
 * 4
 * 
 * @author Michael
 * 
 */
public class ApFXelsine extends Anbaubar {

	private Image bild;

	public ApFXelsine() {
		setGeldWert(13.37);
	}

	@Override
	public Image getBild() {
		if (bild == null) {
			bild = new Image(
					ClassLoader.getSystemResourceAsStream("apfxelsine.jpg"));
		}
		return bild;
	}

	@Override
	public int getWachsdauer() {
		return 10;
	}

}
