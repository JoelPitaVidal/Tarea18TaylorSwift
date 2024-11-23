import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Alumnos implements Runnable {

    private static final int MAX_INTENTOS = 5;

    private static AtomicInteger entradasRestantes = new AtomicInteger(50);
    private static Lock puerta = new ReentrantLock();
    private int id;

    public Alumnos(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        Random random = new Random();
        int intentos = 0;

        while (intentos < MAX_INTENTOS) {
            intentos++;
            if (puerta.tryLock()) {
                try {
                    System.out.println("ENTRADA: Alumno " + id + " intenta entrar. Intento " + intentos);
                    if (entradasRestantes.get() > 0) {
                        int entradasActuales = entradasRestantes.getAndDecrement();
                        if (entradasActuales > 0) {
                            System.out.println("Alumno " + id + " consigue una entrada. Quedan " + (entradasActuales - 1) + " entradas.");
                            return;
                        }
                    }
                    System.out.println("No quedan entradas para el alumno " + id + ". Se va.");
                    return;
                } finally {
                    puerta.unlock();
                }
            } else {
                System.out.println("Alumno " + id + " no pudo entrar en el intento " + intentos);
            }

            // Esperar un tiempo aleatorio antes de intentar de nuevo
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Alumno " + id + " se hace fan de Kanye West después de " + intentos + " intentos fallidos.");
    }
}