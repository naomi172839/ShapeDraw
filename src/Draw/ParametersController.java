/*
 * Copyright (c) 2020
 * Project: DrawShape
 * Filename: ParametersController.java
 * Class Name: Draw.ParametersController
 * Last Modified: 6/4/20, 3:50 PM
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
 * Imports needed for JavaFX and animations as well as creating shapes.
 */

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

/*
 * The ParametersController class represents the controller for the parameters.fxml file.
 * Its primary purpose it to serve as an event handler for the parameters.fxml window.
 * Contains a method to draw each shape as well as a close method to reset the fields when the window closes.
 */
public class ParametersController {

    /*
     * Instance variables.
     * Due to the nature of JavaFX, specifically the use of injection and reflection,
     * all FXML variables can not be private.  The next strongest level of access protection
     * is used instead.  All other variables are private
     */
    @FXML
    protected TextField length;
    @FXML
    protected TextField width;
    @FXML
    protected TextField radius;
    @FXML
    protected TextField height;
    @FXML
    protected TextField minorRadius;
    @FXML
    protected TextField choice;
    @FXML
    protected GridPane root;
    private double len, wit, rad, hgt, minRad, majRad;  //Stores the manipulated values
    private Group group = null; //Initialize variable to be safe

    /*
     * Method to get the users input.
     * Checks for empty strings and sets the value to 0.
     * Normalizes data to prevent clipping
     */
    private void getFields() {
        if (length.getText().matches("^[0-9]+([,.][0-9]+)?$")) {
            len = Double.parseDouble(length.getText()); //Get the length, convert to double
        } else {
            len = 0;
        }
        if (width.getText().matches("^[0-9]+([,.][0-9]+)?$")) {
            wit = Double.parseDouble(width.getText());  //Get the width, convert to double
        } else {
            wit = 0;
        }
        if (radius.getText().matches("^[0-9]+([,.][0-9]+)?$")) {
            rad = Double.parseDouble(radius.getText()); //Get the radius, convert to double
        } else {
            rad = 0;
        }
        if (height.getText().matches("^[0-9]+([,.][0-9]+)?$")) {
            hgt = Double.parseDouble(height.getText()); //Get the height, convert to double
        } else {
            hgt = 0;
        }
        if (minorRadius.getText().matches("^[0-9]+([,.][0-9]+)?$")) {
            minRad = Double.parseDouble(minorRadius.getText()); //Get the minor radius
        } else {
            minRad = 0;
        }
        majRad = rad;   //Separate value to manipulate for the torus
        //Scales the values, does NOT preserve ratios
        len = 125 + (125 * (((len / 125.0)) - (int) (len / 125)));
        wit = 125 + (125 * (((wit / 125.0)) - (int) (wit / 125)));
        hgt = 125 + (125 * (((hgt / 125.0)) - (int) (hgt / 125)));
        rad = 75 + (75 * (((rad / 75.0)) - (int) (rad / 75.0)));
        majRad = 37.5 + (37.5 * (((majRad / 37.5)) - (int) (majRad / 37.5)));
        minRad = 37.5 + (37.5 * (((minRad / 37.5)) - (int) (minRad / 37.5)));
    }

    /*
     * Anaimates the provided shape around the provided axis
     * Takes any Node or subclass as an argument, allows rotation of both 2d and 3d shapes
     * The axis can either be rotation vector or using Rotate.CONSTANT
     */
    private void animateShape(Node shape, Point3D axis) {
        RotateTransition rt = new RotateTransition(Duration.seconds(5), shape); //Creates the transition, lasts 5 sec
        rt.setCycleCount(Animation.INDEFINITE); //Keep going until the window closed
        rt.setFromAngle(0); //Go from the initial angle
        rt.setToAngle(360); //Go 360 degrees around
        rt.setAutoReverse(true);    //When finished, repeat in reverse
        rt.setAxis(axis);   //Rotate around the provided axis
        rt.play();  //Play the animation
    }

    /*
     * Draws a circle on the screen.
     * Uses built in circle method
     */
    private void drawCircle() {
        Circle c = new Circle();
        c.setRadius(rad);   //Sets the size of the circle
        c.setCenterX(150);  //Center X
        c.setCenterY(150);  //Center Y
        group = new Group(c);   //Add the shape to the group that is ultimately added
        animateShape(c, Rotate.Y_AXIS); //Rotate around the Y axis
    }

