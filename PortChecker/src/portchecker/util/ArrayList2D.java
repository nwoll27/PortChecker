package portchecker.util;

import java.util.ArrayList;

public class ArrayList2D<Type> {
	ArrayList<ArrayList<Type>> array;

	public ArrayList2D() {
		array = new ArrayList<ArrayList<Type>>();
	}

	/**
	 * ensures a minimum capacity of rowCapacity rows. Note that this 
	 * does not guarantee that there are that many rows. 
	 * @param rowCapacity
	 */
	public void ensureCapacity(int rowCapacity) {
		array.ensureCapacity(rowCapacity);
	}

	/**
	 * Ensures that the given row has at least the given capacity. 
	 * Note that this method will also ensure that getNumRows() >= rowIndex 
	 * @param rowIndex
	 * @param columnCapacity
	 */
	public void ensureCapacity(int rowIndex, int columnCapacity) {
		ensureCapacity(rowIndex);
		while (rowIndex < getNumRows()) {
			array.add(new ArrayList<Type>());
		}
		array.get(rowIndex).ensureCapacity(columnCapacity);
	}

	/**
	 * Adds an item at the end of the specified row. 
	 * This will guarantee that at least rowIndex rows exist.
	 */
	public void Add(Type data, int rowIndex) {
		ensureCapacity(rowIndex);
		while (rowIndex >= getNumRows()) {
			array.add(new ArrayList<Type>());
		}
		array.get(rowIndex).add(data);
	}

	public Type get(int row, int column) {
		return array.get(row).get(column);
	}

	public void set(int row, int column, Type data) {
		array.get(row).set(column, data);
	}

	public void remove(int row, int column) {
		array.get(row).remove(column);
	}

	public boolean contains(Type data) {
		for (int i = 0; i < array.size(); i++) {
			if (array.get(i).contains(data)) {
				return true;
			}
		}
		return false;
	}

	public int getNumRows() {
		return array.size();
	}

	public int getNumCols(int row) {
		return array.get(row).size();
	}
}
