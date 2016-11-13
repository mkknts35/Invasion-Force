/*
 * Copyright (C) 2016 Michael Koonts, Sukhenko Artur, Laurens van Wingerden, 
 * Vitalii Egorchatov, Mandip Sangha, David Lamothe
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package invasion.force.board;

/**
 * @author sukhenka (Sukhenko Artur)
 */
public class Position {
	private int row;
	private int col;

	/**
	 * @return the col
	 */
	public int getCol() {
		return col;
	}

	/**
	 * @param col
	 *            the col to set
	 */
	public void setCol(int col) {
		this.col = col;
	}

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @param row
	 *            the row to set
	 */
	public void setRow(int row) {
		this.row = row;
	}

	public boolean equals(Position pos) {
		if (pos.getCol() == col && pos.getRow() == row) {
			return true;
		}
		return false;
	}

	public void setPositionAt(int col, int row) {
		this.col = col;
		this.row = row;
	}

	public void setPositionAt(Position p) {
		this.col = p.getCol();
		this.row = p.getRow();
	}

	/**
	 * @param col
	 * @param row
	 */
	public Position(int row, int col) {
		this.col = col;
		this.row = row;
	}

	/**
	 * @param col
	 * @param row
	 */
	public Position() {
		this.col = -1;
		this.row = -1;
	}

	public boolean isSet() {
		if (col == -1 || row == -1) {
			return false;
		}
		return true;
	}

	public boolean isValid() {
		return (col < 10 && row < 10 && col >= 0 && row >= 0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "(" + col + "," + row + ")";
	}
}

