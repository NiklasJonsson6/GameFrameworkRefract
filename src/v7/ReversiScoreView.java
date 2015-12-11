package v7;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

class ReversiScoreView implements PropertyChangeListener {

    //To avoid a print unless score changed
    String previousOutput = "";

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource().getClass().equals(ReversiModel.class)) {
            ReversiModel rev = (ReversiModel)evt.getSource();

            String output = "Bong! White: " + rev.getWhiteScore()
                    + "\tBlack: " + rev.getBlackScore();

            if (!output.equals(previousOutput)) {
                System.out.println(output);
            }

            previousOutput = output;
        }
    }
}
