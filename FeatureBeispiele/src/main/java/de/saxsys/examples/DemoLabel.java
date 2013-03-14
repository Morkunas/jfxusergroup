package de.saxsys.examples;

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
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class DemoLabel extends Group {
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
                        final Pane pane = (Pane) arg2;
                        pane.getChildren().remove(label);
                    }
                });
                fadeOut.play();
            }
        });
    }
}
