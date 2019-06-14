package com.github.squidat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by romero on 26/08/17.
 *
 * Nota: No existen relaciones de vecindad porque para este ejemplo no se necesitan. Solo necesitamos mostrar las acciones.
 */

public class Board extends Actor {

    public Board up;
    public Board down;
    public Board upLeft;
    public Board upRight;
    public Board downLeft;
    public Board downRight;
    public Casilla casilla;
    public int valor;
    private boolean Visitado= false;

    public Board(float addToX, float addToY){


        this.casilla = new Casilla(6+addToX,6+addToY,0,1.25f,false, false);

    }

    public boolean existeNull(){

        boolean aux=false;
        if (this.up == null) aux=true;
        if (this.down == null) aux=true;
        if (this.upRight == null) aux=true;
        if (this.upLeft == null) aux=true;
        if (this.downLeft == null) aux=true;
        if (this.downRight == null) aux=true;

        return aux;
    }

    public void anillo1(){

        this.up = new Board(0.0f,1.0f);
        this.up.down = this;

        this.down = new Board(0.0f,-1.0f);
        this.down.up = this;

        this.upLeft = new Board(-0.75f,0.5f);
        this.upLeft.downRight = this;

        this.upRight = new Board(0.75f,0.5f);
        this.upRight.downLeft = this;

        this.downLeft = new Board(-0.75f,-0.5f);
        this.downLeft.upRight = this;

        this.downRight =new Board(0.75f,-0.5f);
        this.downRight.upLeft = this;

        // para este punto todos los nodos del anillo 1 estan conectados con el nodo base, pero no estan conectados
        // entre ellos mismos en el anillo 1

        this.up.downRight = this.upRight;
        this.upRight.upLeft =  this.up;

        this.up.downLeft = this.upLeft;
        this.upLeft.upRight = this.up;

        this.down.upRight = this.downRight;
        this.downRight.downLeft = this.down;

        this.downLeft.downRight = this.down;
        this.down.upLeft=this.downLeft;

        this.upLeft.down = this.downLeft;
        this.downLeft.up= this.upLeft;

        this.upRight.down =this.downRight;
        this.downRight.up = this.upRight;

        // para este punto ya estan establecidas todas las vecindades entre los puntos del anillo 1 y el nodo central
    }

    public void anillo2(){

        this.up.up = new Board(0,2.0f);
        this.up.up.down = this.up;

        this.down.down = new Board(0,-2.0f);
        this.down.down.up = this.down;

        this.upRight.up = new Board(0.85f,1.6f);
        this.upRight.up.down = this.upRight;

        this.upLeft.up = new Board(-0.85f,1.6f);
        this.upLeft.up.down = this.upLeft;

        this.downRight.down = new Board(0.85f,-1.6f);
        this.downRight.down.up = this.downRight;

        this.downLeft.down = new Board(-0.85f,-1.6f);
        this.downLeft.down.up = this.downLeft;

        this.upRight.upRight = new Board(1.5f,1.0f);
        this.upRight.upRight.downLeft = this.upRight;

        this.upRight.downRight = new Board(1.75f,0.0f);
        this.upRight.downRight.upLeft = this.upRight;

        this.downRight.downRight = new Board(1.5f,-1.0f);
        this.downRight.downRight.upLeft =this.downRight;

        this.upLeft.upLeft = new Board(-1.5f,1.0f);
        this.upLeft.upLeft.downRight = this.upLeft;

        this.upLeft.downLeft = new Board(-1.75f,0);
        this.upLeft.downLeft.upRight = this.upLeft;

        this.downLeft.downLeft = new Board(-1.5f,-1.0f);
        this.downLeft.downLeft.upRight = this.downLeft;

        //para este punto, ya esta creado el grafo con la misma cantidad de casillas que en laps, pero el anillo 2
        // no esta unido entre si ni todas las uniones del anillo 1 con el anillo 2

        this.up.upLeft = this.upLeft.up;
        this.upLeft.up.downRight = this.up;

        this.up.upRight = this.upRight.up;
        this.upRight.up.downLeft = this.up;

        this.downLeft.upLeft = this.upLeft.downLeft;
        this.upLeft.downLeft.downRight = this.downLeft;

        this.downRight.upRight = this.upRight.downRight;
        this.upRight.downRight.downLeft = this.downRight;

        this.down.downLeft = this.downLeft.down;
        this.downLeft.down.upRight = this.down;

        this.down.downRight=this.downRight.down;
        this.downRight.down.upLeft = this.down;

        // en este punto todas las vecindades estan hechas, a excepcion del anillo 2

        this.up.up.downRight = this.up.upRight;
        this.up.upRight.upLeft = this.up.up;

        this.up.up.downLeft = this.up.upLeft;
        this.up.upLeft.upRight = this.up.up;

        this.upRight.upRight.upLeft = this.upRight.up;
        this.upRight.up.downRight = this.upRight.upRight;

        this.upLeft.upLeft.upRight = this.upLeft.up;
        this.upLeft.up.downLeft = this.upLeft.upLeft;

        this.upLeft.upLeft.down = this.upLeft.downLeft;
        this.upLeft.downLeft.up = this.upLeft.upLeft;

        this.upRight.upRight.down = this.upRight.downRight;
        this.upRight.downRight.up = this.upRight.upRight;

        this.downLeft.downLeft.up = this.downLeft.upLeft;
        this.downLeft.upLeft.down = this.downLeft.downLeft;

        this.downRight.upRight.down = this. downRight.downRight;
        this.downRight.downRight.up = this.downRight.upRight;

        this.down.downLeft.upLeft = this.downLeft.downLeft;
        this.downLeft.downLeft.downRight =  this.downLeft.down;

        this.downRight.downRight.downLeft = this.downRight.down;
        this.downRight.down.upRight = this.downRight.downRight;

        this.down.down.upLeft = this.down.downLeft;
        this.down.downLeft.downRight = this.down.down;

        this.down.down.upRight = this.down.downRight;
        this.down.downRight.downLeft = this.down.down;

        // aqui el anillo 2 esta unido entre si y con el anillo 1

        this.up.valor = 1;

        this.upRight.valor = 2;
        this.downRight.valor = 3;
        this.down.valor = 4;
        this.downLeft.valor = 5;
        this.upLeft.valor = 6;


        this.up.up.valor = 7;
        this.up.upRight.valor = 8;
        this.upRight.upRight.valor = 9;
        this.upRight.downRight.valor = 10;
        this.downRight.downRight.valor = 11;
        this.down.downRight.valor = 12;
        this.down.down.valor = 13;
        this.down.downLeft.valor = 14;
        this.downLeft.downLeft.valor = 15;
        this.downLeft.upLeft.valor = 16;
        this.upLeft.upLeft.valor = 17;
        this.up.upLeft.valor = 18;
    }

