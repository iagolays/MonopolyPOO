package monopoly;

import partida.*;
import java.util.ArrayList;

import static monopoly.Valor.FACTOR_SERVICIO;


/// ///////////////////////////////////////////
/// /////////////////////////////////////////
///
/// EN MOFIDICACIÓN (ROSA)
///
/// ////////////////////////////////////////
/// ////////////////////////////////////////

public class Casilla {

    //Atributos:
    private String nombre; //Nombre de la casilla
    private String tipo; //Tipo de casilla (Solar, Especial, Transporte, Servicios, Comunidad, Suerte y Impuesto).
    private float valor; //Valor de esa casilla (en la mayoría será valor de compra, en la casilla parking se usará como el bote).
    private int posicion; //Posición que ocupa la casilla en el tablero (entero entre 1 y 40).
    private Jugador duenho; //Dueño de la casilla (por defecto sería la banca).
    private Grupo grupo; //Grupo al que pertenece la casilla (si es solar).
    private float impuesto; //Cantidad a pagar por caer en la casilla: el alquiler en solares/servicios/transportes o impuestos.
    private float hipoteca; //Valor otorgado por hipotecar una casilla
    private ArrayList<Avatar> avatares; //Avatares que están situados en la casilla.
    private int hipotecada;  //Nueva clase, 0 si no está hipotecada, 1 si está hipotecada y 2 si no es una casilla hipotecable



    //Constructores:
    public Casilla() {

        this.nombre = "";
        this.tipo = "";
        this.valor = 0;
        this.posicion = 0;
        this.duenho = null;
        this.grupo = null;
        this.impuesto = 0;
        this.hipoteca = 0;
        this.avatares = new ArrayList<>();
        this.hipotecada = 0;

    }//Parámetros vacíos (definición de una nueva casilla)

    /*Constructor para casillas tipo Solar, Servicios o Transporte:
     * Parámetros: nombre casilla, tipo (debe ser solar, serv. o transporte), posición en el tablero, valor y dueño.
     */
    public Casilla(String nombre, String tipo, int posicion, float valor, Jugador duenho) {

        this.nombre = nombre;             //Definimos los parámetros que se nos pasan como argumentos de la función...
        this.tipo = tipo;
        this.posicion = posicion;
        this.valor = valor;
        this.duenho = duenho;
        this.grupo = null;
        this.impuesto = 0;
        this.hipoteca = 0;
        this.avatares = new ArrayList<>();

        switch(this.tipo){
            case "solar":
                this.hipotecada = 0;
                break;
            case "serv":
                this.hipotecada = 2;
                break;
            case "transporte":
                this.hipotecada = 2;
        }
                     //...y el resto los definimos como vacíos/nulos/por defecto


    }

    /*Constructor utilizado para inicializar las casillas de tipo IMPUESTOS.
     * Parámetros: nombre, posición en el tablero, impuesto establecido y dueño.
     */
    public Casilla(String nombre, int posicion, float impuesto, Jugador duenho) {

        this.nombre = nombre;              //Definimos los parámetros que se nos pasan como argumentos de la función...
        this.posicion = posicion;
        this.duenho = duenho;
        this.impuesto = impuesto;

        this.tipo = "";
        this.valor = 0;                    //...y el resto los definimos como vacíos/nulos/por defecto
        this.grupo = null;
        this.hipoteca = 0;
        this.avatares = new ArrayList<>();
        this.hipotecada = 2;
    }

    /*Constructor utilizado para crear las otras casillas (Suerte, Caja de comunidad y Especiales):
     * Parámetros: nombre, tipo de la casilla (será uno de los que queda), posición en el tablero y dueño.
     */
    public Casilla(String nombre, String tipo, int posicion, Jugador duenho) {

        this.nombre = nombre;
        this.tipo = tipo;                 //Definimos los parámetros que se nos pasan como argumentos de la función...
        this.posicion = posicion;
        this.duenho = duenho;

        this.valor = 0;
        this.grupo = null;                //...y el resto los definimos como vacíos/nulos/por defecto
        this.impuesto = 0;
        this.hipoteca = 0;
        this.avatares = new ArrayList<>();
        this.hipotecada = 2;

    }

    //Método utilizado para añadir un avatar al array de avatares en casilla.
    public void anhadirAvatar(Avatar av) {

        if (!avatares.contains(av)) {              //Comprobamos que el avatar que queremos añadir al array de avatares no está en el mismo (no queremos duplicados)
            avatares.add(av);                //Si no está en el array, lo añadimos
            System.out.println("Avatar añadido a la lista de jugadores.\n");       //Informamos que hemos añadido el avatar a la lista
        } else {
            System.out.println("El avatar ya está en la lista de jugadores. No se puede añadir de nuevo.\n");     //Si el avatar que queremos añadir ya está en la lista de avatares, no lo podemos añadir porque aparecería dos veces, e informamos de ello
        }
    }

