package de.saxsys.fxarmville.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import de.saxsys.fxarmville.model.util.DataMock;

public class Farm {

	private final ObjectProperty<Korb> korbProperty = new SimpleObjectProperty<Korb>(
			new Korb());
	private final ListProperty<Frucht> angebautProperty = new SimpleListProperty<>(
			FXCollections.<Frucht> observableArrayList());
	private final IntegerProperty anzahlReiferFruechteProperty = new SimpleIntegerProperty();

	public Farm() {
		init();
	}

	private void init() {
		for (int i = 0; i < 100; i++) {
			final Frucht frucht = erzeugeFrucht();
			angebautProperty.add(frucht);
		}
	}

	private Frucht erzeugeFrucht() {
		final Frucht frucht = DataMock.erzeugeZufallsFrucht();
		ChangeListener<Boolean> istReifListener = new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> bean,
					Boolean oldValue, Boolean newValue) {
				if (newValue) {
					anzahlReiferFruechteProperty
							.set(anzahlReiferFruechteProperty.get() + 1);
				} else {
					anzahlReiferFruechteProperty
							.set(anzahlReiferFruechteProperty.get() - 1);
				}
			}
		};
		frucht.istReifProperty().addListener(istReifListener);
		return frucht;
	}

	public void ernteFrucht(Frucht frucht) {
		getKorb().gesammeltProperty().add(frucht);
		ersetzeFrucht(frucht);
	}

	public void ersetzeFrucht(Frucht frucht) {
		int indexFrucht = angebautProperty.indexOf(frucht);
		if (indexFrucht == -1) {
			// Frucht schon nicht mehr da
			return;
		}
		angebautProperty.set(indexFrucht, erzeugeFrucht());
		if (frucht.istReifProperty().get()) {
			anzahlReiferFruechteProperty.set(anzahlReiferFruechteProperty.get() - 1);
		}
	}

	public Korb getKorb() {
		return korbProperty.get();
	}

	public ReadOnlyObjectProperty<Korb> korbProperty() {
		return korbProperty;
	}

	public ReadOnlyListProperty<Frucht> angebautProperty() {
		return angebautProperty;
	}

	public ReadOnlyIntegerProperty anzahlReiferFruechteProperty() {
		return anzahlReiferFruechteProperty;
	}

}
