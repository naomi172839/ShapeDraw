/*
 * Copyright (c) 2020
 * Project: DrawShape
 * Filename: Main.java
 * Class Name: Draw.Main
 * Last Modified: 6/3/20, 3:27 PM
 * Author: nbonnin (Naomi Bonnin)
 * Project Description: This program presents the user with a series of shapes
 * from which the user can choose.
 * The user will then be prompted to enter the dimensions of their chosen shape.
 * Finally, the program will attempt to display a spinning version of the
 * chosen shape with the provided dimensions.
 *
 */

package Draw;

/*
 * Imports needed for the use of JavaFX via FXML
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * The main class represents the entry point to the program.
 * It contains two methods, the main method and an overridden start method.
 * The main method extends Application from JavaFX as this is the recommended way to launch a JavaFX program.
 */
public class Main extends Application {

    /*
     * The main method of the entire program.
     * Accepts CLI arguments that are compatible with any JavaFX application (None by default).
     * Calls the launch method from javafx.application.application
     */
    public static void main(String[] args) {
        launch(args);   //From javafx.application.application
    }

    /*
     * The start method overrides the start method from javafx.application.Application.
     * The start method accepts 1 argument, a Stage.
     * Note that this stage is automatically created when the launch method from
     * javafx.application.Application is called.
     * Loads the draw.fxml file and displays the window to the user
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("draw.fxml"));
        Parent root = loader.load();
        //Creates a parent object from the draw.fxml file using the static FXMLLoader.load method
        //Parent root = FXMLLoader.load(getClass().getResource("draw.fxml"));
        primaryStage.setTitle("Draw Shape");    //Sets the title of the initial window
        primaryStage.setScene(new Scene(root, 300, 275));   //Creates a new scene that is 300p x 275p
        primaryStage.show();    //Shows the window the user
    }

}