    //Método utilizado para eliminar un avatar del array de avatares en casilla.
    public void eliminarAvatar(Avatar av) {

        if (avatares.contains(av)) {             //Solo podemos eliminar un avatar del array de avatares si está, por lo que lo comprobamos
            avatares.remove(av);        //Si el avatar está en la lista de avatares, lo eliminamos
            System.out.println("Avatar eliminado de la lista de jugadores.\n");        //Informamos de que hemos eliminado el avatar
        } else {
            System.out.println("No se puede eliminar el avatar porque no está en la lista de jugadores.\n");       //Si el avatar no está en el array, no lo podemos eliminar e informamos de ello
        }
    }

    /*Método para evaluar qué hacer en una casilla concreta. Parámetros:
     * - Jugador cuyo avatar está en esa casilla.
     * - La banca (para ciertas comprobaciones).
     * - El valor de la tirada: para determinar impuesto a pagar en casillas de servicios.
     * Valor devuelto: true en caso de ser solvente (es decir, de cumplir las deudas), y false
     * en caso de no cumplirlas.*/
    public boolean evaluarCasilla(Jugador actual, Jugador banca, int tirada) {

        //NOTA: Sabemos el tipo de casilla porque llamamos a esta función desde una casilla, en la que haya caído el jugador para evaluar. Luego sabemos el tipo de la casilla desde la que llamamos a la función
        switch (this.tipo.toLowerCase()) {                  //Accedemos al tipo de la casilla sobre la que hemos caído y lo convertimos a minúsculas para hacer el switch y ver de qué tipo es
            case "solar":        //En el caso de que la casilla sea del tipo solar
                if (this.duenho == null || this.duenho.equals(banca)) {        //Si la casilla no tiene dueño o el dueño es el banco, esto significa que el jugador puede comprarla
                    System.out.println("Esta casilla está en venta por " + this.valor + "€.");     //Informamos que la casilla está en venta y su precio (que también sabemos porque llamamos a la función desde la casilla
                    return true;           //Devolvemos true porque el jugador no tiene que pagar nada si no quiere, por lo que siempre podrá pasar
                } else if (!this.duenho.equals(actual)) {        //Si el jugador que está en la casilla no es su dueño, entonces tiene que pagar el alquiler que haya puesto el dueño
                    if (this.hipotecada == 1){      //Si es igual a 1, esto significa que la propiedad está hipotecada y por lo tanto no hay que hacer nada
                        System.out.println("La propiedad está hipotecada. No hay que hacer nada");
                    }else{        //Si la casilla tiene un dueño que no es la banca ni el jugador actual y tiene el solar en propiedad y no hipotecado, entonces el jugador actual tiene que pagarle el alquiler
                        System.out.println("Pagas alquiler de " + this.impuesto + "€ a " + duenho.getNombre());    //Informar al jugador de que hay que pagar el alquiler
                        boolean si_paga = actual.puedePagar(this.impuesto);       //Si el jugador puede pagar, si_paga es true
                        if (si_paga) {      //Si el jugador puede pagar
                            actual.sumarFortuna(-this.impuesto);     //Se le resta la el dinero que tiene que pagar
                            duenho.sumarFortuna(this.impuesto);        //Se le suma al dueño de la casilla el dinero que se le ha restado al jugador actual
                            return true;
                        } else {    //Si el jugador no puede parar, se le declara en bancarrota
                            System.out.println("No tienes suficiente dinero para pagar el alquiler.");
                            return false;
                        }
                    }
                }else{
                    System.out.println("El jugador actual es el dueño de esta casilla.");
                    ///     ////////////////////////////////////////////////////////////////////////////////////////////
                    ///      /////////////////////////////////////////////////////////////////////////////////////////
                    ///     AÑADIR PARTE DE PROPIEDADES EN CASO DE QUE EL JUGADOR SEA EL DUEÑO DE LA CASILLA
                    ///////////////////////////////////////////////////////////////////////////////////////////////////
                    ///     /////////////////////////////////////////////////////////////////////////////////////////
                }
            case "serv":
                if(this.duenho == null || this.duenho.equals(banca)) {    //Si no tiene dueño la casilla o si el dueño es la banca
                    System.out.println("Esta casilla está en venta por " + this.valor + "€.");     //Informamos que la casilla está en venta y su precio (que también sabemos porque llamamos a la función desde la casilla
                    return true;
                }else if(!this.duenho.equals(actual)){        //Si hay dueño pero no es la banca ni el jugador actual, si no otro jugador de la partida
//                    if (Grupo.esDuenhoGrupo(this.duenho)){     //Si además este otro jugador que es el dueño de esta casilla es el dueño de las otras casillas en servicios
                        int cantidad = (int) (10 * FACTOR_SERVICIO);      //Para usar una constante en la clase Valor de la misma carpeta, se hace con el formato "clase.constante". int antes de la operación en sí para que se marque que el resultado es un int al hacer una operación entre un int y un float
                        //Si el jugador es el dueño de todas las casillas del tipo, tiene que pagar el factor de servicio multiplicado por 10
                        System.out.println("Pagas " + cantidad + "al ser el jugador duenho de esta casilla de servicios dueño de todas las casillas de servicios.");
                        boolean si_paga = actual.puedePagar(cantidad);       //Si el jugador puede pagar, si_paga es true
                        if (si_paga) {      //Si el jugador puede pagar
                            actual.sumarFortuna(-cantidad);     //Se le resta la el dinero que tiene que pagar
                            duenho.sumarFortuna(cantidad);        //Se le suma al dueño de la casilla el dinero que se le ha restado al jugador actual
                            return true;
                        } else {    //Si el jugador no puede parar, se le declara en bancarrota
                            System.out.println("No tienes suficiente dinero para pagar el alquiler.");
                            return false;
                        }
                    }else{
                        int cantidad = (int) (4 * FACTOR_SERVICIO);        //Si el jugador es dueño solo de esta casilla del tipo, se multiplica el factor servicio por 4
                        boolean si_paga = actual.puedePagar(cantidad);       //Si el jugador puede pagar, si_paga es true
                        if (si_paga) {      //Si el jugador puede pagar
                            actual.sumarFortuna(-cantidad);     //Se le resta la el dinero que tiene que pagar
                            duenho.sumarFortuna(cantidad);        //Se le suma al dueño de la casilla el dinero que se le ha restado al jugador actual
                            return true;
                        } else {    //Si el jugador no puede pagar, se le declara en bancarrota
                            System.out.println("No tienes suficiente dinero para pagar el alquiler.");
                            return false;
                        }
                    }
                }else {
                    System.out.println("El jugador actual es el dueño de esta casilla.");
                    ///     ////////////////////////////////////////////////////////////////////////////////////////////
                    ///      /////////////////////////////////////////////////////////////////////////////////////////
                    ///     AÑADIR PARTE DE PROPIEDADES EN CASO DE QUE EL JUGADOR SEA EL DUEÑO DE LA CASILLA
                    ///////////////////////////////////////////////////////////////////////////////////////////////////
                    ///     /////////////////////////////////////////////////////////////////////////////////////////
                }
            case "transporte":
                if (this.duenho == null || this.duenho.equals(banca)) {        //Si la casilla no tiene dueño o el dueño es el banco, esto significa que el jugador puede comprarla
                    System.out.println("Esta casilla está en venta por " + this.valor + "€.");     //Informamos que la casilla está en venta y su precio (que también sabemos porque llamamos a la función desde la casilla
                    return true;           //Devolvemos true porque el jugador no tiene que pagar nada si no quiere, por lo que siempre podrá pasar
                } else if (!this.duenho.equals(actual)) {        //Si el jugador que está en la casilla no es su dueño, entonces tiene que pagar el alquiler que haya puesto el dueño
            //        int casillas = this.duenho.num_casillas_grupo();
                    System.out.println("Pagas alquiler de " + this.impuesto + "€ a " + duenho.getNombre());    //Informar al jugador de que hay que pagar el alquiler
                    boolean si_paga = actual.puedePagar(this.impuesto);       //Si el jugador puede pagar, si_paga es true
                    if (si_paga) {      //Si el jugador puede pagar
                        actual.sumarFortuna(-this.impuesto);     //Se le resta la el dinero que tiene que pagar
                        duenho.sumarFortuna(this.impuesto);        //Se le suma al dueño de la casilla el dinero que se le ha restado al jugador actual
                        return true;
                    } else {    //Si el jugador no puede parar, se le declara en bancarrota
                        System.out.println("No tienes suficiente dinero para pagar el alquiler.");
                        return false;
                    }
                } else{
                    System.out.println("El jugador actual es el dueño de esta casilla.");
                    ///     ////////////////////////////////////////////////////////////////////////////////////////////
                    ///      /////////////////////////////////////////////////////////////////////////////////////////
                    ///     AÑADIR PARTE DE PROPIEDADES EN CASO DE QUE EL JUGADOR SEA EL DUEÑO DE LA CASILLA
                    ///////////////////////////////////////////////////////////////////////////////////////////////////
                    ///     /////////////////////////////////////////////////////////////////////////////////////////
                }
        }         //Provisional
        return true;   //provisional porque default aún no está
    }
}     //Provisional
/*

            case "transporte":
                System.out.println("Pagas " + this.impuesto + "€ por uso de transporte.");
                if (this.duenho != null && !this.duenho.equals(actual)) {
                    boolean exito = actual.sumarFortuna(-this.impuesto);
                    if (exito) {
                        duenho.sumarFortuna(this.impuesto);
                        return true;
                    } else {
                        System.out.println("No puedes pagar el transporte.");
                        return false;
                    }
                }
                return true;

            case "impuesto":
                System.out.println("Pagas impuesto de " + this.impuesto + "€.");
                boolean exito = actual.sumarFortuna(-this.impuesto);
                if (exito) {
                    // Sumar al bote de parking
                    Casilla parking =     //encontrar la forma de localizar la casilla del parking
                    parking.sumarValor(this.impuesto);
                    return true;
                } else {
                    return false;
                }

            case "parking":
                System.out.println("Recibes " + this.valor + "€ del bote del parking.");
                actual.sumarFortuna(this.valor);
                this.valor = 0;
                return true;

            case "iracarcel":
                System.out.println("Vas directamente a la cárcel.");
                actual.encarcelar();           //Poner exactamente a dónde va a la cárcel
                return true;

            case "carcel":
                System.out.println("Estás en la cárcel.");
                return true;

            default:
                System.out.println("Tipo de casilla no reconocido.");
                return true;
        }

}
*/
    /*Método usado para comprar una casilla determinada. Parámetros:
    * - Jugador que solicita la compra de la casilla.
    * - Banca del monopoly (es el dueño de las casillas no compradas aún).*/
