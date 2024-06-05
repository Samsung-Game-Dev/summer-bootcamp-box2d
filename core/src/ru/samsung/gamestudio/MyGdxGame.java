package ru.samsung.gamestudio;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {

	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 720;
	public static float SCALE = 0.005f;
	public static final float TIME_STEP = 1f / 60f;
	public static final int VELOCITY_ITERATIONS = 6;
	public static final int POSITION_ITERATIONS = 6;

	SpriteBatch batch;
	OrthographicCamera camera;
	World world;
	SpaceShip spaceShip;

	float accumulator = 0;

	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);

		Box2D.init();
		world = new World(new Vector2(0, 0), true);
		spaceShip = new SpaceShip(world, 100, 100, 100, 100, "spaceship.png");
	}

	@Override
	public void render () {

		handleInput();

		stepWorld();

		camera.update();
		batch.setProjectionMatrix(camera.combined);
		ScreenUtils.clear(Color.CLEAR);
		batch.begin();
		spaceShip.draw(batch);
		batch.end();
	}

	private void handleInput() {

		if (Gdx.input.isTouched()) {
			if (Gdx.input.isTouched()) {
				Vector3 touch = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
				spaceShip.move(touch.x, touch.y);
			}
		}

	}

	@Override
	public void dispose () {
		batch.dispose();
	}

	public void stepWorld() {
		float delta = Gdx.graphics.getDeltaTime();
		accumulator += Math.min(delta, 0.25f);

		if (accumulator >= TIME_STEP) {
			accumulator -= TIME_STEP;
			world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
		}
	}
}
