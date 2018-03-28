package game;

public class Position {

	protected int x;
	protected int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Moves the position by 1 in the specified direction.
	 * 
	 * @param dir
	 *            direction
	 * @return the position moved by 1 in the specified direction.
	 */
	public Position go(int dir) {
		Position p = new Position(x, y);
		if (dir == TileGame.UP)
			p.setX(x - 1);
		else if (dir == TileGame.DOWN)
			p.setX(x + 1);
		else if (dir == TileGame.LEFT)
			p.setY(y - 1);
		else if (dir == TileGame.RIGHT)
			p.setY(y + 1);
		return p;
	}

	/**
	 * Finds the discrete distance between positions.
	 * 
	 * @param p
	 *            position
	 * @return the discrete distance
	 */
	public int dist(Position p) {
		return Math.abs(this.x - p.x) + Math.abs(this.y - p.y);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Position) {
			Position p = (Position) obj;
			if (x == p.x && y == p.y)
				return true;
		}
		return super.equals(obj);
	}

}
