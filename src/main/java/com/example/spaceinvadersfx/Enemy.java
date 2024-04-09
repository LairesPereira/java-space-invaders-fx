package com.example.spaceinvadersfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Enemy {
    // # SHIP CONSTANTS
    // ship width and height on grid are 5x3 cols/rows
    ImageView shipImage;
    ObservableList<Shoot> shootsFired = FXCollections.observableArrayList();
    Timer timer = new Timer();
    public int life = 5;
    int INITIAL_POSITION_X = 22;
    int INITIAL_POSITION_Y = 5;
    int ENEMY_WIDTH = 70;
    int ENEMY_HEIGTH = 70;
    public final int ENEMY_HEIGTH_PIXELS = 3;
    public int ENEMY_WIDTH_PIXELS = 5;
    int positionX = INITIAL_POSITION_X;
    int positionY = INITIAL_POSITION_Y;
    public boolean isDead = false;

    public Enemy() {
        this.shipImage = buildShip();
    }

    public void attack(ObservableList<Shoot> shoots) {
        timer.schedule( new TimerTask() {
            public void run() {
                if(isDead) {
                    timer.cancel();
                    timer.purge();
                    return;
                }
                shoots.add(newShoot());
            }
        }, 0,  new Random().nextInt(1000, 2500));
    }

    public Shoot newShoot() {
        return new Shoot(this.positionX, this.positionY + 3, 1, 1, "enemy");
    }

    public ImageView buildShip() {
        Image shipImageSRC = new Image(new File("src/main/resources/ufo.png").toURI().toString());
        this.shipImage = new ImageView(shipImageSRC);
        this.shipImage.setFitWidth(ENEMY_WIDTH);
        this.shipImage.setFitHeight(ENEMY_HEIGTH);
        this.shipImage.setLayoutX(this.positionX);
        this.shipImage.setLayoutY(this.positionY);
        this.shipImage.setCache(true);
        return shipImage;
    }

    public void takeDamage(int damagePower) {
        this.life -= damagePower;
        if(this.life <= 0) {
            this.isDead = true;
        }
    }

    public void purgeTimer() {
        this.timer.cancel();
        this.timer.purge();
    }
}
