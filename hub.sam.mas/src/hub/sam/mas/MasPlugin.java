/***********************************************************************
 * MASE -- MOF Action Semantics Editor
 * Copyright (C) 2007 Andreas Blunk
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301  USA
 ***********************************************************************/

package hub.sam.mas;

import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class MasPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "hub.sam.mas";
	public static final String PACKAGE_PREFIX = "hub.sam.mas";
	public static final String EDITOR_PACKAGE_PREFIX = PACKAGE_PREFIX + ".editor";
    public static boolean log4jConfigured = false;

	// The shared instance
	private static MasPlugin plugin;
	
	/**
	 * The constructor
	 */
	public MasPlugin() {
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static MasPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
    
    public static void configureLog4j() {
        if (!log4jConfigured && getDefault() != null) {
            try {
            	if (getDefault() == null) {
            		PropertyConfigurator.configure("resources/log4j.properties");
            	} else {
            		PropertyConfigurator.configure(FileLocator.toFileURL(getDefault().getBundle().getEntry("resources/log4j.properties")));	
            	}                
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void logError(String message, Throwable e) {
        log(new Status(IStatus.ERROR, PLUGIN_ID, 0, message, e));  
    }
    
    public static void log(IStatus status) {
        getDefault().getLog().log(status);
    }
    
}
