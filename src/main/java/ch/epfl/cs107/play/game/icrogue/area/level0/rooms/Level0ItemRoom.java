package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.icrogue.actor.items.Item;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;

/*
 * Author: Andrei Pana
 * Date:
 */
public abstract class Level0ItemRoom extends Level0Room {
    private ArrayList<Item> itemList = new ArrayList<>();

    public Level0ItemRoom(DiscreteCoordinates roomCoordinates){
        super(roomCoordinates);
    }

    public void addItem(Item item){
        itemList.add(item);
    }

    public void removeItem(Item item){
        itemList.remove(item);
    }
}
