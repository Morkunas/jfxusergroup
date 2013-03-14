package de.saxsys.examples;

import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
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

public class FXMultitouchExample extends Pane {

    final VideoExample media = new VideoExample();

    public FXMultitouchExample() {
        getChildren().add(media);
        media.setTranslateX(600);
        media.setTranslateY(400);
        createEventHandler();
    }

    private void createEventHandler() {

        initMouseDoubleClick();

        initTouch();

        initSwipe();

        initZoom();

        initRotation();
    }

    private void initMouseDoubleClick() {
        media.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(final MouseEvent arg0) {
                if (arg0.getClickCount() == 2)
                    media.start();
            }
        });
    }

    private void initTouch() {

        final BooleanProperty touchArmed = new SimpleBooleanProperty();
        media.setOnTouchStationary(new EventHandler<TouchEvent>() {
            @Override
            public void handle(final TouchEvent touchEvent) {

                touchArmed.set(true);
                media.setEffect(new DropShadow());
            }
        });
        media.setOnTouchMoved(new EventHandler<TouchEvent>() {
            @Override
            public void handle(final TouchEvent touchEvent) {
                if (touchArmed.get()) {
                    TouchPoint exactPoint = null;
                    for (final TouchPoint touch : touchEvent.getTouchPoints()) {
                        if (exactPoint == null || touch.getX() > exactPoint.getX()) {
                            exactPoint = touch;
                        }
                    }
                    media.setTranslateX(exactPoint.getSceneX() - media.getWidth() / 2);
                    media.setTranslateY(exactPoint.getSceneY() - media.getHeight() / 2);
                    touchEvent.consume();
                }
            }
        });
        media.setOnTouchReleased(new EventHandler<TouchEvent>() {

            @Override
            public void handle(final TouchEvent arg0) {
                touchArmed.set(false);
                media.setEffect(null);
            }
        });
        final List<Circle> circles = FXCollections.<Circle> observableArrayList();
        this.setOnTouchMoved(new EventHandler<TouchEvent>() {

            @Override
            public void handle(final TouchEvent arg0) {
                for (final TouchPoint point : arg0.getTouchPoints()) {

                    final Circle newC = new Circle();
                    newC.setRadius(20);
                    newC.setTranslateX(point.getSceneX() - 10);
                    newC.setTranslateY(point.getSceneY() - 10);
                    newC.setFill(Color.RED);
                    getChildren().add(newC);
                    circles.add(newC);
                }
            }
        });
        this.setOnTouchReleased(new EventHandler<TouchEvent>() {

            @Override
            public void handle(final TouchEvent arg0) {
                clearCircl(circles);
            }

        });
    }

    private void clearCircl(final List<Circle> circles) {
        for (final Circle circl : circles) {
            getChildren().remove(circl);
        }
        circles.clear();
    }

    private void initSwipe() {
        this.setOnSwipeDown(new EventHandler<SwipeEvent>() {
            @Override
            public void handle(final SwipeEvent event) {
                final DemoLabel label = new DemoLabel("Swipe down", event.getX(), event.getY());
                getChildren().add(label);
            }
        });

        this.setOnSwipeUp(new EventHandler<SwipeEvent>() {
            @Override
            public void handle(final SwipeEvent event) {
                final DemoLabel label = new DemoLabel("Swipe up", event.getX(), event.getY());
                getChildren().add(label);
            }
        });

        this.setOnSwipeRight(new EventHandler<SwipeEvent>() {
            @Override
            public void handle(final SwipeEvent event) {
                final DemoLabel label = new DemoLabel("Swipe right", event.getX(), event.getY());
                getChildren().add(label);
            }
        });

        this.setOnSwipeLeft(new EventHandler<SwipeEvent>() {
            @Override
            public void handle(final SwipeEvent event) {

                final DemoLabel label = new DemoLabel("Swipe left", event.getX(), event.getY());
                getChildren().add(label);
            }
        });
    }

    private void initZoom() {
        media.setOnZoom(new EventHandler<ZoomEvent>() {
            @Override
            public void handle(final ZoomEvent event) {
                // Todo pivotpunkt?
                media.setScaleX(media.getScaleX() * event.getZoomFactor());
                media.setScaleY(media.getScaleY() * event.getZoomFactor());
            }
        });
    }

    private void initRotation() {
        media.setRotationAxis(new Point3D(media.getBoundsInLocal().getWidth() / 2,
                media.getBoundsInLocal().getHeight() / 2, 0));
        media.setOnRotate(new EventHandler<RotateEvent>() {
            @Override
            public void handle(final RotateEvent event) {
                media.setRotate(media.getRotate() + event.getAngle());
            }
        });
    }

    /**
     * PRIVATE CLASSES - No good design but... ;)
     */

}
