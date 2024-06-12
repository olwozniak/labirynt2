package AmazeingGui.MazeData;

import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.Map;

public class DJQueue extends LinkedList<Map.Entry<Coordinates, Integer>> {
    public DJQueue()
    {
        super();
    }

    public void addToQueue(Coordinates crd, int length)
    {
        Map.Entry<Coordinates, Integer> newEntry = new AbstractMap.SimpleEntry<>(crd, length);

        add(newEntry);
    }

    public Integer peekLength()
    {
        if(isEmpty())
            return null;

        return peek().getValue();
    }

    public Coordinates popCoordinates()
    {
        if(isEmpty())
            return null;

        return remove().getKey();
    }
}
