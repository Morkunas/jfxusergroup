package de.saxsys.fxarmville.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

public class Korb {

	private ListProperty<Frucht> gesammeltProperty = new SimpleListProperty<>(FXCollections.<Frucht>observableArrayList());
	
	public ListProperty<Frucht> gesammeltProperty() {
		return gesammeltProperty;
	}
	
}
