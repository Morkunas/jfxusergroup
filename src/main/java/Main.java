import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import de.saxsys.presentation.codeeditor.CodeEditor;
import de.saxsys.presentation.elements.AnimatedBug;
import de.saxsys.presentation.pageslider.SlideComponent;
import de.saxsys.presentation.pageslider.SlidePage;
import de.saxsys.presentation.util.UFXBindings;

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
				} else {
					CodeEditor codeEditor = new CodeEditor();
					String sourceCode = "public static void main(String[] args) {\n"
							+ "\tRectangle r = RectangleBuilder.create().width(#%i).height(#%i).build();\n"
							+ "}";
					List<Property<?>> properties = new ArrayList<>();
					properties.add(new SimpleIntegerProperty(50));
					properties.add(new SimpleIntegerProperty(100));
					codeEditor.displaySourceCode(sourceCode, properties);
					slidePage.getChildren().add(codeEditor);
					slidePage.getChildren().add(new AnimatedBug());
				}
				return slidePage;

			}
		};
		UFXBindings.bind(scene, slideComponent);
		pane.getChildren().add(slideComponent);
	}

}
