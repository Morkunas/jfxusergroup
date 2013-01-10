package de.saxsys.fxarmville.presentation;

import javafx.animation.FadeTransition;
import javafx.animation.FadeTransitionBuilder;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.image.ImageViewBuilder;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import de.saxsys.fxarmville.model.Frucht;

// TODO: geerntet + eingegangen evtl. ins Frucht Modell? 
public class FXrucht extends Parent {

    private final ImageView imageView = ImageViewBuilder.create().fitHeight(50).fitWidth(50).build();

    private final Frucht frucht;

    public FXrucht(final Frucht frucht) {

        this.frucht = frucht;

        // Init
        imageView.setImage(frucht.getBild());
        getChildren().add(imageView);

        // Erntelistener
        erzeugeMouseListenerZumErnten();

        // Starten
        starteWachstum();
    }

    // **** BEGIN LIVE CODING ****
    private void erzeugeMouseListenerZumErnten() {
        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(final MouseEvent arg0) {
                frucht.ernten();
            }
        });
    }

    // END

    private void starteWachstum() {

        // FIXME

        final DoubleBinding standDerReifung = frucht.reifegradProperty().divide(frucht.wachsdauerProperty());
        scaleXProperty().bind(standDerReifung);
        scaleYProperty().bind(standDerReifung);

        // Wenn Frucht reif ist, bekommt sie glow
        frucht.istReifProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> arg0, final Boolean alterWert,
                    final Boolean neuerWert) {
                if (neuerWert) {
                    setEffect(new Glow(10));
                } else {
                    setEffect(null);
                }
            }
        });

        // Wenn sie faulig wird
        frucht.istFauligProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> arg0, final Boolean arg1, final Boolean arg2) {
                final FadeTransition eingehen =
                        FadeTransitionBuilder.create().duration(Duration.seconds(frucht.getWachsdauer()))
                                .node(FXrucht.this).toValue(0.0).build();
                eingehen.play();
            }
        });
    }

}
