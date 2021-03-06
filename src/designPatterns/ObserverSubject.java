package designPatterns;

import java.util.HashSet;
import java.util.Set;

/**
 * The type Observer subject.
 */
public abstract class ObserverSubject
{
    /**
     * The Observers.
     */
    protected Set<Observer> observers = new HashSet<Observer>();

    /**
     * Add observer.
     *
     * @param observer the observer
     */

    public abstract void addObserver(Observer observer);

    /**
     * Remove observer.
     *
     * @param observer the observer
     */
    public abstract void removeObserver(Observer observer);

    /**
     * Update observers.
     */
    public void updateObservers()
    {
        for (Observer observer : this.observers)
            observer.update();
    }

}
