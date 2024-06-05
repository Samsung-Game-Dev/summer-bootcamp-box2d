package ru.samsung.gamestudio;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static ru.samsung.gamestudio.MyGdxGame.SCALE;

public class SpaceShip {

    // Ui data
    Texture texture;
    int width;
    int height;

    // Physics data
    public Body body;

    public SpaceShip(World world, int x, int y, int width, int height, String texturePath) {
        this.width = width;
        this.height = height;

        texture = new Texture(texturePath);
        body = createBody(x, y, world);
    }


    private Body createBody(float x, float y, World world) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.fixedRotation = true;
        Body body = world.createBody(def);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(Math.max(width, height) * SCALE / 2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 0.1f;
        fixtureDef.friction = 1f;

        body.createFixture(fixtureDef);
        circleShape.dispose();

        body.setTransform(x * SCALE, y * SCALE, 0);
        body.setLinearDamping(10);
        return body;
    }

    public int getX() {
        return (int) (body.getPosition().x / SCALE);
    }

    public int getY() {
        return (int) (body.getPosition().y / SCALE);
    }

    public void setX(int x) {
        body.setTransform(x * SCALE, body.getPosition().y, 0);
    }

    public void setY(int y) {
        body.setTransform(body.getPosition().x, y * SCALE, 0);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, getX() - (width / 2f), getY() - (height / 2f), width, height);
    }

    public void dispose() {
        texture.dispose();
    }

    public void move(float x, float y) {
        body.applyForceToCenter(new Vector2(
                        (x - getX()) * 0.01f,
                        (y - getY()) * 0.01f),
                true
        );
    }
}
