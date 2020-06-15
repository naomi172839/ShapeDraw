/*
 * Copyright (c) 2020
 * Project: DrawShape
 * Filename: Controller.java
 * Class Name: Draw.Controller
 * Last Modified: 6/3/20, 3:48 PM
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
 * Imports needed to work with FXML.
 * Import needed for handling expected exceptions
 */

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/*
 * The controller class represents the controller for the draw.fxml window.
 * Contains an initialize method which is called during the creation of the controller by JavaFX.
 * Note that @FXML methods are called via injection/reflection and not traditional ways.
 * A controller responds to action events asynchronously in JavaFX.
 * Note that rather than having a scene for each option, one scene is used and modified.
 * This is a more memory efficient approach.
 * Note that the @FXML annotation is not strictly necessary for the event handlers but in my opinion
 * it is really helpful to quickly identify which methods will be used by the JavaFX portion of the
 * application and those which are only used internally.
 * Note that an invisible text field is used to pass information between controllers.
 * This approach results in less code and is much easier to maintain in my opinion.
 */
public class Controller {
    /*
     * Class variables since there will only be 1 instance of this class
     * Allows each method to use/modify these resources rather than creating their own copy.
     * This approach is more memory efficient.
     */
    private static Stage popUp;    //The stage to be shown to the user when a choice is made
    private static ParametersController controller;    //The controller for the popup screen

    /*
     * The initialize method is called by JavaFX during controller creation.
     * This is preferred to the use of a constructor in JavaFX.
     * Loads the parameters.fxml file and creates the popup window
     * Throws an IOException, handled by JavaFX, if the file can not be found
     */
    @FXML
    public void initialize() throws IOException {
        popUp = new Stage();    //Creates the window to be shown to the user
        //Creating a FXMLLoader object enables modification of FXML objects programmatically.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("parameters.fxml"));
        Parent sliderScene = loader.load(); //Loads parameters.fxml and creates a layout based on it
        controller = loader.getController();    //Gets the created controller so FXML objects can be changed
        //Sets the scene to the layout stored in sliderScene.
        popUp.setScene(new Scene(sliderScene, 300, 300));
        //popUp.setResizable(false);
        //Resets the fields when the user closes the window
        popUp.setOnCloseRequest(windowEvent -> {
            controller.length.setText("");
            controller.width.setText("");
            controller.radius.setText("");
            controller.height.setText("");
            controller.minorRadius.setText("");
        });
    }

    /*
     * Event Handler for the circle button.
     * Sets only the radius option to be modifiable.
     * Shows the popUp window to the user.
     */
    @FXML
    public void circle() {
        disableAll();
        controller.radius.setDisable(false);    //Re enables the radius textField
        controller.choice.setText("circle");    //Sets the users choice in an invisible text field
        popUp.show();   //Shows the window
    }

    /*
     * Event Handler for the square button.
     * Sets only the length field to be modifiable.
     * Shows the popup window to the user.
     */
    @FXML
    public void square() {
        disableAll();
        controller.length.setDisable(false);    //Sets length field to be modifiable
        controller.choice.setText("square");    //Sets the users choice in an invisible text field
        popUp.show();   //Show the popup window to the user
    }

    /*
     * Event Handler for triangle button.
     * Sets only the length to be modifiable.
     * Shows the popup window to the user
     */
    @FXML
    public void triangle() {
        disableAll();
        controller.length.setDisable(false);    //Sets the length field to be modifiable
        controller.choice.setText("triangle");  //Sets the users choice in an invisible text field
        popUp.show(); //Shows popup to user
    }

    /*
     * Event Handler for the rectangle button
     * Sets both length and width to be modifiable
     * Shows the popup window to the user
     */
    @FXML
    public void rectangle() {
        disableAll();
        controller.length.setDisable(false);    //Sets length to be modifiable
        controller.width.setDisable(false);     //Sets width to be modifiable
        controller.choice.setText("rectangle"); //Stores the users choice, used by ParametersController
        popUp.show();   //Shows the new window
    }

    /*
     * Event Handler for the sphere button.
     * Sets only radius to be modifiable
     * Shows the window to the user
     */
    @FXML
    public void sphere() {
        disableAll();
        controller.radius.setDisable(false);    //Sets radius to be modifiable
        controller.choice.setText("sphere");    //Stores the users choice
        popUp.show();   //Shows the window
    }

    /*
     * Event Handler for the cube button
     * Sets only length to be modifiable
     * Shows the popup to the user
     */
    @FXML
    public void cube() {
        disableAll();
        controller.length.setDisable(false);    //Sets length to be modifiable
        controller.choice.setText("cube");      //Sets invisible field to cube
        popUp.show();   //Shows the window to the user
    }

    /*
     * Event Handler for the cone button
     * Sets both radius and length to be modifiable
     * Shows the window to the user
     */
    @FXML
    public void cone() {
        disableAll();
        controller.radius.setDisable(false);    //Sets radius to be modifiable
        controller.height.setDisable(false);    //Sets height to be modifiable
        controller.choice.setText("cone");      //Stores the users choice
        popUp.show();   //Shows the window
    }

    /*
     * Event Handler for the cylinder button
     * Sets both radius and length to be modifiable
     * Shows the window to the user
     */
    @FXML
    public void cylinder() {
        disableAll();
        controller.radius.setDisable(false);    //Sets radius to be modifiable
        controller.height.setDisable(false);    //Sets height to be modifiable
        controller.choice.setText("cylinder");  //Stores the users choice
        popUp.show();   //Shows the window to the user
    }

    /*
     * Event handler for the torus button
     * Sets radius and minorRadius to be modifiable
     * Sets the popup to be visible
     */
    @FXML
    public void torus() {
        disableAll();
        controller.radius.setDisable(false);        //Sets radius to be modifiable
        controller.minorRadius.setDisable(false);   //Sets the minor radius to be modifiable
        controller.choice.setText("torus");         //Stores the users choice
        popUp.show();   //Shows the window
    }

    /*
     * Disables all fields
     * This method exists because it prevents duplication of code as it is
     * more efficient to enable 1 or 2 fields when compared to disabling 3 or 4.
     */
    private void disableAll() {
        controller.length.setDisable(true);     //Disables length
        controller.width.setDisable(true);      //Disables width
        controller.radius.setDisable(true);     //Disables radius
        controller.height.setDisable(true);     //Disables height
        controller.minorRadius.setDisable(true);//Disables minor radius
    }
}
