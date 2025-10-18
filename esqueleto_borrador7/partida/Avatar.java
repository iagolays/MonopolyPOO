package partida;

import monopoly.*;

import java.util.ArrayList;
import java.util.Random;


public class Avatar {

    //Atributos
    private String id; //Identificador: una letra generada aleatoriamente.
    private String tipo; //Sombrero, Esfinge, Pelota, Coche
    private Jugador jugador; //Un jugador al que pertenece ese avatar, cada avatar pertenece a un único jugador.
    private Casilla lugar; //Los avatares se sitúan en casillas del tablero.

    //Constructor vacío
    public Avatar() {

    }

    /*Constructor principal. Requiere éstos parámetros:
    * Tipo del avatar, jugador al que pertenece, lugar en el que estará ubicado, y un arraylist con los
    * avatares creados (usado para crear un ID distinto del de los demás avatares).
    */

    public Avatar(String tipo, Jugador jugador, Casilla lugar, ArrayList<Avatar> avCreados) {
        //Asignamos os parámetros recividos os atributos do obxecto
        this.tipo = tipo;
        this.jugador = jugador;
        this.lugar = lugar;
        this.generarId(avCreados); //xenera un ID único (A-Z y 0-9) que non estéa repetido en avCreados
        avCreados.add(this); //engadimos o avatar a unha lista de avatares creados

        // Engadimos o avatar á casilla inicial (se o metodo existe)
        if (this.lugar != null) {
            this.lugar.anhadirAvatar(this);
        }
        System.out.println("Avatar creado: " + this.id + " (Tipo: " + this.tipo + ") para " + jugador.getNombre());
    }

    //A continuación, tenemos otros métodos útiles para el desarrollo del juego.
    /*Metodo que permite mover a un avatar a una casilla concreta. Parámetros:
    * - Un array con las casillas del tablero. Se trata de un arrayList de arrayList de casillas (uno por lado).
    * - Un entero que indica el numero de casillas a moverse (será el valor sacado en la tirada de los dados).
    * EN ESTA VERSIÓN SUPONEMOS QUE valorTirada siemrpe es positivo.
     */
    public void moverAvatar(ArrayList<ArrayList<Casilla>> casillas, int valorTirada) {
        //Creamos unha lista que conteña en orden todas as casillas do taboleiro para que sea mais comodo traballar
        ArrayList<Casilla> todasCasillas = new ArrayList<>();
        for(ArrayList<Casilla> lado : casillas){
            todasCasillas.addAll(lado); // Engadimos todas as casillas de cada lado á lista
        }
        //Atopamos a posición actual do avatar
        int posicionActual = -1;
        for (int i = 0; i < todasCasillas.size(); i++) {
            if (todasCasillas.get(i) == this.lugar) {
                posicionActual = i; //gardamos a posición na que se atopa o avatar
                break;
            }
        }
        if (posicionActual == -1){
            System.out.println("Non se puido atopar a posición do avatar " + this.id);
            return; //Se non se atopa, saímos
        }

        // Gardamos o nome da casilla antiga (para imprimir logo)
        String casillaAnterior = lugar.getNombre();

        // Calculamos a nova posición aplicando módulo (permite que o avatar de voltas completas ao taboleiro)
        int novaPos = (posicionActual + valorTirada) % todasCasillas.size();

        int posicionFinal = novaPos;
        boolean pasoPorSalida = false;

        // Se a nova posición é menor que a actual, significa que deu unha volta completa
        if (novaPos < posicionActual) {
            pasoPorSalida = true;
        }
        // Ou se pasou exactamente pola posición 0 (Salida)
        else if (posicionActual + valorTirada >= todasCasillas.size()) {
            pasoPorSalida = true;
        }

        // Se pasou pola Salida, sumar diñeiro
        if (pasoPorSalida) {
            this.jugador.sumarFortuna(Valor.SUMA_VUELTA);
        }

        Casilla casillaDestino = todasCasillas.get(novaPos);

        // Se a casilla destino é "IrCarcel", mover directamente á Cárcere
        if ("IrCarcel".equalsIgnoreCase(casillaDestino.getNombre())) {

            // Buscar a casilla Cárcere
            Casilla carcel = null;
            for (Casilla casilla : todasCasillas) {
                if ("Carcel".equalsIgnoreCase(casilla.getNombre())) {
                    carcel = casilla;
                    break;
                }
            }

            if (carcel != null) {
                // Mover o avatar á Cárcere
                lugar.eliminarAvatar(this);
                lugar = carcel;
                carcel.anhadirAvatar(this);

                // Encarcelar ao xogador
                this.jugador.encarcelar(casillas);

                System.out.println("O avatar " + this.id + " avanza " + valorTirada +
                        " posiciones, dende " + casillaAnterior + " ata IrACárcel. O avatar colócase na casilla de Cárcel.");
                return; // Saímos do metodo aquí
            }
        }

        // Movemento normal (se non cae en IrCarcel)
        String casillaAnteriorNombre = lugar.getNombre();
        lugar.eliminarAvatar(this);
        lugar = casillaDestino;
        lugar.anhadirAvatar(this);

        // Mensaxe base
        String mensaje = "O avatar " + this.id + " avanza " + valorTirada +
                " posiciones, dende " + casillaAnteriorNombre + " ata " + lugar.getNombre() + ".";

        // Engadir información específica segundo o tipo de casilla
        switch (lugar.getTipo().toLowerCase()) {
            case "solar":
                if (lugar.getDuenho() != null && lugar.getDuenho() != this.jugador &&
                        !"Banca".equalsIgnoreCase(lugar.getDuenho().getNombre())) {
                    mensaje += " Pagouse " + (int)lugar.getImpuesto() + "€ de alquiler.";
                }
                break;
            case "servicio":
                Jugador banca = obtenerBanca(todasCasillas); // Necesitas obter a referencia á banca
                if (banca != null){
                    boolean resultado = lugar.evaluarServicioPublico(this.jugador, banca, valorTirada);
                }
                break;
            case "transporte":
                if (lugar.getDuenho() != null && lugar.getDuenho() != this.jugador && !"Banca".equalsIgnoreCase(lugar.getDuenho().getNombre())) {
                    float alquiler = lugar.getImpuesto();
                    boolean pagoExitoso = this.jugador.pagarJugador(lugar.getDuenho(), alquiler);
                    if (pagoExitoso) {
                        mensaje += " Pagouse " + (int)alquiler + "€ de alquiler a " + lugar.getDuenho().getNombre() + ".";
                    } else {
                        mensaje += " Non se puido pagar o alquiler de " + (int)alquiler + "€.";
                    }
                }
                break;
            case "parking":
                float bote = lugar.getValor();
                if (bote > 0) {
                    mensaje += " O xogador " + this.jugador.getNombre() + " recibe " + (int)lugar.getValor() + "€.";
                    this.jugador.sumarFortuna(bote);
                    lugar.setValor(0); // Reiniciar bote
                }
                break;
            case "impuesto":
                if (this.jugador.puedePagar(lugar.getImpuesto())) {
                    this.jugador.sumarFortuna(-lugar.getImpuesto());
                    mensaje += " O xogador paga " + (int) lugar.getImpuesto() + "€ que se depositan no Parking.";
                    // Engadir ao bote do Parking
                    this.jugador.sumarFortuna(-lugar.getImpuesto());
                    Casilla parking = null;
                    for (Casilla casilla : todasCasillas) {
                        if ("Parking".equalsIgnoreCase(casilla.getNombre())) {
                            parking = casilla;
                            break;
                        }
                    }
                    if (parking != null) {
                        parking.setValor(parking.getValor() + lugar.getImpuesto());
                    }
                } else {
                    mensaje += " O xogador non pode pagar o imposto de " + (int)lugar.getImpuesto() + "€.";
                }
                break;
        }

        System.out.println(mensaje);
    }

