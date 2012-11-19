package de.saxsys.fxarmville;

import java.util.Random;

import de.saxsys.fxarmville.model.BeetReihe;
import de.saxsys.fxarmville.model.Farm;
import de.saxsys.fxarmville.model.fruits.Anbaubar;
import de.saxsys.fxarmville.model.fruits.ApFXel;
import de.saxsys.fxarmville.model.fruits.ApFXelsine;

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
		if (rand.nextBoolean()) {
			return new ApFXel();
		} else {
			return new ApFXelsine();
		}
	}
}
