package com.example.spaceinvadersfx;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import java.io.File;
import java.util.Iterator;

public class Board extends GridPane {
    public final boolean SHOW_GRID = true;
    private int MAX_ENEMIES = 5;
    private final int NUM_COLS = 50;
    private final int NUM_ROWS = 50;

    Ship ship = new Ship();
    ObservableList<Enemy> enemies = FXCollections.observableArrayList();
    ObservableList<Shoot> shootsTraveling = FXCollections.observableArrayList();
    public ObservableList<Shoot> enemiesShoots = FXCollections.observableArrayList();

    public Board() {
        buildGrid();
        setBackgroundSpace();
        setShipInitialPos(this.ship);
        setEnemies();
        addListener();
        shootsTravelingThread();
     }

    private void setEnemies() {
        int aux = 0;
        int lastInitialPosition = 3;
        while (aux < MAX_ENEMIES) {
            Enemy newEnemy = new Enemy();
            newEnemy.positionX = lastInitialPosition;
            enemies.add(newEnemy);
            newEnemy.attack(enemiesShoots);
            getChildren().add(newEnemy.shipImage);
            setColumnIndex(newEnemy.shipImage, newEnemy.positionX);
            setRowIndex(newEnemy.shipImage, newEnemy.INITIAL_POSITION_Y);
            lastInitialPosition += 10;
            aux++;
        }
    }

    public void addListener() {
        enemiesShoots.addListener(new ListChangeListener<com.example.spaceinvadersfx.Shoot>() {
            @Override
            public void onChanged(Change<? extends com.example.spaceinvadersfx.Shoot> change) {
                while (change.next()){
                    if(change.wasAdded()) {
                        for (Shoot n: change.getAddedSubList()) {
                            System.out.println(n);
                            createImageShootEnemy(n);
                        }
                    }
                }
            }
        });
    }

    private void createImageShootEnemy(Shoot shoot) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getChildren().add(shoot.image);
                setColumnIndex(shoot.image, shoot.positionX + 2);
                setRowIndex(shoot.image, shoot.positionY - 3);
            }
        });
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
                            } if (didHitEnemy(shootTravel)) {
                                destroyImageView(shootTravel.image);
                                shoot.remove();
                            }
                            }
                    }

                    if (!enemiesShoots.isEmpty()) {
                        for (Iterator<Shoot> enemyShoot = enemiesShoots.iterator(); enemyShoot.hasNext();) {
                            Shoot enemyShootTravel = enemyShoot.next();
                            setColumnIndex(enemyShootTravel.image, enemyShootTravel.positionX + 2);
                            setRowIndex(enemyShootTravel.image, enemyShootTravel.positionY + 3);
                            enemyShootTravel.positionY += enemyShootTravel.speed;
                            if (enemyShootTravel.positionY > 47) {
                                destroyImageView(enemyShootTravel.image);
                                enemyShoot.remove();
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
                        if(enemyCheck.isDead) {
                            enemy.remove();
                            destroyImageView(enemyCheck.shipImage);
                            return true;
                        } else {
                            enemyCheck.takeDamage(shoot.power);
                            destroyImageView(shoot.image);
                            return true;
                        }
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
