package monopoly;

import partida.*;
import java.util.ArrayList;


class Grupo {

    //Atributos
    private ArrayList<Casilla> miembros; //Casillas miembros del grupo.
    private String colorGrupo; //Color del grupo
    private int numCasillas; //Número de casillas del grupo.


    //Constructor vacío.
    public Grupo() {
        this.miembros = new ArrayList<>();  //Inicializa la lista de casillas
        this.colorGrupo = "";               //Color vacío por defecto
        this.numCasillas = 0;               //No hay casillas aún

    }

    /*Constructor para cuando el grupo está formado por DOS CASILLAS:
     * Requiere como parámetros las dos casillas miembro y el color del grupo.
     */
    public Grupo(Casilla cas1, Casilla cas2, String colorGrupo) {
        this.miembros = new ArrayList<>();   //Inicializamos a lista de membros
        this.miembros.add(cas1);               //Añadimos a primera casilla
        this.miembros.add(cas2);               //Añadimos a segunda casilla
        this.colorGrupo = colorGrupo;        //Asignamos a cor d grupo
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
        this.miembros.add(cas3);
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
        if (miembro != null && !miembros.contains(miembro)) {
            miembros.add(miembro);           // Engadimos a casilla á lista
            miembro.setGrupo(this);          // Establecemos este grupo na casilla
            this.numCasillas = miembros.size(); // Actualizamos o contador
            System.out.println("Casilla " + miembro.getNombre() + " engadida ao grupo " + colorGrupo);
        }
    }

    /**
     * Método que comproba se o xogador pasado ten no seu haber todas as casillas do grupo
     *
     * @param jugador - Xogador que se quere avaliar
     * @return true se é dono de todas as casillas do grupo, false noutro caso
     */
    public boolean esDuenhoGrupo(Jugador jugador) {
        if (jugador == null || miembros.isEmpty()) {
            return false;
        }

        // Comprobamos que o xogador sexa dono de TODAS as casillas do grupo
        for (Casilla casilla : miembros) {
            if (casilla.getDuenho() != jugador) {
                return false; // Se unha casilla non é do xogador, non ten o grupo completo
            }
        }

        return true; // O xogador é dono de todas as casillas do grupo
    }

    /**
     * Método que calcula cantas casillas do grupo ten un xogador
     *
     * @param jugador - Xogador a comprobar
     * @return Número de casillas do grupo que posúe o xogador
     */
    public int num_casillas_grupo(Jugador jugador) {
        if (jugador == null) {
            return 0;
        }

        int contador = 0;
        for (Casilla casilla : miembros) {
            if (casilla.getDuenho() == jugador) {
                contador++;
            }
        }

        return contador;
    }

    /**
     * Método estático para calcular a cor dunha casilla segundo o seu grupo
     *
     * @param casilla - Casilla para a que calcular a cor
     * @return String co código de cor ANSI
     */
    public static String calcularColor(Casilla casilla) {
        if (casilla == null || casilla.getGrupo() == null) {
            return Valor.WHITE + casilla.getNombre(); // Cor branca por defecto
        }

        String colorGrupo = casilla.getGrupo().getColorGrupo();

        // Asignar cores ANSI segundo o color do grupo
        switch (colorGrupo.toLowerCase()) {
            case "marrón":
            case "brown":
                return Valor.BLACK + casilla.getNombre(); // Negro para marrón (mellor visibilidade)

            case "azul claro":
            case "light blue":
                return Valor.CYAN + casilla.getNombre();

            case "rosa":
            case "pink":
                return Valor.PURPLE + casilla.getNombre();

            case "laranxa":
            case "orange":
                return Valor.YELLOW + casilla.getNombre(); // Amarelo para laranxa

            case "vermello":
            case "red":
                return Valor.RED + casilla.getNombre();

            case "amarelo":
            case "yellow":
                return Valor.YELLOW + casilla.getNombre();

            case "verde":
            case "green":
                return Valor.GREEN + casilla.getNombre();

            case "azul escuro":
            case "dark blue":
                return Valor.BLUE + casilla.getNombre();

            default:
                return Valor.WHITE + casilla.getNombre();
        }
    }

    /**
     * Obtén o número total de casillas no grupo
     *
     * @return Número de casillas no grupo
     */
    public int getNumCasillas() {
        return numCasillas;
    }

    /**
     * Obtén a lista de casillas membros do grupo
     *
     * @return ArrayList de casillas do grupo
     */
    public ArrayList<Casilla> getMiembros() {
        return new ArrayList<>(miembros); // Devolvemos copia para evitar modificacións externas
    }

    /**
     * Verifica se unha casilla pertence a este grupo
     *
     * @param casilla - Casilla a verificar
     * @return true se a casilla pertence ao grupo
     */
    public boolean contieneCasilla(Casilla casilla) {
        return miembros.contains(casilla);
    }

    /**
     * Obtén información do grupo como String
     *
     * @return String coa información do grupo
     */
    @Override
    public String toString() {
        return "Grupo{" +
                "color='" + colorGrupo + '\'' +
                ", numCasillas=" + numCasillas +
                ", miembros=" + miembros.size() +
                '}';
    }

    /**
     * Obtén información detallada do grupo
     *
     * @return String con información detallada
     */
    public String infoGrupo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Grupo ").append(colorGrupo).append(":\n");
        sb.append("  Número de casillas: ").append(numCasillas).append("\n");
        sb.append("  Casillas: ");
        for (int i = 0; i < miembros.size(); i++) {
            sb.append(miembros.get(i).getNombre());
            if (i < miembros.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public String getColorGrupo() {
        return colorGrupo;
    }

    public void setColorGrupo(String colorGrupo) {
        this.colorGrupo = colorGrupo;
    }

}