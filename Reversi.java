
/* 17D8103013C 三浦 晃一 2019/11/29 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.Random;
import java.util.Scanner;

class Player {
	public final static int type_human = 0;
	public final static int type_computer = 1;
	private int color;
	private int type;
	public int select = 0;

	Player(int c, int t) {
		if (t == type_computer) {
			if (c == Stone.black)
				System.out.println("黒の戦略: 1(ランダム) 2(最大マス) 3(角優先) 4(新戦法)");
			else
				System.out.println("白の戦略: 1(ランダム) 2(最大マス) 3(角優先) 4(新戦法)");
			Scanner s = new Scanner(System.in);
			select = s.nextInt();
		}
		if (c == Stone.black || c == Stone.white)
			color = c;
		else {
			System.out.println("プレイヤーの石は黒か白でなければなりません:" + c);
			System.exit(0);
		}
		if (t == type_human || t == type_computer)
			type = t;
		else {
			System.out.println("プレイヤーは人間かコンピュータでなければなりません:" + t);
			System.exit(0);
		}
	}

	int getColor() {
		return color;
	}

	int getType() {
		return type;
	}

	Point tactics(Board bd, int select) {
		if (select == 1) {
			ArrayList<Point> point = new ArrayList<Point>();
			if (color == Stone.black) {
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						if (bd.eval_black[i][j] > 0) {
							point.add(new Point(i, j));
						}
					}
				}
				if (point.size() == 0)
					return (new Point(-1, -1));
				Random r = new Random(System.currentTimeMillis());
				int random = r.nextInt(point.size());
				return point.get(random);
			} else if (color == Stone.white) {
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						if (bd.eval_white[i][j] > 0) {
							point.add(new Point(i, j));
						}
					}
				}
				if (point.size() == 0)
					return (new Point(-1, -1));
				Random r = new Random(System.currentTimeMillis());
				int random = r.nextInt(point.size());
				return point.get(random);
			}
		} else if (select == 2) {
			ArrayList<Point> max_point = new ArrayList<Point>();
			int max = -2;
			if (color == Stone.black) {
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						if (bd.eval_black[i][j] > max && bd.eval_black[i][j] > 0) {
							max = bd.eval_black[i][j];
						}
					}
				}
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						if (max == bd.eval_black[i][j])
							max_point.add(new Point(i, j));
					}
				}
				if (max_point.size() == 0)
					return (new Point(-1, -1));
				Random r = new Random(System.currentTimeMillis());
				int random = r.nextInt(max_point.size());
				return max_point.get(random);
			} else if (color == Stone.white) {
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						if (bd.eval_white[i][j] > max && bd.eval_white[i][j] > 0) {
							max = bd.eval_white[i][j];
						}
					}
				}
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						if (max == bd.eval_white[i][j])
							max_point.add(new Point(i, j));
					}
				}
				if (max_point.size() == 0)
					return (new Point(-1, -1));
				Random r = new Random(System.currentTimeMillis());
				int random = r.nextInt(max_point.size());
				return max_point.get(random);
			}
		} else if (select == 3) {
			int flag = 0;
			ArrayList<Point> corner = new ArrayList<Point>();
			if (color == Stone.black) {
				if (bd.eval_black[0][0] > 0)
					return new Point(0, 0);
				if (bd.eval_black[0][7] > 0)
					return new Point(0, 7);
				if (bd.eval_black[7][0] > 0)
					return new Point(7, 0);
				if (bd.eval_black[7][7] > 0)
					return new Point(7, 7);
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						if (i == 0 || i == 7 || j == 0 || j == 7) {
							if (bd.eval_black[i][j] > 0) {
								flag = 1;
								corner.add(new Point(i, j));
							}
						}
					}
				}
				if (flag == 1) {
					Random r = new Random(System.currentTimeMillis());
					int random = r.nextInt(corner.size());
					return corner.get(random);
				} else {
					for (int i = 0; i < 8; i++) {
						for (int j = 0; j < 8; j++) {
							if (bd.eval_black[i][j] > 0) {
								corner.add(new Point(i, j));
							}
						}
					}
					if (corner.size() == 0)
						return (new Point(-1, -1));
					Random r = new Random(System.currentTimeMillis());
					int random = r.nextInt(corner.size());
					return corner.get(random);
				}
			} else if (color == Stone.white) {
				if (bd.eval_white[0][0] > 0)
					return new Point(0, 0);
				if (bd.eval_white[0][7] > 0)
					return new Point(0, 7);
				if (bd.eval_white[7][0] > 0)
					return new Point(7, 0);
				if (bd.eval_white[7][7] > 0)
					return new Point(7, 7);
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						if (i == 0 || i == 7 || j == 0 || j == 7) {
							if (bd.eval_white[i][j] > 0) {
								flag = 1;
								corner.add(new Point(i, j));
							}
						}
					}
				}
				if (flag == 1) {
					Random r = new Random(System.currentTimeMillis());
					int random = r.nextInt(corner.size());
					return corner.get(random);
				} else {
					for (int i = 0; i < 8; i++) {
						for (int j = 0; j < 8; j++) {
							if (bd.eval_white[i][j] > 0) {
								corner.add(new Point(i, j));
							}
						}
					}
					if (corner.size() == 0)
						return (new Point(-1, -1));
					Random r = new Random(System.currentTimeMillis());
					int random = r.nextInt(corner.size());
					return corner.get(random);
				}
			}
		} else if (select == 4) {
			ArrayList<Point> new_strategy = new ArrayList<Point>();
			if (color == Stone.black) {
				if (bd.eval_black[0][0] > 0)
					return (new Point(0, 0));
				if (bd.eval_black[0][7] > 0)
					return (new Point(0, 7));
				if (bd.eval_black[7][0] > 0)
					return (new Point(7, 0));
				if (bd.eval_black[7][7] > 0)
					return (new Point(7, 7));
				if (bd.num_grid_black + bd.num_grid_white > 10) {
					int min = 64;
					for (int i = 0; i < 8; i++) {
						for (int j = 0; j < 8; j++) {
							if (bd.eval_black[i][j] < min && bd.eval_black[i][j] > 0)
								min = bd.eval_black[i][j];
						}
					}
					for (int i = 0; i < 8; i++) {
						for (int j = 0; j < 8; j++) {
							if (min == bd.eval_black[i][j])
								new_strategy.add(new Point(i, j));
						}
					}
					if (new_strategy.size() == 0)
						return (new Point(-1, -1));
					Random r = new Random(System.currentTimeMillis());
					int random = r.nextInt(new_strategy.size());
					return new_strategy.get(random);
				} else {
					int max = -2;
					for (int i = 0; i < 8; i++) {
						for (int j = 0; j < 8; j++) {
							if (bd.eval_black[i][j] > max && bd.eval_black[i][j] > 0)
								max = bd.eval_black[i][j];
						}
					}
					for (int i = 0; i < 8; i++) {
						for (int j = 0; j < 8; j++) {
							if (max == bd.eval_black[i][j])
								new_strategy.add(new Point(i, j));
						}
					}
					if (new_strategy.size() == 0)
						return (new Point(-1, -1));
					Random r = new Random(System.currentTimeMillis());
					int random = r.nextInt(new_strategy.size());
					return new_strategy.get(random);
				}
			} else {
				if (bd.eval_white[0][0] > 0)
					return new Point(0, 0);
				if (bd.eval_white[0][7] > 0)
					return new Point(0, 7);
				if (bd.eval_white[7][0] > 0)
					return new Point(7, 0);
				if (bd.eval_white[7][7] > 0)
					return new Point(7, 7);
				if (bd.num_grid_black + bd.num_grid_white > 10) {
					int min = 64;
					for (int i = 0; i < 8; i++) {
						for (int j = 0; j < 8; j++) {
							if (bd.eval_white[i][j] < min && bd.eval_white[i][j] > 0)
								min = bd.eval_white[i][j];
						}
					}
					for (int i = 0; i < 8; i++) {
						for (int j = 0; j < 8; j++) {
							if (min == bd.eval_white[i][j])
								new_strategy.add(new Point(i, j));
						}
					}
					if (new_strategy.size() == 0)
						return (new Point(-1, -1));
					Random r = new Random(System.currentTimeMillis());
					int random = r.nextInt(new_strategy.size());
					return new_strategy.get(random);
				} else {
					int max = -2;
					for (int i = 0; i < 8; i++) {
						for (int j = 0; j < 8; j++) {
							if (bd.eval_white[i][j] > max && bd.eval_white[i][j] > 0)
								max = bd.eval_white[i][j];
						}
					}
					for (int i = 0; i < 8; i++) {
						for (int j = 0; j < 8; j++) {
							if (max == bd.eval_white[i][j])
								new_strategy.add(new Point(i, j));
						}
					}
					if (new_strategy.size() == 0)
						return (new Point(-1, -1));
					Random r = new Random(System.currentTimeMillis());
					int random = r.nextInt(new_strategy.size());
					return new_strategy.get(random);
				}
			}
		}
		return (new Point(-1, -1));
	}

	Point nextMove(Board bd, Point p) {
		if (type == type_human)
			return p;
		else if (type == type_computer)
			return tactics(bd, select);
		return (new Point(-1, -1));
	}
}

class Stone {
	public final static int black = 1;
	public final static int white = 2;
	private int obverse;

	Stone() {
		obverse = 0;
	}

	void setObverse(int color) {
		if (color == black || color == white)
			obverse = color;
		else
			System.out.println("黒か白でなければいけません");
	}

	void paint(Graphics g, Point p, int rad) {
		if (obverse == black) {
			g.setColor(Color.black);
			g.fillOval(p.x, p.y, rad, rad);
		} else if (obverse == white) {
			g.setColor(Color.white);
			g.fillOval(p.x, p.y, rad, rad);
		}
	}

	int getObverse() {
		return obverse;
	}

	void doReverse() {
		if (obverse == black)
			obverse = white;
		else
			obverse = black;
	}
}

class Board {
	int unit_size = 80;
	Stone stone[][] = new Stone[8][8];
	public int num_grid_black = 0;
	public int num_grid_white = 0;
	private Point[] direction = new Point[8];
	public int[][] eval_black = new int[8][8];
	public int[][] eval_white = new int[8][8];

	Board() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				stone[i][j] = new Stone();
			}
		}

		for (int i = 3; i <= 4; i++) {
			for (int j = 3; j <= 4; j++) {
				stone[i][j] = new Stone();
				if (i == j)
					stone[i][j].setObverse(1);
				else
					stone[i][j].setObverse(2);
			}
		}

		direction[0] = new Point(1, 0);
		direction[1] = new Point(1, 1);
		direction[2] = new Point(0, 1);
		direction[3] = new Point(-1, 1);
		direction[4] = new Point(-1, 0);
		direction[5] = new Point(-1, -1);
		direction[6] = new Point(0, -1);
		direction[7] = new Point(1, -1);
		printBoard();
		evaluateBoard();
		printEval();
	}

	boolean isOnBoard(int x, int y) {
		if (x < 0 || 7 < x || y < 0 || 7 < y)
			return false;
		else
			return true;
	}

	ArrayList<Integer> getLine(int x, int y, Point d) {
		ArrayList<Integer> line = new ArrayList<Integer>();
		int cx = x + d.x;
		int cy = y + d.y;
		while (isOnBoard(cx, cy) && stone[cx][cy].getObverse() != 0) {
			line.add(stone[cx][cy].getObverse());
			cx += d.x;
			cy += d.y;
		}
		return line;
	}

	int countReverseStone(int x, int y, int s) {
		if (stone[x][y].getObverse() != 0)
			return -1;
		int cnt = 0;
		for (int d = 0; d < 8; d++) {
			ArrayList<Integer> line = new ArrayList<Integer>();
			line = getLine(x, y, direction[d]);
			int n = 0;
			while (n < line.size() && line.get(n) != s) {
				n++;
			}
			if (1 <= n && n < line.size()) {
				cnt += n;
			}
		}
		return cnt;
	}

	int countStone(int s) {
		int cnt = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (stone[i][j].getObverse() == s)
					cnt += 1;
			}
		}
		return cnt;
	}

	void evaluateBoard() {
		num_grid_black = 0;
		num_grid_white = 0;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				eval_black[i][j] = countReverseStone(i, j, 1);
				if (eval_black[i][j] > 0)
					num_grid_black++;
				eval_white[i][j] = countReverseStone(i, j, 2);
				if (eval_white[i][j] > 0)
					num_grid_white++;
			}
		}
	}

	void printBoard() {
		System.out.println("盤面(Stone):");
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				System.out.printf("%2d ", stone[i][j].getObverse());
			}
			System.out.println();
		}
	}

	void printEval() {
		System.out.println("Black(1):");
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				System.out.printf("%2d ", eval_black[i][j]);
			}
			System.out.println();
		}
		System.out.println("White(2):");
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				System.out.printf("%2d ", eval_white[i][j]);
			}
			System.out.println();
		}
	}

	void setStoneAndReverse(int x, int y, int s) {
		setStone(x, y, s);
		Point p = new Point();
		for (int d = 0; d < 8; d++) {
			ArrayList<Integer> line = new ArrayList<Integer>();
			p.x = direction[d].y;
			p.y = direction[d].x;
			line = getLine(y, x, p);
			int n = 0;
			while (n < line.size() && line.get(n) != s) {
				n++;
			}
			if (1 <= n && n < line.size()) {
				for (int i = 1; i <= n; i++) {
					stone[y + i * direction[d].y][x + i * direction[d].x].doReverse();
				}
			}
		}
	}

	void paint(Graphics g, int unit_size) {
		g.setColor(Color.black);
		g.fillRect(0, 0, unit_size * 10, unit_size * 10);

		g.setColor(new Color(0, 85, 0));
		g.fillRect(unit_size, unit_size, unit_size * 8, unit_size * 8);

		g.setColor(Color.black);
		for (int i = 1; i <= 9; i++) {
			g.drawLine(unit_size, i * unit_size, 9 * unit_size, i * unit_size);
		}

		for (int i = 1; i <= 9; i++) {
			g.drawLine(i * unit_size, unit_size, i * unit_size, 9 * unit_size);
		}

		for (int i = 1; i <= 2; i++) {
			for (int j = 1; j <= 2; j++) {
				if (i == 1 && j == 1)
					g.fillRect(3 * unit_size - 5, 3 * unit_size - 5, 10, 10);
				else if (i == 1 && j == 2)
					g.fillRect(3 * unit_size + 320 - 5, 3 * unit_size - 5, 10, 10);
				else if (i == 2 && j == 1)
					g.fillRect(3 * unit_size - 5, 3 * unit_size + 320 - 5, 10, 10);
				else
					g.fillRect(3 * unit_size + 320 - 5, 3 * unit_size + 320 - 5, 10, 10);
			}
		}

		int x = 120 - 32;
		int y = 120 - 32;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Point p = new Point(x, y);
				stone[i][j].paint(g, p, unit_size * 4 / 5);
				x += unit_size;
			}
			x = 120 - 32;
			y += unit_size;
		}
	}

	void setStone(int x, int y, int s) {
		stone[y][x].setObverse(s);
	}

}

public class Reversi extends JPanel {

	public final static int UNIT_SIZE = 80;
	Board board = new Board();
	private int turn;
	private Player[] player = new Player[2];

	public Reversi() {
		setPreferredSize(new Dimension(UNIT_SIZE * 10, UNIT_SIZE * 10));
		addMouseListener(new MouseProc());
		player[0] = new Player(Stone.black, Player.type_human);
		player[1] = new Player(Stone.white, Player.type_computer);
		turn = Stone.black;
	}

	public void paintComponent(Graphics g) {
		String msg1 = "";
		board.paint(g, UNIT_SIZE);
		g.setColor(Color.white);
		if (turn == Stone.black)
			msg1 = "黒の番です";
		else
			msg1 = "白の番です";
		if (player[turn - 1].getType() == Player.type_computer)
			msg1 += "(考えています)";
		String msg2 = "[黒:" + board.countStone(Stone.black) + ",白" + board.countStone(Stone.white) + "]";
		g.drawString(msg1, UNIT_SIZE / 2, UNIT_SIZE / 2);
		g.drawString(msg2, UNIT_SIZE / 2, 19 * UNIT_SIZE / 2);

	}

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.getContentPane().setLayout(new FlowLayout());
		f.getContentPane().add(new Reversi());
		f.pack();
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	void EndMessageDialog() {
		int black = board.countStone(Stone.black);
		int white = board.countStone(Stone.white);
		String str = "[黒:" + black + "白:" + white + "]で";
		if (black == white)
			str += "引き分け";
		else if (black > white)
			str += "黒の勝ち";
		else
			str += "白の勝ち";
		JOptionPane.showMessageDialog(this, str, "ゲーム終了", JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}

	void MessageDialog(String str) {
		JOptionPane.showMessageDialog(this, str, "情報", JOptionPane.INFORMATION_MESSAGE);
	}

	void changeTurn() {
		if (turn == Stone.black)
			turn = Stone.white;
		else
			turn = Stone.black;
	}

	class MouseProc extends MouseAdapter {
		public void mouseClicked(MouseEvent me) {
			Point point = me.getPoint();
			int btn = me.getButton();
			Point gp = new Point();
			gp.x = point.x / UNIT_SIZE - 1;
			gp.y = point.y / UNIT_SIZE - 1;
			if (!board.isOnBoard(gp.x, gp.y))
				return;
			removeMouseListener(this);
			if (player[turn - 1].getType() == Player.type_human) {
				if ((player[turn - 1].getColor() == Stone.black && board.num_grid_black == 0)
						|| (player[turn - 1].getColor() == Stone.white && board.num_grid_white == 0)) {
					changeTurn();
					MessageDialog("あなたはパスです");
					repaint();
					board.evaluateBoard();
					board.printBoard();
					board.printEval();
				} else if ((player[turn - 1].getColor() == Stone.black && board.eval_black[gp.y][gp.x] > 0)
						|| (player[turn - 1].getColor() == Stone.white && board.eval_white[gp.y][gp.x] > 0)) {
					Point nm = player[turn - 1].nextMove(board, gp);
					board.setStoneAndReverse(nm.x, nm.y, player[turn - 1].getColor());
					changeTurn();
					repaint();
					board.evaluateBoard();
					board.printBoard();
					board.printEval();
					if (board.num_grid_black == 0 && board.num_grid_white == 0)
						EndMessageDialog();
				}
				if (player[turn - 1].getType() == Player.type_human)
					addMouseListener(this);
			}
			if (player[turn - 1].getType() == Player.type_computer) {
				Thread th = new TacticsThread();
				th.start();
			}
		}
	}

	class TacticsThread extends Thread {
		public void run() {
			try {
				Thread.sleep(2000);
				Point nm = player[turn - 1].nextMove(board, new Point(-1, -1));
				if (nm.x == -1 && nm.y == -1) {
					if (player[turn - 1].getColor() == Stone.black)
						MessageDialog("黒:あなたはパスです");
					else
						MessageDialog("白:あなたはパスです");
				} else {
					board.setStoneAndReverse(nm.y, nm.x, player[turn - 1].getColor());
				}
				changeTurn();
				repaint();
				board.evaluateBoard();
				board.printBoard();
				board.printEval();
				System.out.println(board.num_grid_black);
				System.out.println(board.num_grid_white);
				addMouseListener(new MouseProc());
				if (board.num_grid_black == 0 && board.num_grid_white == 0)
					EndMessageDialog();
				if (player[turn - 1].getColor() == Stone.black && board.num_grid_black == 0)
					return;
				if (player[turn - 1].getColor() == Stone.white && board.num_grid_white == 0)
					return;
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}
}
