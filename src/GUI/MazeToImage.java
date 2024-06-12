package GUI;

import GUI.MazeData.Coords;
import GUI.MazeData.MazeDataSingleton;

import java.awt.*;
import java.awt.image.BufferedImage;
/*
public class MazeToImageConverter {
    public static void paintToImage(BufferedImage image)
    {
        MazeDataSingleton data = MazeDataSingleton.getInstance();

        Graphics2D g2D = image.createGraphics();

        for (int i = 0; i < data.height(); i++) {
            for (int j = 0; j < data.width(); j++)
            {
                Coords temp = new Coords(j, i);

                if(temp.equals(data.getEntry()))
                    g2D.setPaint(new Color(30, 109, 112);
                else if (temp.equals(data.getExit()))
                    g2D.setPaint(new Color(110, 24, 25)));
                else if (data.getMaze()[i][j] == MazeDataSingleton.Wall)
                    g2D.setPaint(Color.BLACK);
                else if (data.getMaze()[i][j] == MazeDataSingleton.Route)
                    g2D.setPaint(new Color(189, 153, 59));
                else
                    g2D.setPaint(Color.WHITE);

                g2D.fillRect(j*8, i*8, 8, 8);
            }
        }

        g2D.dispose();
    }

    public static BufferedImage getImageFromMaze()
    {
        MazeDataSingleton data = MazeDataSingleton.getInstance();

        BufferedImage image = new BufferedImage(data.width()*8, data.height()*8, BufferedImage.TYPE_BYTE_INDEXED);

        paintToImage(image);

        return image;
    }

}
 */