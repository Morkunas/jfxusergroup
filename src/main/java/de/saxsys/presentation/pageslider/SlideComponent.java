package de.saxsys.presentation.pageslider;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.TranslateTransitionBuilder;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * This Component is the implementation of the slideshow. To use it, implement
 * the {@link #createPage(int)} factory method and create the elements which
 * should be displayed. Remember that the size of the elements will be changed
 * to the size of this component.
 * 
 * @author sialcasa
 * 
 */
public abstract class SlideComponent extends Pane {

	private Pane contentPane;
	private List<Pane> placeHolderPanes = new ArrayList<Pane>();

	private int pages;
	private int actualPage;

	/**
	 * Create an instance with the amount of pages which should be displayed.
	 * 
	 * @param pages
	 *            amount
	 */
	public SlideComponent(int pages) {
		this.pages = pages;
		this.getChildren().add(getContentPane());
		initPresentationComponent();
		startPresentation();
		initListeners();
	}

	private void initPresentationComponent() {
		// Create placeholders for the UI
		for (int i = 0; i < pages; i++) {
			StackPane placeholder = new StackPane();
			placeholder.setAlignment(Pos.CENTER);
			placeHolderPanes.add(placeholder);
			placeholder.minWidthProperty().bind(this.widthProperty());
			placeholder.minHeightProperty().bind(this.heightProperty());
			Rectangle e = new Rectangle();
			e.setWidth(100);
			e.setHeight(100);
			placeholder.getChildren().add(e);
			// Add pane to this component
			contentPane.getChildren().add(placeholder);
		}

	}

	private void initListeners() {
		// TODO Geeignete Listener für Seite weiter / zurück
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				nextPage();
			}
		});
	}

	private void navigateToActualIndex() {
		double newX = (placeHolderPanes.get(actualPage).getBoundsInParent()
				.getMinX()) * -1;
		double newY = (placeHolderPanes.get(actualPage).getBoundsInParent()
				.getMinY()) * -1;
		System.out.println(newX + " " + newY);
		TranslateTransitionBuilder.create().node(this)
				.duration(Duration.seconds(0.5)).fromX(getTranslateX())
				.fromY(getTranslateY()).toX(newX).toY(newY).build().play();
	}

	private void startPresentation() {
		placeHolderPanes.get(0).getChildren().add(createPage(0));
	}

	private Pane getContentPane() {
		if (contentPane == null) {
			contentPane = new ZiggZaggPane();
		}
		return contentPane;
	}

	/**
	 * This method creates an page for a page index.
	 * 
	 * @param index
	 *            of the page
	 * @return SlidePage
	 */
	public abstract SlidePage createPage(int index);

	/**
	 * Switch to the next page.
	 */
	public void nextPage() {
		if (actualPage < pages - 1) {
			actualPage++;
		}
		navigateToActualIndex();
	}

	/**
	 * Switch to the last page.
	 */
	public void lastPage() {
		if (actualPage > 1) {
			actualPage--;
		}
		navigateToActualIndex();
	}

}
