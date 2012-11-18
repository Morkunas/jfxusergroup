package de.saxsys.presentation.codeeditor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import org.junit.Before;
import org.junit.Test;

public class CodeEditorTest {

	private EditableCodeEditor codeEditor;

	@Before
	public void setup() {
		String sourceCode = "public static void main(String[] args) {\n"
				+ "\tRectangle r = RectangleBuilder.create().width(#%i).height(#%i).build();\n"
				+ "}";
		List<Property<?>> properties = new ArrayList<>();
		properties.add(new SimpleIntegerProperty(50));
		properties.add(new SimpleIntegerProperty(100));
		codeEditor = new EditableCodeEditor();
		codeEditor.displaySourceCode(sourceCode, properties);
	}

	@Test
	public void rightAmountOfLinesAndPropertyFields() {
		assertEquals(3, codeEditor.getChildrenUnmodifiable().size());

		Node secondLine = codeEditor.getChildrenUnmodifiable().get(1);
		if (secondLine instanceof HBox) {
			HBox secondLineHBox = (HBox) secondLine;
			assertEquals(5, secondLineHBox.getChildrenUnmodifiable().size());
			
			assertTrue(secondLineHBox.getChildrenUnmodifiable().get(0) instanceof Text);
			assertTrue(secondLineHBox.getChildrenUnmodifiable().get(1) instanceof TextField);
			assertTrue(secondLineHBox.getChildrenUnmodifiable().get(2) instanceof Text);
			assertTrue(secondLineHBox.getChildrenUnmodifiable().get(3) instanceof TextField);
			assertTrue(secondLineHBox.getChildrenUnmodifiable().get(4) instanceof Text);
		} else {
			fail("The second line has to have property fields that are displyed in an HBox.");
		}
	}
}
