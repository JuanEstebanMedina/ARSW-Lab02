Escuela Colombiana de Ingeniería

Arquitecturas de Software – ARSW

####Taller – programación concurrente, condiciones de carrera y sincronización de hilos. EJERCICIO INDIVIDUAL O EN PAREJAS.

#####Parte I – Antes de terminar la clase.

Creación, puesta en marcha y coordinación de hilos.

1. Revise el programa “primos concurrentes” (en la carpeta parte1), dispuesto en el paquete edu.eci.arsw.primefinder. Este es un programa que calcula los números primos entre dos intervalos, distribuyendo la búsqueda de los mismos entre hilos independientes. Por ahora, tiene un único hilo de ejecución que busca los primos entre 0 y 30.000.000. Ejecútelo, abra el administrador de procesos del sistema operativo, y verifique cuantos núcleos son usados por el mismo.

2. Modifique el programa para que, en lugar de resolver el problema con un solo hilo, lo haga con tres, donde cada uno de éstos hará la tarcera parte del problema original. Verifique nuevamente el funcionamiento, y nuevamente revise el uso de los núcleos del equipo.

3. Lo que se le ha pedido es: debe modificar la aplicación de manera que cuando hayan transcurrido 5 segundos desde que se inició la ejecución, se detengan todos los hilos y se muestre el número de primos encontrados hasta el momento. Luego, se debe esperar a que el usuario presione ENTER para reanudar la ejecución de los mismo.


---

## Parte II -  Galgo Racing

design of the program

![](./img/media/image2.png)

>Upon starting the application, we identified a critical issue: **the results were displayed before the race actually finished**.

First, we found a mistake when running the program, the results are shown before the race finish.

<img src="img/6. firstExecutionGalgos.png">

We resolved this timing issue by implementing `.join()` method after the dog threads start their execution. This ensures that results are only displayed once all dogs have completed the race and a winner is determined.

In this way, the results are shown as long as all the greyhounds have finished the race and a winner is provided.

<img src="img/10. fixedWinnerMessage.png">

The results are displayed in the console.

<img src="img/9. firstExecutionGalgos.png">

>We identified the inconsistencies in their results by looking at the ranking shown in the console. From this, we identified the critical regions of the program.

**Some of the inconsistencies we found were:**

- test #1

<img src="img/7. firstExecutionGalgos.png">

- test #2

<img src="img/8. firstExecutionGalgos.png">

It is evident that there is no specific order for the final positions of the greyhounds, and many dogs end up with the same positions, so there is no certainty that the winner is the real winner.

**The critical region we determined was:**

<img src="img/11. criticalRegion.png">

Several threads can interrupt and modify the value of ultimaPosicionAlcanza, causing the inconsistencies identified earlier:

1. Multiple greyhounds with the same position
2. Duplicate positions
3. More than one winning greyhound

>We used **"synchronized"** to organize the threads so that they only enter our critical region once.

For this, we synchronized the variable of the arrival record, which was causing the problems, and we refactored the code for better organization.

<img src="img/14. codeSynchronized.png">

We verified that everything works correctly.

<img src="img/12. testingSynchronized1.png">

<img src="img/13. testingSynchronized2.png">

>We implemented the pause and continue functionalities. With these, when ‘Stop’ is clicked, all the greyhound threads stop, and when ‘Continue’ is clicked, they resume the race.

For this, we set up a flag that indicates whether a greyhound is suspended or not, and by using mechanisms such as wait and notify, the new functional buttons were implemented.

This can be seen in the Galgo and MainCanodromo classes in the _“parte2”_ folder.

