package partida;


import java.util.Random;

public class Dado {
    //El dado solo tiene un atributo en nuestro caso: su valor.
    private int valor;

    public Dado(){
        this.valor = 0; //Inicializamos a 0
    }

    //Metodo para simular lanzamiento de un dado: devolverá un valor aleatorio entre 1 y 6.
    public int hacerTirada() {
        valor = (int)(Math.random() * 6) + 1; //devolve numero aleatorio entre 1 e 6
        return valor;
    }

    public int getValor() { //consultar o valor do dado
        return valor;
    }
}
