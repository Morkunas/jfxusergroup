package de.saxsys.presentation.startpage;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransitionBuilder;
import javafx.animation.ScaleTransitionBuilder;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.InsetsBuilder;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import jfxtras.labs.scene.control.gauge.Battery;
import jfxtras.labs.scene.control.gauge.Clock;
import jfxtras.labs.scene.control.gauge.ClockBuilder;
import jfxtras.labs.scene.control.gauge.Content;
import jfxtras.labs.scene.control.gauge.Content.Align;
import jfxtras.labs.scene.control.gauge.Content.Effect;
import jfxtras.labs.scene.control.gauge.Content.Gap;
import jfxtras.labs.scene.control.gauge.Content.MatrixColor;
import jfxtras.labs.scene.control.gauge.Content.MatrixFont;
import jfxtras.labs.scene.control.gauge.Content.PostEffect;
import jfxtras.labs.scene.control.gauge.Content.RotationOrder;
import jfxtras.labs.scene.control.gauge.Content.Type;
import jfxtras.labs.scene.control.gauge.ContentBuilder;
import jfxtras.labs.scene.control.gauge.Gauge;
import jfxtras.labs.scene.control.gauge.Lcd;
import jfxtras.labs.scene.control.gauge.LcdDesign;
import jfxtras.labs.scene.control.gauge.Led;
import jfxtras.labs.scene.control.gauge.LedBuilder;
import jfxtras.labs.scene.control.gauge.MatrixPanel;
import jfxtras.labs.scene.control.gauge.MatrixPanelBuilder;
import jfxtras.labs.scene.control.gauge.Radial;
import jfxtras.labs.scene.control.gauge.StyleModel;
import jfxtras.labs.scene.control.gauge.StyleModelBuilder;

public class StartPage extends Application {

    private static final Random RND = new Random();
    private static final long DATA_PERIOD = 250000000l;

    private long lastDataCall = 0;
    private static final int AMOUNT = 10;
    private final ArrayList<ArrayList<Led>> rows = new ArrayList<ArrayList<Led>>(AMOUNT);

