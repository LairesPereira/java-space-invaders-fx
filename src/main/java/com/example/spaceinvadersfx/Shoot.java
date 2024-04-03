package com.example.spaceinvadersfx;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;

public class Shoot  {
    public ImageView image;
    int BULLET_WIDTH = 15;
    int BULLET_HEIGHT = 15;
    int positionX;
    int positionY;
    int power;
    int speed;

    public Shoot(int positionX, int positionY, int power, int speed) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.power = power;
        this.speed = speed;
        this.image = createShootImageView();
    }

    private ImageView createShootImageView() {
        Image shotImageSRC = new Image(new File("src/main/resources/shot.png").toURI().toString());
        ImageView shoot = new ImageView(shotImageSRC);
        shoot.setFitWidth(BULLET_WIDTH);
        shoot.setFitHeight(BULLET_HEIGHT);
        this.image = shoot;
        return shoot;
    }
}
