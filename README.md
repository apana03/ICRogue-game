# ICRogue - A Java-Based Dungeon Adventure Game

## Description

ICRogue is an exciting dungeon adventure game implemented in Java. In this game, you will embark on a quest to explore a mysterious castle overtaken by the forces of darkness. Your mission is to solve puzzles, unlock magical powers, and confront the ultimate challenge: defeating the malevolent Lord of Darkness. To achieve this, you'll need to master a magical staff with unique abilities.

## Types of Rooms

### SpawnRoom
- This is the room where the player spawns at the very beginning of the game, and it doesn't need to be resolved.

### Level0Room
- This room is an empty chamber resolved as soon as the player visits it; it has nothing special.

### KeyRoom
- A room that contains a key. As soon as the player possesses the key, the doors open.

### PressurePlateRoom
- A room with two randomly generated pressure plates and a boulder. To resolve this room, the player must succeed in pressing both plates simultaneously.

### StaffRoom
- This room holds a magical staff that grants the player special powers. However, acquiring it is not easy because it is defended by phantoms that spawn regularly, chasing the player and inflicting physical damage. To defeat them, the player must first acquire the staff.

### TurretRoom
- This room contains two enemies launching arrows. To resolve it, the player must defeat these foes.

### Boss Room
- This chamber houses the final adversary of the player.

## Game Solution

The Lord of Darkness has taken control of one of the castles, and you decide to stop his tyranny. However, to confront him, you must be in possession of the magical staff, which is located in one of the rooms in his castle.

## Controls

The player controls are as follows:
- Arrow keys: Movement.
- W: Distant interaction (used to open locked doors, retrieve the staff, and push the boulder).
- X: Cast a fireball once the staff is obtained.
- Z: The player places a bomb on themselves, but they must be careful not to be near it when it explodes.

## How to Confront the Lord of Darkness

The Lord is immune to all physical damage; he can only be injured using magic. To produce this magic, you need to acquire a magical staff. This staff has three powers:
- Summoning flying skulls
- Random teleportation within the room

## Running the Game

To play the game, execute the "play" file.

## Game End

The game is finished once the Lord of Darkness is defeated.