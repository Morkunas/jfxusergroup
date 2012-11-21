package de.saxsys.fxarmville.model;

import de.saxsys.fxarmville.model.fruits.Anbaubar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Korb {

	private ObservableList<Anbaubar> gesammeltProperty = FXCollections.observableArrayList();
	
	public ObservableList<Anbaubar> gesammeltProperty() {
		return gesammeltProperty;
	}
	
}
