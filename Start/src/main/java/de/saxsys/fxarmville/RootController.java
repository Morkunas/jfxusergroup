package de.saxsys.fxarmville;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
	private Pane statistik;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		final Farm farm = new Farm();
		FXarm fXarm = new FXarm(farm);

		mainPane.getChildren().add(fXarm);

		anzahlReif.textProperty().bind(
				Bindings.concat("# reife Früchte: ",
						farm.anzahlReiferFruechteProperty()));

		StringBinding korbBinding = new StringBinding() {
			{
				bind(farm.getKorb().gesammeltProperty());
			}

			@Override
			protected String computeValue() {
				return "Im Korb: " + farm.getKorb().gesammeltProperty().size();
			}
		};
		korb.textProperty().bind(korbBinding);
		fXarm.maxWidthProperty().bind(mainPane.widthProperty());
		statistik.getChildren().add(new Statistik(farm));
	}

}
// **** END LIVE CODING ****
