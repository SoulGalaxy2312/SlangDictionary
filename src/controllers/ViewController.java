package controllers;

import views.ViewInterface;

public class ViewController {
    public void showView(ViewInterface view) {
        if (view == null) {
            return;
        }
        view.turnOn();
    }
}
