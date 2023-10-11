package ch.epfl.cs107.play.game.icrogue.actor.Enemies;

import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.RandomHelper;
import ch.epfl.cs107.play.game.icrogue.actor.Enemy;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Skull;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;

import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.awt.*;


public class DarkLord extends Enemy {
    private float hp=10;
    private boolean attackedOnce = true;

    private static final float ATTACK_COOLDOWN = 2.f;
    private static final float ROTATION_COOLDOWN = .75f;
    private static  final float TP_COOLDOWN =.5f;

    private float attackCounter = 0.f;

    private float dtRotation =0.f;
    private float rotationCounter = 0.f;
    private float tpTimer =0;
    private final Sprite[][]spellSprites =  new Sprite[4][3];
    private Sprite[] spriteLine = new Sprite[3];
    private Animation currentAnimation;
    private TextGraphics message;


    public DarkLord(Area area, Orientation orientation, DiscreteCoordinates initialPos) {
        super(area, orientation, initialPos);
        this.setType(EnemyType.DarkLord);
        // setting up the animation
        for(int i=0; i<3;i++){
            for(int j=0;j<4;j++){
                spellSprites[j][i] = new Sprite("zelda/darkLord.spell",2f,2f,this,new RegionOfInterest(i*32,j*32,32,32));
            }
        }



        spriteLine = spellSprites[2];


        currentAnimation = new Animation(24, spriteLine);
        currentAnimation.setSpeedFactor(12);
        currentAnimation.setWidth(2f);
        currentAnimation.setHeight(2f);

        //HP message
        message = new TextGraphics("Boss Hp"+getHp(),1f, Color.red,null,1,true,false,new Vector(0,0));
    }
    @Override
    public void draw(Canvas canvas) {
        currentAnimation.draw(canvas);
        message.draw(canvas);

    }

    public float getHp() {
        return hp;
    }
    public void setHp(float hp) {
        this.hp = hp;
    }


    //method that moves the DarkLord to some Random coordinates in the room
    public void teleportToRandomCoords(){
        tpTimer = 0 ;
        int x = RandomHelper.roomGenerator.nextInt(1,9);
        int y = RandomHelper.roomGenerator.nextInt(1,9);
        DiscreteCoordinates randomCoords = new DiscreteCoordinates(x,y);
        changePosition(randomCoords);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        message.setText("Boss HP: "+getHp());
        currentAnimation.update(deltaTime);

        attackCounter += deltaTime;
        if (attackCounter >= ATTACK_COOLDOWN){//the boss attacks at some cooldown
            if(rotationCounter == 0){//the rotation counter allows us to know in which direction to rotate the boss
                orientate(Orientation.DOWN);

                if(attackedOnce){//so that the boss does not create a very large number of projectiles
                    setCurrentAnimation();
                    attack();
                }
            }else if(rotationCounter == 1){
                orientate(Orientation.RIGHT);

                if(attackedOnce){
                    setCurrentAnimation();
                    attack();
                }

            }else if(rotationCounter == 2){
                orientate(Orientation.UP);

                if(attackedOnce){
                    setCurrentAnimation();
                    attack();
                }
            }else if(rotationCounter == 3) {
                orientate(Orientation.LEFT);

                if(attackedOnce){
                    setCurrentAnimation();
                    attack();
                }
                tpTimer+=deltaTime;
                if(tpTimer>= TP_COOLDOWN) {
                    attackedOnce = true;
                    teleportToRandomCoords();
                    rotationCounter = 0;
                }
            }
            dtRotation+=deltaTime;
            if(dtRotation>=ROTATION_COOLDOWN){//Cooldown that allows the boss to not rotate automatically
                attackedOnce = true;
                rotationCounter++;
                dtRotation = 0;
            }
        }

        //if the boss has less than 0 hp it dies
        if(hp<=0){
            die();
        }

    }


    //method that allows the boss to attack
    private void attack() {
        getOwnerArea().registerActor(new Skull(getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates()));
        attackedOnce = false;
    }

    //method that allows the boss to die
    public void die(){
        getOwnerArea().unregisterActor(this);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this , isCellInteraction);
    }

    //sets up the animation using the orientation of the DarkLord
    private void setCurrentAnimation(){
        switch (getOrientation()){
            case DOWN -> spriteLine = spellSprites[2];
            case RIGHT -> spriteLine=spellSprites[3];
            case UP -> spriteLine=spellSprites[0];
            case LEFT -> spriteLine=spellSprites[1];
        }
        currentAnimation = new Animation(24, spriteLine);
        currentAnimation.setSpeedFactor(12);
        currentAnimation.setWidth(2f);
        currentAnimation.setHeight(2f);
    }

}
