package com.example.spaceinvadersfx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Ship {
    // # SHIP CONSTANTS
    // ship width and height on grid are 5 cols/rows
    ImageView shipImage;
    int playerLife = 5;
    boolean isDead = false;
    int INITIAL_POSITION_X = 22;
    int INITIAL_POSITION_Y = 45;
    int SHIP_WIDTH = 75;
    int SHIP_HEIGTH = 75;
    int SHIP_HEIGTH_PIXELS = 3;
    int SHIP_WIDTH_PIXELS = 5;
    int positionX = INITIAL_POSITION_X;
    int positionY = INITIAL_POSITION_Y;

    public Ship() {
        this.shipImage = buildShip();
    }

    public Shoot newShoot() {
        if(!isDead) {
            Shoot shoot = new Shoot(this.positionX, this.positionY, 1, 1, "player");
            return shoot;
        }
        return null;
    }

    public ImageView buildShip() {
        Image shipImageSRC = new Image(new File("src/main/resources/ship2.png").toURI().toString());
        this.shipImage = new ImageView(shipImageSRC);
        this.shipImage.setFitWidth(SHIP_WIDTH);
        this.shipImage.setFitHeight(SHIP_HEIGTH);
        this.shipImage.setLayoutX(positionX);
        this.shipImage.setLayoutY(positionY);
        this.shipImage.setCache(true);
        return shipImage;
    }

    public void shipTakeDamage(int damagePower) {
        this.playerLife -= damagePower;
        if (this.playerLife <= 0) {
            this.isDead = true;
        }
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public ImageView explosion() {
        ImageView shipExplosion = new ImageView(new Image(new File("src/main/resources/explosion.png").toURI().toString()));
        shipExplosion.setFitWidth(SHIP_WIDTH);
        shipExplosion.setFitHeight(SHIP_HEIGTH);
        shipExplosion.setLayoutX(positionX);
        shipExplosion.setLayoutY(positionY);
        shipExplosion.setCache(true);
       return shipExplosion;
    }
}
