package de.saxsys.fxarmville.model;

import de.saxsys.fxarmville.model.fruits.Anbaubar;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 6
 * 
 * @author Michael
 * 
 */
public class Farm {

	private ObjectProperty<Korb> korbProperty = new SimpleObjectProperty<Korb>(new Korb());
	
	private final ObservableList<Anbaubar> anbaubaresProperty = FXCollections
			.observableArrayList();

	public ObservableList<Anbaubar> anbaubaresProperty() {
		return anbaubaresProperty;
	}
	
	public Korb getKorb() {
		return korbProperty.get();
	}
	
	public ObjectProperty<Korb> korbProperty() {
		return korbProperty;
	}

}
