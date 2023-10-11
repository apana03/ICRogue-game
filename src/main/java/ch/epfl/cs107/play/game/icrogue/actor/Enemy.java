package ch.epfl.cs107.play.game.icrogue.actor;


import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public abstract class Enemy extends ICRogueActor{
    private boolean isAlive = true;
    private EnemyType type;
    private Sprite sprite;

    public enum EnemyType{
        Turret,
        DarkLord,
        Ghost,
    }

    public Enemy(Area area, Orientation orientation, DiscreteCoordinates initialPos){
        super(area, orientation, initialPos);
    }

    public boolean isAlive(){
        return isAlive;
    }
    public void kill(){
        isAlive = false;
    } 
    
    public void setType(EnemyType type){
        this.type = type;
    }
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
    public Sprite getSprite() {
        return sprite;
    }
}
