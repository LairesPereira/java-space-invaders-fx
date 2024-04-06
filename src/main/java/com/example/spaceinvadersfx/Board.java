package com.example.spaceinvadersfx;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;

import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.time.temporal.TemporalUnit;
import java.util.Iterator;

public class Board extends GridPane{
    public final boolean SHOW_GRID = true;
    private int MAX_ENEMIES = 5;
    private final int NUM_COLS = 50;
    private final int NUM_ROWS = 50;

    Ship ship = new Ship();
    Enemy enemy = new Enemy();
    ObservableList<Enemy> enemies = FXCollections.observableArrayList();
    ObservableList<Shoot> shootsTraveling = FXCollections.observableArrayList();

    public Board() {
        buildGrid();
        setBackgroundSpace();
        setShipInitialPos(this.ship);
        setEnemies(this.enemy);
        shootsTravelingThread();
    }

    private void setEnemies(Enemy enemy) {
        enemies.add(enemy);
        getChildren().add(enemy.shipImage);
        setColumnIndex(enemy.shipImage, enemy.INITIAL_POSITION_X);
        setRowIndex(enemy.shipImage, enemy.INITIAL_POSITION_Y);
    }

    private void destroyImageView(ImageView shoot) {
        // Run the specified Runnable on the JavaFX Application Thread at some unspecified time in the future.
        Platform.runLater(
                new Runnable() {
                    @Override
                    public void run() {
                        getChildren().remove(shoot);
                    }
                }
        );
    }

    private void shootsTravelingThread() {
        // ACESSO EM BANCOS DE DADOS PODEM SER EM THREADS SEPARADAS?
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (!shootsTraveling.isEmpty()) {
                            for (Iterator<Shoot> shoot = shootsTraveling.iterator(); shoot.hasNext();) {
                            Shoot shootTravel = shoot.next();
                            setRowIndex(shootTravel.image, shootTravel.positionY - 3);
                            shootTravel.positionY -= shootTravel.speed;
                            if(shootTravel.positionY <= 3) {
                                destroyImageView(shootTravel.image);
                                shoot.remove();
                            } else if (didHitEnemy(shootTravel)) {
                                destroyImageView(shootTravel.image);
                            }
                            }
                    }
                    try {
                        Thread.sleep(70);
                    } catch (InterruptedException e) {
                        System.out.println(e);
                    }
                }
            }
        }).start();
    }

    private boolean didHitEnemy(Shoot shoot) {
        for (Iterator<Enemy> enemy = enemies.iterator(); enemy.hasNext();) {
            Enemy enemyCheck = enemy.next();
            if(enemyCheck != null) {
                if (shoot.positionY >= getRowIndex(enemyCheck.shipImage) && shoot.positionY <= (getRowIndex(enemyCheck.shipImage) + enemyCheck.ENEMY_HEIGTH_PIXELS)){
                    if((shoot.positionX >= getColumnIndex(enemyCheck.shipImage) - 2) && (shoot.positionX <= (getColumnIndex(enemyCheck.shipImage) + 2))){
                        enemy.remove();
                        destroyImageView(enemyCheck.shipImage);
                        System.out.printf("\nShot Pos X: %d", shoot.positionX);
                        System.out.printf("\nShot Pos Y: %d", shoot.positionY);
                        System.out.printf("\nShip Y Pos: %d", getRowIndex(enemyCheck.shipImage));
                        System.out.printf("\nShip X Pos: %d", getColumnIndex(enemyCheck.shipImage));
                        System.out.println("\nhit");
                        return true;
                }
            }
        }
    }
    return false;
    }

    public void buildGrid() {
        setGridLinesVisible(SHOW_GRID);
        for (int i = 0; i < NUM_COLS; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / NUM_COLS);
            getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < NUM_ROWS; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / NUM_ROWS);
            getRowConstraints().add(rowConst);
        }
    }


    public void setBackgroundSpace() {
        setBackground(
                new Background(
                        new BackgroundImage(
                                new Image(new File("src/main/resources/space.png").toURI().toString()),
                                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                                new BackgroundPosition(Side.LEFT, 0, true, Side.BOTTOM, 0, true),
                                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true)
                        ),
                        new BackgroundImage(
                                new Image(new File("src/main/resources/stars2.png").toURI().toString()),
                                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                                new BackgroundPosition(Side.LEFT, 0, true, Side.BOTTOM, 0, true),
                                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true)
                        )
                )
        );
    }

    public void setShipInitialPos(Ship ship) {
        getChildren().add(ship.shipImage);
        setColumnIndex(ship.shipImage, ship.INITIAL_POSITION_X);
        setRowIndex(ship.shipImage, ship.INITIAL_POSITION_Y);
    }

    public void moveShip(KeyCode direction) {
        if(direction.toString().equals("RIGHT") && (this.ship.positionX + 1) < (NUM_COLS - 7)) {
            this.ship.positionX += 1;
            setColumnIndex(ship.shipImage, ship.positionX);
        } else if(direction.toString().equals("LEFT") && (this.ship.positionX - 1) > (NUM_COLS - 47)) {
            this.ship.positionX -= 1;
            setColumnIndex(ship.shipImage, ship.positionX);
        }
    }

    public void createShoot(KeyCode code) {
        Shoot newShoot = this.ship.newShoot();
        shootsTraveling .add(newShoot);
        getChildren().add(newShoot.image);
        setColumnIndex(newShoot.image, newShoot.positionX + 2);
        setRowIndex(newShoot.image, newShoot.positionY - 3);
    }
}
