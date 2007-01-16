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

package hub.sam.mase.m2model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

import org.apache.log4j.Logger;

/**
 * A garbage collector for AMOF models.
 * 
 * Model objects can be explicitly marked and unmarked as garbage. Another feature allows
 * to mark model objects as garbage as long as a specific property does not change.
 *
 * @author Andreas Blunk
 */
public class ModelGarbageCollector implements PropertyChangeListener {
    
    private static Logger logger = Logger.getLogger(ModelGarbageCollector.class.getName());
    
    /**
     * List of potential garbage.
     */
    private List<cmof.reflection.Object> garbage = new ArrayList<cmof.reflection.Object>();
    
    private static ModelGarbageCollector instance = null;
    
    private ModelGarbageCollector() {
    }
    
    public static ModelGarbageCollector getInstance() {
        if (instance == null) {
            instance = new ModelGarbageCollector();
        }
        return instance;
    }
    
    protected void finalize() {
        instance = null;
    }
    
    public void dispose() {
        finalize();
    }
    
    /**
     * Mark the model object as potential garbage + listen for changes of the specified property
     * and unmark the model if the property changes.
     * 
     * @param model model
     * @param propertyName property name that should be observed
     */
    public void observeProperty(cmof.reflection.Object model, java.lang.String propertyName) {
        logger.debug("observing property '" + propertyName + "' in model object '" + getName(model) + "'");
        model.addListener(propertyName, this);
        if (!garbage.contains(model)) {
            garbage.add(model);
        }
        else {
            logger.warn("already observing model " + getName(model));
        }
    }
    
    /**
     * Mark the model object as potential garbage.
     * 
     * @param model
     */
    public void mark(cmof.reflection.Object model) {
        logger.debug("marked " + getName(model));
        if (!garbage.contains(model)) {
            garbage.add(model);
        }
        else {
            logger.warn("model " + getName(model) + " already marked");
        }
    }
    
    /**
     * Unmark the model object as potential garbage.
     * 
     * @param model
     */
    public void unmark(cmof.reflection.Object model) {
        if (logger.isDebugEnabled()) {
            logger.debug("unmarked " + getName(model));
            if (!garbage.contains(model)) {
                logger.warn("model " + getName(model) + " was never marked");
            }
        }
        garbage.remove(model);
    }
    
    /**
     * Delete the model object.
     * 
     * @param model
     */
    public void deleteModel(cmof.reflection.Object model) {
        unmark(model);
        model.delete();
    }

    /**
     * Gets called if the observed property of some model object changed.
     * The model object is no longer considered potential garbage and is
     * removed from the garbage list.
     */
    public void propertyChange(PropertyChangeEvent event) {
        Object source = event.getSource();
        if (source != null && source instanceof cmof.reflection.Object) {
            cmof.reflection.Object model = (cmof.reflection.Object) source;
            logger.debug("dismissed " + getName(model));
            model.removeListener(event.getPropertyName(), this);
            unmark(model);
        }
    }
    
    /**
     * Destroys all model objects in the garbage list.
     */
    public void cleanUp() {
        logger.debug("cleaning up ...");
        for(cmof.reflection.Object model: garbage) {
            model.delete();
            logger.debug("destroyed " + getName(model));
        }
        garbage.clear();
    }
    
    private java.lang.String getName(java.lang.Object object) {
        return object.getClass().getSimpleName() + "_" + java.lang.Integer.toString(object.hashCode());
    }
    
}
