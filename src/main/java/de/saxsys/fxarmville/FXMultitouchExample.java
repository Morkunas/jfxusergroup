package de.saxsys.fxarmville;

import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.RotateEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class FXMultitouchExample extends Pane {

	final MediaExample media = new MediaExample();

	public FXMultitouchExample() {
		getChildren().add(media);
		media.setTranslateX(50);
		media.setTranslateY(50);
		createEventHandler();
	}

	private void createEventHandler() {

		initTouch();

		initSwipe();

		initZoom();

		initRotation();
	}

	private void initTouch() {

		final BooleanProperty touchStat = new SimpleBooleanProperty();
		media.setOnTouchStationary(new EventHandler<TouchEvent>() {
			@Override
			public void handle(TouchEvent touchEvent) {
				touchStat.set(true);
				media.setEffect(new DropShadow());
			}
		});
		media.setOnTouchMoved(new EventHandler<TouchEvent>() {
			@Override
			public void handle(TouchEvent touchEvent) {
				if (touchStat.get()) {
					media.setTranslateX(touchEvent.getTouchPoint().getX()
							- media.getBoundsInLocal().getWidth() / 2);
					media.setTranslateY(touchEvent.getTouchPoint().getY()
							- media.getBoundsInLocal().getHeight() / 2);
				}
			}
		});
		media.setOnTouchReleased(new EventHandler<TouchEvent>() {

			@Override
			public void handle(TouchEvent arg0) {
				touchStat.set(false);
				media.setEffect(null);
			}
		});
	}

	private void initSwipe() {
		this.setOnSwipeDown(new EventHandler<SwipeEvent>() {
			@Override
			public void handle(SwipeEvent event) {
				DemoLabel label = new DemoLabel("Swipe down", event.getX(),
						event.getY());
				getChildren().add(label);
			}
		});

		this.setOnSwipeUp(new EventHandler<SwipeEvent>() {
			@Override
			public void handle(SwipeEvent event) {
				DemoLabel label = new DemoLabel("Swipe up", event.getX(), event
						.getY());
				getChildren().add(label);
			}
		});

		this.setOnSwipeRight(new EventHandler<SwipeEvent>() {
			@Override
			public void handle(SwipeEvent event) {
				DemoLabel label = new DemoLabel("Swipe right", event.getX(),
						event.getY());
				getChildren().add(label);
			}
		});

		this.setOnSwipeLeft(new EventHandler<SwipeEvent>() {
			@Override
			public void handle(SwipeEvent event) {

				DemoLabel label = new DemoLabel("Swipe left", event.getX(),
						event.getY());
				getChildren().add(label);
			}
		});
	}

	private void initZoom() {
		media.setOnZoom(new EventHandler<ZoomEvent>() {
			@Override
			public void handle(ZoomEvent event) {
				// Todo pivotpunkt?
				media.setScaleX(media.getScaleX() * event.getZoomFactor());
				media.setScaleY(media.getScaleY() * event.getZoomFactor());
			}
		});
	}

	private void initRotation() {
		media.setRotationAxis(new Point3D(
				media.getBoundsInLocal().getWidth() / 2, media
						.getBoundsInLocal().getHeight() / 2, 0));
		media.setOnRotate(new EventHandler<RotateEvent>() {
			@Override
			public void handle(RotateEvent event) {
				media.setRotate(media.getRotate() + event.getAngle());
			}
		});
	}

	private class DemoLabel extends Group {
		Label label = new Label();

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

	private class MediaExample extends Pane {
		// TODO auf platte laden
		private MediaPlayer mediaPlayer;
		final double mediaWidth = 240;
		final double mediaHeight = 145;

		public MediaExample() {
			mediaPlayer = new MediaPlayer(new Media(ClassLoader
					.getSystemResource(
							"JavaRap_ProRes_H264_768kbit_Widescreen.mp4")
					.toString()));
			mediaPlayer.setAutoPlay(true);
			MediaView playerPane = new MediaView(mediaPlayer);
			this.setMinSize(mediaWidth, mediaHeight);
			this.setPrefSize(mediaWidth, mediaHeight);
			this.setMaxSize(mediaWidth, mediaHeight);
			this.getChildren().add(playerPane);
		}
	}
}
