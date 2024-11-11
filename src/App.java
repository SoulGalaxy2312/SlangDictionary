import services.FileService;

import java.util.Map;

import controllers.MainController;

import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        MainController mainController = new MainController();
        mainController.start();
    }
}
