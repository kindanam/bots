package skal.tools.DHBot;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class InstancePropertyChangeSupport
{
    protected PropertyChangeSupport changeSupport ;
	public InstancePropertyChangeSupport(Object sourceBean )
	{
		Object sb = sourceBean == null ? this : sourceBean;
		changeSupport = new PropertyChangeSupport(sb);
	}

   public void addPropertyChangeListener (PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
   }

   public void removePropertyChangeListener(
        PropertyChangeListener listener) {       
        changeSupport.removePropertyChangeListener(listener);
   }

   public void firePropertyChange (String propertyName,
        Object old, Object newObj) {
        changeSupport.firePropertyChange(propertyName, old, newObj);
   }
}