package com.example.spaceinvadersfx;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class Board extends GridPane{
    private final int NUM_COLS = 50;
    private final int NUM_ROWS = 50;

    Ship ship = new Ship();
    ObservableList<Shoot> shootsTraveling = FXCollections.observableArrayList();

    public Board() {
        buildGrid();
        setBackgroundSpace();
        setShipInitialPos(this.ship);
        shootsTravelingThread();
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
                            }
                        }
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        System.out.println(e);
                    }
                }
            }
        }).start();
    }

    public void buildGrid() {
        setGridLinesVisible(true);
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
        setBackground(new Background(
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
        shootsTraveling.add(newShoot);
        getChildren().add(newShoot.image);
        setColumnIndex(newShoot.image, newShoot.positionX + 2);
        setRowIndex(newShoot.image, newShoot.positionY - 3);
    }
}
