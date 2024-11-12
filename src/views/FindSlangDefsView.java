package views;

import javax.swing.JFrame;

public class FindSlangDefsView extends JFrame implements ViewInterface {
    public FindSlangDefsView() {
        setTitle("Find Slang's Definitions");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void turnOn() {
        setVisible(true);
    }    

    public void turnOff() {
        setVisible(false);
    }
}
