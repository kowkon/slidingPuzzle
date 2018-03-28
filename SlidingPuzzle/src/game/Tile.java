package game;

import javax.swing.ImageIcon;

public class Tile {

	private int number;
	private ImageIcon image;

	public Tile(int number) {
		this.number = number;
		this.image = new ImageIcon("images/sincap" + number + ".jpg");
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public ImageIcon getImage() {
		return image;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Tile) {
			Tile t = (Tile) obj;
			return this.number == t.number;
		}
		return super.equals(obj);
	}
	
	

}
