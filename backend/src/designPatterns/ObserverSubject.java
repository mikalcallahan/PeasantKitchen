package designPatterns;

import java.util.HashSet;

public abstract class ObserverSubject 
{
	protected HashSet<Observer> observers = new HashSet<Observer>();
	
	//abstract methods
	public abstract void addObserver(Observer observer);
	public abstract void removeObserver(Observer observer);
	
	public void updateObservers()
	{
		this.observers.forEach(Observer::update);
	}
	
}