//    public void comprarCasilla(Jugador solicitante, Jugador banca) {

/*        if (this.duenho == null || this.duenho.equals(banca)) {
            if (solicitante.sumarFortuna(this.valor) == true) {
                solicitante.sumarFortuna(-this.valor);
                this.duenho = solicitante;
                solicitante.anadirPropiedad(this);
                System.out.println(solicitante.getNombre() + " ha comprado la casilla " + this.nombre + " por " + this.valor + "€.");
            } else {
                System.out.println("No tienes suficiente dinero para comprar esta casilla.");
            }
        } else {
            System.out.println("La casilla ya tiene dueño.");
        }
    }

 */

    /*Método para añadir valor a una casilla. Utilidad:
    * - Sumar valor a la casilla de parking.
    * - Sumar valor a las casillas de solar al no comprarlas tras cuatro vueltas de todos los jugadores.
    * Este método toma como argumento la cantidad a añadir del valor de la casilla.*/
//    public void sumarValor(float suma) {

/*        this.valor += suma;
        System.out.println("Se han añadido " + suma + "€ al valor de la casilla " + this.nombre);

    }


 */
    /*Método para mostrar información sobre una casilla.
    * Devuelve una cadena con información específica de cada tipo de casilla.*/
//    public String infoCasilla() {

