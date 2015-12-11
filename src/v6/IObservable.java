package v6;

import java.beans.PropertyChangeListener;

/**
 * Created by Niklas on 10/12/15.
 */
public interface IObservable {
    void addObserver(PropertyChangeListener observer);
    void removeObserver(PropertyChangeListener observer);
}
