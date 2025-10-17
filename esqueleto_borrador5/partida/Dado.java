package partida;

import java.util.Random;

public class Dado {
    private int valor; //valor do dado
    private Random random; //número random

    public Dado(){
        this.valor = 0; //Inicializamos a 0 porque aínda non foi lanzado
        this.random = new Random(); //xera un número aleatorio
    }

    //Metodo para simular lanzamiento de un dado: devolverá un valor aleatorio entre 1 y 6.
    public int hacerTirada() {
        this.valor = random.nextInt(6) + 1; //nextInt(6) danos 6 posibles valores [0, 6), con random son aleatorios
        return this.valor;
    }

    //metodo para realizar un lanzamiento forzado sen sairse do rango
    public void lanzarForzado(int valorForzado){
        this.valor = Math.max(1, Math.min(6, valorForzado));
        //Math.min(6, valorForzado) da o valor mínimo entre 6 e valorForzado para non poder avanzar máis valores dos permitidos
        //Math.max(1, resultado) evita que o valor sexa menor ca 1
    }

    /* Metodo lanzar dado para que pueda aleatorio o forzado
        public void lanzarDados() {
            Jugador actual = jugadores.get(turno);
            Avatar avatar = actual.getAvatar();

            int valor1 = dado1.getValor(); // usamos el valor que ya está en el dado
            int valor2 = dado2.getValor();
            int total = valor1 + valor2;

            System.out.println(actual.getNombre() + " lanza os dados: [" + valor1 + "," + valor2 + "] = " + total);

        }

     */
    public int getValor() { //consultar o valor do dado
        return valor;
    }

    @Override //non obligatorio pero recomendable
    public String toString() {
        return String.valueOf(valor); //deste modo o obxeto imprímese co dato valor
    }
}