    /*
     * Draws a square on the screen
     * Uses the built-in rectangle method
     */
    private void drawSquare() {
        Rectangle r = new Rectangle();
        r.setWidth(len);    //Set width to be the same as height
        r.setHeight(len);   //Sets height to be the same as width
        r.setX(150 - (len / 2.0));  //Sets the X to be 1/2 the length
        r.setY(150 - (len / 2.0));  //Sets the Y to be 1/2 the length
        group = new Group(r);   //Adds the shape to the group
        animateShape(r, Rotate.Y_AXIS); //Rotate around the Y axis
    }

    /*
     * Draws a triangle on the screen
     * Uses the built in polygon method to define the vertices
     */
    private void drawTriangle() {
        Polygon t = new Polygon(    //Creates the polygon
                (150 - len / 2.0),  //Vertex 1
                (150 + len / 2.0),  //Vertex 1
                (150 + len / 2.0),  //Vertex 2
                (150 + len / 2.0),  //Vertex 2
                (150 + len / 2.0),  //Vertex 3
                (150 - len / 2.0)   //Vertex 3
        );
        group = new Group(t);   //Add the triangle to the group
        animateShape(t, Rotate.Y_AXIS); //Rotates the triangle
    }

    /*
     * Draws a rectangle on the screen
     * Uses the Built-In rectangle method
     */
    private void drawRectangle() {
        Rectangle re = new Rectangle(); //Create a rectangle
        re.setWidth(wit);   //Sets the width to be the provided width
        re.setHeight(len);  //Sets the length to be the provided length
        re.setY(150 - (len / 2.0)); //Sets the Y position, centered
        re.setX(150 - (wit / 2.0)); //Sets the X position, centered
        group = new Group(re);  //Adds the rectangle to the group
        animateShape(re, Rotate.Y_AXIS);    //Rotates the rectangle
    }

    /*
     * Draws a sphere on the screen
     * Uses the built in sphere method
     */
    private void drawSphere() {
        Sphere s = new Sphere();    //Creates a sphere
        s.setRadius(rad);   //Sets the radius
        s.setLayoutX(150);  //Center X
        s.setLayoutY(150);  //Center Y
        group = new Group(s);   //Add to the group
        animateShape(s, Rotate.Z_AXIS); //Rotate around the z axis.  Its a sphere so you can't tell
    }

    /*
     * Draws a cube on the screen
     * Uses the built in box method
     */
    private void drawCube() {
        Box b = new Box();  //Creates a new box
        b.setWidth(len);    //Sets width to be height and length
        b.setHeight(len);   //Sets height to be width and length
        b.setDepth(len);    //Sets the depth to be the width and height
        b.setLayoutX(150);  //Centers the X
        b.setLayoutY(150);  //Centers the Y
        b.setMaterial(new PhongMaterial(Color.GRAY));   //Sets material to be gray
        group = new Group(b);   //Adds the cube to the group
        animateShape(b, Rotate.X_AXIS); //Rotate the cube around the cube
    }

