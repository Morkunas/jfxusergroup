package de.saxsys.examples;

import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 * MediaExample
 */
public class MediaExample extends Pane {
    private final MediaPlayer mediaPlayer;
    final double mediaWidth = 800;
    final double mediaHeight = 520;

    public MediaExample() {
        mediaPlayer =
                new MediaPlayer(new Media(ClassLoader.getSystemResource("JavaRap_ProRes_H264_768kbit_Widescreen.mp4")
                        .toString()));
        mediaPlayer.setAutoPlay(true);
        final MediaView playerPane = new MediaView(mediaPlayer);
        this.setMinSize(mediaWidth, mediaHeight);
        this.setPrefSize(mediaWidth, mediaHeight);
        this.setMaxSize(mediaWidth, mediaHeight);
        this.getChildren().add(playerPane);
    }
}
