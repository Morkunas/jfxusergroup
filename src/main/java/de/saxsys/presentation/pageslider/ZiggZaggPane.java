package de.saxsys.presentation.pageslider;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/**
 * This pane manages the layout of panes in zigzag form.
 * 
 * @author sialcasa
 * 
 */
public class ZiggZaggPane extends GridPane {

	public ZiggZaggPane() {
		this.getChildren().addListener(createChildAddedListener());
	}

	private void layoutContents(ObservableList<? extends Node> list) {
		int x = 0, y = 0;
		for (int i = 0; i < list.size(); i++) {
			if (i % 2 == 0 && i > 0) {
				y++;
				x--;
			}
			GridPane.setConstraints(list.get(i), y, x);
			x++;
		}
	}

	private ListChangeListener<Node> createChildAddedListener() {
		return new ListChangeListener<Node>() {
			@Override
			public void onChanged(
					javafx.collections.ListChangeListener.Change<? extends Node> event) {
				event.next();
				layoutContents(event.getList());
			}
		};
	}
}
