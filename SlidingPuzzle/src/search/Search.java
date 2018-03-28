package search;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import game.Move;

public abstract class Search {

	protected Move start;
	protected Move goal;

	public Search(Move start, Move goal) {
		this.start = start;
		this.goal = goal;
	}

	/**
	 * Finds the solution for the puzzle and updates parentMap
	 * 
	 * @param parentMap
	 *            that will keep the path.
	 * @return <tt>true</tt> if the puzzle is solved.
	 */
	public abstract boolean search(HashMap<Move, Move> parentMap);

	/**
	 * Find the path from start to goal.
	 * 
	 * @return The list of moves from start to goal.
	 */
	public List<Move> solve() {
		HashMap<Move, Move> parentMap = new HashMap<>();
		boolean found = search(parentMap);
		if (!found)
			return new LinkedList<>();
		List<Move> path = constructPath(parentMap);
		return path;
	}

	/**
	 * Constructs the path from start to goal.
	 * 
	 * @param parentMap
	 *            The paths that traveled.
	 * @return The List of moves from start to goal.
	 */
	private List<Move> constructPath(HashMap<Move, Move> parentMap) {
		LinkedList<Move> path = new LinkedList<>();
		Move curr = goal;
		while (!curr.equals(start)) {
			path.addFirst(curr);
			curr = parentMap.get(curr);
		}
		path.addFirst(start);
		return path;
	}

}
