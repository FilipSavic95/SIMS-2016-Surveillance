package controler; 

public interface Subject {
	/** dodaj posmatraca u red queueNo */
	public void register(Observer obj, int queueNo);
	
	/** izbaci posmatraca iz reda queueNo */
	public void unregister(Observer obj, int queueNo);
	
	//method to notify observers of change
	public void notifyObservers();
	
	//method to get updates from subject
	public Object getUpdate(Observer obj);
	
}