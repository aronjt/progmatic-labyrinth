package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.CellType;
import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.exceptions.CellException;
import com.progmatic.labyrinthproject.exceptions.InvalidMoveException;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author pappgergely
 */
public class LabyrinthImpl implements Labyrinth {

    private Labyrinth l;
    private Coordinate player;
    private CellType[][] lab;
    private boolean isFinish;


    public LabyrinthImpl() {
        lab = new CellType[1][1];
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
        return lab[c.getRow()][c.getCol()];
    }

    @Override
    public void setSize(int width, int height) {
        l = new LabyrinthImpl();
        lab = new CellType[height][width];
    }

    @Override
    public void setCellType(Coordinate c, CellType type) throws CellException {
        lab[c.getRow()][c.getCol()] = type;
    }

    @Override
    public Coordinate getPlayerPosition() {
        return player;
    }

    @Override
    public boolean hasPlayerFinished() {
        return isFinish;
    }

    @Override
    public List<Direction> possibleMoves() {
        return null;
    }

    @Override
    public void movePlayer(Direction direction) throws InvalidMoveException {

    }

}
