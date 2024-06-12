package Main;

import Main.MazeData.MazeDataSingleton;
import javax.swing.*;
import java.util.*;

class Main {
    public static void main(String[] args) {
        CustomEventManager.initialize();
        MazeBrowse.initialize();

        SwingUtilities.invokeLater(() -> {
            setLookAndFeel();
            startGui(args);
        });
    }

    private static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private static void startGui(String[] args) {
        ApplicationGUI mainGui = new ApplicationGUI();
        startCli(args);
        mainGui.start();
    }
}