    public void addToStage(Stage stage){
        stage.addActor(this);
        stage.addActor(this.casilla);

    }

    public void AddBoard(Stage stage ){
        this.addToStage(stage);
        //anillo 1
        up.addToStage(stage);
        down.addToStage(stage);
        downLeft.addToStage(stage);
        upRight.addToStage(stage);
        downRight.addToStage(stage);
        upLeft.addToStage(stage);
        //anillo 2
        up.up.addToStage(stage);
        down.down.addToStage(stage);
        upRight.up.addToStage(stage);
        upLeft.up.addToStage(stage);
        downRight.down.addToStage(stage);
        downLeft.down.addToStage(stage);
        upRight.upRight.addToStage(stage);
        upRight.downRight.addToStage(stage);
        downRight.downRight.addToStage(stage);
        upLeft.upLeft.addToStage(stage);
        upLeft.downLeft.addToStage(stage);
        downLeft.downLeft.addToStage(stage);

    }

    public void InitialBoardRandomize(){

        Random randomizer = new Random();
        ArrayList<Integer> randomList = new ArrayList<Integer>();
        randomList.add(1);
        randomList.add(2);
        randomList.add(4);

        this.casilla.SetValue(randomList.get(randomizer.nextInt(3)));
        this.casilla.UpdateTexture();
        this.up.casilla.SetValue(randomList.get(randomizer.nextInt(3)));
        this.up.casilla.UpdateTexture();
        this.upLeft.casilla.SetValue(randomList.get(randomizer.nextInt(3)));
        this.upLeft.casilla.UpdateTexture();
        this.upRight.casilla.SetValue(randomList.get(randomizer.nextInt(3)));
        this.upRight.casilla.UpdateTexture();
        this.downLeft.casilla.SetValue(randomList.get(randomizer.nextInt(3)));
        this.downLeft.casilla.UpdateTexture();
        this.downRight.casilla.SetValue(randomList.get(randomizer.nextInt(3)));
        this.downRight.casilla.UpdateTexture();
        this.down.casilla.SetValue(randomList.get(randomizer.nextInt(3)));
        this.down.casilla.UpdateTexture();

    }

