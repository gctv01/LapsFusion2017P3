package com.github.squidat;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class NumerMerge extends ApplicationAdapter {
	GameStage stage;
	public static TextureAtlas textureAtlas;

	@Override
	public void create () {
		textureAtlas = new TextureAtlas(Gdx.files.internal("casillas.atlas"));
		OrthographicCamera camera = new OrthographicCamera();
		Viewport viewport = new FitViewport(13,13,camera);
		stage = new GameStage(viewport,Constants.DIMENSION);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render () {
		cls();
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width,height,true);
	}

	@Override
	public void dispose () {
		stage.dispose();
		textureAtlas.dispose();
	}

	public static void cls() {
		Gdx.gl.glClearColor(0, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
}