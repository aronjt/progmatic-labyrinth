package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import com.progmatic.labyrinthproject.interfaces.Player;

public class RandomPlayer implements Player {

    @Override
    public Direction nextMove(Labyrinth l) {
        int random = (int)(Math.random()*l.possibleMoves().size());
        return l.possibleMoves().get(random);
    }
}
