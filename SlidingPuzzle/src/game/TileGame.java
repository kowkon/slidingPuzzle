package game;

import java.util.List;
import java.util.Random;

import search.Search;

public class TileGame {

	public static int UP = 0;
	public static int DOWN = 1;
	public static int LEFT = 2;
	public static int RIGHT = 3;

	private Move start;
	private Move goal;

	/**
	 * Constructs the game with random tiles.
	 * 
	 * @param row
	 *            row size of the matrix.
	 * @param col
	 *            column size of the matrix.
	 */
	public TileGame(int row, int col) {

		start = new Move(randomStart(row, col));
		goal = new Move(createGoal(row, col));

	}

	/**
	 * 
	 * Randomly generates a tile matrix.
	 * 
	 * @param row
	 *            row size of the matrix.
	 * @param col
	 *            column size of the matrix.
	 * @return randomly generated tile matrix.
	 */
	private Tile[][] randomStart(int row, int col) {
		Tile[][] tiles = createGoal(row, col);
		Move curr = new Move(tiles);
		Random rndm = new Random();
		for (int i = 0; i < 10000; ++i) {
			List<Move> moves = curr.getMoves();
			int next = rndm.nextInt(moves.size());
			curr = moves.get(next);
		}
		return curr.getTiles();
	}

	/**
	 * Generates the goal matrix.
	 * 
	 * @param row
	 *            row size of the matrix.
	 * @param col
	 *            column size of the matrix.
	 * @return the goal matrix.
	 */
	private Tile[][] createGoal(int row, int col) {
		Tile[][] tiles = new Tile[row][col];
		for (int i = 0; i < row; ++i) {
			for (int j = 0; j < col; ++j) {
				tiles[i][j] = new Tile(i * col + j);
			}
		}
		return tiles;
	}

	public List<Move> solve(Search search) {
		return search.solve();
	}

	public Move getStart() {
		return start;
	}

	public void setStart(Move start) {
		this.start = start;
	}

	public Move getGoal() {
		return goal;
	}

}
