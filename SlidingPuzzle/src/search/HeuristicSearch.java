package search;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import game.Move;

public class HeuristicSearch extends Search {

	public HeuristicSearch(Move start, Move goal) {
		super(start, goal);
	}

	@Override
	public boolean search(HashMap<Move, Move> parentMap) {
		PriorityQueue<Move> toExplore = new PriorityQueue<>();
		HashSet<Move> visited = new HashSet<>();

		toExplore.add(start);
		visited.add(start);

		boolean found = false;
		while (!toExplore.isEmpty()) {
			Move curr = toExplore.remove();
			toExplore.remove(curr);
			if (curr.equals(goal)) {
				found = true;
				break;
			}
			List<Move> moves = curr.getMoves();
			Iterator<Move> itMoves = moves.iterator();
			while (itMoves.hasNext()) {
				Move next = itMoves.next();
				if (!visited.contains(next)) {
					visited.add(next);
					toExplore.add(next);
					parentMap.put(next, curr);
				}
			}
		}
		return found;
	}

}
