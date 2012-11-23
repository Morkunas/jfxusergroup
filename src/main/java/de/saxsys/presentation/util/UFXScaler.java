package de.saxsys.presentation.util;

import javafx.scene.layout.Pane;

public class UFXScaler {

	/**
	 * @param args
	 */
	public static void scaleTo(Pane from, Pane to) {
		to.scaleXProperty().bind(
				from.widthProperty().divide(to.widthProperty()));
		to.scaleYProperty().bind(
				from.widthProperty().divide(to.widthProperty()));
	}

}
