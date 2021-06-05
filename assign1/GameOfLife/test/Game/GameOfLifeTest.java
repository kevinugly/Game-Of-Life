package Game;

import Game.preview.CellPosition;
import java.util.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameOfLifeTest {
  @Test
    void canary(){ assert(true); }

  @Test
    void deadCellBehaviors() {
        assertAll(
          () -> assertEquals(CellState.DEAD, GameOfLife.computeCellState(CellState.DEAD, 0)),
          () -> assertEquals(CellState.DEAD, GameOfLife.computeCellState(CellState.DEAD, 1)),
          () -> assertEquals(CellState.DEAD, GameOfLife.computeCellState(CellState.DEAD, 2)),
          () -> assertEquals(CellState.DEAD, GameOfLife.computeCellState(CellState.DEAD, 5)),
          () -> assertEquals(CellState.DEAD, GameOfLife.computeCellState(CellState.DEAD, 8)),
          () -> assertEquals(CellState.ALIVE, GameOfLife.computeCellState(CellState.DEAD, 3))
        );
    }

  @Test
    void liveCellBehaviors() {
        assertAll(
          () -> assertEquals(CellState.DEAD, GameOfLife.computeCellState(CellState.ALIVE, 1)),
          () -> assertEquals(CellState.DEAD, GameOfLife.computeCellState(CellState.ALIVE, 4)),
          () -> assertEquals(CellState.DEAD, GameOfLife.computeCellState(CellState.ALIVE, 8)),
          () -> assertEquals(CellState.ALIVE, GameOfLife.computeCellState(CellState.ALIVE, 2)),
          () -> assertEquals(CellState.ALIVE, GameOfLife.computeCellState(CellState.ALIVE, 3))
        );
    }

  @Test
    void cellSignalsOnePosition() {
        CellPosition p1 = new CellPosition(2, 3);
        List<CellPosition> e1 = new ArrayList<> (List.of(
          new CellPosition(1, 2),
          new CellPosition(1, 3),
          new CellPosition(1, 4),
          new CellPosition(2, 2),
          new CellPosition(2, 4),
          new CellPosition(3, 2),
          new CellPosition(3, 3),
          new CellPosition(3, 4))
        );

        CellPosition p2 = new CellPosition(3, 3);
        List<CellPosition> e2 = new ArrayList<> (List.of(
          new CellPosition(2, 2),
          new CellPosition(2, 3),
          new CellPosition(2, 4),
          new CellPosition(3, 2),
          new CellPosition(3, 4),
          new CellPosition(4, 2),
          new CellPosition(4, 3),
          new CellPosition(4, 4))
        );

        CellPosition p3 = new CellPosition(2, 4);
        List<CellPosition> e3 = new ArrayList<> (List.of(
          new CellPosition(1, 3),
          new CellPosition(1, 4),
          new CellPosition(1, 5),
          new CellPosition(2, 3),
          new CellPosition(2, 5),
          new CellPosition(3, 3),
          new CellPosition(3, 4),
          new CellPosition(3, 5))
        );

        CellPosition p4 = new CellPosition(0, 0);
        List<CellPosition> e4 = new ArrayList<> (List.of(
          new CellPosition(-1, -1),
          new CellPosition(-1, 0),
          new CellPosition(-1, 1),
          new CellPosition(0, -1),
          new CellPosition(0, 1),
          new CellPosition(1, -1),
          new CellPosition(1, 0),
          new CellPosition(1, 1))
        );

        assertAll(
          () -> assertEquals(e1, GameOfLife.generateSignalsForOnePosition(p1)),
          () -> assertEquals(e2, GameOfLife.generateSignalsForOnePosition(p2)),
          () -> assertEquals(e3, GameOfLife.generateSignalsForOnePosition(p3)),
          () -> assertEquals(e4, GameOfLife.generateSignalsForOnePosition(p4))
        );

    }

  @Test
    void cellSignalsMultiplePositions() {
       List <CellPosition> noPosition = new ArrayList<>();
       List <CellPosition> onePosition = new ArrayList<> (List.of(new CellPosition(2, 3)));
       List <CellPosition> twoPositions = new ArrayList<> (List.of(new CellPosition(2, 3), new CellPosition(3, 3)));
       List <CellPosition> threePositions = new ArrayList<> (List.of(new CellPosition(2, 3), new CellPosition(3, 3), new CellPosition(2, 4)));

       assertAll(
         () -> assertEquals(0, GameOfLife.generateSignalsForMultiplePositions(noPosition).size()),
         () -> assertEquals(8, GameOfLife.generateSignalsForMultiplePositions(onePosition).size()),
         () -> assertEquals(16, GameOfLife.generateSignalsForMultiplePositions(twoPositions).size()),
         () -> assertEquals(24, GameOfLife.generateSignalsForMultiplePositions(threePositions).size())
       );
    }

  @Test
    void Signals() {
        List <CellPosition> noPosition = new ArrayList<>();

        List <CellPosition> onePosition = new ArrayList<> (List.of(new CellPosition(2, 3)));
        Map<CellPosition, Integer> e2 = new HashMap<>();
        e2.put(new CellPosition(2,3), 1);

        List <CellPosition> twoPositions = new ArrayList<> (List.of(new CellPosition(2, 3), new CellPosition(2, 3)));
        Map<CellPosition, Integer> e3 = new HashMap<>();
        e3.put(new CellPosition(2,3), 2);

        List <CellPosition> threePositions = new ArrayList<> (List.of(new CellPosition(2, 3), new CellPosition(1, 2), new CellPosition(2, 3)));
        Map<CellPosition, Integer> e4 = new HashMap<>();
        e4.put(new CellPosition(1,2), 1);
        e4.put(new CellPosition(2,3), 2);

        assertAll(
          () -> assertEquals(Collections.emptyMap(), GameOfLife.count_signals(noPosition)),
          () -> assertEquals(e2, GameOfLife.count_signals(onePosition)),
          () -> assertEquals(e3, GameOfLife.count_signals(twoPositions)),
          () -> assertEquals(e4, GameOfLife.count_signals(threePositions))
        );
    }

  @Test
    void NextGeneration() {
        List <CellPosition> noPosition = new ArrayList<>();

        List <CellPosition> onePosition = new ArrayList<> (List.of(new CellPosition(2, 3)));

        List <CellPosition> twoPositions = new ArrayList<> (List.of(new CellPosition(2, 3), new CellPosition(2, 4)));

        List <CellPosition> threePositionsA = new ArrayList<> (List.of(new CellPosition(1, 1), new CellPosition(1, 2), new CellPosition(3, 0)));
        List <CellPosition> expectedThreePositionsA = new ArrayList<> (List.of(new CellPosition(2, 1)));


        List <CellPosition> threePositionsB = new ArrayList<> (List.of(new CellPosition(1, 1), new CellPosition(1, 2), new CellPosition(2, 2)));
        List <CellPosition> expectedThreePositionsB = new ArrayList<> (List.of(
          new CellPosition(1, 1),
          new CellPosition(1, 2),
          new CellPosition(2, 1),
          new CellPosition(2, 2))
        );

        List <CellPosition> block = new ArrayList<> (List.of(
          new CellPosition(0, 0),
          new CellPosition(0, 1),
          new CellPosition(1, 0),
          new CellPosition(1, 1))
        );

        List <CellPosition> beehive = new ArrayList<> (List.of(
          new CellPosition(0, 0),
          new CellPosition(1, -1),
          new CellPosition(1, 1),
          new CellPosition(2, -1),
          new CellPosition(2, 1),
          new CellPosition(3, 0))
        );

        List <CellPosition> horizontalBlinker = new ArrayList<> (List.of(new CellPosition(-1, 0), new CellPosition(0, 0), new CellPosition(1, 0)));
        List <CellPosition> verticalBlinker = new ArrayList<> (List.of(new CellPosition(0, -1), new CellPosition(0, 0), new CellPosition(0, 1)));

        List <CellPosition> glider = new ArrayList<> (List.of(
          new CellPosition(0, 0),
          new CellPosition(0, 2),
          new CellPosition(1, 0),
          new CellPosition(1, 1),
          new CellPosition(2, 1))
        );
        List <CellPosition> expectedGlider = new ArrayList<> (List.of(
          new CellPosition(0, 0),
          new CellPosition(1, 0),
          new CellPosition(1, 2),
          new CellPosition(2, 0),
          new CellPosition(2, 1))
        );

        assertAll(
          () -> assertEquals(Collections.emptyList(), GameOfLife.next_generation(noPosition)),
          () -> assertEquals(Collections.emptyList(), GameOfLife.next_generation(onePosition)),
          () -> assertEquals(Collections.emptyList(), GameOfLife.next_generation(twoPositions)),
          () -> assertEquals(expectedThreePositionsA, GameOfLife.next_generation(threePositionsA)),
          () -> assertEquals(expectedThreePositionsB, GameOfLife.next_generation(threePositionsB)),
          () -> assertEquals(block, GameOfLife.next_generation(block)),
          () -> assertEquals(beehive, GameOfLife.next_generation(beehive)),
          () -> assertEquals(verticalBlinker, GameOfLife.next_generation(horizontalBlinker)),
          () -> assertEquals(horizontalBlinker, GameOfLife.next_generation(verticalBlinker)),
          () -> assertEquals(expectedGlider, GameOfLife.next_generation(glider))
        );
    }

  @Test
    void getBounds() {
        List <CellPosition> noPosition = new ArrayList<>();

        List <CellPosition> onePosition = new ArrayList<> (List.of(new CellPosition(2, 3)));
        List <CellPosition> p2 = new ArrayList<> (List.of(new CellPosition(2, 3), new CellPosition(2,3)));


        List <CellPosition> twoPositions = new ArrayList<> (List.of(new CellPosition(2, 5), new CellPosition(2, 3)));
        List <CellPosition> p3 = new ArrayList<> (List.of(new CellPosition(2, 3), new CellPosition(2, 5)));

        List <CellPosition> threePositions = new ArrayList<> (List.of(new CellPosition(2, 3), new CellPosition(3, 3), new CellPosition(1, 2)));
        List <CellPosition> p4 = new ArrayList<> (List.of(new CellPosition(1, 2), new CellPosition(3,3)));

        List <CellPosition> fourPositions = new ArrayList<> (List.of(
          new CellPosition(2, 3),
          new CellPosition(1, 2),
          new CellPosition(3, 3),
          new CellPosition(1, 3))
        );
        List <CellPosition> p5 = new ArrayList<> (List.of(new CellPosition(1, 2), new CellPosition(3,3)));

        assertAll(
          () -> assertEquals(noPosition, GameOfLife.getBounds(noPosition)),
          () -> assertEquals(p2, GameOfLife.getBounds(onePosition)),
          () -> assertEquals(p3, GameOfLife.getBounds(twoPositions)),
          () -> assertEquals(p4, GameOfLife.getBounds(threePositions)),
          () -> assertEquals(p5, GameOfLife.getBounds(fourPositions))
        );
    }
}