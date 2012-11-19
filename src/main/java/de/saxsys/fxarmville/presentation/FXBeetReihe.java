package de.saxsys.fxarmville.presentation;

import javafx.collections.ListChangeListener;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import de.saxsys.fxarmville.model.Anbaubar;
import de.saxsys.fxarmville.model.BeetReihe;

/**
 * 7
 * 
 * @author Michael
 *
 */
public class FXBeetReihe extends Parent {

	private BeetReihe beetReihe;

	private HBox reihe;

	public FXBeetReihe(BeetReihe beetReihe) {
		this.beetReihe = beetReihe;

		reihe = HBoxBuilder.create().build();

		for (Anbaubar anbaubar : beetReihe.getWaechstHier()) {
			ImageView bild = new ImageView(anbaubar.getBild());
			// Alterung über Effekte; Scaling entspricht Alter; Wachstumsgrenze: Ausfaden
			reihe.getChildren().add(bild);
		}
		
		beetReihe.getWaechstHier().addListener(new ListChangeListener<Anbaubar>(){
			@Override
			public void onChanged(
					javafx.collections.ListChangeListener.Change<? extends Anbaubar> arg0) {
				//TODO
			}
		});
	}

}
