package monopoly;

import partida.*;
import java.util.ArrayList;


class Grupo {

    //Atributos
    private ArrayList<Casilla> miembros; //Casillas miembros del grupo.
    private String colorGrupo; //Color del grupo
    private int numCasillas; //Número de casillas del grupo.


    //Constructor vacío, Crea un grupo sen casillas e sen cor definida.
    public Grupo() {
        this.miembros = new ArrayList<>();  //Inicializa a lista de casillas baleira
        this.colorGrupo = "";               //Color baleiro por defecto
        this.numCasillas = 0;               //Inicialízase sen casillas

    }

    /*Constructor para cuando el grupo está formado por DOS CASILLAS:
     * Requiere como parámetros las dos casillas miembro y el color del grupo.
     */
    public Grupo(Casilla cas1, Casilla cas2, String colorGrupo) {
        this.miembros = new ArrayList<>();   //Inicializamos a lista de membros
        this.miembros.add(cas1);               //Añadimos a primera casilla
        this.miembros.add(cas2);               //Añadimos a segunda casilla
        this.colorGrupo = colorGrupo;        //Asignamos a cor do grupo
        this.numCasillas = miembros.size();  //Gardamos o número de casillas (2 neste caso)

        //Vinculamos cada casilla con este grupo:
        cas1.setGrupo(this);
        cas2.setGrupo(this);
    }

    /*Constructor para cuando el grupo está formado por TRES CASILLAS:
     * Requiere como parámetros las tres casillas miembro y el color del grupo.
     */
    public Grupo(Casilla cas1, Casilla cas2, Casilla cas3, String colorGrupo) {

        this.miembros = new ArrayList<>();   //Inicializamos a lista de membros
        this.miembros.add(cas1);               //Añadimos a primera casilla
        this.miembros.add(cas2);               //Añadimos a segunda casilla
        this.miembros.add(cas3);               //Añadimos a terceira casilla
        this.colorGrupo = colorGrupo;        //Asignamos a color do grupo
        this.numCasillas = miembros.size();  //Guardamos o número de casillas (3 neste caso)

        //Vinculamos cada casilla con este grupo:
        cas1.setGrupo(this);
        cas2.setGrupo(this);
        cas3.setGrupo(this);

    }

    /* Metodo que añade una casilla al array de casillas miembro de un grupo.
     * Parámetro: casilla que se quiere añadir.
     */
    public void anhadirCasilla(Casilla miembro) {
        // Verificamos que a casilla sexa válida e non estea xa no grupo
        if (miembro != null && !miembros.contains(miembro)) {
            miembros.add(miembro);           // Engadimos a casilla á lista
            miembro.setGrupo(this);          // Establecemos este grupo na casilla
            this.numCasillas = miembros.size(); // Actualizamos o contador
            System.out.println("Casilla " + miembro.getNombre() + " engadida ao grupo " + colorGrupo);
        }
    }

    /*Metodo que comprueba si el jugador pasado tiene en su haber todas las casillas del grupo:
     * Parámetro: jugador que se quiere evaluar.
     * Valor devuelto: true si es dueño de todas las casillas del grupo, false en otro caso.
     */
    public boolean esDuenhoGrupo(Jugador jugador) {
        // Compróbase que o xogador non sexa inválido ou grupo baleiro
        if (jugador == null || miembros.isEmpty()){
            return false;
        }
        //Comprobamos cada casilla do grupo
        for (Casilla casilla : miembros){
            //Se algunha casilla non é do xogador non ten o grupo completo
            if (casilla.getDuenho() != jugador){
                System.out.println("O xogador " + jugador.getNombre() + " non é o dono do grupo completo");
                return false;
            }
        }
        //Todas as casillas son do xogador
        System.out.println("O xogador " + jugador.getNombre() + " é o dono do grupo completo");
        return true;
    }

    //----------Metodos auxiliares

    //Calcula o número de casillas do grupo que posúe un xogador.
    public int contarCasillasDeJugador(Jugador jugador) {
        if (jugador == null) {
            return 0; // Se o xogador é null, non ten ningunha casilla
        }

        int contador = 0; // Inicializa o contador a cero
        for (Casilla casilla : miembros) {
            if (casilla.getDuenho() == jugador) {
                contador++; // Incrementa o contador por cada casilla que é do xogador
            }
        }
        return contador; // Devolve o número total de casillas do xogador
    }

