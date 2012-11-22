package de.saxsys.fxarmville;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import de.saxsys.fxarmville.model.Farm;
import de.saxsys.fxarmville.model.fruits.Anbaubar;
import de.saxsys.fxarmville.presentation.FXFarm;

public class RootController implements Initializable {

	@FXML
	private Pane mainPane;

	@FXML
	private Label anzahlReif;

	@FXML
	private Label korb;

	@FXML
	private Pane statistik;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		final Farm farm = DataMock.getData();
		final FXFarm fxFarm = new FXFarm(farm);
		mainPane.getChildren().add(fxFarm);

		StringBinding anzahlDerReifenFruechte = new StringBinding() {
			{
				for (Anbaubar anbaubar : farm.angebautProperty()) {
					bind(anbaubar.istReifProperty());
				}
				farm.angebautProperty().addListener(
						new ListChangeListener<Anbaubar>() {
							@Override
							public void onChanged(
									javafx.collections.ListChangeListener.Change<? extends Anbaubar> c) {
								c.next();
								for (Anbaubar anbaubar : c.getAddedSubList()) {
									bind(anbaubar.istReifProperty());
								}
								for (Anbaubar anbaubar : c.getRemoved()) {
									unbind(anbaubar.istReifProperty());
								}
							}
						});
			}

			@Override
			protected String computeValue() {
				int anzahlReiferFrüchte = 0;
				for (Anbaubar anbaubar : farm.angebautProperty()) {
					if (anbaubar.istReifProperty().get()) {
						anzahlReiferFrüchte++;
					}
				}
				return String.valueOf(anzahlReiferFrüchte);
			}
		};
		anzahlReif.textProperty().bind(
				Bindings.concat("Reife Früchte: ", anzahlDerReifenFruechte));

		StringBinding anzahlGesammelt = new StringBinding() {
			{
				bind(farm.getKorb().gesammeltProperty());
			}

			@Override
			protected String computeValue() {
				return "Im Korb: " + farm.getKorb().gesammeltProperty().size();
			}
		};
		korb.textProperty().bind(anzahlGesammelt);

		statistik.getChildren().add(new Statistik(farm));
	}

}
