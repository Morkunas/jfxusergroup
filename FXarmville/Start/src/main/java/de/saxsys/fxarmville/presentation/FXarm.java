package de.saxsys.fxarmville.presentation;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import de.saxsys.fxarmville.model.Farm;
import de.saxsys.fxarmville.model.Frucht;
import de.saxsys.fxarmville.presentation.special.CodeFactory;

public class FXarm extends Pane {

	private final Farm farm;
	private final VBox beetReihenVertikal = new VBox();
	private ObservableList<Frucht> fruechte;

	public FXarm(final Farm farm) {
		this.farm = farm;
		this.setId("FXarm");
		initBeet();
		getReal();

		parentProperty().addListener(new ChangeListener<Parent>() {
			@Override
			public void changed(final ObservableValue<? extends Parent> arg0,
					final Parent arg1, final Parent arg2) {
				if (arg2 != null && arg2 instanceof Pane) {
					final Pane pane = (Pane) arg2;
					maxHeightProperty().bind(pane.heightProperty());
					maxWidthProperty().bind(pane.widthProperty());
				}
			}
		});
	}

	private void getReal() {

		final EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent actionEvent) {
				getChildren().add(CodeFactory.getInstance().erzeugeRealismus());
			}
		};

		// **** BEGIN LIVE CODING ****
	}

	private void initBeet() {
		HBox reihe = null;
		for (int i = 0; i < farm.angebautProperty().size(); i++) {
			if (i % 10 == 0) {
				// neue Reihe
				reihe = new HBox();
				beetReihenVertikal.getChildren().add(reihe);
			}
		}
		getChildren().add(beetReihenVertikal);

		fruechte = FXCollections.observableArrayList();
		final ListChangeListener<Frucht> listChangeListener = new ListChangeListener<Frucht>() {
			@Override
			public void onChanged(
					final javafx.collections.ListChangeListener.Change<? extends Frucht> c) {
				c.next();
				for (int index = c.getFrom(); index < c.getTo(); index++) {
					// neue Frucht oder auch Frucht ersetzt...
					if (c.wasAdded()) {
						final Frucht frucht = c.getList().get(index);
						final FXrucht fXrucht = new FXrucht(frucht);
						frucht.istEingegangenProperty().addListener(
								neueFruchtHandler(frucht, false));
						frucht.istGeerntetWordenProperty().addListener(
								neueFruchtHandler(frucht, true));
						frucht.baueAn();
						// ein bissl hässlich: die richtige Zelle finden
						final Pane beetReihe = (Pane) beetReihenVertikal
								.getChildren().get(index / 10);
						if (beetReihe.getChildren().size() < 10) {
							beetReihe.getChildren().add(fXrucht);
						} else {
							beetReihe.getChildren().set(index % 10, fXrucht);
						}
					}
				}
			}
		};

		fruechte.addListener(listChangeListener);
		Bindings.bindContent(fruechte, farm.angebautProperty());
	}

	private ChangeListener<Boolean> neueFruchtHandler(final Frucht frucht,
			final boolean geerntet) {
		return new ChangeListener<Boolean>() {
			@Override
			public void changed(final ObservableValue<? extends Boolean> arg0,
					final Boolean arg1, final Boolean arg2) {
				if (geerntet) {
					farm.ernteFrucht(frucht);
				} else {
					farm.ersetzeFrucht(frucht);
				}
			}
		};
	}

}
