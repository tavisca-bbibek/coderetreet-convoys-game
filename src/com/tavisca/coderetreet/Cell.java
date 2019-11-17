package com.tavisca.coderetreet;

import java.util.Objects;
import java.util.Set;

public class Cell {
    private int x;
    private int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isNeighborOf(Cell cell) {
        int xDifference = difference(this.getX(), cell.getX());
        int yDifference = difference(this.getY(), cell.getY());

        return (xDifference == 0 && yDifference == 1) ||
                (xDifference == 1 && yDifference == 0) ||
                (xDifference == 1 && yDifference == 1);
    }

    private int difference(int a, int b) {
        return Math.abs(a - b);
    }

    public Set<Cell> getNeighbors() {
        return Set.of(
                new Cell(x - 1, y - 1),
                new Cell(x - 1, y),
                new Cell(x, y - 1),
                new Cell(x + 1, y),
                new Cell(x, y + 1),
                new Cell(x + 1, y - 1),
                new Cell(x - 1, y + 1),
                new Cell(x + 1, y + 1)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return x == cell.x &&
                y == cell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
