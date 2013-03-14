package de.saxsys.fxarmville;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.layout.Pane;
import de.saxsys.fxarmville.model.Farm;
import de.saxsys.fxarmville.model.Frucht;

//**** NUR ZEIGEN ****
public class Statistik extends Pane {

	private Farm farm;

	public Statistik(Farm farm) {
		this.farm = farm;
		init();
	}

	private void init() {
		final ObservableList<Data> data = FXCollections.observableArrayList(new Data(
				"reif", 0), new Data("unreif", 0), new Data("faulig", 0));
		farm.getKorb().gesammeltProperty().addListener(new ListChangeListener<Frucht>() {
			@Override
			public void onChanged(
					javafx.collections.ListChangeListener.Change<? extends Frucht> c) {
				c.next();
				int reif = 0;
				int unreif = 0;
				int faulig = 0;
				for (Frucht imKorb : c.getList()) {
					if (imKorb.istReifProperty().get()) {
						reif++;
					} else if (imKorb.istFauligProperty().get()) {
						faulig++;
					} else {
						unreif++;
					}
				}
				data.get(0).setPieValue(reif);
				data.get(1).setPieValue(unreif);
				data.get(2).setPieValue(faulig);
			}
		});
		PieChart statistik = new PieChart(data);
		getChildren().add(statistik);
	}

}