    /*
     * Draws a cone on the screen
     * Code can be quickly modifiable to work with a truncated cone
     * Math and inspiration from:
     * https://stackoverflow.com/questions/32392456/how-to-create-hollow-cylinder-and-truncated-cone-with-javafx
     */
    private void drawCone() {
        Group cone = new Group();   //Create a group for the cone
        int rounds = 360;   //Say that we want to draw all 360 degrees
        double r1 = rad;    //Lower radius
        double r2 = 0.0;    //Upper radius
        double h = hgt;     //Height of cone between radi
        PhongMaterial material = new PhongMaterial(Color.GRAY); //Defines the color of the cone as Gray
        float[] points = new float[rounds * 12];    //Creates an array of 'points' for the mesh
        float[] textCoords = {                      //Create the texture coordinates
                0.5f, 0,
                0, 1,
                1, 1
        };
        int[] faces = new int[rounds * 12];     //Create an array for the faces
        //Iterate over the array and set the values to be the appropriate location on the cone
        //All values must be float, so casting happens
        for (int i = 0; i < rounds; i++) {
            int index = i * 12;
            points[index] = (float) (Math.cos(Math.toRadians(i)) * r2);
            points[index + 1] = (float) (Math.sin(Math.toRadians(i)) * r2);
            points[index + 2] = (float) (h / 2);
            points[index + 3] = (float) (Math.cos(Math.toRadians(i)) * r1);
            points[index + 4] = (float) (Math.sin(Math.toRadians(i)) * r1);
            points[index + 5] = (float) (-h / 2);
            points[index + 6] = (float) (Math.cos(Math.toRadians(i + 1)) * r1);
            points[index + 7] = (float) (Math.sin(Math.toRadians(i + 1)) * r1);
            points[index + 8] = (float) (-h / 2);
            points[index + 9] = (float) (Math.cos(Math.toRadians(i + 1)) * r2);
            points[index + 10] = (float) (Math.sin(Math.toRadians(i + 1)) * r2);
            points[index + 11] = (float) (h / 2);
        }
        //Sets the face array with the appropriate value
        for (int i = 0; i < rounds; i++) {
            int index = i * 12;
            faces[index] = i * 4;
            faces[index + 1] = 0;
            faces[index + 2] = i * 4 + 1;
            faces[index + 3] = 1;
            faces[index + 4] = i * 4 + 2;
            faces[index + 5] = 2;
            faces[index + 6] = i * 4;
            faces[index + 7] = 0;
            faces[index + 8] = i * 4 + 2;
            faces[index + 9] = 1;
            faces[index + 10] = i * 4 + 3;
            faces[index + 11] = 2;
        }
        //Creates a new Triangle mesh and adds the points, faces, and texture coordinates
        TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().addAll(points);
        mesh.getTexCoords().addAll(textCoords);
        mesh.getFaces().addAll(faces);
        //Creates the lower Cylinder
        Cylinder c1 = new Cylinder(r1, 0.1);
        c1.setMaterial(material);
        c1.setTranslateZ(-h / 2);
        c1.setRotationAxis(Rotate.X_AXIS);
        c1.setRotate(90);
        //The upper cylinder
        Cylinder c2 = new Cylinder(r2, 0.1);
        c2.setMaterial(material);
        c2.setTranslateZ(h / 2);
        c2.setRotationAxis(Rotate.X_AXIS);
        c2.setRotate(90);
        //Creates a MeshView, adds the mesh to the view
        MeshView meshView = new MeshView();
        meshView.setMesh(mesh);
        meshView.setMaterial(material); //Sets material to be Gray
        cone.getChildren().addAll(meshView);    //Add mesh to the cone group
        cone.getChildren().addAll(c1);          //Adds the lower cylinder to the cone group
        Rotate rotate = new Rotate(90, Rotate.X_AXIS);  //Rotate the cone 90 degrees
        cone.getTransforms().add(rotate);   //Perform the rotation
        cone.setLayoutX(150);   //Center X
        cone.setLayoutY(150);   //Center Y
        group = new Group(cone);    //Add cone to the group
        animateShape(cone, Rotate.X_AXIS);  //Animate the rotation
    }

    /*
     * Draws a cylinder on the screen
     * Uses the built in Cylinder object
     */
    private void drawCylinder() {
        Cylinder cy = new Cylinder();   //Creates the cylinder
        cy.setRadius(rad);  //Sets radius
        cy.setHeight(hgt);  //Sets the height
        cy.setLayoutX(150); //Center X
        cy.setLayoutY(150); //Center Y
        cy.setMaterial(new PhongMaterial(Color.GRAY));  //Set material to be Gray
        group = new Group(cy);  //Add cylinder to the group
        animateShape(cy, Rotate.X_AXIS);    //Rotate about X axis
    }

