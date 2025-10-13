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

        this.miembros = new ArrayList<>();   //Inicializamos la lista de miembros
        this.miembros.add(cas1);               //Añadimos la primera casilla
        this.miembros.add(cas2);               //Añadimos la segunda casilla
        this.colorGrupo = colorGrupo;        //Asignamos el color del grupo
        this.numCasillas = miembros.size();  //Guardamos el número de casillas (2 en este caso)

        //Vinculamos cada casilla con este grupo:
        cas1.setGrupo(this);
        cas2.setGrupo(this);
    }

    /*Constructor para cuando el grupo está formado por TRES CASILLAS:
    * Requiere como parámetros las tres casillas miembro y el color del grupo.
     */
    public Grupo(Casilla cas1, Casilla cas2, Casilla cas3, String colorGrupo) {

        this.miembros = new ArrayList<>();   //Inicializamos la lista de miembros
        this.miembros.add(cas1);               //Añadimos la primera casilla
        this.miembros.add(cas2);               //Añadimos la segunda casilla
        this.miembros.add(cas3)
        this.colorGrupo = colorGrupo;        //Asignamos el color del grupo
        this.numCasillas = miembros.size();  //Guardamos el número de casillas (2 en este caso)

        //Vinculamos cada casilla con este grupo:
        cas1.setGrupo(this);
        cas2.setGrupo(this);
        cas3.setGrupo(this);

    }

    /* Metodo que añade una casilla al array de casillas miembro de un grupo.
    * Parámetro: casilla que se quiere añadir.
     */
    public void anhadirCasilla(Casilla miembro) {

    }

    public String getColorGrupo() {
        return colorGrupo;
    }

    public void setColorGrupo(String colorGrupo) {
        this.colorGrupo = colorGrupo;
    }


/*Metodo que comprueba si el jugador pasado tiene en su haber todas las casillas del grupo:
    * Parámetro: jugador que se quiere evaluar.
    * Valor devuelto: true si es dueño de todas las casillas del grupo, false en otro caso.
     */
/*    public static boolean esDuenhoGrupo(Jugador jugador) {

    }

    public int num_casillas_grupo(Jugador jugador){

    }
}
*/