    public Board GetBoardByCasilla(Casilla c){

        if (this.casilla==c) return this;
        if (this.up.casilla==c) return this.up;
        if (this.upRight.casilla==c) return this.upRight;
        if (this.upLeft.casilla==c) return this.upLeft;
        if (this.downRight.casilla==c) return this.downRight;
        if (this.downLeft.casilla==c) return this.downLeft;
        if (this.down.casilla==c) return this.down;

        if (this.up.up.casilla==c) return this.up.up;
        if (this.up.upLeft.casilla==c) return this.up.upLeft;
        if (this.up.upRight.casilla==c) return this.up.upRight;
        if (this.up.downLeft.casilla==c) return this.up.downLeft;
        if (this.up.downRight.casilla==c) return this.up.downRight;

        if (this.upLeft.downLeft.casilla==c) return this.upLeft.downLeft;
        if (this.upRight.downRight.casilla==c) return this.upRight.downRight;
        if (this.downLeft.downLeft.casilla==c) return this.downLeft.downLeft;
        if (this.downRight.downRight.casilla==c) return this.downRight.downRight;
        if (this.down.downLeft.casilla==c) return this.down.downRight;
        if (this.down.down.casilla==c) return this.down.down;

        return null;
    }

    public void TryMerge(){ //este board no es el central, tratara de conseguir 3 valores juntos
        int valor= this.casilla.GetValue();



        //System.out.println("count mergers " + this.CountMergers(valor));
        if (this.CountMergers(valor)>=3) {

            GameStage auxStage = (GameStage) this.getStage();
            Board auxboard = auxStage.getBoard();
            auxboard.inicializarBanderas();

            Merge(this.casilla.GetValue());

            ArrayList<Integer> ListaDeFichas = new ArrayList<Integer>();
            ListaDeFichas.add(1);
            ListaDeFichas.add(2);
            ListaDeFichas.add(4);
            ListaDeFichas.add(8);
            ListaDeFichas.add(16);
            ListaDeFichas.add(32);
            ListaDeFichas.add(64);

            int contValue=0;

            for(int i = 0; i < ListaDeFichas.size(); i++){
                if (valor == ListaDeFichas.get(i))
                    contValue = i;
            }

            contValue = ListaDeFichas.get(contValue+1);

           /* contValue=this.casilla.GetValue();
            contValue= contValue *2;*/
            this.casilla.SetValue(contValue);
            this.casilla.UpdateTexture();
        }
    }

    public int CountMergers(int value) {
        int cont = 0;
        if (this!= null) {
            if (value == this.casilla.GetValue()) {
                //.out.println("value: " + value);
                //System.out.println("this value: " + this.casilla.GetValue());
                cont++;
                this.Visitado = true;

                if ((this.up != null) && (this.up.Visitado == false))
                    cont += this.up.CountMergers(value);

                if ((this.upLeft != null) && (this.upLeft.Visitado == false))
                    cont += this.upLeft.CountMergers(value);

                if ((this.upRight != null) && (this.upRight.Visitado == false))
                    cont += this.upRight.CountMergers(value);

                if ((this.downLeft != null) && (this.downLeft.Visitado == false))
                    cont += this.downLeft.CountMergers(value);

                if ((this.downRight != null) && (this.downRight.Visitado == false))
                    cont += this.downRight.CountMergers(value);

                if ((this.down != null) && (this.down.Visitado == false))
                    cont += this.down.CountMergers(value);

            }
        }
        System.out.println("cont: " + cont);
        return cont;
    }

    public void Merge(int value){

        if (this.casilla.GetValue()== value){
            this.Visitado = true;
            // hay que volver a poner visitado para que el merge no crashee, hay un ciclo infinito
            this.casilla.SetValue(0);
            this.casilla.UpdateTexture();

            if ((this.up!=null)&&(this.up.Visitado==false))
                this.up.Merge(value);
            if ((this.down!=null)&&(this.down.Visitado==false))
                this.down.Merge(value);
            if ((this.upLeft!=null)&&(this.upLeft.Visitado==false))
                this.upLeft.Merge(value);
            if ((this.upRight!=null)&&(this.upRight.Visitado==false))
                this.upRight.Merge(value);
            if ((this.downLeft!=null)&&(this.downLeft.Visitado==false))
                this.downLeft.Merge(value);
            if ((this.downRight!=null)&&(this.downRight.Visitado==false))
                this.downRight.Merge(value);


        }

    }

    public void  inicializarBanderas (){// recibe la casilla central

        this.Visitado = false;

        this.up.Visitado = false;
        this.upRight.Visitado = false;
        this.downRight.Visitado = false;
        this.down.Visitado = false;
        this.downLeft.Visitado = false;
        this.upLeft.Visitado = false;

        this.up.up.Visitado = false;
        this.up.upRight.Visitado = false;
        this.upRight.upRight.Visitado = false;
        this.upRight.downRight.Visitado = false;
        this.downRight.downRight.Visitado = false;
        this.down.downRight.Visitado = false;
        this.down.down.Visitado = false;
        this.down.downLeft.Visitado = false;
        this.downLeft.downLeft.Visitado = false;
        this.downLeft.upLeft.Visitado = false;
        this.upLeft.upLeft.Visitado = false;
        this.up.upLeft.Visitado = false;


    }


}


