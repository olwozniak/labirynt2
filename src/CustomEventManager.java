
import ActionObservers.ActionObserver;

import java.util.ArrayList;
import java.util.HashMap;

public final class CustomEventManager {
    private static CustomEventManager instance = null;
    private final HashMap<EventType, ArrayList<ActionObserver>> registeredObservers;

    private CustomEventManager()
    {
        registeredObservers = new HashMap<>();
    }

    public synchronized void registerObserver(EventType type, ActionObserver observer)
    {
        if(instance == null)
            throw new RuntimeException("Nie zainicializowano event managera!");

        if(!registeredObservers.containsKey(type))
            registeredObservers.put(type, new ArrayList<>());

        registeredObservers.get(type).add(observer);
    }

    public synchronized void callEvent(EventType type)
    {
        if(instance == null)
            throw new RuntimeException("Nie zainicializowano event managera!");

        ArrayList<ActionObserver> observers = registeredObservers.get(type);

        if(observers == null)
            return;

        observers.forEach(ActionObserver::call);
    }

    public static CustomEventManager getInstance() {
        return instance;
    }

    static void initialize()
    {
        instance = new CustomEventManager();
    }
}
