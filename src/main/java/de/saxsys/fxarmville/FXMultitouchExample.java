package de.saxsys.fxarmville;

import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.RotateEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.input.TouchPoint;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class FXMultitouchExample extends Pane {

	public FXMultitouchExample() {
		createEventHandler();
	}

	private void createEventHandler() {
		initMouseHandler();

		initTouch();

		initSwipe();

		initZoom();

		initRotation();
	}

	private void initMouseHandler() {
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				getChildren().clear();
				addCircleForMouseEvent(event);
				DemoLabel label = new DemoLabel("Mouse click");
				label.setTranslateX(event.getX());
				label.setTranslateY(event.getY());
				getChildren().add(label);
			}
		});
		this.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				getChildren().clear();
				addCircleForMouseEvent(event);
				DemoLabel label = new DemoLabel("Mouse dragged", event.getX(),
						event.getX());
				getChildren().add(label);
			}
		});
	}

	private void initTouch() {
		this.setOnTouchStationary(new EventHandler<TouchEvent>() {
			@Override
			public void handle(TouchEvent touchEvent) {
				getChildren().clear();
				for (TouchPoint touch : touchEvent.getTouchPoints()) {
					createCircleForTouch(touchEvent);
					DemoLabel label = new DemoLabel("Stationary Touch", touch
							.getX(), touch.getY());
					getChildren().add(label);
				}
			}
		});
		this.setOnTouchMoved(new EventHandler<TouchEvent>() {
			@Override
			public void handle(TouchEvent touchEvent) {
				getChildren().clear();
				for (TouchPoint touch : touchEvent.getTouchPoints()) {
					createCircleForTouch(touchEvent);
					DemoLabel label = new DemoLabel("Touch moved",
							touch.getX(), touch.getY());
					label.setTranslateX(touch.getX());
					label.setTranslateY(touch.getY());
					getChildren().add(label);
				}
			}
		});
	}

	private void initSwipe() {
		this.setOnSwipeDown(new EventHandler<SwipeEvent>() {
			@Override
			public void handle(SwipeEvent event) {
				getChildren().clear();
				DemoLabel label = new DemoLabel("Swipe down", event.getX(),
						event.getY());
				getChildren().add(label);
			}
		});

		this.setOnSwipeUp(new EventHandler<SwipeEvent>() {
			@Override
			public void handle(SwipeEvent event) {
				getChildren().clear();
				DemoLabel label = new DemoLabel("Swipe up", event.getX(), event
						.getY());
				getChildren().add(label);
			}
		});

		this.setOnSwipeRight(new EventHandler<SwipeEvent>() {
			@Override
			public void handle(SwipeEvent event) {
				getChildren().clear();
				DemoLabel label = new DemoLabel("Swipe right", event.getX(),
						event.getY());
				getChildren().add(label);
			}
		});

		this.setOnSwipeLeft(new EventHandler<SwipeEvent>() {
			@Override
			public void handle(SwipeEvent event) {
				getChildren().clear();
				DemoLabel label = new DemoLabel("Swipe left", event.getX(),
						event.getY());
				getChildren().add(label);
			}
		});
	}

	private void initZoom() {
		final Circle circle = new Circle(50);

		this.setOnZoomStarted(new EventHandler<ZoomEvent>() {
			@Override
			public void handle(ZoomEvent event) {
				DemoLabel label = new DemoLabel("Zoom detected", event.getX(),
						event.getY());
				circle.setFill(Color.ORANGE);
				circle.setCenterX(event.getX());
				circle.setCenterY(event.getY());
				getChildren().addAll(circle, label);
			}
		});

		this.setOnZoom(new EventHandler<ZoomEvent>() {
			@Override
			public void handle(ZoomEvent event) {
				circle.setScaleX(circle.getScaleX() * event.getZoomFactor());
				circle.setScaleY(circle.getScaleY() * event.getZoomFactor());
				circle.setCenterX(event.getX());
				circle.setCenterY(event.getY());

			}
		});
		this.setOnZoomFinished(new EventHandler<ZoomEvent>() {
			@Override
			public void handle(ZoomEvent event) {
				circle.setScaleX(1);
				circle.setScaleY(1);
				getChildren().clear();
			}
		});
	}

	private void initRotation() {
		getChildren().clear();
		final DemoLabel label = new DemoLabel("Rotate");
		final Rectangle rect = new Rectangle(80, 80);
		rect.setFill(Color.RED);

		this.setOnRotationStarted(new EventHandler<RotateEvent>() {
			@Override
			public void handle(RotateEvent event) {
				getChildren().addAll(rect, label);
				label.setTranslateX(event.getX());
				label.setTranslateY(event.getY());
				rect.setX(event.getX() - rect.getWidth() / 2);
				rect.setY(event.getY() - rect.getHeight() / 2);
			}
		});

		this.setOnRotate(new EventHandler<RotateEvent>() {
			@Override
			public void handle(RotateEvent event) {
				rect.setRotate(rect.getRotate() + event.getAngle());
			}
		});

		this.setOnRotationFinished(new EventHandler<RotateEvent>() {
			@Override
			public void handle(RotateEvent event) {
				getChildren().clear();
			}
		});
	}

	private class DemoLabel extends Group {
		Label label = new Label();

		public DemoLabel(String text) {
			this(text, 0, 0);
		}

		public DemoLabel(String text, double x, double y) {
			setTranslateX(x);
			setTranslateY(y);
			label.setText(text);
			label.setStyle("-fx-font-size:80px;");
			DropShadow dropShadow = new DropShadow();
			label.setEffect(dropShadow);
			getChildren().add(label);
			this.parentProperty().addListener(new ChangeListener<Parent>() {
				@Override
				public void changed(ObservableValue<? extends Parent> arg0,
						Parent arg1, Parent arg2) {
					FadeTransition fadeOut = FadeTransitionBuilder.create()
							.duration(Duration.seconds(5)).toValue(0)
							.node(label).build();
					fadeOut.setOnFinished(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent arg0) {
							Pane pane = FXMultitouchExample.this;
							pane.getChildren().remove(label);
						}
					});
					fadeOut.play();
				}
			});
		}
	}

	private void addCircleForMouseEvent(MouseEvent event) {
		Circle circle = new Circle(50);
		circle.setFill(Color.GREEN);
		circle.setCenterX(event.getX());
		circle.setCenterY(event.getY());
		getChildren().add(circle);
	}

	private void createCircleForTouch(TouchEvent event) {
		Circle circle = new Circle(50);
		circle.setFill(Color.ORANGE);
		circle.setCenterX(event.getTouchPoint().getX());
		circle.setCenterY(event.getTouchPoint().getY());
		getChildren().add(circle);
	}
}
