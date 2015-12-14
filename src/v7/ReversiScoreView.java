package v7;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

class ReversiScoreView implements PropertyChangeListener {

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource().getClass().equals(ReversiModel.class)
                && evt.getPropertyName().equals("ReversiScore")) {
            ReversiModel rev = (ReversiModel)evt.getSource();
            String output = "Bong! White: " + rev.getWhiteScore()
                    + "\tBlack: " + rev.getBlackScore();
            System.out.println(output);
        }
    }
}
