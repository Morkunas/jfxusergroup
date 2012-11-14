package de.saxsys.presentation.util;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class UFXBindings {

	public static void bind(Pane from, Pane to){
		to.minWidthProperty().bind(from.widthProperty());
		to.minHeightProperty().bind(from.heightProperty());
		to.maxWidthProperty().bind(from.widthProperty());
		to.maxHeightProperty().bind(from.heightProperty());
	}
	
	public static void bind(Scene from, Pane to){
		to.minWidthProperty().bind(from.widthProperty());
		to.minHeightProperty().bind(from.heightProperty());
		to.maxWidthProperty().bind(from.widthProperty());
		to.maxHeightProperty().bind(from.heightProperty());
	}
	
}