    /*Metodo que permite generar un ID para un avatar. Sólo lo usamos en esta clase (por ello es privado).
    * El ID generado será una letra mayúscula. Parámetros:
    * - Un arraylist de los avatares ya creados, con el objetivo de evitar que se generen dos ID iguales.
     */
    private void generarId(ArrayList<Avatar> avCreados) {
        Random rand = new Random(); //xenera números aleatorios
        String novoId;
        boolean idValido; //indica que o ID non está repetido

        //buscamos mentras o ID non sexa válido
        do {
            //xeramos números aleatorios entre 0-35, letras maiusculas + numeros
            int r = rand.nextInt(36);

            if (r < 26){ //usamos unha letra da A-Z
                novoId = String.valueOf((char) ('A' + r)); // Convertimos o número a letra (0--A, 1--B)
            }
            else{ //usamos os números de 0-9
                novoId = String.valueOf((char) ('0' + (r -26))); // Convertimos o número a díxito (26--0, 27--1)
            }

            //comprobamos que o ID non estea en uso
            idValido = true; //asumimos que é válido inicialmente
            for (Avatar av : avCreados){
                if (av.getId().equalsIgnoreCase(novoId)){
                    idValido = false; //está repetido e temos que buscar outro
                    break; //non fai falta seguir buscando
                }
            }
        } while (!idValido);

        //Asignamos o ID ao avatar
        this.id = novoId;
    }

    //----------Metodos auxiliares

    private Jugador obtenerBanca(ArrayList<Casilla> todasCasillas) {
        // Buscar unha casilla que teña como dono á banca
        for (Casilla casilla : todasCasillas) {
            if (casilla.getDuenho() != null && "Banca".equalsIgnoreCase(casilla.getDuenho().getNombre())) {
                return casilla.getDuenho();
            }
        }
        // Se non se atopa, crear unha banca temporal
        System.out.println("Non se atopou a banca, creando unha temporal...");
        return new Jugador(); // Isto crea unha banca co constructor vacío
    }
    public String getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public Casilla getLugar() {
        return lugar;
    }

    public void setLugar(Casilla lugar) {
        this.lugar = lugar;
    }

    @Override
    public String toString() {
        // Útil para comandos tipo "listar jugadores"
        return "{avatar: " + this.id + ", tipo: " + this.tipo + "}";
    }
}
