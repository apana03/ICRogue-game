package ch.epfl.cs107.play.game.icrogue.actor.Enemies;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.actor.Enemy;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;
import java.lang.Math;

public class Ghost extends Enemy{

    private static final float COOLDOWN = 0.8f;
    private float COUNTER = 0.f;
    private ICRogueActor target;
    private final int MOVE_DURATION = 5;
    private float Damage = 1.f;
    private boolean didDamage = false;

    public Ghost(Area area, Orientation orientation, DiscreteCoordinates initialPos) {
        super(area, orientation, initialPos);
        this.setType(EnemyType.Turret);
        setSprite(new Sprite("ghost.1", 1.f, 1.f, this));
        target = null;
    }

    @Override
    public void draw(Canvas canvas) {
        this.getSprite().draw(canvas);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        COUNTER += deltaTime;
        
        if (target != null){ // Check if the gost has a target locked on
            Damage = 1.f; // Sets the damage (prevents taking damage on every tick)
            if (COUNTER >= COOLDOWN){ // If time has passed
                orientate(focus(target)); // Look in the targets direction
                move(MOVE_DURATION); 
                COUNTER = 0.f;
                didDamage = false; // Prevents giving damage every tick
            }
            
        }
    }

    private Orientation focus(ICRogueActor actor){

        // Gets both positions
        DiscreteCoordinates actor_pos = actor.getCurrentCells().get(0);
        DiscreteCoordinates ghost_pos = getCurrentCells().get(0);

        // Finds which difference is greater
        float dx = Math.abs(ghost_pos.x - actor_pos.x);
        float dy = Math.abs(ghost_pos.y - actor_pos.y);

        // Changes orientation
        if (dx > dy){
            if (ghost_pos.x > actor_pos.x){ return Orientation.LEFT;}
            return Orientation.RIGHT;
        }
        else{
            if(ghost_pos.y > actor_pos.y){return Orientation.DOWN;}
            return Orientation.UP;
        }
    }

    // Gives the ghost the player as a target to be able to follow
    public void setTarget(ICRogueActor target){
        this.target = target;
    }

    public void die(){
        getOwnerArea().unregisterActor(this);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this , isCellInteraction);
    }

    // Gives the damage if the ghost hasnt attacked in the last action loop
    // Prevents instant death upon contact
    public float getDamage() {
        if (!didDamage){
            didDamage = true;
            return Damage;
        }
        return 0.f;
    }

}
