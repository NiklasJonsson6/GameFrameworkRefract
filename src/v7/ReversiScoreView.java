package v7;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

class ReversiScoreView implements PropertyChangeListener {
    public void propertyChange(PropertyChangeEvent e) {
        if (e.getSource().getClass().equals(ReversiModel.class)) {
            ReversiModel rev = (ReversiModel)e.getSource();

            System.out.println("Bong! White: " + rev.getWhiteScore()
                    + "\tBlack: " + rev.getBlackScore());
        }
    }
}
