public class Main {
    public static void main(String[] args) {
        Thread[] alumnos = new Thread[200];
        for (int i = 0; i < 200; i++) {
            alumnos[i] = new Thread(new Alumnos(i + 1));
            alumnos[i].start();
        }
        for (int i = 0; i < 200; i++) {
            try {
                alumnos[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
