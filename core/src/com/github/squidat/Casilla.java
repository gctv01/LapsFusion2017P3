package com.github.squidat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by juan on 26/08/17.
 * TODO: Cambiar textura atlas a solo una textura y el numero lo diibujamos con un font
 */

public /*abstract*/ class Casilla extends Actor {
    private final String TAG = getClass().getSimpleName();
    private TextureRegion graphic;
    private int value;
    private boolean throwable;
    public boolean nextable;
    private Random random;
    private float yincremento;
    private float xincremento;
    private float angulo;
    public int randomizer[];
    public int contrandomizer;
    public ArrayList<Board> vecino;
    public Casilla(float x, float y, int value, float lado, boolean throwable, boolean nextable) {
        random = new Random();
        this.throwable = throwable;
        this.nextable= nextable;
        this.value = value;
        graphic = NumerMerge.textureAtlas.findRegion(String.valueOf(value));
        this.setBounds(x,y,lado,lado);



        if (value == 0) {
            this.addListener(new ClickListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    GameStage stage = (GameStage) Casilla.this.getStage();
                    Casilla beingThrown = stage.getToThrow();
                    beingThrown.disparar(Casilla.this);
                    //Perdemos el focus intencionalmente para no permitir cambio de direcciones
                    Gdx.input.setInputProcessor(new InputAdapter());
                    return true;
                }
            });

        }
    }


    public Casilla GetCasillaCercana( ArrayList<Board> anillo2){
        Float Xthrow, Ythrow, Xaux, Yaux, distancia, distAux;
        Casilla aux;

        aux= anillo2.get(0).casilla;

        Xthrow = this.getX();
        Ythrow = this.getY();
        Xaux = aux.getX();
        Yaux = aux.getY();

        distancia = ((Xthrow - Xaux)* (Xthrow - Xaux)) + ((Ythrow-Yaux)*(Ythrow - Yaux));

        distancia = (float) Math.sqrt((float)distancia);


        for (int i =0; i<anillo2.size();i++){
            Xaux= anillo2.get(i).casilla.getX();
            Yaux = anillo2.get(i).casilla.getY();

            distAux = ((Xthrow - Xaux)* (Xthrow - Xaux)) + ((Ythrow-Yaux)*(Ythrow - Yaux));
            distAux = (float) Math.sqrt((float)distancia);

            if (distAux < distancia){
                distancia= distAux;
                aux = anillo2.get(i).casilla;
            }

        }

        return aux;
    }

    public Casilla GetCasilla(){
        return this;
    }

    public int GetValue(){
        return this.value;
    }

    public void SetValue(int set){
        this.value = set;
    }

    public void UpdateTexture(){
        graphic = NumerMerge.textureAtlas.findRegion(String.valueOf(value));
    }

    public Vector2 getPosition() {
        return new Vector2(getX(),getY());
    }
    @Override

    public void draw(Batch batch, float parentAlpha) {
        //movimiento vertical
        if (this.throwable == true) {
            yincremento += (30.0f * Gdx.graphics.getDeltaTime());

            if (Gdx.input.justTouched()){


                int o=0;
                boolean cambio =false;
                float Xpos = this.getX();
                float Ypos = this.getY();


                if ((Xpos >=9) && (Xpos < 10) && (Ypos >= 5) && (Ypos< 7)){o=9; cambio = true;}
                if ((Xpos >=1) && (Xpos < 2.4) && (Ypos >= 5) && (Ypos< 7)){o=3; cambio = true;}
                if ((Xpos >=5) && (Xpos < 7) && (Ypos >= 1) && (Ypos< 2.4)){o=6; cambio = true;}
                if ((Xpos >=5) && (Xpos < 7) && (Ypos >= 9.3) && (Ypos< 10)){o=0; cambio = true;}

                if ((Xpos >=3.4) && (Xpos < 5) && (Ypos >= 8.6) && (Ypos< 9.6)){o=1; cambio = true;}
                if ((Xpos >=2.4) && (Xpos < 3.4) && (Ypos >= 7) && (Ypos< 8.6)){o=2; cambio = true;}
                if ((Xpos >=2.4) && (Xpos < 3.4) && (Ypos >= 3.4) && (Ypos< 5)){o=4; cambio = true;}
                if ((Xpos >=3.4) && (Xpos < 5) && (Ypos >= 2.4) && (Ypos< 4)){o=5; cambio = true;}

                if ((Xpos >=7) && (Xpos < 9) && (Ypos >= 2.4) && (Ypos< 4)){o=7; cambio = true;}
                if ((Xpos >=8) && (Xpos < 9.4) && (Ypos >= 3.4) && (Ypos< 5)){o=8; cambio = true;}
                if ((Xpos >=8) && (Xpos < 9.4) && (Ypos >= 7) && (Ypos< 8.6)){o=10; cambio = true;}
                if ((Xpos >=7) && (Xpos < 9) && (Ypos >= 8.6) && (Ypos< 9.6)){o=11; cambio = true;}



                //System.out.println("upleft upleft" +this.vecino.get(o).casilla.value);


                if ((cambio==true) && (this.vecino.get(o).casilla.value == 0 )){
                    //System.out.println("entra");
                    this.vecino.get(o).casilla.SetValue(this.value);
                    this.vecino.get(o).casilla.UpdateTexture();


                    /// codigo nuevo
                    GameStage stage = (GameStage) this.getStage();
                    Casilla auxc = stage.getToThrow();
                    Casilla auxc2 = stage.getnextable();
                    auxc.SetValue(auxc2.GetValue());
                    auxc.UpdateTexture();
                    Random randomizer = new Random();
                    int alo = randomizer.nextInt(auxc.contrandomizer+1);
                    auxc2.SetValue(auxc.randomizer[alo]);
                    auxc2.UpdateTexture();

                    Board AuxBoard= stage.getBoard();
                    AuxBoard.inicializarBanderas();
                    AuxBoard = AuxBoard.GetBoardByCasilla(this.vecino.get(o).casilla);
                    //System.out.println("numero de board: " + AuxBoard.valor);
                    int contMergers =0;
                    if ((AuxBoard!=null)&& (AuxBoard.casilla != null)){
                        AuxBoard.TryMerge();
                        this.vecino.get(o).casilla.UpdateTexture(); // aqui ya muestra la casilla cuando se mergeo

                        Board centrador = this.vecino.get(o);
                        Board centradorAux;


                        if (o==9){
                            if(centrador.upLeft.casilla.GetValue() == 0) {
                                centrador.upLeft.casilla.SetValue(centrador.casilla.GetValue());
                                centrador.casilla.SetValue(0);
                                centrador.casilla.UpdateTexture();
                                centrador.upLeft.casilla.UpdateTexture();
                                centrador = centrador.upLeft;

                                if (centrador.downLeft.casilla.value == 0){
                                    centrador.downLeft.casilla.SetValue(centrador.casilla.GetValue());
                                    centrador.casilla.SetValue(0);
                                    centrador.casilla.UpdateTexture();
                                    centrador.downLeft.casilla.UpdateTexture();
                                }
                            }
                            else if(centrador.downLeft.casilla.GetValue() == 0) {
                                centrador.downLeft.casilla.SetValue(centrador.casilla.GetValue());
                                centrador.casilla.SetValue(0);
                                centrador.casilla.UpdateTexture();
                                centrador.downLeft.casilla.UpdateTexture();
                                centrador = centrador.downLeft;

                                if (centrador.upLeft.casilla.value == 0){
                                    centrador.upLeft.casilla.SetValue(centrador.casilla.GetValue());
                                    centrador.casilla.SetValue(0);
                                    centrador.casilla.UpdateTexture();
                                    centrador.upLeft.casilla.UpdateTexture();
                                }
                            }
                        }

                        if (o==1){
                            if(centrador.downRight.casilla.GetValue() == 0) {
                                centrador.downRight.casilla.SetValue(centrador.casilla.GetValue());
                                centrador.casilla.SetValue(0);
                                centrador.casilla.UpdateTexture();
                                centrador.downRight.casilla.UpdateTexture();
                                centrador = centrador.downRight;

                                if (centrador.down.casilla.value == 0){
                                    centrador.down.casilla.SetValue(centrador.casilla.GetValue());
                                    centrador.casilla.SetValue(0);
                                    centrador.casilla.UpdateTexture();
                                    centrador.down.casilla.UpdateTexture();
                                }
                            }
                            else if(centrador.down.casilla.GetValue() == 0) {
                                centrador.down.casilla.SetValue(centrador.casilla.GetValue());
                                centrador.casilla.SetValue(0);
                                centrador.casilla.UpdateTexture();
                                centrador.down.casilla.UpdateTexture();
                                centrador = centrador.down;

                                if (centrador.downRight.casilla.value == 0){
                                    centrador.downRight.casilla.SetValue(centrador.casilla.GetValue());
                                    centrador.casilla.SetValue(0);
                                    centrador.casilla.UpdateTexture();
                                    centrador.downRight.casilla.UpdateTexture();
                                }
                            }
                        }

                        if (o==2){
                            if(centrador.downRight.casilla.GetValue() == 0) {
                                centrador.downRight.casilla.SetValue(centrador.casilla.GetValue());
                                centrador.casilla.SetValue(0);
                                centrador.casilla.UpdateTexture();
                                centrador.downRight.casilla.UpdateTexture();
                                centrador = centrador.downRight;

                                if (centrador.downRight.casilla.value == 0){
                                    centrador.downRight.casilla.SetValue(centrador.casilla.GetValue());
                                    centrador.casilla.SetValue(0);
                                    centrador.casilla.UpdateTexture();
                                    centrador.downRight.casilla.UpdateTexture();
                                }
                            }

                        }

                        if (o==3){

                            if(centrador.upRight.casilla.GetValue() == 0) {
                                centrador.upRight.casilla.SetValue(centrador.casilla.GetValue());
                                centrador.casilla.SetValue(0);
                                centrador.casilla.UpdateTexture();
                                centrador.upRight.casilla.UpdateTexture();
                                centrador = centrador.upRight;

                                if (centrador.downRight.casilla.value == 0){
                                    centrador.downRight.casilla.SetValue(centrador.casilla.GetValue());
                                    centrador.casilla.SetValue(0);
                                    centrador.casilla.UpdateTexture();
                                    centrador.downRight.casilla.UpdateTexture();
                                }
                            }
                            else if(centrador.downRight.casilla.GetValue() == 0) {
                                centrador.downRight.casilla.SetValue(centrador.casilla.GetValue());
                                centrador.casilla.SetValue(0);
                                centrador.casilla.UpdateTexture();
                                centrador.downRight.casilla.UpdateTexture();
                                centrador = centrador.downRight;

                                if (centrador.upRight.casilla.value == 0){
                                    centrador.upRight.casilla.SetValue(centrador.casilla.GetValue());
                                    centrador.casilla.SetValue(0);
                                    centrador.casilla.UpdateTexture();
                                    centrador.upRight.casilla.UpdateTexture();
                                }
                            }
                        }

                        if (o==4){
                            if(centrador.upRight.casilla.GetValue() == 0) {
                                centrador.upRight.casilla.SetValue(centrador.casilla.GetValue());
                                centrador.casilla.SetValue(0);
                                centrador.casilla.UpdateTexture();
                                centrador.upRight.casilla.UpdateTexture();
                                centrador = centrador.upRight;

                                if (centrador.upRight.casilla.value == 0){
                                    centrador.upRight.casilla.SetValue(centrador.casilla.GetValue());
                                    centrador.casilla.SetValue(0);
                                    centrador.casilla.UpdateTexture();
                                    centrador.upRight.casilla.UpdateTexture();
                                }
                            }

                        }

                        if (o==5){
                            if(centrador.upRight.casilla.GetValue() == 0) {
                                centrador.upRight.casilla.SetValue(centrador.casilla.GetValue());
                                centrador.casilla.SetValue(0);
                                centrador.casilla.UpdateTexture();
                                centrador.upRight.casilla.UpdateTexture();
                                centrador = centrador.upRight;

                                if (centrador.up.casilla.value == 0){
                                    centrador.up.casilla.SetValue(centrador.casilla.GetValue());
                                    centrador.casilla.SetValue(0);
                                    centrador.casilla.UpdateTexture();
                                    centrador.up.casilla.UpdateTexture();
                                }
                            }
                            else if(centrador.up.casilla.GetValue() == 0) {
                                centrador.up.casilla.SetValue(centrador.casilla.GetValue());
                                centrador.casilla.SetValue(0);
                                centrador.casilla.UpdateTexture();
                                centrador.up.casilla.UpdateTexture();
                                centrador = centrador.up;

                                if (centrador.upRight.casilla.value == 0){
                                    centrador.upRight.casilla.SetValue(centrador.casilla.GetValue());
                                    centrador.casilla.SetValue(0);
                                    centrador.casilla.UpdateTexture();
                                    centrador.upRight.casilla.UpdateTexture();
                                }
                            }
                        }

                        if (o==6){
                            if(centrador.up.casilla.GetValue() == 0) {
                                centrador.up.casilla.SetValue(centrador.casilla.GetValue());
                                centrador.casilla.SetValue(0);
                                centrador.casilla.UpdateTexture();
                                centrador.up.casilla.UpdateTexture();
                                centrador = centrador.up;

                                if (centrador.up.casilla.value == 0){
                                    centrador.up.casilla.SetValue(centrador.casilla.GetValue());
                                    centrador.casilla.SetValue(0);
                                    centrador.casilla.UpdateTexture();
                                    centrador.up.casilla.UpdateTexture();
                                }
                            }
                        }

                        if (o==7){

                            if(centrador.up.casilla.GetValue() == 0) {
                                centrador.up.casilla.SetValue(centrador.casilla.GetValue());
                                centrador.casilla.SetValue(0);
                                centrador.casilla.UpdateTexture();
                                centrador.up.casilla.UpdateTexture();
                                centrador = centrador.up;

                                if (centrador.upLeft.casilla.value == 0){
                                    centrador.upLeft.casilla.SetValue(centrador.casilla.GetValue());
                                    centrador.casilla.SetValue(0);
                                    centrador.casilla.UpdateTexture();
                                    centrador.upLeft.casilla.UpdateTexture();
                                }
                            }
                            else if(centrador.upLeft.casilla.GetValue() == 0) {
                                centrador.upLeft.casilla.SetValue(centrador.casilla.GetValue());
                                centrador.casilla.SetValue(0);
                                centrador.casilla.UpdateTexture();
                                centrador.upLeft.casilla.UpdateTexture();
                                centrador = centrador.upLeft;

                                if (centrador.up.casilla.value == 0){
                                    centrador.up.casilla.SetValue(centrador.casilla.GetValue());
                                    centrador.casilla.SetValue(0);
                                    centrador.casilla.UpdateTexture();
                                    centrador.up.casilla.UpdateTexture();
                                }
                            }
                        }

                        if (o==8){
                            if(centrador.upLeft.casilla.GetValue() == 0) {
                                centrador.upLeft.casilla.SetValue(centrador.casilla.GetValue());
                                centrador.casilla.SetValue(0);
                                centrador.casilla.UpdateTexture();
                                centrador.upLeft.casilla.UpdateTexture();
                                centrador = centrador.upLeft;

                                if (centrador.upLeft.casilla.value == 0){
                                    centrador.upLeft.casilla.SetValue(centrador.casilla.GetValue());
                                    centrador.casilla.SetValue(0);
                                    centrador.casilla.UpdateTexture();
                                    centrador.upLeft.casilla.UpdateTexture();
                                }
                            }



                        }

                        if (o==0){

                            if(centrador.down.casilla.GetValue() == 0) {
                                centrador.down.casilla.SetValue(centrador.casilla.GetValue());
                                centrador.casilla.SetValue(0);
                                centrador.casilla.UpdateTexture();
                                centrador.down.casilla.UpdateTexture();
                                centrador = centrador.down;

                                if (centrador.down.casilla.value == 0){
                                    centrador.down.casilla.SetValue(centrador.casilla.GetValue());
                                    centrador.casilla.SetValue(0);
                                    centrador.casilla.UpdateTexture();
                                    centrador.down.casilla.UpdateTexture();
                                }
                            }

                        }

                        if (o==10){
                            if(centrador.downLeft.casilla.GetValue() == 0) {
                                centrador.downLeft.casilla.SetValue(centrador.casilla.GetValue());
                                centrador.casilla.SetValue(0);
                                centrador.casilla.UpdateTexture();
                                centrador.downLeft.casilla.UpdateTexture();
                                centrador = centrador.downLeft;

                                if (centrador.downLeft.casilla.value == 0){
                                    centrador.downLeft.casilla.SetValue(centrador.casilla.GetValue());
                                    centrador.casilla.SetValue(0);
                                    centrador.casilla.UpdateTexture();
                                    centrador.downLeft.casilla.UpdateTexture();
                                }
                            }
                        }

                        if (o==11){
                            if(centrador.downLeft.casilla.GetValue() == 0) {
                                centrador.downLeft.casilla.SetValue(centrador.casilla.GetValue());
                                centrador.casilla.SetValue(0);
                                centrador.casilla.UpdateTexture();
                                centrador.downLeft.casilla.UpdateTexture();
                                centrador = centrador.downLeft;

                                if (centrador.down.casilla.value == 0){
                                    centrador.down.casilla.SetValue(centrador.casilla.GetValue());
                                    centrador.casilla.SetValue(0);
                                    centrador.casilla.UpdateTexture();
                                    centrador.down.casilla.UpdateTexture();
                                }
                            }
                            else if(centrador.down.casilla.GetValue() == 0) {
                                centrador.down.casilla.SetValue(centrador.casilla.GetValue());
                                centrador.casilla.SetValue(0);
                                centrador.casilla.UpdateTexture();
                                centrador.down.casilla.UpdateTexture();
                                centrador = centrador.down;

                                if (centrador.downLeft.casilla.value == 0){
                                    centrador.downLeft.casilla.SetValue(centrador.casilla.GetValue());
                                    centrador.casilla.SetValue(0);
                                    centrador.casilla.UpdateTexture();
                                    centrador.downLeft.casilla.UpdateTexture();
                                }
                            }
                        }


                    }
                }



                /*Casilla reemplazo;
                reemplazo = this.GetCasillaCercana(vecino);

                reemplazo.value = this.GetValue();
                reemplazo.UpdateTexture();


                    ////// PRUEBA
                    /// codigo nuevo
                   /* GameStage stage = (GameStage) Casilla.this.getStage();
                    Board AuxBoard= stage.getBoard();
                    AuxBoard = AuxBoard.GetBoardByCasilla(this.vecino.get(o).casilla);
                    if (AuxBoard!=null)
                      AuxBoard.inicializarBanderas();*/
                //AuxBoard.TryMerge();///NO SIRVE REVISAR!!!!!!

            }

            if(angulo>=(2.0F*Math.PI)) {
                angulo=0.0f;
                double x = 6.0f + (3.7f * Math.cos(2*angulo));
                double y =6.0f + (3.7f * Math.sin(2*angulo));
                this.setX((float) x);
                this.setY((float) y);
                //System.out.println( this.getX() +  this.getY());
                batch.draw(graphic, (float)x,(float)y, this.getWidth(), this.getHeight());
                //System.out.println(this.getX()+  " "  + this.getY());
            }
            else{
                angulo += 0.0174533f;
                double x = 6.0f + (3.7f * Math.cos(2*angulo));
                double y = 6.0f + ( 3.7f * Math.sin(2*angulo));
                this.setX((float) x);
                this.setY((float) y);
                batch.draw(graphic, (float)x,(float)y, this.getWidth(), this.getHeight());
                //System.out.println("X: " + this.getX() + " Y: "+ this.getY());
                //System.out.println(this.getX()+  " "  + this.getY());
            }
        }
        else{
            batch.draw(graphic, this.getX(),this.getY(), this.getWidth(), this.getHeight());
            if (this.nextable == false) {
                    GameStage stage = (GameStage) this.getStage();
                    Casilla auxcd= stage.getToThrow();
                    //System.out.println("Randomizer" + auxcd.contrandomizer);
                    if ((this.value == 4) && (contrandomizer <= 2)) auxcd.contrandomizer =2;
                    if ((this.value == 8) && (contrandomizer <= 3)) auxcd.contrandomizer = 3;
                    if ((this.value == 16) && (contrandomizer <= 4)) auxcd.contrandomizer = 4;
                    if ((this.value == 32) && (contrandomizer <= 5)) auxcd.contrandomizer = 5;
                    if ((this.value == 64) && (contrandomizer <= 6)) auxcd.contrandomizer = 6;
            }


        }
    }

    public void changeValue(int newValue) {
        this.value = newValue;
        this.graphic = NumerMerge.textureAtlas.findRegion(String.valueOf(value));
    }

    public void disparar(final Casilla targetCas) {
        if (!this.throwable)
            return;

        final Vector2 startingPos = new Vector2(getX(),getY());
        Vector2 targetPos = targetCas.getPosition();
        ThrowAction throwAction = new ThrowAction(targetPos);
        throwAction.setSpeed(Constants.THROW_SPEED);

        //Lanzamos la casilla y creamos una nueva accion que cambie el valor de la casilla en el board
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(throwAction);
        sequenceAction.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                targetCas.changeValue(Casilla.this.value);
                return true;
            }
        });
        sequenceAction.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                //Generar nuevo valor
                int index = random.nextInt(2) + 1;
                Casilla.this.changeValue(index);
                //Mover pieza a su posicion original
                Casilla.this.setPosition(startingPos.x,startingPos.y);
                //Devolvemos el focus al Stage para poder colocar otra Casilla
                Gdx.input.setInputProcessor(targetCas.getStage());
                return true;
            }
        });

        this.addAction(sequenceAction);
    }

    public void addarray(){

        GameStage stage = (GameStage) Casilla.this.getStage();
        Board board = stage.getBoard();
        this.vecino = new ArrayList<Board>();
        this.vecino.add(board.up.up);
        this.vecino.add(board.upLeft.up);
        this.vecino.add(board.upLeft.upLeft);
        //this.vecino.add(board.downLeft.upLeft.upRight);
        this.vecino.add(board.upLeft.downLeft);
        this.vecino.add(board.downLeft.downLeft);
        this.vecino.add(board.downLeft.down);
        this.vecino.add(board.down.down);
        this.vecino.add(board.downRight.down);
        this.vecino.add(board.downRight.downRight);
        this.vecino.add(board.upRight.downRight);
        this.vecino.add(board.upRight.upRight);
        this.vecino.add(board.upRight.up);





















        /*this.vecino.add(stage.board.up.up);
        this.vecino.add(stage.board.down.down);
        this.vecino.add(stage.board.upRight.up);
        this.vecino.add(stage.board.upLeft.up);
        this.vecino.add(stage.board.downRight.down);
        this.vecino.add(stage.board.downLeft.down);
        this.vecino.add(stage.board.upRight.upRight);
        this.vecino.add(stage.board.upRight.downRight);
        this.vecino.add(stage.board.downRight.downRight);
        this.vecino.add(stage.board.upLeft.upLeft);
        this.vecino.add(stage.board.upLeft.downLeft);
        this.vecino.add(stage.board.downLeft.downLeft);*/

    }
    public void iniciarcont() {
        randomizer = new int[7];
        randomizer[0] = 1;
        randomizer[1] = 2;
        randomizer[2] = 4;
        randomizer[3] = 8;
        randomizer[4] = 16;
        randomizer[5] = 32;
        randomizer[6] = 64;

        this.contrandomizer = 1;
    }

}



