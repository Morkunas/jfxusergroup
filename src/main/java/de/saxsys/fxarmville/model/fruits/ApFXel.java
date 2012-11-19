package de.saxsys.fxarmville.model.fruits;

import javafx.scene.image.Image;

/**
 * 3
 * 
 * @author Michael
 * 
 */
public class ApFXel extends Anbaubar {

	private Image apfxelImage;

	public ApFXel() {
		setGeldWert(0.42);
	}

	@Override
	public Image getBild() {
		if (apfxelImage == null) {
			apfxelImage = new Image(
					ClassLoader.getSystemResourceAsStream("apfxel.png"));
		}
		return apfxelImage;
	}

	@Override
	public int getWachsdauer() {
		return 5;
	}

}
