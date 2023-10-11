package ch.epfl.cs107.play.game.icrogue.actor.Enemies;

import java.util.List;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.actor.Enemy;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Arrow;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class Turret extends Enemy{

    private static final float COOLDOWN = 2.f;
    private float COUNTER = 0.f;
    private List<Orientation> attackDirections = null;

    public Turret(Area area, Orientation orientation, DiscreteCoordinates initialPos) {
        super(area, orientation, initialPos);
        this.setType(EnemyType.Turret);
        setSprite(new Sprite("icrogue/static_npc", 1.5f, 1.5f, this));
    }
    public Turret(Area area, Orientation orientation, DiscreteCoordinates initialPos, List<Orientation> Orientations) {
        super(area, orientation, initialPos);
        this.setType(EnemyType.Turret);
        this.attackDirections = Orientations;
        setSprite(new Sprite("icrogue/static_npc", 1.5f, 1.5f, this));
    }

    @Override
    public void draw(Canvas canvas) {
        this.getSprite().draw(canvas);
    }
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        COUNTER += deltaTime; 
        if (COUNTER >= COOLDOWN){ // Checks if the counter has reached the attack cooldown
            attack();
            COUNTER = 0.f;
        }

    }

    // Performs the attack in all the attack directions if specified, else in its orientation
    private void attack() {
        if (attackDirections != null){
            for (Orientation ori : attackDirections){
                getOwnerArea().registerActor(new Arrow(getOwnerArea(), ori, getCurrentMainCellCoordinates()));
            }
        }
        else{
            getOwnerArea().registerActor(new Arrow(getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates()));
        }

    }


    public void die(){
        getOwnerArea().unregisterActor(this);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this , isCellInteraction);
    }

}
