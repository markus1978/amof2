package hub.sam.mof.plugin;

import java.util.Collection;
import java.util.Vector;

import hub.sam.mof.IRepository;
import hub.sam.mof.Repository;
import hub.sam.mof.plugin.modelview.tree.TreeObject;

import org.eclipse.ui.plugin.*;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class Mof2Plugin extends AbstractUIPlugin {

    public static final String PLUGIN_ID = "hub.sam.mof.browser";
    
	//The shared instance.
	private static Mof2Plugin plugin;
	
	/**
	 * The constructor.
	 */
	public Mof2Plugin() {
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		Platform.getAdapterManager().registerAdapters(new MOF2AdapterFactory(), TreeObject.class);
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 */
	public static Mof2Plugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path.
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	private static Collection<IRepository> additionalRepositories = new Vector<IRepository>();		
	/**
	 * Registers additonal repositories to the model view. Each model view that is opened
	 * will try to at these repositories to its content provider. When the repositories aren't
	 * accessable (an exception occurs, using them) they will be removed from this
	 * registry. 
	 * 
	 * This is used for showing remote repositories in the view.
	 */
	public static void registerAdditionalRepository(IRepository repository) {
		additionalRepositories.add(repository);
		for (AdditionalRepositoriesChangeListener listener: additionalRepositoriesChangeListener) {
			listener.repositoryAdded(repository);
		}
	}

	public static Collection<IRepository> getAdditionalRepositories() {
		Collection<IRepository> invalidRepositories = new Vector<IRepository>();
		for (IRepository additionalRepository: additionalRepositories) {
			try {
				additionalRepository.getExtentNames();
			} catch (Exception ex) {
				invalidRepositories.add(additionalRepository);
			}
		}
		additionalRepositories.removeAll(invalidRepositories);
		return additionalRepositories;
	}
	
	public static class AdditionalRepositoriesChangeListener {
		public void repositoryAdded(IRepository repository) {
			
		}
	}

	private static Collection<AdditionalRepositoriesChangeListener> additionalRepositoriesChangeListener = new Vector<AdditionalRepositoriesChangeListener>();
	
	public static void addAdditionalRepositoriesChangeListener(AdditionalRepositoriesChangeListener listener) {
		additionalRepositoriesChangeListener.add(listener);
	}
	
	public static void removeAdditionalRepositoriesChangeListener(AdditionalRepositoriesChangeListener listener) {
		additionalRepositoriesChangeListener.remove(listener);
	}
}
