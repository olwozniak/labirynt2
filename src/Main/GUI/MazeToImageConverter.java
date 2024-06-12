package Main.GUI;

import Main.MazeData.Coords;
import Main.MazeData.MazeDataSingleton;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MazeToImageConverter {

    private static final int TILE_SIZE = 8;

    public static void paintToImage(BufferedImage image) {
        MazeDataSingleton data = MazeDataSingleton.getInstance();
        Graphics2D g2D = image.createGraphics();

        for (int i = 0; i < data.height(); i++) {
            for (int j = 0; j < data.width(); j++) {
                Coords temp = new Coords(j, i);
                Color color = getColorForCell(data, temp, i, j);
                g2D.setPaint(color);
                g2D.fillRect(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }

    private static Color getColorForCell(MazeDataSingleton data, Coords coords, int row, int col) {
        if (coords.equals(data.getEntry())) {
            return new Color(30, 10, 30);  // Wejście
        } else if (coords.equals(data.getExit())) {
            return new Color(10, 100, 100);   // Wyjście
        } else if (data.getMaze()[row][col] == MazeDataSingleton.Wall) {
            return new Color(8, 13, 42);            // Sciany
        } else if (data.getMaze()[row][col] == MazeDataSingleton.Route) {
            return new Color(189, 153, 59); // Trasy
        } else {
            return Color.WHITE;             // Pustego pola
        }
    }

    public static BufferedImage getImageFromMaze() {
        MazeDataSingleton data = MazeDataSingleton.getInstance();
        BufferedImage image = new BufferedImage(data.width() * TILE_SIZE, data.height() * TILE_SIZE, BufferedImage.TYPE_INT_RGB);
        paintToImage(image);
        return image;
    }
}
