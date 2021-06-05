package Game;

import Game.preview.CellPosition;
import java.util.*;
import java.util.stream.Collectors;

public interface GameOfLife{
  int TWO_LIVE_NEIGHBORS = 2;
  int THREE_LIVE_NEIGHBORS = 3;

    static CellState computeCellState(CellState currentState, int numberOfLiveNeighbors) {
        return numberOfLiveNeighbors == THREE_LIVE_NEIGHBORS || numberOfLiveNeighbors == TWO_LIVE_NEIGHBORS && currentState.equals(CellState.ALIVE) ? CellState.ALIVE : CellState.DEAD;
    }

    static List<CellPosition> generateSignalsForOnePosition(CellPosition position) {
        List<CellPosition> neighborPositions = new ArrayList<>();

        neighborPositions.add(new CellPosition(position.x() - 1, position.y() - 1));
        neighborPositions.add(new CellPosition(position.x() - 1, position.y()));
        neighborPositions.add(new CellPosition(position.x() - 1, position.y() + 1));
        neighborPositions.add(new CellPosition(position.x(), position.y() - 1));
        neighborPositions.add(new CellPosition(position.x(), position.y() + 1));
        neighborPositions.add(new CellPosition(position.x() + 1, position.y() - 1));
        neighborPositions.add(new CellPosition(position.x() + 1, position.y()));
        neighborPositions.add(new CellPosition(position.x() + 1, position.y() + 1));

        return neighborPositions;
    }

    static List<CellPosition> generateSignalsForMultiplePositions(List <CellPosition> positions) {
        return positions.stream()
          .flatMap(position -> generateSignalsForOnePosition(position).stream())
          .collect(Collectors.toList());
    }

    static Map<CellPosition, Integer> count_signals(List <CellPosition> positions) {
        Map<CellPosition, Integer> positionOfCells = new HashMap<>();

        for(CellPosition position : positions){
            Integer value = positionOfCells.get(position);
            if(value == null){
                value = 1;
            }
            else{
                value += 1;
            }
            positionOfCells.put(position, value);
        }

        return positionOfCells;
    }

    static List<CellPosition> next_generation(List <CellPosition> positions) {
        Map<CellPosition, Integer> neighborCount = count_signals(generateSignalsForMultiplePositions(positions));
        List<CellPosition> nextGeneration = new ArrayList<>();

        for(CellPosition entry : neighborCount.keySet()) {
            if(positions.contains(entry)){
                if(neighborCount.get(entry) == TWO_LIVE_NEIGHBORS || neighborCount.get(entry) == THREE_LIVE_NEIGHBORS){
                    nextGeneration.add(entry);
                }
            }
            else{
                if(neighborCount.get(entry) == THREE_LIVE_NEIGHBORS){
                    nextGeneration.add(entry);
                }
            }
        }
        nextGeneration.sort(Comparator.comparingInt(CellPosition::x).thenComparingInt(CellPosition::y));

        return nextGeneration;
      }

    static List<CellPosition> getBounds(List <CellPosition> positions) {
        List<CellPosition> returnPositions = new ArrayList<>();

        if(positions.size() == 0) {
            return returnPositions;
        }

        positions.sort(Comparator.comparingInt(CellPosition::x).thenComparingInt(CellPosition::y));

        CellPosition topLeft = positions.get(0);
        CellPosition bottomRight = positions.get(positions.size() - 1);

        if(positions.size() == 1) {
            returnPositions.add(topLeft);
            returnPositions.add(topLeft);
        }
        else{
            returnPositions.add(topLeft);
            returnPositions.add(bottomRight);
        }

        return returnPositions;
    }
}