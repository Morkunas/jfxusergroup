package de.saxsys.presentation.pageslider;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * Page which is implemented as a stack pane and binds its size to its parent.
 * 
 * @author sialcasa
 * 
 */
public class SlidePage extends StackPane {

	public SlidePage() {
		this.parentProperty().addListener(createParentChangeListener());
	}

	private ChangeListener<Parent> createParentChangeListener() {
		return new ChangeListener<Parent>() {
			public void changed(ObservableValue<? extends Parent> arg0,
					Parent arg1, Parent newParent) {
				if (newParent != null) {
					Pane pane = (Pane) newParent;
					minWidthProperty().bind(pane.widthProperty());
					minHeightProperty().bind(pane.heightProperty());
				}
			}
		};
	}
}
