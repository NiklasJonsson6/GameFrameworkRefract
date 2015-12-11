package v6;

/**
 * Created by Niklas on 2015-12-08.
 */
public final class GameUtils {
    private GameUtils() throws Exception {
        throw new Exception();
    }

    /**
     * Set the tile on a specified position in the gameboard.
     *
     * @param pos  The position in the gameboard matrix.
     * @param tile The type of tile to paint in specified position
     */
    public static void setGameboardState(GameTile[][] gameboard, final Position pos, final GameTile tile) {
        setGameboardState(gameboard, pos.getX(), pos.getY(), tile);
    }

    /**
     * Set the tile on a specified position in the gameboard.
     *
     * @param x    Coordinate in the gameboard matrix.
     * @param y    Coordinate in the gameboard matrix.
     * @param tile The type of tile to paint in specified position
     */
    public static void setGameboardState(GameTile[][] gameboard, final int x, final int y, final GameTile tile) {
        gameboard[x][y] = tile;
    }
}
