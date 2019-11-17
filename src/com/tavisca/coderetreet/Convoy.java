package com.tavisca.coderetreet;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class Convoy {
    private Map<Cell, Boolean> cellToStatusMap;

    Convoy(Map<Cell, Boolean> initialState) {
        cellToStatusMap = initialState;
    }

    Map<Cell, Boolean> getCellToStatusMap() {
        return Collections.unmodifiableMap(cellToStatusMap);
    }

    private Map<Cell, Boolean> getNeighbors(Cell cell) {
        Set<Cell> neighbors = cell.getNeighbors();
        Map<Cell, Boolean> neighbourToStatusMap = new HashMap<>();
        neighbors.stream().forEach(
                n -> neighbourToStatusMap.put(n, cellToStatusMap.getOrDefault(n, false))
        );

        return neighbourToStatusMap;
    }

    long aliveNeighbourCount(Cell cell) {
        return aliveNeighbourCount(getNeighbors(cell));

    }

    private long aliveNeighbourCount(Map<Cell, Boolean> cellToStatusMap) {
        return cellToStatusMap.values().stream()
                .filter(Boolean::booleanValue).count();
    }

    boolean fate(Cell cell) {
        boolean isAlive = cellToStatusMap.get(cell);
        long count = aliveNeighbourCount(cell);
        if (isAlive && (isUnderPopulation(count) || isOverPopulation(count)))
            return false;
        if (!isAlive && canReproduce(count))
            return true;
        return isAlive;
    }

    boolean canReproduce(long aliveNeighbourCount) {
        return aliveNeighbourCount == 3;
    }

    boolean isUnderPopulation(long aliveNeighbourCount) {
        return aliveNeighbourCount < 2;
    }

    boolean isOverPopulation(long aliveNeighbourCount) {
        return aliveNeighbourCount > 3;
    }

    Map<Cell, Boolean> nextGeneration() {
        Map<Cell, Boolean> nextGen = new HashMap<>();
        Map<Cell, Boolean> newCells = new HashMap<>();
        cellToStatusMap.forEach((c, s) -> {
            Map<Cell, Boolean> neighbors = getNeighbors(c);
            neighbors.forEach((k, v) -> {
                if (!cellToStatusMap.containsKey(k))
                    newCells.put(k, v);
                nextGen.put(c, fate(c));
            });
        });
        nextGen.putAll(newCells);
        return nextGen;
    }

    Map<Cell, Boolean> evolve() {
        cellToStatusMap = nextGeneration();
        return getCellToStatusMap();
    }
}
