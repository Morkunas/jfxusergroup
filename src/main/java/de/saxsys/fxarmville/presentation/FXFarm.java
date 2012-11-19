package de.saxsys.fxarmville.presentation;

import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import de.saxsys.fxarmville.model.BeetReihe;
import de.saxsys.fxarmville.model.Farm;

public class FXFarm extends Parent {

	private VBox zeile;

	public FXFarm(Farm farm) {
		zeile = VBoxBuilder.create().build();
		for (BeetReihe reihe : farm.beetReiheProperty()) {
			FXBeetReihe reiheFX = new FXBeetReihe(reihe);
			zeile.getChildren().add(reiheFX);
		}
		getChildren().add(zeile);
	}

}
