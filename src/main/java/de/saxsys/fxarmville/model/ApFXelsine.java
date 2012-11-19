package de.saxsys.fxarmville.model;

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
			bild = new Image(ApFXelsine.class.getResourceAsStream("apfxelsine.png"));
		}
		return bild;
	}

	@Override
	public int getWachsdauer() {
		return 200;
	}

}
