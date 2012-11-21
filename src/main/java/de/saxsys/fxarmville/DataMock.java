package de.saxsys.fxarmville;

import java.util.Random;

import de.saxsys.fxarmville.model.BeetReihe;
import de.saxsys.fxarmville.model.Farm;
import de.saxsys.fxarmville.model.fruits.Anbaubar;
import de.saxsys.fxarmville.model.fruits.ApFXel;
import de.saxsys.fxarmville.model.fruits.ApFXelsine;
import de.saxsys.fxarmville.model.fruits.Tomate;

public class DataMock {

	public static Farm getData() {
		Farm farm = new Farm();

		for (int beetCount = 0; beetCount < 10; beetCount++) {
			farm.beetReiheProperty().add(new BeetReihe());
		}

		for (BeetReihe beetReihe : farm.beetReiheProperty()) {
			for (int obstAnzahl = 0; obstAnzahl < 10; obstAnzahl++) {
				beetReihe.waechstHierProperty().add(createRandomFruit());
			}
		}

		return farm;
	}

	public static Anbaubar createRandomFruit() {
		Random rand = new Random();
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
