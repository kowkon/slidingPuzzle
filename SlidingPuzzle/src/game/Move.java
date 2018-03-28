package game;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Move implements Comparable<Move> {

	private Tile[][] tiles;
	private Position zeroPos;
	private int heuristic;
	private Position[] map = { new Position(0, 0), new Position(0, 1), new Position(0, 2), new Position(1, 0),
			new Position(1, 1), new Position(1, 2), new Position(2, 0), new Position(2, 1), new Position(2, 2) };

	/**
	 * Constructs a move with the given tile matrix and finds the position of
	 * the zero
	 * 
	 * @param tiles
	 *            tiles
	 */
	public Move(Tile[][] tiles) {
		this.tiles = copyMat(tiles);
		this.zeroPos = findNum(tiles, 0);
		this.heuristic = getHeuristic();
	}

	/**
	 * Constructs a move with the given tile matrix and zero position.
	 * 
	 * @param tiles
	 *            tiles
	 * @param zeroPos
	 *            position of the zero
	 */
	public Move(Tile[][] tiles, Position zeroPos) {
		this.tiles = copyMat(tiles);
		this.zeroPos = new Position(zeroPos.x, zeroPos.y);
		this.heuristic = getHeuristic();
	}

	/**
	 * Finds the heuristic for this move.
	 * 
	 * @return The heuristic
	 */
	private int getHeuristic() {
		int heu = 0;
		for (int i = 0; i < tiles.length; ++i) {
			for (int j = 0; j < tiles[i].length; ++j) {
				Position actual = map[tiles[i][j].getNumber()];
				heu += actual.dist(new Position(i, j));
			}
		}
		return heu;
	}

	/**
	 * Finds the position of zero in the given matrix. Returns <tt>null</tt> if
	 * not found.
	 * 
	 * @param tiles
	 *            the matrix that will be searched.
	 * @return The position of zero. <tt>Null</tt> if not found.
	 */
	private Position findNum(Tile[][] tiles, int num) {
		for (int i = 0; i < tiles.length; ++i) {
			for (int j = 0; j < tiles[i].length; ++j) {
				if (tiles[i][j].getNumber() == num)
					return new Position(i, j);
			}
		}
		return null;
	}

	/**
	 * Moves the zero in the matrix in the specified direction.
	 * 
	 * @param dir
	 *            direction.
	 * @return the new matrix after the movement.
	 */
	public Tile[][] moveZero(int dir) {
		Tile[][] res = copyMat(tiles);
		Tile zero = new Tile(0);
		if (dir == TileGame.UP) {
			res[zeroPos.x][zeroPos.y] = res[zeroPos.x - 1][zeroPos.y];
			res[zeroPos.x - 1][zeroPos.y] = zero;
		} else if (dir == TileGame.DOWN) {
			res[zeroPos.x][zeroPos.y] = res[zeroPos.x + 1][zeroPos.y];
			res[zeroPos.x + 1][zeroPos.y] = zero;
		} else if (dir == TileGame.LEFT) {
			res[zeroPos.x][zeroPos.y] = res[zeroPos.x][zeroPos.y - 1];
			res[zeroPos.x][zeroPos.y - 1] = zero;
		} else if (dir == TileGame.RIGHT) {
			res[zeroPos.x][zeroPos.y] = res[zeroPos.x][zeroPos.y + 1];
			res[zeroPos.x][zeroPos.y + 1] = zero;
		}
		return res;
	}

	/**
	 * Returns the possible moves.
	 * 
	 * @return possible moves
	 */
	public List<Move> getMoves() {
		List<Move> moves = new LinkedList<>();
		if (zeroPos.x > 0) {
			Move m = new Move(moveZero(TileGame.UP), zeroPos.go(TileGame.UP));
			moves.add(m);
		}
		if (zeroPos.x < tiles.length - 1) {
			Move m = new Move(moveZero(TileGame.DOWN), zeroPos.go(TileGame.DOWN));
			moves.add(m);
		}
		if (zeroPos.y > 0) {
			Move m = new Move(moveZero(TileGame.LEFT), zeroPos.go(TileGame.LEFT));
			moves.add(m);
		}
		if (zeroPos.y < tiles[zeroPos.x].length - 1) {
			Move m = new Move(moveZero(TileGame.RIGHT), zeroPos.go(TileGame.RIGHT));
			moves.add(m);
		}
		return moves;
	}

	/**
	 * Copies the given matrix and returns the copy.
	 * 
	 * @param tiles
	 *            matrix to be copied.
	 * @return the copied matrix.
	 */
	private Tile[][] copyMat(Tile[][] tiles) {
		Tile[][] tTiles = new Tile[tiles.length][];
		for (int i = 0; i < tiles.length; ++i) {
			tTiles[i] = new Tile[tiles[i].length];
			System.arraycopy(tiles[i], 0, tTiles[i], 0, tiles[i].length);
		}
		return tTiles;
	}

	/**
	 * Checks if the given tile matrices are equal.
	 * 
	 * @param a
	 *            tile matrix to be checked.
	 * @param b
	 *            tile matrix to be checked.
	 * @return <tt>true</tt> if the matrices are equal.
	 */
	private boolean matEq(Tile[][] a, Tile[][] b) {
		for (int i = 0; i < a.length; ++i) {
			for (int j = 0; j < a[i].length; ++j) {
				if (!a[i][j].equals(b[i][j]))
					return false;
			}
		}
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Move) {
			Move m = (Move) obj;
			// equal if the tile matrices are equal.
			if (matEq(tiles, m.tiles))
				return true;
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		int[] hash = new int[tiles.length * tiles[0].length];
		for (int i = 0; i < tiles.length; ++i) {
			for (int j = 0; j < tiles[i].length; ++j) {
				hash[i * tiles[i].length + j] = tiles[i][j].getNumber();
			}
		}
		return Arrays.hashCode(hash);
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
	}

	public Position getZeroPos() {
		return zeroPos;
	}

	public void setZeroPos(Position zeroPos) {
		this.zeroPos = zeroPos;
	}

	@Override
	public int compareTo(Move o) {
		return this.heuristic - o.heuristic;
	}

}
