package grundy;

class Pausa extends Thread {
     
     Grundy programa;

     public Pausa(Grundy juego) {
         programa = juego;
     }

    @Override
        public void run() {
            try { Thread.sleep(2500); }
            catch (InterruptedException e) {}
            programa.jugarPC();
        }
    }

