
class MeuDadoThreads {
    private int Dado;
    public void armazenar(int Dado) {
        this.Dado=Dado;
    }
    public int carregar() {
        return this.Dado;
    }
}

class ProdutorThreads implements Runnable {
    MeuDadoThreads dado;
    public ProdutorThreads(MeuDadoThreads dado) {
        this.dado=dado;
    }
    public void run() {
        int i;
        for(i=0;i<30;i++) {
            dado.armazenar(i);
            System.out.println ("Produtor: "+i);
            try {
                // cochila por um tempo randômico (0 to 0.5 sec)
                Thread.sleep((int)(Math.random()*500));
            } catch (InterruptedException e) { }
        }
    }
}

class ConsumidorThreads implements Runnable {
    MeuDadoThreads dado;
    public ConsumidorThreads(MeuDadoThreads dado) {
        this.dado=dado;
    }
    public void run() {
        for(int i=0;i<30;i++) {
            System.out.println("Consumidor: "+dado.carregar());
            try {
                // cochila por um tempo randômico (0 to 0.5 sec)
                Thread.sleep((int)(Math.random()*500));
            } catch (InterruptedException e) { }
        }
    }
}

class MeuDadoThreadsJava {
    public static void main(String argv[]) {
        MeuDadoThreads dado = new MeuDadoThreads();
        new Thread(new ProdutorThreads(dado)).start();
        new Thread(new ConsumidorThreads(dado)).start();
    }
}
