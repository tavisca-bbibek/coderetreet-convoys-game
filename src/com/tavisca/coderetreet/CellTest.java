package com.tavisca.coderetreet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

class CellTest {

    @Test
    void shouldIdentifyItsNeighbour() {
        Cell a = new Cell(0, 0);
        Cell b = new Cell(0, 1);
        boolean areNeighbors = a.isNeighborOf(b);
        Assertions.assertEquals(true, areNeighbors);
    }

    @Test
    void shouldGenerateItsNeighbor() {
        Cell c = new Cell(0, 0);
        Set<Cell> neighbors = Set.of(
                new Cell(0 - 1, 0 - 1),
                new Cell(0 - 1, 0),
                new Cell(0, 0 - 1),
                new Cell(0 + 1, 0),
                new Cell(0, 0 + 1),
                new Cell(0 + 1, 0 - 1),
                new Cell(0 - 1, 0 + 1),
                new Cell(0 + 1, 0 + 1)
        );

        Assertions.assertIterableEquals(c.getNeighbors(), neighbors);
    }

}