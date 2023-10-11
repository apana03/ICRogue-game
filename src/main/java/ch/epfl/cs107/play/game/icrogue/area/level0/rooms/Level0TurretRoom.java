package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.Enemies.Turret;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import java.util.Arrays;


public class Level0TurretRoom extends Level0EnemyRoom{

    // Create two turrets
    private Turret turret1 = new Turret(this, Orientation.UP, new DiscreteCoordinates(1, 8), Arrays.asList(new Orientation[]{Orientation.DOWN,Orientation.RIGHT}) );
    private Turret turret2 = new Turret(this, Orientation.UP, new DiscreteCoordinates(8, 1), Arrays.asList(new Orientation[]{Orientation.UP,Orientation.LEFT}) );

    public Level0TurretRoom(DiscreteCoordinates roomCoordinates){
        super(roomCoordinates);

        //Adds both turrets
        addEnemy(turret1);
        addEnemy(turret2);
    }

}
