import javafx.application.Application;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.util.List;

public class Interface extends Application {

    Scene TheScene;
    double clickOnX, clickOnY;

    World world = new World("tpaeroport/src/data/airport-codes_no_comma.csv");

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("DataFlight");

        // Instantiate and add the Earth object
        Earth earth = new Earth();

        // Create the scene
        Scene theScene = new Scene(earth, 800, 600, true);

        // Set the scene and show the stage
        primaryStage.setScene(theScene);
        primaryStage.show();

        // Set up the PerspectiveCamera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-1000);
        camera.setNearClip(0.1);
        camera.setFarClip(2000.0);
        camera.setFieldOfView(35);


        // Add rotation to the Earth
        theScene.setCamera(camera);


        // Handle mouse interactions
        theScene.addEventHandler(MouseEvent.ANY, event -> {
            if (event.getEventType() == MouseEvent.MOUSE_PRESSED ) {
                earth.setRotating(false); // Pause rotation on click
                clickOnX=event.getSceneX();
                clickOnY=event.getSceneY();
                Rotate viewRotate = new Rotate();
                viewRotate.setAxis(Rotate.Y_AXIS);
                viewRotate.setPivotX(0);
                viewRotate.setPivotY(0);
                viewRotate.setPivotZ(-camera.getTranslateZ());
                earth.getTransforms().add(viewRotate);
                Translate zooomTranslation = new Translate();
                camera.getTransforms().add(zooomTranslation);
            }

            if (event.getEventType() == MouseEvent.MOUSE_DRAGGED && !earth.isRotating()  && event.getButton() == MouseButton.PRIMARY) {
                double deltaX = clickOnX-event.getSceneX();
                double deltaY = clickOnY-event.getSceneY();

                // Translate the Earth based on mouse movement
                Translate current = (Translate) camera.getTransforms().getLast();
                current.setZ(deltaY*5);

                List listTransform = earth.getTransforms();
                Rotate currentRotate=(Rotate)listTransform.getLast();
                currentRotate.setPivotZ(-earth.getTranslateZ());
                currentRotate.setAngle(deltaX);
            }

            if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
                earth.setRotating(true); // Resume rotation
            }
        });

        // Add right-click event to find the nearest airport
        theScene.addEventHandler(MouseEvent.ANY, event -> {
            if (event.getButton() == MouseButton.SECONDARY && event.getEventType() == MouseEvent.MOUSE_CLICKED) {
                PickResult pickResult = event.getPickResult();
                if (pickResult.getIntersectedNode() != null) {
                    // Example logic: Retrieve intersection point and convert to lat/long
                    double x = pickResult.getIntersectedTexCoord().getX();
                    double y = pickResult.getIntersectedTexCoord().getY();

                    // Convert to latitude and longitude
                    double latitude = 180 * (0.5 - y);
                    double longitude = 360 * (x - 0.5);

                    Aeroport clientRequest = world.findNearestAirport(latitude,longitude);
                    System.out.println("Nearest airport: "+clientRequest.getIATA()+" ("+clientRequest.getCountry()+
                            ") at lat=" + latitude + ", long=" + longitude );
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
