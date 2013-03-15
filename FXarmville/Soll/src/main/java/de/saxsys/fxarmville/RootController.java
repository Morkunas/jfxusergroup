package de.saxsys.fxarmville;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import de.saxsys.fxarmville.model.Farm;
import de.saxsys.fxarmville.presentation.FXarm;

//**** BEGIN LIVE CODING ****
public class RootController implements Initializable {

	@FXML
	private Pane mainPane;
	@FXML
	private Label anzahlReif;
	@FXML
	private Label korb;
	@FXML
	private PieChart statistik;

	@Override
	public void initialize(final URL arg0, final ResourceBundle arg1) {
		final Farm farm = new Farm();
		final FXarm fxFarm = new FXarm(farm);
		mainPane.getChildren().add(fxFarm);

		anzahlReif.textProperty().bind(
				Bindings.concat("Reife Früchte: ",
						farm.anzahlReiferFruechteProperty()));

		final StringBinding anzahlGesammelt = new StringBinding() {
			{
				bind(farm.getKorb().gesammeltProperty());
			}

			@Override
			protected String computeValue() {
				return "Im Korb: " + farm.getKorb().gesammeltProperty().size();
			}
		};
		korb.textProperty().bind(anzahlGesammelt);

		Statistik.erzeugeStatistikDaten(farm, statistik);
	}

}
// **** END LIVE CODING ****