    private Battery BATTERY;
    private double charge;
    private boolean charging = true;

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) throws Exception {
        final GridPane rootPane = new GridPane();
        rootPane.setStyle("-fx-background-color: BLACK;");
        rootPane.setPadding(InsetsBuilder.create().left(60).right(60).top(60).bottom(60).build());
        final ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(33);
        final ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(33);
        final ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(33);
        rootPane.getColumnConstraints().addAll(column1, column2, column3);
        rootPane.setAlignment(Pos.CENTER);

        initRoot(rootPane);

        final Scene scene = new Scene(rootPane, 1024, 768);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    private final AnimationTimer TIMER = new AnimationTimer() {

        private int counter = 0;

        @Override
        public void handle(final long l) {
            final long currentNanoTime = System.nanoTime();
            if (currentNanoTime > lastDataCall + DATA_PERIOD) {
                if (counter > 10) {
                    // LCD
                    lcd4.setValue(RND.nextDouble() * 100);

                    counter = 0;
                }
                // LED
                for (int row = 0; row < AMOUNT; row++) {
                    for (int col = 0; col < AMOUNT; col++) {
                        rows.get(row).get(col).setOn(RND.nextInt(2) == 1);
                    }
                }
                // RADIAL
                radial1.setValue(RND.nextDouble() * 100);
                if (counter > 8) {
                    // BATTERY
                    if (charging) {
                        charge += 0.05;
                        if (charge >= 1.0) {
                            charging = false;
                        }
                    } else {
                        charge -= 0.05;
                        if (charge <= 0) {
                            charging = true;
                        }
                    }
                    BATTERY.setChargingLevel(charge);
                }

                // set time
                counter++;
                lastDataCall = System.nanoTime();
            }
        }
    };
    private Lcd lcd4;
    private Radial radial1;

    private void initRoot(final GridPane rootPane) {
        rootPane.setOnMouseReleased(new EventHandler<Event>() {
            public void handle(final Event arg0) {
                System.exit(0);
            }
        });
        initLcd(rootPane);
        initLed(rootPane);
        initClock(rootPane);
        initBattery(rootPane);
        initRadial(rootPane);
        initMatrixPanel(rootPane);
        TIMER.start();
    }

    private void initMatrixPanel(final GridPane rootPane) {
        final MatrixPanel matrixPanel =
                MatrixPanelBuilder
                        .create()

                        .ledWidth(96)
                        .ledHeight(34)

                        .prefWidth(300.0)
                        .prefHeight(300.0)

                        .frameDesign(Gauge.FrameDesign.DARK_GLOSSY)

                        .contents(
                                new Content[] {
                                        new ContentBuilder().create().color(MatrixColor.GREEN).type(Type.TEXT)
                                                .origin(0, 1).area(0, 0, 96, 34).txtContent("JavaFX")
                                                .font(MatrixFont.FF_15x32).fontGap(Gap.SIMPLE).align(Align.LEFT)
                                                .effect(Effect.SPRAY).lapse(50).postEffect(PostEffect.PAUSE)
                                                .pause(2000).order(RotationOrder.FIRST).clear(true).build(),

                                        new ContentBuilder().create().color(MatrixColor.RED).type(Type.TEXT)
                                                .origin(0, 0).area(0, 0, 96, 34).txtContent("rocks!")
                                                .font(MatrixFont.FF_15x32).fontGap(Gap.SIMPLE).align(Align.LEFT)
                                                .effect(Effect.SCROLL_LEFT).lapse(50).postEffect(PostEffect.PAUSE)
                                                .pause(4000).order(RotationOrder.SECOND).clear(true).build()

                                })

                        .build();
        rootPane.add(matrixPanel, 1, 1);

    }

    private void initRadial(final GridPane rootPane) {
        final StyleModel STYLE_MODEL_1 =
                StyleModelBuilder.create().frameDesign(Gauge.FrameDesign.STEEL)
                        .tickLabelOrientation(Gauge.TicklabelOrientation.HORIZONTAL)
                        .pointerType(Gauge.PointerType.TYPE14).thresholdVisible(true)
                        .lcdDesign(LcdDesign.STANDARD_GREEN).build();

        radial1 = new Radial(STYLE_MODEL_1);
        radial1.setThreshold(30);
        radial1.setPrefSize(200, 200);
        rootPane.add(radial1, 0, 1);
    }

    private void initBattery(final GridPane rootPane) {
        BATTERY = new Battery();
        BATTERY.setPrefSize(200, 200);
        charge = BATTERY.getChargingLevel();
        RotateTransitionBuilder.create().node(BATTERY).duration(Duration.seconds(35)).fromAngle(-90).toAngle(90)
                .cycleCount(Timeline.INDEFINITE).autoReverse(true).build().play();
        rootPane.add(BATTERY, 2, 1);
    }

    private void initClock(final GridPane rootPane) {
        final Clock clockCet =
                ClockBuilder.create().title("Berlin").timeZone("CET").autoDimEnabled(true).running(true).build();
        // clockCet.setCache(true);
        // clockCet.setCacheHint(CacheHint.SCALE);
        ScaleTransitionBuilder.create().node(clockCet).duration(Duration.seconds(5)).rate(1).fromX(1.0).fromY(1.0)
                .toX(0.5).toY(0.5).cycleCount(Timeline.INDEFINITE).autoReverse(true)
                .interpolator(Interpolator.EASE_BOTH).build().play();
        rootPane.add(clockCet, 2, 0);
    }

    private void initLed(final GridPane rootPane) {

        final GridPane pane = new GridPane();
        pane.setPadding(new Insets(5));
        // pane.setHgap(2);
        // pane.setVgap(2);
        pane.setAlignment(Pos.TOP_CENTER);

        // Create some controls
        for (int row = 0; row < AMOUNT; row++) {
            final ArrayList<Led> column = new ArrayList<Led>(AMOUNT);
            for (int col = 0; col < AMOUNT; col++) {
                final int green = RND.nextInt(255);
                final int blue = RND.nextInt(128) + 128;
                final Led led = LedBuilder.create().color(Color.rgb(0, green, blue)).build();
                column.add(led);
                pane.add(led, col, row);
            }
            rows.add(column);
        }

        rootPane.add(pane, 1, 0);
    }

    private void initLcd(final GridPane rootPane) {
        lcd4 = new Lcd();
        lcd4.setLcdDesign(LcdDesign.DARKGREEN);
        lcd4.setTitle("Values");
        lcd4.setUnit("unit");
        lcd4.setThreshold(50);
        lcd4.setValueAnimationEnabled(true);
        lcd4.setPrefSize(250, 70);
        rootPane.add(lcd4, 0, 0);
    }

}