    //Metodo estático para calcular a cor dunha casilla segundo a súa posición.
    public static String calcularColor(Casilla c) {
        int pos = c.getPosicion();  // Obtén a posición da casilla (0-39)
        final String RESET = "\u001B[0m";  // Código ANSI para resetear a cor

        // Definición de cores ANSI para cada grupo
        final String MARRON = "\u001B[38;5;130m";    // Marrón (grupo de 2 solares)
        final String AZUL_CL = "\u001B[36m";         // Azul claro (grupo de 3 solares)
        final String ROSA = "\u001B[35m";            // Rosa (grupo de 3 solares)
        final String NARANJA = "\u001B[33m";         // Laranxa (grupo de 3 solares)
        final String ROJO = "\u001B[31m";            // Vermello (grupo de 3 solares)
        final String AMARILLO = "\u001B[93m";        // Amarelo (grupo de 3 solares)
        final String VERDE = "\u001B[32m";           // Verde (grupo de 3 solares)
        final String AZUL_OSC = "\u001B[34m";        // Azul escuro (grupo de 2 solares)

        // Asigna cores segundo a posición (CORRECCIÓN: posicións 0-39)
        if (pos == 1 || pos == 3)                    // Solar1 e Solar2 - Grupo Marrón
            return MARRON + c.getNombre() + RESET;
        if (pos == 6 || pos == 8 || pos == 9)        // Solar3, Solar4, Solar5 - Grupo Azul Claro
            return AZUL_CL + c.getNombre() + RESET;
        if (pos == 11 || pos == 13 || pos == 14)     // Solar6, Solar7, Solar8 - Grupo Rosa
            return ROSA + c.getNombre() + RESET;
        if (pos == 16 || pos == 18 || pos == 19)     // Solar9, Solar10, Solar11 - Grupo Laranxa
            return NARANJA + c.getNombre() + RESET;
        if (pos == 21 || pos == 23 || pos == 24)     // Solar12, Solar13, Solar14 - Grupo Vermello
            return ROJO + c.getNombre() + RESET;
        if (pos == 26 || pos == 27 || pos == 29)     // Solar15, Solar16, Solar17 - Grupo Amarelo
            return AMARILLO + c.getNombre() + RESET;
        if (pos == 31 || pos == 32 || pos == 34)     // Solar18, Solar19, Solar20 - Grupo Verde
            return VERDE + c.getNombre() + RESET;
        if (pos == 37 || pos == 39)                  // Solar21, Solar22 - Grupo Azul Escuro
            return AZUL_OSC + c.getNombre() + RESET;

        return c.getNombre(); // Se non é un solar ou non pertence a un grupo de cor
    }


    //Obtén o código de cor ANSI para este grupo.
    public String obtenerColor() {
        // Usa un switch para asignar diferentes códigos ANSI segundo a cor do grupo
        switch (this.colorGrupo.toLowerCase()) {
            case "marrón":
            case "brown":
                return "\u001B[90m"; // Gris escur0 (mellor visibilidade para marrón)

            case "azul claro":
            case "light blue":
                return "\u001B[96m"; // Cian claro

            case "rosa":
            case "pink":
                return "\u001B[95m"; // Rosa/morado claro

            case "laranxa":
            case "orange":
                return "\u001B[38;5;214m"; // Laranxa (código RGB extendido)

            case "vermello":
            case "red":
                return "\u001B[91m"; // Vermello claro

            case "amarelo":
            case "yellow":
                return "\u001B[93m"; // Amarelo claro

            case "verde":
            case "green":
                return "\u001B[92m"; // Verde claro

            case "azul escuro":
            case "dark blue":
                return "\u001B[94m"; // Azul claro

            default:
                return "\u001B[97m"; // Branco (cor por defecto)
        }
    }

    //Aplica a cor do grupo ao nome dunha casilla para a súa visualización.
    public String aplicarColorACasilla(String nombreCasilla) {
        return this.obtenerColor() + nombreCasilla + "\u001B[0m";
        // \u001B[0m é o código ANSI para resetear a cor ao final
    }

    //Verifica se unha casilla específica pertence a ese grupo.
    public boolean contieneCasilla(Casilla casilla) {
        return miembros.contains(casilla); // Usa o metodo contains() de ArrayList para buscar a casilla
    }

    //Obtén unha lista cos nomes de todas as casillas do grupo.
    public ArrayList<String> obtenerNomesCasillas() {
        ArrayList<String> nomes = new ArrayList<>(); // Crea unha nova lista para os nomes
        for (Casilla casilla : miembros) {
            nomes.add(casilla.getNombre()); // Engade o nome de cada casilla á lista
        }
        return nomes; // Devolve a lista completa de nomes
    }

    public int getNumCasillas() {
        return numCasillas;
    }

    public String getColorGrupo() {
        return colorGrupo;
    }



    public ArrayList<Casilla> getMiembros() {
        return miembros;
    }

    //Representación en texto do grupo.
    @Override
    public String toString() {
        return "Grupo{color='" + colorGrupo + "', casillas=" + numCasillas + "}";
        // Formato: Grupo{color='Rosa', casillas=3}
    }

    //Obtén información detallada do grupo para mostrar ao usuario.
    public String obtenerInfoGrupo() {
        StringBuilder info = new StringBuilder(); // Crea un StringBuilder para construír o texto
        info.append("{\n");
        info.append("Grupo ").append(colorGrupo).append(":\n");
        info.append("número de solares: ").append(numCasillas).append(",\n");
        info.append("solares: [");

        for (int i = 0; i < miembros.size(); i++) {
            info.append(miembros.get(i).getNombre());
            if (i < miembros.size() - 1) {
                info.append(", ");
            }
        }
        info.append("]\n");
        return info.toString();
    }
}