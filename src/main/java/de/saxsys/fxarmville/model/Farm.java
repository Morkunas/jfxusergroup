package de.saxsys.fxarmville.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import de.saxsys.fxarmville.model.fruits.Anbaubar;

/**
 * 6
 * 
 * @author Michael
 * 
 */
public class Farm {

	private ObjectProperty<Korb> korbProperty = new SimpleObjectProperty<Korb>(
			new Korb());

	private final ObservableList<Anbaubar> angebaut = FXCollections
			.observableArrayList();

	public ObservableList<Anbaubar> angebautProperty() {
		return angebaut;
	}

	public Korb getKorb() {
		return korbProperty.get();
	}

	public ObjectProperty<Korb> korbProperty() {
		return korbProperty;
	}

}
