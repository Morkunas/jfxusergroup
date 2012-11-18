package de.saxsys.presentation.slides;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import de.saxsys.presentation.codeeditor.EditableCodeEditor;
import de.saxsys.presentation.codeeditor.CodeEditor;
import de.saxsys.presentation.util.UFXBindings;

public class Databinding implements Initializable {

	private enum Mode {
		NORMAL, BIDIRECTIONAL, OBJB, FLUENTAPI, INFO
	}

	@FXML
	private TextField leftTextField;
	@FXML
	private TextField rightTextField;

	@FXML
	private Button normalButton;
	@FXML
	private Button biButton;
	@FXML
	private Button objbButton;
	@FXML
	private Button fluentApiButton;
	@FXML
	private Button infoButton;

	@FXML
	private Label titleLabel;

	@FXML
	private Pane examplePane;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		switchToMode(Mode.NORMAL);
		initButtonListeners();
	}

	private void switchToMode(Mode mode) {
		reset();
		switch (mode) {
		case NORMAL:
			switchToNormalBindingMode();
			break;
		case BIDIRECTIONAL:
			switchToBidirectionalMode();
			break;
		case OBJB:
			switchToObjectBindingOneMode();
			break;
		case FLUENTAPI:
			switchToFluentAPIMode();
			break;
		case INFO:
			switchToInfoMode();
			break;
		default:
			break;
		}
	}

	private void switchToNormalBindingMode() {
		titleLabel.setText("Databinding (One-Way)");
		examplePane.getChildren().clear();
		CodeEditor editor = new CodeEditor(
				"TextField textFieldLeft; \n TextField textFieldRight; \n textFieldRight.textProperty().bind(textFieldLeft.textProperty());");
		UFXBindings.bind(examplePane, editor);
		rightTextField.textProperty().bind(leftTextField.textProperty());
		examplePane.getChildren().add(editor);
	}

	private void switchToBidirectionalMode() {
		titleLabel.setText("Databinding (Two-Way / Bidirectional)");
		CodeEditor editor = new CodeEditor(
				"TextField textFieldLeft; \n TextField textFieldRight; \n textFieldLeft.textProperty().bindBidirectional(textFieldRight.textProperty());");
		UFXBindings.bind(examplePane, editor);
		rightTextField.textProperty().bindBidirectional(
				leftTextField.textProperty());
		examplePane.getChildren().add(editor);
	}

	private void switchToObjectBindingOneMode() {
		titleLabel
				.setText("Databinding (Zwischen verschiedenen Datentypen - ObjectBinding)");
		EditableCodeEditor codeEditor = new EditableCodeEditor();
		String sourceCode = "private TextField textFieldLeft;\nIntegerProperty intProp = new SimpleIntegerProperty();\n"
				+ "\n\ntextFieldLeft.textProperty().bind(new ObjectBinding<String>() {"
				+ "\n\t{"
				+ "\n\t\tbind(intProp);"
				+ "\n\t}"
				+ "\n\n\t@Override"
				+ "\n\tprotected String computeValue() {"
				+ "\n\\ttreturn String.valueOf(intProp.get());"
				+ "\n\t}"
				+ "\n});" + "intProp.set(#%i);";
		List<Property<?>> properties = new ArrayList<>();
		final IntegerProperty targetProp = new SimpleIntegerProperty(50);
		properties.add(targetProp);
		leftTextField.textProperty().bind(new ObjectBinding<String>() {
			{
				bind(targetProp);
			}

			@Override
			protected String computeValue() {
				return String.valueOf(targetProp.get());
			}
		});
		rightTextField.setOpacity(0.1);
		codeEditor.displaySourceCode(sourceCode, properties);
		examplePane.getChildren().add(codeEditor);
	}

	private void switchToFluentAPIMode() {
		titleLabel.setText("Databinding (Beispiel der Fluent-API)");
		EditableCodeEditor codeEditor = new EditableCodeEditor();
		String sourceCode = "IntegerProperty intProp = new SimpleIntegerProperty();\n"
				+ "IntegerProperty secondIntProp = new SimpleIntegerProperty();\n"
				+ "showPropety.bind(Bindings.subtract(#%i, targetProp).multiply(2).add(1));"
				+ "intProp.set(#%i);";
		List<Property<?>> properties = new ArrayList<>();
		final IntegerProperty targetProp = new SimpleIntegerProperty(1);
		final IntegerProperty summandProp = new SimpleIntegerProperty(1);
		final IntegerProperty showPropety = new SimpleIntegerProperty();
		showPropety.bind(Bindings.subtract(summandProp, targetProp).multiply(2)
				.add(1));
		properties.add(summandProp);
		properties.add(targetProp);
		leftTextField.textProperty().bind(new ObjectBinding<String>() {
			{
				bind(showPropety);
			}

			@Override
			protected String computeValue() {
				return String.valueOf(showPropety.get());
			}
		});

		rightTextField.setOpacity(0.1);
		codeEditor.displaySourceCode(sourceCode, properties);
		examplePane.getChildren().add(codeEditor);
	}

	private void switchToInfoMode() {

	}

	private void initButtonListeners() {
		normalButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				switchToMode(Mode.NORMAL);
			}
		});
		biButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				switchToMode(Mode.BIDIRECTIONAL);
			}
		});
		objbButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				switchToMode(Mode.OBJB);
			}
		});
		fluentApiButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				switchToMode(Mode.FLUENTAPI);
			}
		});
		infoButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				switchToMode(Mode.INFO);
			}
		});
	}

	private void reset() {
		examplePane.getChildren().clear();
		leftTextField.textProperty().unbind();
		rightTextField.textProperty().unbind();
		rightTextField.textProperty().unbindBidirectional(
				leftTextField.textProperty());
		leftTextField.setOpacity(1.0);
		rightTextField.setOpacity(1.0);
		leftTextField.setText("");
		rightTextField.setText("");
	}
}
