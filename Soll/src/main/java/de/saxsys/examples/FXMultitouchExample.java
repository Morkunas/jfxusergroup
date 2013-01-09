package de.saxsys.examples;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.RotateEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.input.TouchPoint;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class FXMultitouchExample extends Pane {

    final VideoExample media = new VideoExample();

    public FXMultitouchExample() {
        getChildren().add(media);
        media.setTranslateX(50);
        media.setTranslateY(50);
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

    private class DemoLabel extends Group {
        Label label = new Label();

        public DemoLabel(final String text, final double x, final double y) {
            setTranslateX(x);
            setTranslateY(y);
            label.setText(text);
            label.setStyle("-fx-font-size:80px;");
            final DropShadow dropShadow = new DropShadow();
            label.setEffect(dropShadow);
            getChildren().add(label);
            this.parentProperty().addListener(new ChangeListener<Parent>() {
                @Override
                public void changed(final ObservableValue<? extends Parent> arg0, final Parent arg1, final Parent arg2) {
                    final FadeTransition fadeOut =
                            FadeTransitionBuilder.create().duration(Duration.seconds(5)).toValue(0).node(label).build();
                    fadeOut.setOnFinished(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(final ActionEvent arg0) {
                            final Pane pane = FXMultitouchExample.this;
                            pane.getChildren().remove(label);
                        }
                    });
                    fadeOut.play();
                }
            });
        }
    }

    protected class VideoExample extends Pane {
        private final AnimationTimer timer;
        private final Canvas canvas;
        private final MediaExample background;
        private final List<Particle> particles = new ArrayList<Particle>();
        private final Paint[] colors;
        private int countDownTillNextFirework = 40;

        public VideoExample() {
            // create a color palette of 180 colors
            colors = new Paint[181];
            colors[0] =
                    new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE, new Stop(0, Color.WHITE),
                            new Stop(0.2, Color.hsb(59, 0.38, 1)), new Stop(0.6, Color.hsb(59, 0.38, 1, 0.1)),
                            new Stop(1, Color.hsb(59, 0.38, 1, 0)));
            for (int h = 0; h < 360; h += 2) {
                colors[1 + (h / 2)] =
                        new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE, new Stop(0, Color.WHITE),
                                new Stop(0.2, Color.hsb(h, 1, 1)), new Stop(0.6, Color.hsb(h, 1, 1, 0.1)), new Stop(1,
                                        Color.hsb(h, 1, 1, 0)));
            }
            // create canvas
            background = new MediaExample();
            canvas = new Canvas(background.getWidth(), background.getHeight());

            canvas.setBlendMode(BlendMode.ADD);
            canvas.setEffect(new Reflection(0, 0.4, 0.15, 0));
            getChildren().addAll(background, canvas);
            // create animation timer that will be called every frame
            // final AnimationTimer timer = new AnimationTimer() {
            timer = new AnimationTimer() {

                @Override
                public void handle(final long now) {
                    final GraphicsContext gc = canvas.getGraphicsContext2D();
                    // clear area with transparent black
                    gc.setFill(Color.rgb(0, 0, 0, 0.2));
                    gc.fillRect(0, 0, 1024, 708);
                    // draw fireworks
                    drawFireworks(gc);
                    // countdown to launching the next firework
                    if (countDownTillNextFirework == 0) {
                        countDownTillNextFirework = 10 + (int) (Math.random() * 30);
                        fireParticle();
                    }
                    countDownTillNextFirework--;
                }
            };
        }

        public void start() {
            timer.start();
        }

        public void stop() {
            timer.stop();
        }

        /**
         * Make resizable and keep background image proportions and center.
         */
        @Override
        protected void layoutChildren() {
            // final double w = 480.0;
            // final double h = 360.0;
            final double w = 800;
            final double h = 520;
            final double scale = Math.min(w / 1024d, h / 708d);
            final int width = (int) (1024 * scale);
            final int height = (int) (708 * scale);
            final int x = (int) ((w - width) / 2);
            final int y = (int) ((h - height) / 2);
            background.relocate(x, y);
            canvas.relocate(x, y);
            canvas.setWidth(width);
            canvas.setHeight(height * 0.706);
        }

        private void drawFireworks(final GraphicsContext gc) {
            final Iterator<Particle> iter = particles.iterator();
            final List<Particle> newParticles = new ArrayList<Particle>();
            while (iter.hasNext()) {
                final Particle firework = iter.next();
                // if the update returns true then particle has expired
                if (firework.update()) {
                    // remove particle from those drawn
                    iter.remove();
                    // check if it should be exploded
                    if (firework.shouldExplodeChildren) {
                        if (firework.size == 9) {
                            explodeCircle(firework, newParticles);
                        } else if (firework.size == 8) {
                            explodeSmallCircle(firework, newParticles);
                        }
                    }
                }
                firework.draw(gc);
            }
            particles.addAll(newParticles);
        }

        private void fireParticle() {
            particles.add(new Particle(canvas.getWidth() * 0.5, canvas.getHeight() + 10, Math.random() * 5 - 2.5, 0, 0,
                    150 + Math.random() * 100, colors[0], 9, false, true, true));
        }

        private void explodeCircle(final Particle firework, final List<Particle> newParticles) {
            final int count = 20 + (int) (60 * Math.random());
            final boolean shouldExplodeChildren = Math.random() > 0.5;
            final double angle = (Math.PI * 2) / count;
            final int color = (int) (Math.random() * colors.length);
            for (int i = count; i > 0; i--) {
                final double randomVelocity = 4 + Math.random() * 4;
                final double particleAngle = i * angle;
                newParticles.add(new Particle(firework.posX, firework.posY, Math.cos(particleAngle) * randomVelocity,
                        Math.sin(particleAngle) * randomVelocity, 0, 0, colors[color], 8, true, shouldExplodeChildren,
                        true));
            }
        }

        private void explodeSmallCircle(final Particle firework, final List<Particle> newParticles) {
            final double angle = (Math.PI * 2) / 12;
            for (int count = 12; count > 0; count--) {
                final double randomVelocity = 2 + Math.random() * 2;
                final double particleAngle = count * angle;
                newParticles.add(new Particle(firework.posX, firework.posY, Math.cos(particleAngle) * randomVelocity,
                        Math.sin(particleAngle) * randomVelocity, 0, 0, firework.color, 4, true, false, false));
            }
        }
    }

    /**
     * A Simple Particle that draws its self as a circle.
     */
    public static class Particle {
        private static final double GRAVITY = 0.06;
        // properties for animation
        // and colouring
        double alpha;
        final double easing;
        double fade;
        double posX;
        double posY;
        double velX;
        double velY;
        final double targetX;
        final double targetY;
        final Paint color;
        final int size;
        final boolean usePhysics;
        final boolean shouldExplodeChildren;
        final boolean hasTail;
        double lastPosX;
        double lastPosY;

        public Particle(final double posX, final double posY, final double velX, final double velY,
                final double targetX, final double targetY, final Paint color, final int size,
                final boolean usePhysics, final boolean shouldExplodeChildren, final boolean hasTail) {
            this.posX = posX;
            this.posY = posY;
            this.velX = velX;
            this.velY = velY;
            this.targetX = targetX;
            this.targetY = targetY;
            this.color = color;
            this.size = size;
            this.usePhysics = usePhysics;
            this.shouldExplodeChildren = shouldExplodeChildren;
            this.hasTail = hasTail;
            this.alpha = 1;
            this.easing = Math.random() * 0.02;
            this.fade = Math.random() * 0.1;
        }

        public boolean update() {
            lastPosX = posX;
            lastPosY = posY;
            if (this.usePhysics) { // on way down
                velY += GRAVITY;
                posY += velY;
                this.alpha -= this.fade; // fade out particle
            } else { // on way up
                final double distance = (targetY - posY);
                // ease the position
                posY += distance * (0.03 + easing);
                // cap to 1
                alpha = Math.min(distance * distance * 0.00005, 1);
            }
            posX += velX;
            return alpha < 0.005;
        }

        public void draw(final GraphicsContext context) {
            final double x = Math.round(posX);
            final double y = Math.round(posY);
            final double xVel = (x - lastPosX) * -5;
            final double yVel = (y - lastPosY) * -5;
            // set the opacity for all drawing of this particle
            context.setGlobalAlpha(Math.random() * this.alpha);
            // draw particle
            context.setFill(color);
            context.fillOval(x - size, y - size, size + size, size + size);
            // draw the arrow triangle from where we were to where we are now
            if (hasTail) {
                context.setFill(Color.rgb(255, 255, 255, 0.3));
                context.fillPolygon(new double[] { posX + 1.5, posX + xVel, posX - 1.5 }, new double[] { posY,
                        posY + yVel, posY }, 3);
            }
        }
    }

    protected class MediaExample extends Pane {
        private final MediaPlayer mediaPlayer;
        final double mediaWidth = 800;
        final double mediaHeight = 520;

        public MediaExample() {
            mediaPlayer =
                    new MediaPlayer(new Media(ClassLoader.getSystemResource(
                            "JavaRap_ProRes_H264_768kbit_Widescreen.mp4").toString()));
            mediaPlayer.setAutoPlay(true);
            final MediaView playerPane = new MediaView(mediaPlayer);
            this.setMinSize(mediaWidth, mediaHeight);
            this.setPrefSize(mediaWidth, mediaHeight);
            this.setMaxSize(mediaWidth, mediaHeight);
            this.getChildren().add(playerPane);
        }
    }

}
