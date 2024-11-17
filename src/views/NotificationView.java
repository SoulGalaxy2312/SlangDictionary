package views;

import java.awt.BorderLayout;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class NotificationView extends JFrame implements ViewInterface {
    private JFrame parentFrame;
    
    @Override
    public void turnOn() {
        setVisible(true);
    }

    public NotificationView(String message, JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 100);

        JLabel label = new JLabel(message, SwingConstants.CENTER);
        setLayout(new BorderLayout());
        add(label, BorderLayout.CENTER);
        setToTheRight();
    }

    public void setToTheRight() {
        Point parentLocation = parentFrame.getLocation();
        int parentWidth = parentFrame.getWidth();
        int parentHeight = parentFrame.getHeight();

        int x = parentLocation.x + parentWidth;
        int y = parentLocation.y + (parentHeight / 2) - (getHeight() / 2);

        setLocation(x, y);
    }
}
