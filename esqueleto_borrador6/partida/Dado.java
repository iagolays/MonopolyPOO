package partida;

import java.util.Random;

public class Dado {
    private int valor; //valor do dado
    private Random random; // xerador de números aleatorios

    public Dado(){
        this.valor = 1; //Inicializamos a 1, valor inicial do dado
        this.random = new Random(); // Inicializamos o xerador de números aleatorios
    }

    //Metodo para simular lanzamiento de un dado: devolverá un valor aleatorio entre 1 y 6.
    public int hacerTirada() {
        this.valor = random.nextInt(6) + 1; //nextInt(6) danos 6 posibles valores [0, 6), con random son aleatorios
        return this.valor;
    }

    //----------Metodos auxiliares

    //metodo para realizar un lanzamiento forzado sen sairse do rango
    public void lanzarForzado(int valorForzado){
        this.valor = Math.max(1, Math.min(6, valorForzado));
        //Math.min(6, valorForzado) da o valor mínimo entre 6 e valorForzado para non poder avanzar máis valores dos permitidos
        //Math.max(1, resultado) evita que o valor sexa menor ca 1
    }

    // Metodo estático para procesar "2+4" al forzar valores
    public static int[] procesarTiradaForzada(String entrada) {
        try {
            String[] partes = entrada.split("\\+"); //Separamos as partes por "+", "2+4" separa ["2", "4"]
            if (partes.length == 2) { //se hai dúas partes o primeiro corresponde ao primeiro dado e o segundo ao segundo dado
                int dado1 = Integer.parseInt(partes[0].trim());
                int dado2 = Integer.parseInt(partes[1].trim());

                //.trim() elimina espazos en branco ao principio e ao final para evitar erros
                //Integer.parseInt() converte un String a un número enteiro e lanza NumberFormatException se non é un número válido

                // Validamos que ambos valores estean no rango permitido [1,6]
                if (dado1 >= 1 && dado1 <= 6 && dado2 >= 1 && dado2 <= 6) {
                    return new int[]{dado1, dado2}; // Devolvemos os valores válidos
                }
            }
        } catch (NumberFormatException e) {
            // Capturamos errores de formato (ex: letras en vez de números)
        }
        return null; // Devolvemos null se o formato é inválido
    }

    public int getValor() { //consultar o valor do dado
        return valor;
    }

    @Override //non obligatorio pero recomendable
    public String toString() {
        return String.valueOf(valor); //deste modo o obxeto imprímese co dato valor
    }
}