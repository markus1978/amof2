package hub.sam.mof.plugin;

import hub.sam.mof.plugin.modelview.tree.TreeObject;

import org.eclipse.ui.plugin.*;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class Mof2Plugin extends AbstractUIPlugin {

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
		return AbstractUIPlugin.imageDescriptorFromPlugin("MOF2Plugin", path);
	}
}
