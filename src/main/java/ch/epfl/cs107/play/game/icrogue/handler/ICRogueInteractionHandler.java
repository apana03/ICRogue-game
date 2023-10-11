package ch.epfl.cs107.play.game.icrogue.handler;

import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior.*;
import ch.epfl.cs107.play.game.icrogue.actor.Connector;
import ch.epfl.cs107.play.game.icrogue.actor.Enemies.DarkLord;
import ch.epfl.cs107.play.game.icrogue.actor.Enemies.Ghost;
import ch.epfl.cs107.play.game.icrogue.actor.Enemies.Turret;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.actor.items.Cherry;
import ch.epfl.cs107.play.game.icrogue.actor.items.Key;
import ch.epfl.cs107.play.game.icrogue.actor.items.PressurePlate;
import ch.epfl.cs107.play.game.icrogue.actor.items.Rock;
import ch.epfl.cs107.play.game.icrogue.actor.items.Staff;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Arrow;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Fire;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Skull;


// Interactions between actors to be coded in the subclasses
public interface ICRogueInteractionHandler extends AreaInteractionVisitor {
    default void interactWith(ICrogueCell other, boolean isCellInteraction){
    }
    default void interactWith(ICRoguePlayer other, boolean isCellInteraction){
    }
    default void interactWith(Cherry other, boolean isCellInteraction){
    }
    default void interactWith(Staff other, boolean isCellInteraction){
    }
    default void interactWith(Fire other, boolean isCellInteraction){
    }
    default void interactWith(Key other, boolean isCellInteraction){
    }
    default void interactWith(Connector other, boolean isCellInteraction){
    }
    default void interactWith(Arrow other, boolean isCellInteraction){
    }
    default void interactWith(Turret other, boolean isCellInteraction){
    }
    default void interactWith(Rock other, boolean isCellInteraction){
    }
    default void interactWith(PressurePlate other, boolean isCellInteraction){
    }
    default void interactWith(Skull other, boolean isCellInteraction){
    }
    default void interactWith(DarkLord other, boolean isCellInteraction) {
    }
    default void interactWith(Ghost other, boolean isCellInteraction) {
    }
}