    /*
     * Draws a torus on the screen
     * Works for all 3 types of tori
     * Uses a msh of 128x64 triangles.
     * This provides acceptable smoothness for the rendered size.
     * Increasing mesh density requires more resources.
     * Torus Math from:
     * http://www.lagers.org.uk/javafx/toroidclass.html
     */
    private void drawTorus() {
        int ringSegments = 128;
        int tubeSegments = 64;
        float majRadius = (float) majRad;   //Converts the radius to a float
        float minRadius = (float) minRad;   //Convers the minor radius to a float
        float zeroPos = (float) Math.toRadians(0); //Zero Angle
        int ringSteps = ringSegments + 1; //Convenient for the math
        int tubeSteps = tubeSegments + 1; //Convenient for the math

        TriangleMesh mesh = new TriangleMesh();
        //Calculate the Vertices
        Point3D[][] cord = new Point3D[ringSteps][tubeSteps];
        float ringDelta = (float) (2 * Math.PI / ringSegments);
        float tubeDelta = (float) (2 * Math.PI / tubeSegments);
        //Z=0 Plane points
        for (int t = 0; t < tubeSteps; t++) {
            float angle = zeroPos + t * tubeDelta;
            cord[0][t] = new Point3D(
                    majRadius + minRadius * Math.cos(angle),
                    minRadius * Math.sin(angle),
                    0
            );
        }
        //Calculate the rest of the points
        for (int r = 1; r < ringSteps; r++) {
            float angle = r * ringDelta;
            float sinA = (float) Math.sin(angle);
            float cosA = (float) Math.cos(angle);
            for (int t = 0; t < tubeSteps; t++) {
                Point3D point0 = cord[0][t];
                cord[r][t] = new Point3D(
                        point0.getX() * cosA,
                        point0.getY(),
                        point0.getX() * sinA
                );
            }
        }
        //Convert to 1d array
        float[] points = new float[ringSteps * tubeSteps * 3];
        int i = 0;
        for (int t = 0; t < tubeSteps; t++) {
            for (int r = 0; r < ringSteps; r++) {
                points[i++] = (float) cord[r][t].getX();
                points[i++] = (float) cord[r][t].getY();
                points[i++] = (float) cord[r][t].getZ();
            }
        }
        mesh.getPoints().setAll(points);
        //Calculate the texture coordinates
        float deltaU = (float) 1 / ringSegments;
        float deltaV = (float) 1 / tubeSegments;

        float[] uv = new float[ringSteps * tubeSteps * 2];
        i = 0;
        for (int t = 0; t < tubeSteps; t++) {
            for (int r = 0; r < ringSteps; r++) {
                uv[i++] = r * deltaU;
                uv[i++] = t * deltaV;
            }
        }
        mesh.getTexCoords().setAll(uv);

        //Calculating the faces
        i = 0;
        int[] faces = new int[ringSegments * tubeSegments * 12];
        for (int t = 0; t < tubeSegments; t++) {
            for (int r = 0; r < ringSegments; r++) {
                int iTopLeft = r + t * ringSteps;
                int iBottomLeft = r + (t + 1) * ringSteps;
                int iBottomRight = r + 1 + (t + 1) * ringSteps;
                int iTopRight = r + 1 + t * ringSteps;
                //Top Left Triangle, counterclockwise
                faces[i++] = iTopLeft;
                faces[i++] = iTopLeft;
                faces[i++] = iBottomLeft;
                faces[i++] = iBottomLeft;
                faces[i++] = iTopRight;
                faces[i++] = iTopRight;
                //Bottom Right Triangle, counterclockwise
                faces[i++] = iBottomLeft;
                faces[i++] = iBottomLeft;
                faces[i++] = iBottomRight;
                faces[i++] = iBottomRight;
                faces[i++] = iTopRight;
                faces[i++] = iTopRight;
            }
        }
        mesh.getFaces().setAll(faces);  //Add faces to the mesh
        PhongMaterial material = new PhongMaterial(Color.GRAY); //Defines the material
        MeshView view = new MeshView();
        view.setMesh(mesh);
        view.setMaterial(material);
        view.setLayoutX(150);   //Center X
        view.setLayoutY(150);   //Center Y
        group = new Group(view);
        animateShape(view, Rotate.X_AXIS);  //Rotate around the X axis
    }

    /*
     * Event handler for the draw shape button
     */
    @FXML
    public void drawShape() {
        getFields();    //Populates the instance variables
        Stage shapeView = new Stage();  //Creates a new window
        Scene shapeScene;   //Creates a new scene
        PerspectiveCamera camera = new PerspectiveCamera(false);    //Creates a new camera to view shapes
        switch (choice.getText()) { //Switch based off of the users choice
            case ("circle") -> drawCircle();
            case ("square") -> drawSquare();
            case ("triangle") -> drawTriangle();
            case ("rectangle") -> drawRectangle();
            case ("sphere") -> drawSphere();
            case ("cube") -> drawCube();
            case ("cone") -> drawCone();
            case ("cylinder") -> drawCylinder();
            case ("torus") -> drawTorus();
            default -> group = new Group(new Label("No Shape Chosen")); //This should be unreachable
        }
        shapeScene = new Scene(group, 300, 300);    //Creates a 300x300 scene with whatever shape is chosen
        shapeScene.setCamera(camera);   //Sets the camera at the appropriate position (0,0,0)
        shapeView.setScene(shapeScene); //Sets the window's sene
        shapeView.setResizable(false);
        shapeView.show();
        close();    //Closes the other window
    }

    /*
     * Hides the parameters window
     */
    @FXML
    public void close() {
        clearEntries();
        root.getScene().getWindow().hide();
    }

    /*
     * Clears all of the parameter fields
     */
    private void clearEntries() {
        radius.setText("");
        length.setText("");
        width.setText("");
        height.setText("");
        minorRadius.setText("");
    }
}
