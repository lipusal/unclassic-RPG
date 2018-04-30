package tiles;

/**
 * This Exception is thrown when an invalid action is attempted on a Tile.
 */
public class TileException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TileException() {}

	public TileException(String message) {
		super(message);
	}
}
