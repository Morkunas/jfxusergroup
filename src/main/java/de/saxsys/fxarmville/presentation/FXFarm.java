package de.saxsys.fxarmville.presentation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import de.saxsys.fxarmville.DataMock;
import de.saxsys.fxarmville.model.Farm;
import de.saxsys.fxarmville.model.fruits.Anbaubar;

//TODO Eigendlich müsste die Logik zum adden / removen an einer bestimmten Stelle in die Farm
public class FXFarm extends Parent {

	private VBox beetReihen = new VBox();
	private Farm farm;

	public FXFarm(final Farm farm) {
		this.farm = farm;
		initBeet();
		startBugs();
	}

	private void startBugs() {

		TimelineBuilder
				.create()
				.keyFrames(
						new KeyFrame(Duration.seconds(10),
								new EventHandler<ActionEvent>() {
									@Override
									public void handle(ActionEvent arg0) {
										getChildren().add(new FXAnimatedBug());
									}
								})).cycleCount(Timeline.INDEFINITE).build()
				.play();
	}

	private void initBeet() {
		HBox reihe = null;
		for (int i = 0; i < farm.angebautProperty().size(); i++) {
			if (i % 10 == 0) {
				// neue Reihe
				reihe = new HBox();
				beetReihen.getChildren().add(reihe);
			}
			final Anbaubar anbaubar = farm.angebautProperty().get(i);
			FXAnbaubar fxAnbaubar = createAnbaubarFX(anbaubar, reihe, i);

			reihe.getChildren().add(fxAnbaubar);
		}
		getChildren().add(beetReihen);
	}

	/*
	 * Wenn frucht geerntet wurde wird sie in den Korb gelegt und eine neue
	 * Frucht and der selben Stelle erstellt
	 */
	private ChangeListener<Boolean> createFruchtGeerntetHandler(
			final Anbaubar anbaubar, final HBox reihe, final int i) {

		return new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean arg1, Boolean arg2) {
				farm.getKorb().gesammeltProperty().add(anbaubar);
				createAnbaubarAnBeetStelle(reihe, i);
			}
		};
	}

	/*
	 * Legt eine neue Frucht an einer Stelle in einer HBox (Beetreihe) an
	 */
	private ChangeListener<Boolean> createLegeNeueFruchtAnHandler(
			final Anbaubar anbaubar, final HBox reihe, final int i) {

		return new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0,
					Boolean arg1, Boolean arg2) {
				createAnbaubarAnBeetStelle(reihe, i);
			}
		};

	}

	private void createAnbaubarAnBeetStelle(final HBox reihe, final int i) {
		Anbaubar anbaubar = DataMock.createRandomFruit();
		FXAnbaubar fxAnbaubar = createAnbaubarFX(anbaubar, reihe, i);
		reihe.getChildren().set(i % 10, fxAnbaubar);
		farm.angebautProperty().set(i, anbaubar);
	}

	private FXAnbaubar createAnbaubarFX(final Anbaubar anbaubar, HBox reihe,
			int i) {
		FXAnbaubar fxAnbaubar = new FXAnbaubar(anbaubar);
		fxAnbaubar.eingegangenProperty().addListener(
				createLegeNeueFruchtAnHandler(anbaubar, reihe, i));
		fxAnbaubar.geerntetProperty().addListener(
				createFruchtGeerntetHandler(anbaubar, reihe, i));
		anbaubar.baueAn();
		return fxAnbaubar;
	}

	@Override
	public ObservableList<Node> getChildren() {
		return super.getChildren();
	}
}
