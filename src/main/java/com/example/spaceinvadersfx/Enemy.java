package com.example.spaceinvadersfx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;

public class Enemy {
    // # SHIP CONSTANTS
    // ship width and height on grid are 5x3 cols/rows
    ImageView shipImage;
    int INITIAL_POSITION_X = 22;
    int INITIAL_POSITION_Y = 5;
    int ENEMY_WIDTH = 70;
    int ENEMY_HEIGTH = 70;
    public final int ENEMY_HEIGTH_PIXELS = 3;
    public int ENEMY_WIDTH_PIXELS = 5;
    int positionX = INITIAL_POSITION_X;
    int positionY = INITIAL_POSITION_Y;


    public Enemy() {
        this.shipImage = buildShip();
    }

    public Shoot newShoot() {
        Shoot shoot = new Shoot(this.positionX, this.positionY, 10, 1);
        return shoot;
    }

    public ImageView buildShip() {
        Image shipImageSRC = new Image(new File("src/main/resources/ufo.png").toURI().toString());
        this.shipImage = new ImageView(shipImageSRC);
        this.shipImage.setFitWidth(ENEMY_WIDTH);
        this.shipImage.setFitHeight(ENEMY_HEIGTH);
        this.shipImage.setLayoutX(INITIAL_POSITION_X);
        this.shipImage.setLayoutY(INITIAL_POSITION_Y);
        this.shipImage.setCache(true);
        return shipImage;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }
}
