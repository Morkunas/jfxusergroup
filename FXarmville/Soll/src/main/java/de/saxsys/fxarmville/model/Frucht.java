package de.saxsys.fxarmville.model;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import javafx.util.Duration;
import de.saxsys.fxarmville.model.util.FruchtBildLader;
import de.saxsys.fxarmville.presentation.special.BugTracker;

public class Frucht {

    // Zeit für Reifung / Faulen
    private final DoubleProperty lebenszeit = new SimpleDoubleProperty();

    // Aktueller Reifegrad
    private final DoubleProperty aktuelleLebenszeit = new SimpleDoubleProperty();

    /*
     * Lebensabschnitte
     */
    private final BooleanProperty istReif = new SimpleBooleanProperty();
    private final BooleanProperty istFaulig = new SimpleBooleanProperty();
    private final BooleanProperty istEingegangen = new SimpleBooleanProperty();
    private final BooleanProperty istGeerntetWorden = new SimpleBooleanProperty();

    private final LebensZyklus lebensZyklus = new LebensZyklus();
    private final String bildName;

    public Frucht(final String bildName, final double lebenszeit) {
        this.bildName = bildName;
        this.lebenszeit.set(lebenszeit);
    }

    public Image getBild() {
        return FruchtBildLader.getInstance().getBild(bildName);
    }

    public void baueAn() {
        // **** BEGIN LIVE CODING ****
        final DoubleBinding mitteDesLebens = lebenszeit.divide(2);
        istReif.bind(aktuelleLebenszeit.greaterThan(mitteDesLebens.subtract(getReifedauer())).and(
                aktuelleLebenszeit.lessThan(mitteDesLebens.add(getReifedauer()))));

        // SPÄTER - nach Lebenszyklus
        istEingegangen.bind(aktuelleLebenszeit.greaterThanOrEqualTo(lebenszeit).and(istGeerntetWorden.not()));
        istFaulig.bind(aktuelleLebenszeit.greaterThan(mitteDesLebens).and(istReif.not()));
        // SPÄTER
        lebensZyklus.wachse();
        // **** END LIVE CODING ****
    }

    public void ernten() {
        lebensZyklus.ernten();
        istGeerntetWorden.set(true);
    }

    /*
     * WACHSDAUER
     */
    public ReadOnlyDoubleProperty lebenszeitProperty() {
        return lebenszeit;
    }

    public double getLebenszeit() {
        return lebenszeit.get();
    }

    /*
     * REIFEGRAD
     */
    public double getAktuelleLebenszeit() {
        return aktuelleLebenszeit.get();
    }

    public DoubleProperty aktuelleLebenszeitProperty() {
        return aktuelleLebenszeit;
    }

    /*
     * AKTUELLER ZUSTAND
     */

    public ReadOnlyBooleanProperty istReifProperty() {
        return istReif;
    }

    public ReadOnlyBooleanProperty istFauligProperty() {
        return istFaulig;
    }

    public ReadOnlyBooleanProperty istEingegangenProperty() {
        return istEingegangen;
    }

    public ReadOnlyBooleanProperty istGeerntetWordenProperty() {
        return istGeerntetWorden;
    }

    // **** BEGIN LIVE CODING ****
    private double getReifedauer() {
        return getLebenszeit() / (16d * (BugTracker.getInstance().anzahlDerBugs() + 1));
    }

    // **** END LIVE CODING ****

    /**
     * Private Klasse welche den Lebenszyklus einer Frucht abbildet.
     * 
     * @author sialcasa
     * 
     */
    private class LebensZyklus {

        private Timeline lebensZyklus;

        public void wachse() {
            // **** BEGIN LIVE CODING ****

            lebensZyklus =
                    TimelineBuilder
                            .create()
                            .delay(Duration.seconds(new Random().nextInt(10)))
                            .keyFrames(
                                    new KeyFrame(Duration.seconds(getLebenszeit()), new KeyValue(aktuelleLebenszeit,
                                            getLebenszeit()))).build();

            lebensZyklus.play();

            // **** END LIVE CODING ****
        }

        public void ernten() {
            lebensZyklus.stop();
        }
    }
}
