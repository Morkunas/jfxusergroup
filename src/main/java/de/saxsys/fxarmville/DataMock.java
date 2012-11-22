package de.saxsys.fxarmville;

import java.util.Random;

import de.saxsys.fxarmville.model.Farm;
import de.saxsys.fxarmville.model.fruits.Anbaubar;
import de.saxsys.fxarmville.model.fruits.ApFXel;
import de.saxsys.fxarmville.model.fruits.ApFXelsine;
import de.saxsys.fxarmville.model.fruits.Tomate;

public class DataMock {

	private static Random rand = new Random(System.currentTimeMillis());
	
	public static Farm getData() {
		Farm farm = new Farm();

		for (int beetCount = 0; beetCount < 100; beetCount++) {
			farm.angebautProperty().add(createRandomFruit());
		}

		return farm;
	}

	public static Anbaubar createRandomFruit() {
		switch (rand.nextInt(3)) {
		case 0:
			return new ApFXel();
		case 1:
			return new ApFXelsine();
		case 2:
			return new Tomate();
		default:
			return null;
		}
	}
}
