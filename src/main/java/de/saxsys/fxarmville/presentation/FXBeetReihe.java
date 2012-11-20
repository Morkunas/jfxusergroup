package de.saxsys.fxarmville.presentation;

import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import de.saxsys.fxarmville.model.BeetReihe;
import de.saxsys.fxarmville.model.fruits.Anbaubar;

/**
 * 7
 * 
 * @author Michael
 * 
 */
public class FXBeetReihe extends Parent {

	private HBox reihe;

	public FXBeetReihe(BeetReihe beetReihe) {

		reihe = HBoxBuilder.create().build();

		for (Anbaubar anbaubar : beetReihe.waechstHierProperty()) {
			FXAnbaubar fxAnbaubar = new FXAnbaubar(anbaubar);
			reihe.getChildren().add(fxAnbaubar);

		}
		getChildren().add(reihe);
	}
}
