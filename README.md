Escuela Colombiana de Ingeniería

Arquitecturas de Software – ARSW

####Taller – programación concurrente, condiciones de carrera y sincronización de hilos. EJERCICIO INDIVIDUAL O EN PAREJAS.

#####Parte I – Antes de terminar la clase.

Creación, puesta en marcha y coordinación de hilos.

1. Revise el programa “primos concurrentes” (en la carpeta parte1), dispuesto en el paquete edu.eci.arsw.primefinder. Este es un programa que calcula los números primos entre dos intervalos, distribuyendo la búsqueda de los mismos entre hilos independientes. Por ahora, tiene un único hilo de ejecución que busca los primos entre 0 y 30.000.000. Ejecútelo, abra el administrador de procesos del sistema operativo, y verifique cuantos núcleos son usados por el mismo.

2. Modifique el programa para que, en lugar de resolver el problema con un solo hilo, lo haga con tres, donde cada uno de éstos hará la tarcera parte del problema original. Verifique nuevamente el funcionamiento, y nuevamente revise el uso de los núcleos del equipo.

3. Lo que se le ha pedido es: debe modificar la aplicación de manera que cuando hayan transcurrido 5 segundos desde que se inició la ejecución, se detengan todos los hilos y se muestre el número de primos encontrados hasta el momento. Luego, se debe esperar a que el usuario presione ENTER para reanudar la ejecución de los mismo.


---

Parte II

design of the program

![](./img/media/image2.png)

>Al iniciar la aplicación, hay un primer error evidente: los resultados (total recorrido y número del galgo ganador) son mostrados antes de que finalice la carrera como tal.

First, we found a mistake when running the program, the results are shown before the race finish.

<img src="img/6. firstExecutionGalgos.png">

Then, we resolve the problem, putting a ".JOIN()" after de que los galgos (hilos) inicien su recorrido.

De esta manera, los resultados se muestran siempre y cuando todos los galgos hallan terminado la carrera y se provee un ganador.

<img src="img/10. fixedWinnerMessage.png">

Se observan los resultados en consola

<img src="img/9. firstExecutionGalgos.png">

>identificamos las inconsistencias en los resultados de las
    mismas viendo el ‘ranking’ mostrado en consola. A partir de esto, identificamos las regiones
    críticas () del programa.

**Algunas de las inconsistencias que encontramos fueron:**

- prueba #1

<img src="img/7. firstExecutionGalgos.png">

- prueba #2

<img src="img/8. firstExecutionGalgos.png">

Se evidencia que no hay un orden en especifico para las posiciones finales de los Galgos y se encuentra que muchos perros obtienen las mismas posiciones por lo que no hay una certeza de que el ganador sea el verdadero ganador.

**La region crítica que determinamos fue:**

<img src="img/11. criticalRegion.png">

varios hilos pueden interrumpir y modificar el valor de ultimaPosicionAlcanza, causando que se cumplan las incoherencias identificadas anteriormente

1. Múltiples galgos con la misma posición
2. Posiciones duplicadas
3. Más de un galgo ganador

>Utilizamos "synchronized" para organizar los hilos y que solo entren una vez a nuestra region crítica

Para eso sincronizamos la variable del registro de llegada que era la provocante de los problemas, y refactorizamos el código para mayor orden

<img src="img/14. codeSynchronized.png">

verificamos que todo funciona correctamente

<img src="img/12. testingSynchronized1.png">

<img src="img/13. testingSynchronized2.png">

>Implementamos las funcionalidades de pausa y continuar. Con estas,
    cuando se haga clic en ‘Stop’, todos los hilos de los galgos frenan, y cuando se haga clic en ‘Continue’ los mismos continuan con la carrera.

Para lo anterior,  instauramos una bandera que anuncia si un galgo esta o no suspendido, y mediante mecanismos como wait y notify se implementaron los nuevos botones funcionales.

Puede observarse en las clases Galgo y MainCanodromo en la carpeta "parte2"

