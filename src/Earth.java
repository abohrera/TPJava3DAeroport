import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.paint.Color;


public class Earth extends Group {
    private Sphere sph;
    private Rotate ry;

    public void setRotating(boolean rotating) {
        isRotating = rotating;
    }

    public boolean isRotating() {
        return isRotating;
    }

    private boolean isRotating = true;


    public Earth() {
        // Create a Sphere representing the Earth
        sph = new Sphere(300);

        // Add texture to the sphere
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(new javafx.scene.image.Image("file:tpaeroport/earth_lights.png"));
        material.setSpecularMap(new javafx.scene.image.Image("file:tpaeroport/earth_lights.png"));
        sph.setMaterial(material);

        // Add the sphere to the group
        this.getChildren().add(sph);

        ry = new Rotate(0, Rotate.Y_AXIS);
        sph.getTransforms().add(ry);


        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long time) {
                if (isRotating) {
                    double angle = (time / 1_000_000_000.0) * 360 / 60; // Full rotation in 15 seconds
                    ry.setAngle(angle % 360);
                }
            }
        };
        animationTimer.start();
    }

}
