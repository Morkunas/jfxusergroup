package de.saxsys.fxarmville.presentation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import de.saxsys.fxarmville.model.Farm;
import de.saxsys.fxarmville.model.Frucht;
import de.saxsys.fxarmville.presentation.special.BugTracker;

// TODO Testen mit Jemmy: Jagd Bugs
public class FXarm extends Pane {

	private Farm farm;
	private VBox beetReihenVertikal = new VBox();
	private ObservableList<Frucht> fruechte;

	public FXarm(final Farm farm) {
		this.farm = farm;
		this.setId("FXarm");
		initBeet();
		startBugs();
		
		parentProperty().addListener(new ChangeListener<Parent>() {
			@Override
			public void changed(ObservableValue<? extends Parent> arg0,
					Parent arg1, Parent arg2) {
				if (arg2 != null && arg2 instanceof Pane) {
					Pane pane = (Pane) arg2;
					maxHeightProperty().bind(pane.heightProperty());
					maxWidthProperty().bind(pane.widthProperty());
				}
			}
		});
	}

	// **** BEGIN LIVE CODING ****
	private void startBugs() {

		TimelineBuilder
				.create()
				.keyFrames(
						new KeyFrame(Duration.seconds(10),
								new EventHandler<ActionEvent>() {
									@Override
									public void handle(ActionEvent arg0) {
										getChildren().add(BugTracker.getInstance().erzeugeBug());
									}
								})).cycleCount(Timeline.INDEFINITE).build()
				.play();
	}

	// **** END LIVE CODING ****

	private void initBeet() {
		// **** BEGIN LIVE CODING ****
		HBox reihe = null;
		for (int i = 0; i < farm.angebautProperty().size(); i++) {
			if (i % 10 == 0) {
				// neue Reihe
				reihe = new HBox();
				beetReihenVertikal.getChildren().add(reihe);
			}
		}
		getChildren().add(beetReihenVertikal);
		// **** END LIVE CODING ****

		fruechte = FXCollections.observableArrayList();
		ListChangeListener<Frucht> listChangeListener = new ListChangeListener<Frucht>() {
			@Override
			public void onChanged(
					javafx.collections.ListChangeListener.Change<? extends Frucht> c) {
				c.next();
				for (int index = c.getFrom(); index < c.getTo(); index++) {
					// neue Frucht oder auch Frucht ersetzt...
					if (c.wasAdded()) {
						Frucht frucht = c.getList().get(index);
						FXrucht fXrucht = new FXrucht(frucht);
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
		// END
	}

	private ChangeListener<Boolean> neueFruchtHandler(final Frucht frucht,
			final boolean geerntet) {
		return new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean arg1, Boolean arg2) {
				if (geerntet) {
					farm.ernteFrucht(frucht);
				} else {
					farm.ersetzeFrucht(frucht);
				}
			}
		};
	}

	@Override
	public ObservableList<Node> getChildren() {
		return super.getChildren();
	}
}
