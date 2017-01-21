package restaurant;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import restaurant.Adress;
import restaurant.Container;

/**
 * Created by Aleksander Kaźmierczak on 17.01.2017.
 */
public class DrawingShape {
    private Rectangle shape;
    private Adress position;
    private Paint color;

    public DrawingShape(Adress position, Paint paint) {
        this.position = position;
        color = paint;
        draw();
    }

    public Rectangle getShape() {
        return shape;
    }

    public void setShape(Rectangle shape) {
        this.shape = shape;
    }

    public Adress getPosition() {
        return position;
    }

    public void setPosition(Adress position) {
        this.position = position;
    }

    public void draw(){
        shape = new Rectangle(Container.get().getPolygonSize(),Container.get().getPolygonSize());
        shape.setFill(color);
        shape.xProperty().set(position.getX()* Container.get().getPolygonSize());
        shape.yProperty().set(position.getY()*Container.get().getPolygonSize());

        Container.get().getPaneChildren().add(shape);
    }

    public void drawMove(){
        Platform.runLater(() -> {
            shape.relocate(position.getX()*Container.get().getPolygonSize(),position.getY()*Container.get().getPolygonSize());
        });
    }

    public void remove(){
        Platform.runLater(() -> {
            Container.get().getPaneChildren().remove(shape);
        });
    }
}
