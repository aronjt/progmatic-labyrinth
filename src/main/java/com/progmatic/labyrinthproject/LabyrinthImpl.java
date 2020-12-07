package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.CellType;
import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.exceptions.CellException;
import com.progmatic.labyrinthproject.exceptions.InvalidMoveException;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author pappgergely
 */
public class LabyrinthImpl implements Labyrinth {

    private Coordinate player;
    private CellType[][] lab;



    public LabyrinthImpl() {
        lab = new CellType[1][1];
        player = null;
    }

    @Override
    public int getWidth() {
        if (lab[0].length == 1) {
            return -1;
        }
        return lab[0].length;
    }

    @Override
    public int getHeight() {
        if (lab.length == 1) {
            return -1;
        }
        return lab.length;
    }

    @Override
    public void loadLabyrinthFile(String fileName) {
        try {
            Scanner sc = new Scanner(new File(fileName));
            int width = Integer.parseInt(sc.nextLine());
            int height = Integer.parseInt(sc.nextLine());
            lab = new CellType[height][width];

            for (int hh = 0; hh < height; hh++) {
                String line = sc.nextLine();
                for (int ww = 0; ww < width; ww++) {
                    CellType type;
                    switch (line.charAt(ww)) {
                        case 'W':
                            type = CellType.WALL;
                            break;
                        case 'E':
                            type = CellType.END;
                            break;
                        case 'S':
                            type = CellType.START;
                            player = new Coordinate(ww, hh);
                            break;
                        default:
                            type = CellType.EMPTY;
                            break;
                    }
                    setCellType(new Coordinate(ww, hh), type);
                }
            }
        } catch (FileNotFoundException | NumberFormatException | CellException ex) {
            System.out.println(ex.toString());
        }
    }

    @Override
    public CellType getCellType(Coordinate c) throws CellException {
        if (c.getCol() < 0 || c.getRow() < 0) {
            throw new CellException(c.getRow(), c.getCol(), "Hibás koordináta");
        }
        if (c.getRow() > lab.length - 1 || c.getCol() > lab[0].length - 1) {
            throw new CellException(c, "Túl nagy koordináta");
        }
        return lab[c.getRow()][c.getCol()];
    }

    @Override
    public void setSize(int width, int height) {
        lab = new CellType[height][width];
    }

    @Override
    public void setCellType(Coordinate c, CellType type) throws CellException {
        if (c.getCol() < 0 || c.getRow() < 0) {
            throw new CellException(c.getRow(), c.getCol(), "Hibás koordináta");
        }
        if (c.getRow() > lab.length - 1 || c.getCol() > lab[0].length - 1) {
            throw new CellException(c, "Túl nagy koordináta");
        }
        if (type.equals(CellType.START)) {
            player = new Coordinate(c.getCol(), c.getRow());
        }
        lab[c.getRow()][c.getCol()] = type;
    }

    @Override
    public Coordinate getPlayerPosition() {
        return player;
    }

    @Override
    public boolean hasPlayerFinished() {
        return lab[player.getRow()][player.getCol()] == CellType.END;
    }

    @Override
    public List<Direction> possibleMoves() {
        List<Direction> directions = new ArrayList<>();
        int hh = player.getRow();
        int ww = player.getCol();
        if (hh > 0 && lab[hh-1][ww] == CellType.EMPTY) {
            directions.add(Direction.NORTH);
        }
        if (hh < lab.length - 1 && lab[hh+1][ww] == CellType.EMPTY) {
            directions.add(Direction.SOUTH);
        }
        if (ww < lab[hh].length - 1 && lab[hh][ww+1] == CellType.EMPTY) {
            directions.add(Direction.EAST);
        }
        if (ww > 0 && lab[hh][ww-1] == CellType.EMPTY) {
            directions.add(Direction.WEST);
        }
        return directions;
    }

    @Override
    public void movePlayer(Direction direction) throws InvalidMoveException {
        if (possibleMoves().contains(direction)) {
            switch (direction) {
                case NORTH:
                    player = new Coordinate(player.getCol(), player.getRow() - 1);
                    break;
                case SOUTH:
                    player = new Coordinate(player.getCol(), player.getRow() + 1);
                    break;
                case EAST:
                    player = new Coordinate(player.getCol() + 1, player.getRow());
                    break;
                case WEST:
                    player = new Coordinate(player.getCol() - 1, player.getRow());
                    break;
            }
        } else {
            throw new InvalidMoveException();
        }
    }
}
