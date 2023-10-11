package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public abstract class Projectile extends ICRogueActor implements Consumable, Interactor {
    private static final int DEFAULT_DAMAGE =1;

    private static final int DEFAULT_MOVE_DURATION = 10;

    private int frameNb = DEFAULT_MOVE_DURATION;
    private float damage = DEFAULT_DAMAGE;
    private boolean isConsumed;

    private Sprite sprite;


    public Projectile(Area area, Orientation orientation, DiscreteCoordinates startCoords, float damage, int frameNb){
        super(area,orientation,startCoords);
        this.damage = damage;
        this.frameNb = frameNb;
    }

    public Projectile(Area area, Orientation orientation, DiscreteCoordinates startCoords){
        super(area,orientation,startCoords);
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    // Destroy itself
    public void consume(){
        isConsumed = true;
        getOwnerArea().unregisterActor(this);
    }

    public boolean isConsumed(){
        return isConsumed;
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }


    //implements Interactor
    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return Collections.singletonList
                (getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
    }

    @Override
    public boolean wantsCellInteraction() {
        return true;
    }

    @Override
    public boolean wantsViewInteraction() {
        return true;
    }

    // Returns the damage done by the projectile
    public float getDamage() {
        return damage;
    }


}
