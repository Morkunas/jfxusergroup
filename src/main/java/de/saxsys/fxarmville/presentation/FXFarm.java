package de.saxsys.fxarmville.presentation;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import de.saxsys.fxarmville.DataMock;
import de.saxsys.fxarmville.model.Farm;
import de.saxsys.fxarmville.model.Korb;
import de.saxsys.fxarmville.model.fruits.Anbaubar;

public class FXFarm extends Parent {

	private VBox zeile;

	public FXFarm(final Farm farm) {
		final Korb korb = farm.getKorb();

		zeile = VBoxBuilder.create().build();
		HBox reihe = null;
		for (int i = 0; i < farm.anbaubaresProperty().size(); i++) {
			if (i % 10 == 0) {
				// neue Reihe
				reihe = HBoxBuilder.create().build();
				zeile.getChildren().add(reihe);
			}
			final Anbaubar anbaubar = farm.anbaubaresProperty().get(i);
			anbaubar.baueAn();
			FXAnbaubar fxAnbaubar = new FXAnbaubar(anbaubar, korb);
			reihe.getChildren().add(fxAnbaubar);

			fxAnbaubar.geerntetProperty().addListener(
					new GeerntetListener(farm, reihe, i));
		}
		getChildren().add(zeile);
	}

	private final class GeerntetListener implements ChangeListener<Boolean> {
		private Farm farm;
		private HBox reihe;
		private int i;

		private GeerntetListener(Farm farm, HBox reihe, int i) {
			this.farm = farm;
			this.reihe = reihe;
			this.i = i;
		}

		@Override
		public void changed(ObservableValue<? extends Boolean> observable,
				Boolean oldValue, Boolean newValue) {
			if (newValue) {
				Anbaubar anbaubar = DataMock.createRandomFruit();
				anbaubar.baueAn();
				farm.anbaubaresProperty().set(i, anbaubar);
				FXAnbaubar fxAnbaubar = new FXAnbaubar(anbaubar, farm.getKorb());
				reihe.getChildren().set(i % 10, fxAnbaubar);
				fxAnbaubar.geerntetProperty().addListener(
						new GeerntetListener(farm, reihe, i));
			}
		}
	}
}
