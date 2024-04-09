//https://freeinvaders.org/
//https://www.shutterstock.com/pt/video/clip-1055070602-retro-space-arcade-video-game-animation-co
package com.example.spaceinvadersfx;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Game extends Application {
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("Space Invaders");
        Board board = new Board();
        Text text = new Text("Teste");
        Scene scene = new Scene(board, 750, 750);

        EventHandler<KeyEvent> filter = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if((
                        keyEvent.getCode().toString().equals("LEFT") && keyEvent.getEventType().toString().equals("KEY_PRESSED")) ||
                        keyEvent.getCode().toString().equals("RIGHT") && keyEvent.getEventType().toString().equals("KEY_PRESSED"))
                {
                    board.moveShip(keyEvent.getCode());
                }
                else if (keyEvent.getCode().toString().equals("SPACE") && keyEvent.getEventType().toString().equals("KEY_PRESSED")) {
                    board.createShoot(keyEvent.getCode());
                }
            }
        };

        scene.addEventFilter(KeyEvent.ANY, filter);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}