/*       String dueñoStr;
        if (duenho == null) {
            dueñoStr = "Banca";
        } else {
            dueñoStr = duenho.getNombre();
        }

        String info = "Nombre: " + nombre + "\n" +
                "Tipo: " + tipo + "\n" +
                "Posición: " + posicion + "\n" +
                "Valor: " + valor + "€\n" +
                "Dueño: " + dueñoStr + "\n";

        switch (tipo.toLowerCase()) {
            case "solar":
                if (grupo != null) {
                    info += "Grupo: " + grupo.getNombre() + "\n" +
                            "Alquiler base: " + impuesto + "€\n" +
                            "Hipoteca: " + hipoteca + "€\n" +
                            "Edificaciones: " + grupo.anhadirPropiedad(this) + "\n";
                }
                break;
            case "impuesto":
                info += "Cantidad a pagar: " + impuesto + "€\n";
                break;
            case "parking":
                info += "Bote acumulado: " + valor + "€\n" +
                        "Avatares en la casilla: " + avatares.size() + "\n";
                break;
            case "carcel":
                info += "Precio para salir: 500000€\n";
                break;
        }

        return info;

    }

 */

    /* Método para mostrar información de una casilla en venta.
     * Valor devuelto: texto con esa información.
     */
//    public String casEnVenta() {

/*        if (this.duenho == null || this.duenho.getNombre().equalsIgnoreCase("Banca")) {
            return String.format("{tipo: %s, grupo: %s, valor: %.0f€}",
                    tipo.toLowerCase(),
                    grupo != null ? grupo.getNombre() : "-",
                    valor);
        }
        return null; // No está en venta



    }




//}



//

 */