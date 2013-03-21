package de.saxsys.examples;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
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

/**
 * Dieses Beispiel zeigt die Funktionaltät von Touch-Events und Anwendung von Filtern.
 */
public class FXMultitouchExample extends Pane {

    private final VideoExample media = new VideoExample();

    /**
     * Um konkurierende Nebenläufigkeiten der Touchevents zu vermeiden, nutzen wir eine Statemachine.
     */
    private enum TouchState {
        UNTOUCHED, TOUCHED, MOVING
    }

    private TouchState touchState = TouchState.UNTOUCHED;

    public FXMultitouchExample() {
        getChildren().add(media);
        media.setTranslateX(600);
        media.setTranslateY(400);
        createEventHandler();
    }

    private void createEventHandler() {
        initTouch();
        initSwipe();
        initZoom();
        initRotation();
        initMouseDoubleClick();
    }

    private void initZoom() {
        media.setOnZoom(new EventHandler<ZoomEvent>() {
            @Override
            public void handle(final ZoomEvent event) {
                touchState = TouchState.TOUCHED;
                // FIXME
                media.setScaleX(media.getScaleX() * event.getZoomFactor());
                media.setScaleY(media.getScaleY() * event.getZoomFactor());
            }
        });
    }

    private void initRotation() {

        media.setOnRotate(new EventHandler<RotateEvent>() {
            @Override
            public void handle(final RotateEvent event) {
                touchState = TouchState.TOUCHED;
                // FIXME
                media.setRotate(media.getRotate() + event.getAngle());
            }
        });
    }

    private void initSwipe() {

        this.setOnSwipeDown(new EventHandler<SwipeEvent>() {
            @Override
            public void handle(final SwipeEvent event) {
                // FIXME
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

    private void initTouch() {

        /*
         * TOUCH MOVE - Circles zeichnen
         */
        final List<Circle> circles = FXCollections.<Circle> observableArrayList();

        this.setOnTouchMoved(new EventHandler<TouchEvent>() {

            @Override
            public void handle(final TouchEvent touchEvent) {
                // FIXME
                for (final TouchPoint point : touchEvent.getTouchPoints()) {

                    final Circle circle = createCircle();

                    circle.setTranslateX(point.getSceneX() - circle.getRadius() / 2);
                    circle.setTranslateY(point.getSceneY() - circle.getRadius() / 2);

                    getChildren().add(circle);
                    circles.add(circle);
                }
            }

        });

        /*
         * TOUCH RELEASE CIRCLES ENTFERNEN
         */

        this.setOnTouchReleased(new EventHandler<TouchEvent>() {
            @Override
            public void handle(final TouchEvent touchEvent) {
                removeCircles(circles);
            }

        });

        /**
         * END-LIVE
         */
        /**
         * END-LIVE
         */
        /**
         * END-LIVE
         */
        /**
         * END-LIVE
         */

        /*
         * TOUCH HALTEN
         */
        media.setOnTouchStationary(new EventHandler<TouchEvent>() {
            @Override
            public void handle(final TouchEvent touchEvent) {
                if (touchState == TouchState.UNTOUCHED) {
                    touchState = TouchState.MOVING;
                    media.setEffect(new DropShadow());
                }
            }
        });

        /*
         * TOUCH RELEASEN
         */
        media.setOnTouchReleased(new EventHandler<TouchEvent>() {
            @Override
            public void handle(final TouchEvent touchEvent) {
                touchState = TouchState.UNTOUCHED;
                media.setEffect(null);
            }
        });

        /*
         * TOUCH MOVE
         */
        media.setOnTouchMoved(new EventHandler<TouchEvent>() {
            @Override
            public void handle(final TouchEvent touchEvent) {
                if (touchState == TouchState.MOVING) {
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
    }

    private void initMouseDoubleClick() {
        media.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent event) {
                if (event.getClickCount() == 2) {
                    media.start();
                }
            }
        });
    }

    /*
     * HELPER
     */

    private Circle createCircle() {
        final Circle newC = new Circle();
        newC.setRadius(20);
        newC.setFill(Color.RED);
        return newC;
    }

    private void removeCircles(final List<Circle> circles) {
        for (final Circle circl : circles) {
            getChildren().remove(circl);
        }
        circles.clear();
    }
}
