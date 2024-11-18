package views;

import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JFrame;

public class PopUpView extends JFrame {
    private JFrame parentFrame;

    private int width;

    private int height;

    public PopUpView(JFrame parentFrame, int width, int height) {
        this.parentFrame = parentFrame;
        this.width = width;
        this.height = height;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(new Dimension(width, height));
        setToTheRight();
    }

    public void turnOn() {
        setVisible(true);
    }

    public void setToTheRight() {
        Point parentLocation = parentFrame.getLocation();
        int parentWidth = parentFrame.getWidth();
        int parentHeight = parentFrame.getHeight();

        int x = parentLocation.x + parentWidth;
        int y = parentLocation.y + (parentHeight / 2) - height/2;

        setLocation(x, y);
    }
}
