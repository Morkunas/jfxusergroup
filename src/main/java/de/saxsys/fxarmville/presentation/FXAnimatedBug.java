package de.saxsys.fxarmville.presentation;

import java.util.Random;

import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.animation.PathTransitionBuilder;
import javafx.animation.RotateTransitionBuilder;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

/**
 * This component is a bug which crawls over the element where it is added. If
 * you hit the bug, it will die.
 * 
 * @author sialcasa
 * 
 */
public class FXAnimatedBug extends Group {

	private ImageView bug = new ImageView(new Image(
			ClassLoader.getSystemResourceAsStream("bug.png")));

	private PathTransition transition;

	public FXAnimatedBug() {
		int size = new Random().nextInt(40) + 70;
		bug.setFitHeight(size);
		bug.setFitWidth(size);
		// Füge Imageview als Kind zur Group (this)
		this.getChildren().add(bug);
		// Clicklistener - Ändert Bild von Bug zu Blut
		bug.setOnMouseClicked(createMouseListener());
		// Parent listener - automatisches Starten der Animation, wenn this ein
		// parent bekommt
		parentProperty().addListener(createParentChangedListener());
	}

	private void startBugAnimation() {
		// erstmaliges Ausrichten des Bildes
		bug.setRotate(90);
		// Erzeugen des Animation paths
		Path path = new Path();
		path.getElements().add(new MoveTo(300, 300));
		path.getElements().add(new CubicCurveTo(350, 0, 350, 200, 200, 200));
		path.getElements().add(new CubicCurveTo(0, 200, 0, 240, 350, 240));
		path.getElements().add(new CubicCurveTo(350, 0, 350, 200, 200, 200));
		path.getElements().add(new CubicCurveTo(0, 200, 0, 240, 350, 240));

		// Erstellen der Transition
		int transitionDuration = new Random().nextInt(25) + 25;
		transition = PathTransitionBuilder.create()
				.duration(Duration.seconds(transitionDuration)).node(this)
				.path(path).orientation(OrientationType.ORTHOGONAL_TO_TANGENT)
				.autoReverse(true).rate(5f).cycleCount(Timeline.INDEFINITE)
				.build();

		//
		transition.currentRateProperty().addListener(
				createRateChangedListener());

		transition.play();
	}

	/*
	 * Animation Helper
	 */
	private void checkForRotateBug(Number oldRate, Number newRate) {
		if (oldRate.intValue() == transition.getRate()
				&& newRate.intValue() == -transition.getRate()) {
			RotateTransitionBuilder.create().toAngle(260).node(bug).build()
					.play();
		}
		if (oldRate.intValue() == -transition.getRate()
				&& newRate.intValue() == transition.getRate()) {
			RotateTransitionBuilder.create().toAngle(90).node(bug).build()
					.play();
		}
	}

	private void fadeBugOut() {
		FadeTransition fadeTransition = FadeTransitionBuilder.create()
				.fromValue(1.0).node(FXAnimatedBug.this).toValue(0).rate(1f)
				.duration(Duration.seconds(2)).build();
		fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				try {
					FXFarm parent = (FXFarm) getParent();
					parent.getChildren().remove(FXAnimatedBug.this);
				} catch (Exception e) {
					// Hier fliegen ab und zu exceptions auf die ich jetzt kein
					// bock hab :P
				}
			}
		});
		fadeTransition.play();
	}

	/*
	 * Helper
	 */

	private ChangeListener<? super Number> createRateChangedListener() {
		return new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0,
					Number oldRate, Number newRate) {
				checkForRotateBug(oldRate, newRate);
			}
		};
	}

	private EventHandler<? super MouseEvent> createMouseListener() {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				arg0.consume();
				bug.setImage(new Image(ClassLoader
						.getSystemResourceAsStream("blood.png")));
				transition.stop();
				fadeBugOut();
			}
		};
	}

	private ChangeListener<? super Parent> createParentChangedListener() {
		return new ChangeListener<Parent>() {
			@Override
			public void changed(ObservableValue<? extends Parent> arg0,
					Parent oldVal, Parent newVal) {
				if (newVal != null) {
					startBugAnimation();
				}
			}
		};
	}

}
