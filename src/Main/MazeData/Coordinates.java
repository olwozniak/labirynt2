package AmazeingGui.MazeData;

public class Coordinates{
    public int x;
    public int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Coordinates c)
            return c.x == x && c.y == y;

        return false;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}
