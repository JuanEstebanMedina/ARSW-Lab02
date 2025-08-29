package arsw.threads;

/**
 * Un galgo que puede correr en un carril
 *
 * @author rlopez
 *
 */
public class Galgo extends Thread {

    private int paso;
    private Carril carril;
    RegistroLlegada regl;
    private boolean isPaused = false;

    public Galgo(Carril carril, String name, RegistroLlegada reg) {
        super(name);
        this.carril = carril;
        paso = 0;
        this.regl = reg;
    }

    public synchronized void pauseGalgo() {
        isPaused = true;
    }

    public synchronized void resumeGalgo() {

        isPaused = false;
        notifyAll();
    }

    public void corra() throws InterruptedException {
        while (paso < carril.size()) {

            synchronized (this) {
                while (isPaused) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            Thread.sleep(100);
            carril.setPasoOn(paso++);
            carril.displayPasos(paso);
            if (paso == carril.size()) {
                carril.finish();
                synchronized (regl) {
                    position();
                }
            }
        }

    }

    public void position() {
        int ubicacion = regl.getUltimaPosicionAlcanzada();
        regl.setUltimaPosicionAlcanzada(ubicacion + 1);
        System.out.println("El galgo " + this.getName() + " llego en la posicion " + ubicacion);
        if (ubicacion == 1) {
            regl.setGanador(this.getName());
        }
    }

    @Override
    public void run() {
        try {
            corra();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
