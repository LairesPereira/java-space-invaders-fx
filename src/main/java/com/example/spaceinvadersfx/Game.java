//https://freeinvaders.org/
//https://www.shutterstock.com/pt/video/clip-1055070602-retro-space-arcade-video-game-animation-co
package com.example.spaceinvadersfx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.stage.Stage;

import java.io.File;

public class Game extends Application {
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("Space Invaders");
        VBox root = new VBox();
        Pane pane = new Pane();
        root.setBackground(new Background(
                new BackgroundFill(
                        new LinearGradient(0, 0, 0, 1, true,
                                CycleMethod.NO_CYCLE,
                                new Stop(0, Color.web("#454545")),
                                new Stop(1, Color.web("#454590"))
                        ), CornerRadii.EMPTY, Insets.EMPTY
                ),
                new BackgroundFill(
                        new ImagePattern(
                                new Image(new File("src/main/resources/stars.png").toURI().toString()),
                                0, 0, 128, 128, false
                        ), CornerRadii.EMPTY, Insets.EMPTY
                ),
                new BackgroundFill(
                        new RadialGradient(
                                0, 0, 0.5, 0.5, 0.5, true,
                                CycleMethod.NO_CYCLE,
                                new Stop(0, Color.web("#FFFFFF33")),
                                new Stop(1, Color.web("#00000033"))),
                        CornerRadii.EMPTY, Insets.EMPTY
                )
        ));

        // load ship image
        Image ship = new Image(new File("src/main/resources/ship.png").toURI().toString());
        ImageView imageView = new ImageView(ship);

        imageView.setFitWidth(70);
        imageView.setFitHeight(70);
        imageView.setLayoutY(750);
        imageView.setSmooth(true);
        imageView.setCache(true);

        pane.getChildren().add(imageView);
        root.getChildren().add(pane);
        Scene scene = new Scene(root, 900, 900);
        stage.setScene(scene);

        stage.show();
    }
}