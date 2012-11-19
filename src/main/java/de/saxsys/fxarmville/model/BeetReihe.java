package de.saxsys.fxarmville.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * 5
 * 
 * @author Michael
 *
 */
public class BeetReihe {

	private final ObservableList<Anbaubar> waechstHier = FXCollections.emptyObservableList();

	public ObservableList<Anbaubar> getWaechstHier() {
		return waechstHier;
	}
}
