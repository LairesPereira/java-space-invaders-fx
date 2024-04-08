package com.example.spaceinvadersfx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Ship {
    // # SHIP CONSTANTS
    // ship width and height on grid are 5 cols/rows
    ImageView shipImage;
    int INITIAL_POSITION_X = 22;
    int INITIAL_POSITION_Y = 45;
    int SHIP_WIDTH = 75;
    int SHIP_HEIGTH = 75;
    int positionX = INITIAL_POSITION_X;
    int positionY = INITIAL_POSITION_Y;

    public Ship() {
        this.shipImage = buildShip();
    }

    public Shoot newShoot() {
        Shoot shoot = new Shoot(this.positionX, this.positionY, 1, 1, "player");
        return shoot;
    }

    public ImageView buildShip() {
        Image shipImageSRC = new Image(new File("src/main/resources/ship2.png").toURI().toString());
        this.shipImage = new ImageView(shipImageSRC);
        this.shipImage.setFitWidth(SHIP_WIDTH);
        this.shipImage.setFitHeight(SHIP_HEIGTH);
        this.shipImage.setLayoutX(INITIAL_POSITION_X);
        this.shipImage.setLayoutY(INITIAL_POSITION_Y);
        this.shipImage.setCache(true);
        return shipImage;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
}
