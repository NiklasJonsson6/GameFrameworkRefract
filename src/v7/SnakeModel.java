package v7;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayDeque;
import java.util.Deque;

/** The game Snake */
public class SnakeModel implements GameModel {

    //Variables:
    public enum Directions {
        EAST(1, 0),
        WEST(-1, 0),
        NORTH(0, -1),
        SOUTH(0, 1),
        NONE(0, 0);

        private final int xDelta;
        private final int yDelta;

        Directions(final int xDelta, final int yDelta) {
            this.xDelta = xDelta;
            this.yDelta = yDelta;
        }

        public int getXDelta() {
            return this.xDelta;
        }

        public int getYDelta() {
            return this.yDelta;
        }
    }
    /** Graphical representation of the food tile */
    private static final GameTile FOOD_TILE = new RoundTile(Color.RED);
    /** Graphical representation of a blank tile */
    private static final GameTile BLANK_TILE = new BlankTile();
    /** Graphical representation of a snake tile */
    private static final GameTile SNAKE_TILE = new RectangularTile(Color.BLACK);
    /** Graphical representation of the snake*/
    private Deque<Position> Snake = new ArrayDeque<>();
    /** Current direction of the snake */
    private Directions direction = Directions.NORTH;
    /** Food eaten / snake body length */
    private int score;
    /** Size of gameboard */
    private Dimension size = getGameboardSize();
    /** Matrix containing tiles of the gameboard */
    private GameTile[][] gameboardState;
    /** Move speed of the snake (delay in ms) */
    private int updateSpeed = 500;

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    //Constructors

    /**
     * Create a new model for the snake game
     */
    public SnakeModel() {
        gameboardState = new GameTile[size.width][size.height];

        //Blank out gameboard
        for (int i = 0; i < size.width; i++) {
            for (int j = 0; j < size.height; j++) {
                GameUtils.setGameboardState(gameboardState, i, j, BLANK_TILE);
            }
        }

        //Create snake head
        Snake.add(new Position(size.width/2, size.height/2));

        //Insert snake
        GameUtils.setGameboardState(gameboardState, Snake.element(), SNAKE_TILE);

        //Insert food
        addFood();
    }

    //Methods

    /**
     * Adds a food tile on a random tile on the gameboard
     */
    private void addFood() {
        Position foodPos;
        //Find blank position
        do {
            foodPos = new Position((int) (Math.random() * size.width), (int) (Math.random() * size.height));
        } while (!isPositionEmpty(foodPos));
        //add new food
        GameUtils.setGameboardState(gameboardState, foodPos, FOOD_TILE);
    }

    /**
     * Checks if the given position on gameboard is blank
     * @param pos The position to check
     * @return  <code>true</code> if the tile is blank, <code>false</code> otherwise.
     */
    private boolean isPositionEmpty(final Position pos) {
        return (getGameboardState(pos) == BLANK_TILE);
    }

    /**
     * Update the direction of the collector
     * according to the user's keypress.
     */
    private void updateDirection(final int key) {
        switch (key) {
            case KeyEvent.VK_LEFT:
                if (!(direction == Directions.EAST)) {
                    direction = Directions.WEST;
                }
                break;
            case KeyEvent.VK_UP:
                if (!(direction == Directions.SOUTH)) {
                    direction = Directions.NORTH;
                }                break;
            case KeyEvent.VK_RIGHT:
                if (!(direction == Directions.WEST)) {
                    direction = Directions.EAST;
                }                break;
            case KeyEvent.VK_DOWN:
                if (!(direction == Directions.NORTH)) {
                    direction = Directions.SOUTH;
                }                break;
            default:
                // Don't change direction if another key is pressed
                break;
        }
    }

    /**
     * Get next position of the snake head
     */
    private Position getNextSnakePos() {
        return new Position(
                (Snake.getFirst()).getX() + direction.getXDelta(),
                (Snake.getFirst()).getY() + direction.getYDelta());
    }

    /**
     *
     * @param pos The position to test.
     * @return <code>false</code> if the position is outside the playing field, <code>true</code> otherwise.
     */
    private boolean isOutOfBounds(Position pos) {
        return pos.getX() < 0 || pos.getX() >= getGameboardSize().width
                || pos.getY() < 0 || pos.getY() >= getGameboardSize().height;
    }

    /**
     * This method is called repeatedly so that the
     * game can update its state.
     *
     * @param lastKey
     *            The most recent keystroke.
     */
    @Override
    public void gameUpdate(final int lastKey) throws GameOverException {
        updateDirection(lastKey);

        //Move snake..
        Snake.addFirst(getNextSnakePos());

        if(isOutOfBounds(Snake.getFirst()) //if border or snake body is hit
                || (getGameboardState(Snake.getFirst()) instanceof RectangularTile)) {
            throw new GameOverException(score);
        }

        if (!(getGameboardState(Snake.getFirst()) instanceof RoundTile)) { //if food is not eaten, remove tail
            GameUtils.setGameboardState(gameboardState, Snake.getLast(), BLANK_TILE);
            Snake.removeLast();
        } else { //if food is eaten, skip removing tail once and add new food
            if (updateSpeed > 100) updateSpeed -= 10;
            score++;
            addFood();
        }
        GameUtils.setGameboardState(gameboardState, Snake.getFirst(), SNAKE_TILE);

        pcs.firePropertyChange("GameboardState", null, null);
    }

    @Override
    public GameTile getGameboardState(Position pos) {
        return getGameboardState(pos.getX(), pos.getY());
    }

    @Override
    public GameTile getGameboardState(int x, int y) {
        return gameboardState[x][y];
    }

    @Override
    public int getUpdateSpeed() {
        return updateSpeed;
    }

    @Override
    public Dimension getGameboardSize() {
        return Constants.getGameSize();
    }

    @Override
    public void addObserver(PropertyChangeListener observer) {
        pcs.addPropertyChangeListener(observer);
    }

    @Override
    public void removeObserver(PropertyChangeListener observer) {
        pcs.removePropertyChangeListener(observer);
    }
}
