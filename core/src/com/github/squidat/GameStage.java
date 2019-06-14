package com.github.squidat;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

/**
 * Created by juan on 27/08/17.
 */

public class GameStage extends Stage {
    Board board;
    Casilla toThrow;
    Casilla next;
    Letras letras;

    public GameStage(Viewport viewport, int board_dimension) {

        super(viewport);
        board = new Board(0,0); //anillo 0 o ficha central
        board.addToStage(this);
        board.anillo1(); // crea el anillo 1 que rodea al central agregando indivualmente todos los nodos al stage
        board.anillo2(); // crea el 2do anillo y los agrega al stage
        board.InitialBoardRandomize();
        board.AddBoard(this);
        toThrow = new Casilla(0,0,2,1.25f, true,true);
        this.addActor(toThrow);
        toThrow.addarray();
        toThrow.iniciarcont();
        next = new Casilla(0.5f,11,2,1.25f,false,true);
        this.addActor(next);

        letras = new Letras();
        this.addActor(letras);
        letras.llenar();


    }

    public Casilla getToThrow() {
        return toThrow;
    }
    public Casilla getnextable() {
        return next;
    }
    public Board getBoard(){ return board;}
}
