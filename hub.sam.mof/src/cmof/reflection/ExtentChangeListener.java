package cmof.reflection;

public interface ExtentChangeListener {
	
	public void newObject(cmof.reflection.Object newObject);
	
	public void removedObject(cmof.reflection.Object oldObject);
}
