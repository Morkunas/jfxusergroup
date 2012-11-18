import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import de.saxsys.presentation.pageslider.SlideComponent;
import de.saxsys.presentation.pageslider.SlidePage;
import de.saxsys.presentation.util.UFXBindings;
import de.saxsys.presentation.util.UFXScaler;

public class Main extends Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Pane pane = new Pane();

		Scene scene = new Scene(pane, 600, 600);

		UFXBindings.bind(scene, pane);

		stage.setScene(scene);
		stage.setTitle("Creating an Application Window");

		stage.show();
		SlideComponent slideComponent = new SlideComponent(7) {

			@Override
			public SlidePage createPage(int i) {
				switch (i) {
				case 0: {
					SlidePage slidePage = createSlideComponent(i);
					return slidePage;
				}
				case 1: {
					SlidePage slidePage = createSlideComponent(i);
					return slidePage;
				}
				case 2: {
					SlidePage slidePage = createSlideComponent(i);
					return slidePage;
				}
				case 3: {
					SlidePage slidePage = createSlideComponent(i);
					return slidePage;
				}
				case 4: {
					SlidePage slidePage = createSlideComponent(i);
					return slidePage;
				}
				case 5: {
					SlidePage slidePage = createSlideComponent(i);
					return slidePage;
				}
				case 6: {
					SlidePage slidePage = createSlideComponent(i);
					return slidePage;
				}
				default:
					return null;
				}
			}

			private SlidePage createSlideComponent(int page) {
				SlidePage slidePage = new SlidePage();
				// FIXME wozu ist diese abfrage- danach geht bei mir nix.
				if (page == 0) {
					AnchorPane blub = null;
					try {
						blub = (AnchorPane) FXMLLoader.load(Main.class
								.getResource("test.fxml"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					slidePage.getChildren().add(blub);
					return slidePage;
				}

				if (page == 1) {
					StackPane blub = null;
					try {
						blub = (StackPane) FXMLLoader.load(Main.class
								.getResource("Databinding.fxml"));
					} catch (IOException e) {
						e.printStackTrace();
					}
					UFXScaler.scaleTo(slidePage, blub);
					slidePage.getChildren().add(blub);
					return slidePage;
				}
				return slidePage;

			}
		};
		UFXBindings.bind(scene, slideComponent);
		pane.getChildren().add(slideComponent);
	}

}
