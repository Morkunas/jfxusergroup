import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import de.saxsys.presentation.pageslider.SlideComponent;
import de.saxsys.presentation.pageslider.SlidePage;

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

		pane.minWidthProperty().bind(scene.widthProperty());
		pane.minHeightProperty().bind(scene.heightProperty());

		stage.setScene(scene);
		stage.setTitle("Creating an Application Window");

		stage.show();
		SlideComponent slideComponent = new SlideComponent(7) {

			@Override
			public SlidePage createPage(int i) {
				switch (i) {
				case 0:
					return new SlidePage();
				case 2:
					return new SlidePage();
				case 3:
					return new SlidePage();
				case 4:
					return new SlidePage();
				case 5:
					return new SlidePage();
				case 6:
					return new SlidePage();
				default:
					return null;
				}
			}
		};
		slideComponent.minWidthProperty().bind(stage.widthProperty());
		slideComponent.minHeightProperty().bind(stage.heightProperty());
		pane.getChildren().add(slideComponent);
	}

}
