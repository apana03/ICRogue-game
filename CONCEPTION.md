# # Conception ICRogue

- Dans le niveau genéré aléatoirement nous avons changé le boss. Pour ce faire on a codé une nouvelle classe Level0DarkLordRoom qui etends la classe Level0Enemy Room. Nous avons placé cette classe dans **package ch.epfl.cs107.play.game.icrogue.area.level0.rooms**
- Nous avons ajouté une nouvelle salle nomme Level0PlateRoom avec sa classe qui se trouve dans **package ch.epfl.cs107.play.game.icrogue.area.level0.rooms**
- Cette même classe utilise un nouvel Item, le pressure plate sous le paquetage **package ch.epfl.cs107.play.game.icrogue.actor.items**
- Nous avons ajouté aussi une bombe et un skull qui sont des projectiles sous le paquetage **package ch.epfl.cs107.play.game.icrogue.actor.projectiles**
- Nous avons ajouté deux nouveaux acteurs de type Enemy les deux sous le paquetage **package ch.epfl.cs107.play.game.icrogue.actor.Enemies**, un est la classe Ghost et le deuxième est utilise comme Boss
- Nous avons aussi modifié la classe cherry de manière que le joueur gagne des HP après l'avoir collecté.
