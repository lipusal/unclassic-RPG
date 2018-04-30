- El ejecutable bajo la carpeta "EJECUTABLE" está listo para ser jugado.


- Key bindings:

Move up									=> UP/W
Move left								=> LEFT/A
Move down								=> DOWN/S
Move right								=> RIGHT/D
Interact 								=> Q,
Use item (in or out of combat) 			=> E,
Run away from combat 					=> R,
Drop currently selected stack 			=> T,
Attack 									=> F,
Examine entity in front 				=> G,
Unequip all equipped items 				=> CONTROL,
Save and Quit 							=> ESCAPE

- En caso de hacer un .JAR ejecutable, extraer del mismo las carpetas Textures y Worlds, y ponerlas bajo una carpeta "assets" (a minúscula)
en el mismo directorio que el .JAR para que el juego lea las imágenes correctamente.

- El testPlayer.player bajo la carpeta "saves" se utiliza para todos los tests de JUnit, los casos fallarán si no se encuentra al jugador.