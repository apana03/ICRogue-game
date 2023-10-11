package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.Enemies.DarkLord;
import ch.epfl.cs107.play.game.icrogue.actor.items.Cherry;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0DarkLordRoom extends Level0EnemyRoom{

    private DarkLord darkLord = new DarkLord(this, Orientation.DOWN, new DiscreteCoordinates(4,4));

    public Level0DarkLordRoom(DiscreteCoordinates roomCoordinates){
        super(roomCoordinates);
        addEnemy(darkLord);

        //Add cherries to all room corners
        addCherry(new Cherry(this, Orientation.DOWN, new DiscreteCoordinates(1, 1)));
        addCherry(new Cherry(this, Orientation.DOWN, new DiscreteCoordinates(1, 8)));
        addCherry(new Cherry(this, Orientation.DOWN, new DiscreteCoordinates(8, 1)));
        addCherry(new Cherry(this, Orientation.DOWN, new DiscreteCoordinates(8, 8)));
    }


}
