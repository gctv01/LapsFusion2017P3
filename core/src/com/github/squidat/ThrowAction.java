package com.github.squidat;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

/**
 * Created by juan on 27/08/17.
 * A diferencia del MoveToAction, esta accion no depende del tiempo sino que depende de la velocidad.
 * Realizamos este cambio agregando un metodo que calcule el tiempo basado en una velocidad (escalar)
 */

public class ThrowAction extends MoveToAction {
    private final String TAG = getClass().getSimpleName();
    private float Speed;

    public ThrowAction(Vector2 targetPos) {
        super();
        this.setPosition(targetPos.x,targetPos.y);
    }

    private float calculateTime(float speed) {
        Actor actor = getActor();
        if (actor != null) {
            float targetX = getX();
            float targetY = getY();
            float currentX = actor.getX();
            float currentY = actor.getY();
            //Armamos el vector distancia con los puntos current y target
            Vector2 distancia = new Vector2(currentX - targetX, currentY - targetY);
            //Modulo del vector distancia
            float distancia_mod = distancia.len();
            //Calculamos el tiempo necesario para mantener la velocidad constante usando
            //la formula V = d/t
            return distancia_mod / speed;
        }

        //TODO: Arreglar tiempo cuando no se tiene posicion del actor
        /* A veces no se tiene referencia de un actor en pleno movimiento
         * como lo manejamos actualmente, devolveria 0 de tiempo por lo que
         * saltaria inmediatamente.
         *
         * Este error probablemente no suceda en el uso normal del juego porque
         * solo se ejecutara una vez el movimiento por Casilla.
         */
        return 0f;
    }

    @Override
    public void setActor(Actor actor) {
        super.setActor(actor);
        float dur = calculateTime(Speed);
        this.setDuration(dur);
    }

    public void setSpeed(float speed) {
        this.Speed = speed;
    }
}