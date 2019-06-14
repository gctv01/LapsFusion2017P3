package com.github.squidat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by giancarlotricerri on 6/1/18.
 */

public class Letras extends Actor {
    private Texture next;
    private Texture laps;

    public void llenar() {
        next = new Texture(Gdx.files.internal("NEXT.png"));;
        laps = new Texture(Gdx.files.internal("LAPS.png"));;

    }
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(next, 0.3f,11.7f, 1f, 1f);
        batch.draw(laps,5.3f ,10.2f, 2f, 2f);


    }


}

