package com.game.Entity.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.List;

public class Enemy extends Actor {
    private int health;
    private int dmg;



    private int money;
    private float speed;

    private Animation<TextureRegion> currentAnimation;
    private Animation<TextureRegion>[] animationArr;
    private int enemySize;

    private Vector2 position;

    private float stateTime;
    private float scale;

    private boolean isAtEnd;

    private List<Vector2> path;

    private String moveDirection;
    public Enemy(){

    }

    public Enemy(int health,String path, String name, int enemySize){
        this.enemySize = enemySize;
        this.health = health;

        this.dmg = 7;
        this.money = 15;

        moveDirection = "";
        isAtEnd = false;


        this.speed = 100; //get from super()

        stateTime = 0f;

        animationArr = new Animation[4];
        Texture spriteMap = new Texture(Gdx.files.internal(path));
        TextureRegion[][] spritePosition = TextureRegion.split(spriteMap, 64, 64); // frame width and height get from extended class
        TextureRegion[] animationSprites;


        for (int j=0; j<4; j++) {

            animationSprites = new TextureRegion[4];

            for (int i = 0; i < 4; i++) {
                animationSprites[i] = spritePosition[j][i];

            }

            animationArr[j] = new Animation<>(0.125f, animationSprites);

        }

        currentAnimation = animationArr[1];


    }

    public int getMoney() {
        return money;
    }

    public void dealDmg(float damage)
    {
        health -= damage;
    }

    public boolean isAlive()
    {
        return health > 0;
    }

    public Vector2 getPosition(){
        return position;
    }

    public void initEnemy(java.util.List<Vector2> path, float scale) {

        this.scale = scale;
        this.path = new ArrayList<>();
        this.speed = speed*scale;

        for (Vector2 v : path)
        {
            this.path.add(new Vector2(v.x, v.y));
        }

        position = new Vector2(this.path.get(0).x, this.path.get(0).y);
        //this.path.remove(0);
    }


    private boolean MoveToPoint(Vector2 finalPoint, float deltaTime)
    {

        if (position.equals(finalPoint))
            return true;


        // Find direction from current position to goal
        Vector2 direction = new Vector2  (position.x - finalPoint.x, position.y - finalPoint.y);

        //System.out.println("x: "+ direction.x + ", y: "+ direction.y);

        // Move in that direction
        if (direction.x > 0)
            position.x = position.x - speed * deltaTime;
        else if (direction.x < 0)
            position.x = position.x + speed * deltaTime;
        else if (direction.y > 0)
            position.y = position.y - speed * deltaTime;
        else if (direction.y < 0)
            position.y = position.y + speed * deltaTime;


        // If we moved PAST the goal, move it back to the goal
        Vector2 directionAfterMove = new Vector2  (position.x - finalPoint.x, position.y - finalPoint.y);
        if ((direction.x * directionAfterMove.x < 0) || (direction.y * directionAfterMove.y < 0))
            position = finalPoint;

        // Return whether we've reached the goal or not
        //return position == finalPoint;
        return position.equals(finalPoint);

    }


    public boolean isAtEnd()
    {
        return isAtEnd;
    }

    public void update(float deltaTime){

        if(path.size() > 0 && MoveToPoint(path.get(0), deltaTime)) {
            path.remove(0);


            //chamge rotation if enemy changes direction
            if (path.size() > 0)
            {
                Vector2 direction = new Vector2  (position.x - path.get(0).x, position.y - path.get(0).y);
                if (direction.x < 0 && direction.y == 0 && moveDirection!="Right") {
                    moveDirection = "Right";
                    currentAnimation = animationArr[0];
                }
                else if (direction.x > 0 && direction.y == 0 && moveDirection!="Left") {
                    moveDirection = "Left";
                    currentAnimation = animationArr[1];
                }
                else if (direction.x == 0 && direction.y > 0 && moveDirection!="Down") {
                    moveDirection = "Down";
                    currentAnimation = animationArr[2];
                }
                else if (direction.x == 0 && direction.y < 0 && moveDirection!="Up") {
                    moveDirection = "Up";
                    currentAnimation = animationArr[3];
                }
            }
            else if (path.size() == 0)
                isAtEnd = true;
        }
    }


    public void render(SpriteBatch batch){
        stateTime += Gdx.graphics.getDeltaTime();



        batch.draw(currentAnimation.getKeyFrame(stateTime, true), position.x, position.y ,scale*enemySize, scale*enemySize);

        batch.end();
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.circle(position.x+enemySize*scale/2, position.y+enemySize*scale/2,1);
        shapeRenderer.end();
        batch.begin();



    }


    public int getDmg() {
        return dmg;
    }
}



