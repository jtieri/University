package HW1;

public class Percolation {
	private enum Coordinate {
		IS_OPEN,
		IS_CLOSED,
		IS_FULL,
	}

	private int[][] grid;
	private int size;
	private int openSites;

	public Percolation(int n) throws IllegalArgumentException {
		if (n <= 0) {
			throw new IllegalArgumentException;
		} else {
			this.grid = new int[n][n];
			this.size = n;
			this.openSites = 0;
			this.fillGrid();
		}
	}

	public void open(int i, int j) throws IndexOutOfBoundsException {
		if (i < 0 || i > this.size-1) {
			throw new IndexOutOfBoundsException;
		}

		if (j < 0 || j > this.size-1) {
			throw new IndexOutOfBoundsException;
		}

		if (this.grid[i][j] == Coordinate.IS_CLOSED) {
			this.grid[i][j] = Coordinate.IS_OPEN;
			this.openSites++;

			// A coordinate adjacent to a coordinate with X=0, i.e. (0, X), opening up is marked full to be checked in checkForFullCoords()
			if (i == 0) {
				this.grid[i][j] = Coordinate.IS_FULL;
			}

			checkForFullCoords(i, j);

			for (int i = 0; i < this.size; i++) {
				dfs(0, i);
			}
		}
	}

	public boolean isOpen(int i, int j) throws IndexOutOfBoundsException {
		if (i < 0 || i > this.size-1) {
			throw new IndexOutOfBoundsException;
		}

		if (j < 0 || j > this.size-1) {
			throw new IndexOutOfBoundsException;
		}

		return this.grid[i][j] != Coordinate.IS_CLOSED;
	}

	public boolean isFull(int i, int j) throws IndexOutOfBoundsException {
		if (i < 0 || i > this.size-1) {
			throw new IndexOutOfBoundsException;
		}

		if (j < 0 || j > this.size-1) {
			throw new IndexOutOfBoundsException;
		}

		return this.grid[i][j] == Coordinate.IS_FULL;
	}
	
	public int numberOfOpenSites() {
		return this.numberOfOpenSites;
	}

	public boolean percolates() {
		// Grid cannot possibly percolate if there are no open sites or less than N open sites, where N is the grid height.
		if (this.numberOfOpenSites <= 0 || this.numberOfOpenSites < this.size) {
			return false;
		}

		// If every coordinate in the grid is open, then it percolates.
		if (this.numberOfOpenSites == this.size*this.size) {
			return true;
		}

		for (int i = 0; i < this.size; i++) {
			if (isFull(this.grid.length-1, i)) {
				return true;
			}
		}		

		return false;
	}

	private void dfs(int x, int y) {
		// Out of bounds
		if (i < 0 || i > this.size-1) {
			return;
		}

		// Out of bounds
		if (j < 0 || j > this.size-1) {
			return;
		}

		// Already full or not even open
		if (isFull(i, j) || !isOpen(i, j)) {
			return;
		}

		this.grid[i][j] = Coordinate.IS_FULL;

		dfs(i-1, j);
		dfs(i, j-1);
		dfs(i, j+1);
		dfs(i+1, j);
	}

	private void checkForFullCoords() {
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				if (this.grid[i][j] == Coordinate.IS_FULL) {
					this.grid[i][j] = Coordinate.IS_OPEN;
				}
			}
		}
	}

	private void fillGrid() {
		for (int i = 0; i < this.size-1; i++) {
			for (int j = 0; j < this.size-1; j++) {
				this.grid[i][j] = Coordinate.IS_CLOSED;
			}
		}
	}
}