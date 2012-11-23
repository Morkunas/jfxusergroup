package de.saxsys.fxarmville.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Korb {

	private ObservableList<Frucht> gesammeltProperty = FXCollections.observableArrayList();
	
	public ObservableList<Frucht> gesammeltProperty() {
		return gesammeltProperty;
	}
	
}
