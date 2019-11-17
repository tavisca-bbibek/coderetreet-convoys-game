package com.tavisca.coderetreet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class ConvoyTest {

    @Test
    void shouldAcceptInitialState() {
        Map<Cell, Boolean> initialState = generateDeadCells(0, 2);

        Convoy convoy = new Convoy(initialState);

        Assertions.assertEquals(initialState.size(), convoy.getCellToStatusMap().size());
    }

    @Test
    void shouldGetAliveNeighborsCount() {
        Map<Cell, Boolean> initialState = generateDeadCells(0, 2);
        initialState.put(new Cell(1, 1), true);
        Convoy convoy = new Convoy(initialState);

        Cell with1AliveNeighbour = new Cell(0, 0);
        long count = convoy.aliveNeighbourCount(with1AliveNeighbour);

        Assertions.assertEquals(1, count);
    }

    @Test
    void shouldGrowWithEachGenerationToSquareOfNextOddNumber() {
        Map<Cell, Boolean> initialState = generateDeadCells(0, 2);
        Convoy convoy = new Convoy(initialState);

        int nextGenCellCount = convoy.nextGeneration().size();

        Assertions.assertEquals(25, nextGenCellCount);
    }

    @Test
    void shouldSatisfyUnderpopulationCondition() {
        Map<Cell, Boolean> initialState = generateDeadCells(0, 2);
        initialState.put(new Cell(0, 0), true);
        Cell with1AliveNeighbor = new Cell(1, 1);
        initialState.put(with1AliveNeighbor, true);
        Convoy convoy = new Convoy(initialState);

        Map<Cell, Boolean> nextGen = convoy.nextGeneration();
        Assertions.assertFalse(nextGen.get(with1AliveNeighbor));
    }

    @Test
    void shouldSatisfyOverpopulationCondition() {
        Map<Cell, Boolean> initialState = generateDeadCells(0, 2);
        initialState.put(new Cell(0, 0), true);
        initialState.put(new Cell(0, 2), true);
        initialState.put(new Cell(2, 0), true);
        initialState.put(new Cell(2, 2), true);
        Cell with4AliveNeighbors = new Cell(1, 1);
        initialState.put(with4AliveNeighbors, true);
        Convoy convoy = new Convoy(initialState);

        Map<Cell, Boolean> nextGen = convoy.nextGeneration();
        Assertions.assertFalse(nextGen.get(with4AliveNeighbors));
    }

    @Test
    void shouldSatisfyReproductionCondition() {
        Map<Cell, Boolean> initialState = generateDeadCells(0, 2);
        initialState.put(new Cell(0, 0), true);
        initialState.put(new Cell(0, 2), true);
        initialState.put(new Cell(1, 1), true);
        Cell with3AliveNeighbors = new Cell(0, 1);
        Convoy convoy = new Convoy(initialState);

        Map<Cell, Boolean> nextGen = convoy.nextGeneration();
        Assertions.assertTrue(nextGen.get(with3AliveNeighbors));
    }

    @Test
    void shouldSatisfySurvivalCondition() {
        Map<Cell, Boolean> initialState = generateDeadCells(0, 2);
        initialState.put(new Cell(0, 0), true);
        initialState.put(new Cell(2, 2), true);
        Cell with2AliveNeighbors = new Cell(1, 1);
        initialState.put(with2AliveNeighbors, true);
        Convoy convoy1 = new Convoy(initialState);

        Map<Cell, Boolean> nextGen = convoy1.nextGeneration();
        Assertions.assertTrue(nextGen.get(with2AliveNeighbors));

        initialState.put(new Cell(0, 2), true);
        Cell with3AliveNeighbors = new Cell(1, 1);
        Convoy convoy2 = new Convoy(initialState);

        Map<Cell, Boolean> nextGen2 = convoy2.nextGeneration();
        Assertions.assertTrue(nextGen.get(with2AliveNeighbors));
    }

    @Test
    void shouldEvolveWithNewPopulation() {
        Map<Cell, Boolean> initialState = generateDeadCells(0, 2);
        initialState.put(new Cell(0, 0), true);
        initialState.put(new Cell(2, 2), true);
        Convoy convoy = new Convoy(initialState);

        Map<Cell, Boolean> nextGen = convoy.evolve();
        Map<Cell, Boolean> cellToStatusMap = convoy.getCellToStatusMap();

        nextGen.forEach((k, v) ->
                Assertions.assertEquals(
                        cellToStatusMap.containsKey(k) && cellToStatusMap.get(k), v));
    }


    private Map<Cell, Boolean> generateDeadCells(int begin, int end) {
        HashMap<Cell, Boolean> deadCells = new HashMap<>();
        for (int x = begin; x <= end; x++)
            for (int y = begin; y <= end; y++)
                deadCells.put(new Cell(x, y), false);
        return deadCells;
    }
}