package gameInterface;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import game.Move;
import game.Position;
import game.Tile;
import game.TileGame;
import search.BfsSearch;
import search.DfsSearch;
import search.HeuristicSearch;
import search.Search;
import javax.swing.JLabel;

public class GameWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private TileGame game;
	private JButton[][] buttons = new JButton[3][3];
	private List<Move> solution;
	private int index = 1;

	JButton btnNextMove;
	JButton btnSolve;
	JButton btnStart;
	JLabel lblInfo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameWindow frame = new GameWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GameWindow() {
		setTitle("Tiles");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 800, 620);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JPanel tiles = new JPanel();
		tiles.setBounds(0, 0, 600, 600);
		tiles.setLayout(new GridLayout(3, 3));
		contentPane.add(tiles);

		lblInfo = new JLabel("");
		lblInfo.setBounds(612, 437, 141, 137);
		contentPane.add(lblInfo);

		layButtons(tiles);

		JRadioButton rdbtnBfs = new JRadioButton("bfs");
		rdbtnBfs.setBounds(612, 163, 141, 23);
		contentPane.add(rdbtnBfs);

		JRadioButton rdbtnDfs = new JRadioButton("dfs");
		rdbtnDfs.setBounds(612, 208, 141, 23);
		contentPane.add(rdbtnDfs);

		JRadioButton rdbtnHeuristic = new JRadioButton("heuristic");
		rdbtnHeuristic.setBounds(612, 255, 141, 23);
		contentPane.add(rdbtnHeuristic);

		rdbtnBfs.setSelected(true);
		ButtonGroup search = new ButtonGroup();
		search.add(rdbtnBfs);
		search.add(rdbtnDfs);
		search.add(rdbtnHeuristic);

		btnNextMove = new JButton("next move");
		btnNextMove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				game.setStart(solution.get(index));
				printTiles();
				if (finished()) {
					btnNextMove.setEnabled(false);
					btnSolve.setEnabled(false);
				}
				lblInfo.setText("<html>" + (solution.size() - index - 1) + " steps</html>");
				++index;
			}
		});
		btnNextMove.setBounds(612, 396, 148, 29);
		contentPane.add(btnNextMove);
		btnNextMove.setEnabled(false);

		btnSolve = new JButton("solve");
		btnSolve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				index = 1;
				Search s;
				
				if (rdbtnBfs.isSelected())
					s = new BfsSearch(game.getStart(), game.getGoal());
				else if (rdbtnDfs.isSelected())
					s = new DfsSearch(game.getStart(), game.getGoal());
				else
					s = new HeuristicSearch(game.getStart(), game.getGoal());
				double start = System.currentTimeMillis();
				solution = solve(s);
				double finish = System.currentTimeMillis();
				btnNextMove.setEnabled(true);
				lblInfo.setText("<html>Found the solution! " + (solution.size() - 1) + " steps<br/>" + ((finish - start) / 1000) + " seconds</html>");
			}
		});
		btnSolve.setBounds(612, 319, 117, 29);
		contentPane.add(btnSolve);
		btnSolve.setEnabled(false);

		btnStart = new JButton("START");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game = new TileGame(3, 3);
				printTiles();
				addListener();
				btnSolve.setEnabled(true);
			}
		});
		btnStart.setBounds(640, 70, 120, 30);
		contentPane.add(btnStart);

	}

	private List<Move> solve(Search search) {
		solution = search.solve();
		return solution;
	}

	/**
	 * Adds listeners to the buttons.
	 */
	private void addListener() {
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				buttons[i][j].addActionListener(new MoveAction(i, j));
			}
		}
	}

	/**
	 * Adds the buttons on the panel
	 * 
	 * @param panel
	 *            that buttons will be added.
	 */
	private void layButtons(JPanel panel) {
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				JButton button = new JButton();
				buttons[i][j] = button;
				panel.add(button);
			}
		}
	}

	/**
	 * Sets icons of the buttons.
	 */
	private void printTiles() {
		if (game != null) {
			Tile[][] tiles = game.getStart().getTiles();
			for (int i = 0; i < 3; ++i) {
				for (int j = 0; j < 3; ++j) {
					buttons[i][j].setIcon(tiles[i][j].getImage());
				}
			}
		}
	}

	/**
	 * Moves the tile if there is a possible way.
	 * 
	 * @param i
	 *            row number of the tile.
	 * @param j
	 *            column number of the tile.
	 */
	private void moveTile(int i, int j) {
		int dir = getDirection(i, j);
		if (dir != -1) {
			game.setStart(new Move(game.getStart().moveZero(dir)));
			if (finished()) {
				btnNextMove.setEnabled(false);
				btnSolve.setEnabled(false);
				lblInfo.setText("You've made it!");
			}
		}
		printTiles();
	}

	/**
	 * Finds the way that you can move the tile.
	 * 
	 * @param i
	 *            row number of the tile
	 * @param j
	 *            column number of the tile
	 * @return direction that you can move the tile.
	 */
	private int getDirection(int i, int j) {
		Position zeroPos = game.getStart().getZeroPos();
		if (i + 1 == zeroPos.getX() && j == zeroPos.getY())
			return TileGame.UP;
		else if (i - 1 == zeroPos.getX() && j == zeroPos.getY())
			return TileGame.DOWN;
		else if (j + 1 == zeroPos.getY() && i == zeroPos.getX())
			return TileGame.LEFT;
		else if (j - 1 == zeroPos.getY() && i == zeroPos.getX())
			return TileGame.RIGHT;
		else
			return -1;
	}

	/**
	 * Checks if the game is finished.
	 * 
	 * @return <tt>true</tt> if the game is finished.
	 */
	private boolean finished() {
		return game.getStart().equals(game.getGoal());
	}

	/**
	 * Action for tile buttons
	 *
	 */
	private class MoveAction implements ActionListener {

		int i;
		int j;

		public MoveAction(int i, int j) {
			this.i = i;
			this.j = j;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			moveTile(i, j);
			btnNextMove.setEnabled(false);

		}

	}
}
