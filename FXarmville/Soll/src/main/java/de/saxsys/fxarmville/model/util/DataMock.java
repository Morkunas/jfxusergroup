package de.saxsys.fxarmville.model.util;

import java.util.Random;

import de.saxsys.fxarmville.model.Frucht;

//**** NUR ZEIGEN ****
public class DataMock {

	private static Random rand = new Random();

	public static Frucht erzeugeZufallsFrucht() {
		switch (rand.nextInt(3)) {
		case 0:
			return new Frucht("apfxel.png", 5);
		case 1:
			return new Frucht("apfxelsine.png", 10);
		case 2:
			return new Frucht("tomate.png", 20);
		default:
			return null;
		}
	}
}
