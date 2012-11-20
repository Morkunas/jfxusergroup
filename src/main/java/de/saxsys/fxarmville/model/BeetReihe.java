package de.saxsys.fxarmville.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import de.saxsys.fxarmville.model.fruits.Anbaubar;

/**
 * 5
 * 
 * @author Michael
 * 
 */
public class BeetReihe {

	private final ObservableList<Anbaubar> waechstHier = FXCollections
			.observableArrayList();

	public ObservableList<Anbaubar> waechstHierProperty() {
		return waechstHier;
	}
}
