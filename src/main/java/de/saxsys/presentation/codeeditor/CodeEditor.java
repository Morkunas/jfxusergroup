package de.saxsys.presentation.codeeditor;

import java.util.List;

import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;

import org.apache.commons.lang.StringUtils;

/**
 * A component to display source code that contains editable text fields that
 * are bound to properties.
 * 
 * @author michael.thiele
 * 
 */
public class CodeEditor extends VBox {

	private String[] javaKeywords = new String[] {
		"public", "static", "void", "final", "if", "else", "private", "protected"
	};
	
	/**
	 * Displays the given source code with editable {@link TextField}s for the
	 * given {@link Property}s. The source code can be formatted in the
	 * following ways:
	 * <ul>
	 * <li>'\t' and other whitespace are displayed as is
	 * <li>'\n' denotes the end of a line; will add a row in the display
	 * {@link VBox}
	 * <li>'#%i' will denote a placeholder for a property value; %i for
	 * {@link IntegerProperty}s, %d for {@link DoubleProperty}s, etc.
	 * <ul>
	 * 
	 * @param sourceCode
	 *            the source code to display
	 * @param changeableProperties
	 *            a {@link List} of {@link Property}s that are displayed in the
	 *            placeholders of the given source code
	 */
	public void displaySourceCode(final String sourceCode,
			final List<Property<?>> changeableProperties) {

		// keep track of the changeableProperties
		int indexOfChangeableProperties = 0;

		for (final String codeLine : sourceCode.split("\n")) {
			final HBox codeLineHBox = new HBox();
			this.getChildren().add(codeLineHBox);

			for (final String inLine : StringUtils.splitPreserveAllTokens(
					codeLine, "#")) {
				String displayLine;
				if (inLine.startsWith("%")) {
					final TextField textFieldForProperty = initTextField(
							inLine.substring(1, 2), changeableProperties,
							indexOfChangeableProperties);
					indexOfChangeableProperties += 1;

					codeLineHBox.getChildren().add(textFieldForProperty);

					displayLine = inLine.substring(2);
				} else {
					displayLine = inLine;
				}

				
				final Label inLineText = new Label();
				inLineText.setText(displayLine);
				codeLineHBox.getChildren().add(inLineText);

			}

		}
	}

	private TextField initTextField(final String type,
			final List<Property<?>> changeableProperties,
			int indexOfChangeableProperties) {
		final TextField textFieldForProperty = new TextField();
		final Property<?> property = changeableProperties
				.get(indexOfChangeableProperties);
		if (type.equals("i") && property.getValue() instanceof Integer) {
			final Property<Integer> integerProperty = (Property<Integer>) property;
			textFieldForProperty.textProperty().bindBidirectional(
					integerProperty, new IntegerStringConverter());
			IntegerBinding lengthBinding = new IntegerBinding() {
				{
					bind(integerProperty);
				}

				@Override
				protected int computeValue() {
					
					return integerProperty == null ? 1 : (int) Math
							.log10(integerProperty.getValue()) + 1;
				}
			};
			textFieldForProperty.prefColumnCountProperty().bind(lengthBinding);
		}

		return textFieldForProperty;
	}

}
