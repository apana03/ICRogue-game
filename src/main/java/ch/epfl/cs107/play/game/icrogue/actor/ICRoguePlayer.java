package ch.epfl.cs107.play.game.icrogue.actor;

import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.*;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.actor.Enemies.Ghost;
import ch.epfl.cs107.play.game.icrogue.actor.Enemies.Turret;
import ch.epfl.cs107.play.game.icrogue.actor.items.Cherry;
import ch.epfl.cs107.play.game.icrogue.actor.items.Key;
import ch.epfl.cs107.play.game.icrogue.actor.items.PressurePlate;
import ch.epfl.cs107.play.game.icrogue.actor.items.Rock;
import ch.epfl.cs107.play.game.icrogue.actor.items.Staff;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Bomb;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Fire;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0EnemyRoom;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0StaffRoom;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;


import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ICRoguePlayer extends ICRogueActor implements Interactor {
    private float hp;
    private final TextGraphics message;
    private final Sprite[][] sprites = new Sprite[4][4];
    private  Sprite[] spriteLine ;

    private Animation currentAnimation;

    private boolean gotStaff = false;

    /// Animation duration in frame number
    private final static int MOVE_DURATION = 6;

    private boolean isPassingDoor = false;

    private String destination;

    // Key inventory as a list, will be used to have multiple keys for multiple different doors
    private ArrayList<Key> keyInventory = new ArrayList<Key>();

    private ICRoguePlayerInteractionHandler handler = new ICRoguePlayerInteractionHandler();

    public ICRoguePlayer(Area owner, Orientation orientation, DiscreteCoordinates coordinates, String spriteName) {
        super(owner, orientation, coordinates);

        // Setting the hp
        this.hp = 10;
        message = new TextGraphics("Hp :"+getHp(),1f, Color.WHITE,null,1,true,false,new Vector(7,9));
        
        // Add the sprites
        for(int i=0; i<4;i++){
            for(int j=0;j<4;j++){
                sprites[j][i] = new Sprite(spriteName,0.8f,1.6f,this,new RegionOfInterest(i*16,j*32,16,32));
            }
        }

        // setting up the animation
        spriteLine = sprites[0];
        currentAnimation = new Animation(24, spriteLine);
        currentAnimation.setSpeedFactor(12);
        currentAnimation.setAnchor(new Vector(0, 0));
        currentAnimation.setWidth(0.8f);
        currentAnimation.setHeight(1.6f);
        resetMotion();
    }

    /**
     * Center the camera on the player
     */
    public void centerCamera() {
        getOwnerArea().setViewCandidate(this);
    }

    
    @Override
    public void update(float deltaTime) {

        if(isDisplacementOccurs()){
            currentAnimation.update(deltaTime);
        }

        // Update HP
        message.setText("HP : "+getHp());
        if (hp <= 0){
            this.die();
        }

        //Keyboard inputs
        Keyboard keyboard = getOwnerArea().getKeyboard();

        moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
        moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
        moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
        moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));
        shootFire(this.getOrientation(), keyboard.get(Keyboard.X));
        dropBomb(this.getOrientation(), keyboard.get(Keyboard.Z));

        //if in staff room gives itself as a target to the ghosts
        if (getOwnerArea().getClass() == Level0StaffRoom.class){
            ((Level0StaffRoom)this.getOwnerArea()).setGhostTarget(this);
        }

        super.update(deltaTime);

    }

    /**
     * Orientate and Move this player in the given orientation if the given button is down
     *
     * @param orientation (Orientation): given orientation, not null
     * @param b           (Button): button corresponding to the given orientation, not null
     */
    private void moveIfPressed(Orientation orientation, ch.epfl.cs107.play.window.Button b) {
        if (b.isDown()) {
            if (!isDisplacementOccurs()) {
                switch (orientation) {
                    case DOWN -> spriteLine=sprites[0];
                    case RIGHT -> spriteLine=sprites[1];
                    case UP -> spriteLine=sprites[2];
                    case LEFT -> spriteLine=sprites[3];
                }
                currentAnimation = new Animation(24, spriteLine);
                currentAnimation.setSpeedFactor(12);
                currentAnimation.setAnchor(new Vector(0, 0));
                currentAnimation.setWidth(0.8f);
                currentAnimation.setHeight(1.6f);
                orientate(orientation);
                move(MOVE_DURATION);
            }
        }
    }

    // Shoot fire if we have the staff
    // check if the key is pressed and registers a new fire actor in its direction
    private void shootFire(Orientation orientation, Button b) {
        if (b.isPressed() && gotStaff) {
            getOwnerArea().registerActor(new Fire(getOwnerArea(), orientation, getCurrentMainCellCoordinates()));
        }
    }

    // Drops bomb
    private void dropBomb(Orientation orientation , Button b){
        if (b.isPressed()) {
            getOwnerArea().registerActor(new Bomb(getOwnerArea(), orientation, getCurrentMainCellCoordinates()));
        }
    }

    public boolean isPassingDoor() {
        return isPassingDoor;
    }

    public void setPassingDoor(boolean isPassingDoor) {
        this.isPassingDoor = isPassingDoor;
    }

    public String getDestination() {
        return destination;
    }

    /**
     * Leave an area by unregister this player
     */
    public void leaveArea() {
        getOwnerArea().unregisterActor(this);
    }

    /**
     * @param area     (Area): initial area, not null
     * @param position (DiscreteCoordinates): initial position, not null
     */
    public void enterArea(Area area, DiscreteCoordinates position) {
        area.registerActor(this);
        setOwnerArea(area);
        setCurrentPosition(position.toVector());
        resetMotion();
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

    
    private void die(){
        // On death unregister self
        this.getOwnerArea().unregisterActor(this);

        // Show you died screen
        getOwnerArea().registerActor(new Foreground(getOwnerArea(),new RegionOfInterest(0,10,1200,600),"icrogue/gameover"));
        System.out.println("Game Over");
    }

    ///ICRogue player implements Interactable
    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this , isCellInteraction);
    }

    //implements Interactor
    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return Collections.singletonList
                (getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
    }

    @Override
    public boolean wantsCellInteraction() {
        return true;
    }


    // If w is down
    @Override
    public boolean wantsViewInteraction() {
        Keyboard keyboard = getOwnerArea().getKeyboard();
        return keyboard.get(Keyboard.W).isDown();
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler, isCellInteraction);
    }

    //inner class implimenting the interactions
    private class ICRoguePlayerInteractionHandler implements ICRogueInteractionHandler {

        //Items

            //Cherry
        @Override
        public void interactWith(Cherry other, boolean isCellInteraction) {
            hp = hp + other.gethealvalue();
            other.collect();
        }

            //Staff
        @Override
        public void interactWith(Staff other, boolean isCellInteraction) {
            if (wantsViewInteraction()) {
                other.collect();
                gotStaff = true;
            }
        }
        
            //Key
        @Override
        public void interactWith(Key other, boolean isCellInteraction) {
            other.collect();
            keyInventory.add(other);
        }

            //Rock
        @Override
        public void interactWith(Rock other, boolean isCellInteraction){

            if (wantsViewInteraction()) {
                // Push it in the players orientation
                other.push(MOVE_DURATION, getOrientation());
            }
        }

            // Pressure plate
        @Override
        public void interactWith(PressurePlate other, boolean isCellInteraction) {
            other.press();
        }


        //Connector
        @Override
        public void interactWith(Connector other, boolean isCellInteraction) {

            // For locked connects
            if(other.getState() == Connector.ConnectorStates.LOCKED){
                for (int i = 0; i < keyInventory.size(); i++) { // Iterate through keys in inventory
                    if (keyInventory.get(i).getId() == other.getNeededKeyId()) { // Check if its the needed key
                        if (wantsViewInteraction()) { // if w key is down
                            other.setState(Connector.ConnectorStates.OPEN); // unlock it
                        }
                    }
                }
                // If the connector is open and we arent moving set passing door to true and set destination to the connectors destination
            }else if (other.getState() == Connector.ConnectorStates.OPEN && !isDisplacementOccurs()) {
                isPassingDoor = true;
                if (other.getDestination() != null){
                    destination = other.getDestination();
                }
            }
        }

        // If we walk on a turret eliminate 
        @Override
        public void interactWith(Turret turret, boolean isCellInteraction){
            turret.die();
            ((Level0EnemyRoom)getOwnerArea()).eliminateEnemy(turret);
        }

        // If we collide with a ghost, get the damage that needs to be done
        @Override
        public void interactWith(Ghost other, boolean isCellInteraction) {
            hp = hp - other.getDamage();
        }

        
    }
}
