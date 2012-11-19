package de.saxsys.fxarmville.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 6
 * 
 * @author Michael
 * 
 */
public class Farm {

	// TODO FXFarm
	private final ObservableList<BeetReihe> beetReihe = FXCollections
			.observableArrayList();

	public ObservableList<BeetReihe> beetReiheProperty() {
		return beetReihe;
	}

}
