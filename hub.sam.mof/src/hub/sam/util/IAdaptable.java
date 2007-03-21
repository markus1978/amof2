package hub.sam.util;

public interface IAdaptable {
	public <T> T getAdaptor(Class<T> adaptorClass);
}
