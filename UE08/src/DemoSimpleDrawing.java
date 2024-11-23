/*
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.util.Set;
 */

public class DemoSimpleDrawing {
    /*
        @Override
        public void start(Stage primarystage) {
            Canvas  canva = new Canvas(700, 600);
            StackPane stackPane = new StackPane(canva);
            Scene scene = new Scene(stackPane);
            GraphicsContext gc = canva.getGraphicsContext2D();

            double x1 = canva.getWidth() / 2;
            double y1 = 40;
            double x2 = 40;
            double y2 = canva.getHeight() - 40;
            double x3 = canva.getWidth() - 40;
            double y3 = canva.getHeight() - 40;

            drawSierpinskiTriangle(gc, 5, x1, y1, x2, y2, x3, y3);

            primarystage.setTitle("Sirpinski Triangle");
            primarystage.setScene(scene);
            primarystage.centerOnScreen();
            primarystage.show();
        }

        public void drawSierpinskiTriangle (GraphicsContext gc, int size, double x1, double y1, double x2, double y2, double x3, double y3) {
            double seitenlaenge = new Point2D(x1, y1).distance(new Point2D(x2, y2));

            if (seitenlaenge < size) {
                return;
            }

            gc.strokePolygon(new double[] {x1, x2, x3}, new double[] {y1, y2, y3}, 3);

            double midx1 = (x1 + x2) / 2;
            double midy1 = (y1 + y2) / 2;
            double midx2 = (x2 + x3) / 2;
            double midy2 = (y2 + y3) / 2;
            double midx3 = (x3 + x1) / 2;
            double midy3 = (y3 + y1) / 2;

            drawSierpinskiTriangle(gc, size, x1, y1, midx1, midy1, midx3, midy3); //oben
            drawSierpinskiTriangle(gc, size, midx3, midy3, midx2, midy2, x3, y3); //rechts
            drawSierpinskiTriangle(gc, size, midx1, midy1, x2, y2, midx2, midy2); //links
        }
     */
    }
