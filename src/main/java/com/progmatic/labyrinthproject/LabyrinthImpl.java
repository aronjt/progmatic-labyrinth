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
    private Coordinate heightWight;
    private CellType[][] lab;



    public LabyrinthImpl() {
        
    }

    @Override
    public int getWidth() {
        if (l == null) {
            return -1;
        }
        return heightWight.getCol();
    }

    @Override
    public int getHeight() {
        if (l == null) {
            return -1;
        }
        return heightWight.getRow();
    }

    @Override
    public void loadLabyrinthFile(String fileName) {
        try {
            Scanner sc = new Scanner(new File(fileName));
            int width = Integer.parseInt(sc.nextLine());
            int height = Integer.parseInt(sc.nextLine());
            this.heightWight = new Coordinate(width, height);
            lab = new CellType[height][width];
            l.setSize(width, height);

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
                            break;
                        default:
                            type = CellType.EMPTY;
                            break;
                    }
                    l.setCellType(new Coordinate(ww, hh), type);
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
        lab = new CellType[height][width];
    }

    @Override
    public void setCellType(Coordinate c, CellType type) throws CellException {
        lab[c.getRow()][c.getCol()] = type;
    }

    @Override
    public Coordinate getPlayerPosition() {
        return null;
    }

    @Override
    public boolean hasPlayerFinished() {
        return false;
    }

    @Override
    public List<Direction> possibleMoves() {
        return null;
    }

    @Override
    public void movePlayer(Direction direction) throws InvalidMoveException {

    }

}
