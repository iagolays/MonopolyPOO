package monopoly;

import partida.Jugador;

public class Edificacion {

    public enum Tipo{CASA, HOTEL, PISCINA, PISTA_DEPORTE};

    private final String id;
    private final Tipo tipo;
    private final String solar;
    private final float coste;

    public Edificacion(Tipo tipo, String solar, float coste){
        this.tipo = tipo;
        this.solar = solar;
        this.coste = coste;
        this.id = generarId(tipo, solar);
    }

    public String getId() { return id; }
    public Tipo getTipo() { return tipo; }
    public String getSolar() { return solar; }
    public float getCoste() { return coste; }

    public static String generarId(Tipo tipo, String casilla) {
        long t = System.currentTimeMillis() % 100000;
        return tipo.name().toLowerCase() + "-" + casilla + "-" + t;
    }

    public static void construir(Jugador jugador, String tipoEdificio, Tablero tablero) {
        Casilla casilla = jugador.getAvatar().getLugar();

        if (casilla == null) {
            System.out.println("No se encontró la posición del jugador.");
            return;
        }

        if (!"solar".equalsIgnoreCase(casilla.getTipo())) {
            System.out.println("Solo se puede edificar en solares.");
            return;
        }

        // Comprobar propietario
        if (casilla.getDuenho() != jugador) {
            System.out.println("No eres el dueño de " + casilla.getNombre());
            return;
        }

        // Comprobar grupo completo
        if (!casilla.getGrupo().esDuenhoGrupo(jugador)) {
            System.out.println("Debes poseer todo el grupo para edificar.");
            return;
        }

        String resultado;
        switch (tipoEdificio.toLowerCase()) {
            case "casa":
                resultado = construirCasa(jugador, casilla);
                break;
            case "hotel":
                resultado = construirHotel(jugador, casilla);
                break;
            case "piscina":
                resultado = construirPiscina(jugador, casilla);
                break;
            case "pista":
            case "pista_deporte":
                resultado = construirPista(jugador, casilla);
                break;
            default:
                resultado = "Tipo de edificación no válido.";
                break;
        }

        System.out.println(resultado);
    }


    private static String construirCasa(Jugador j, Casilla c) {
        if (c.hasHotel()) {
            return "No se puede edificar casas: ya existe un hotel en " + c.getNombre() + ".";
        }
        int existentes = c.getNumCasas();
        if (existentes >= 4) {
            return "No se puede edificar más casas en " + c.getNombre() + ". Máximo 4 casas.";
        }

        float precioCasa = Solares.obtenerPrecioCasa(c.getNombre());
        // Construir hasta llegar a 4 casas (se puede adaptar para construir una sola)
        int aConstruir = 4 - existentes;
        float costeTotal = precioCasa * aConstruir;

        if (j.getFortuna() < costeTotal) {
            return "La fortuna de " + j.getNombre() + " no es suficiente para edificar " + aConstruir +
                    " casa(s) en " + c.getNombre() + ".";
        }

        // aplicar construcción
        c.construirCasas(aConstruir);
        j.sumarFortuna(-costeTotal);
        j.sumarGastos(costeTotal);

        String id = Edificacion.generarId(Edificacion.Tipo.CASA, c.getNombre());
        j.getEdificios().add(id);

        return "Se ha edificado " + aConstruir + " casa(s) en " + c.getNombre() + ". La fortuna de " +
                j.getNombre() + " se reduce en " + Math.round(costeTotal) + "€.";

    }

    private static String construirHotel(Jugador j, Casilla c) {
        if (c.hasHotel()) {
            return "No se puede edificar otro hotel en " + c.getNombre() + ".";
        }
        if (c.getNumCasas() < 4) {
            return "No se puede edificar un hotel: hacen falta 4 casas en " + c.getNombre() + ".";
        }
        float precioHotel = Solares.obtenerPrecioHotel(c.getNombre());
        if (j.getFortuna() < precioHotel) {
            return "La fortuna de " + j.getNombre() + " no es suficiente para edificar un hotel en la solar " + c.getNombre() + ".";
        }

        String id = Edificacion.generarId(Edificacion.Tipo.HOTEL, c.getNombre());
        c.construirHotel(); //método existente que sustituye casas por hotel
        j.sumarFortuna(-precioHotel);
        j.sumarGastos(precioHotel);

        return "Se ha edificado un hotel en " + c.getNombre() + ". La fortuna de " + j.getNombre() +
                " se reduce en " + Math.round(precioHotel) + "€.";
    }

    private static String construirPiscina(Jugador j, Casilla c) {
        if (!c.hasHotel()) {
            return "No se puede edificar una piscina: no existe hotel en " + c.getNombre() + ".";
        }
        if (c.hasPiscina()) {
            return "No se puede edificar otra piscina en " + c.getNombre() + ".";
        }
        float precioPiscina = Solares.obtenerPrecioPiscina(c.getNombre());
        if (j.getFortuna() < precioPiscina) {
            return "La fortuna de " + j.getNombre() + " no es suficiente para edificar una piscina en " + c.getNombre() + ".";
        }

        String id = Edificacion.generarId(Edificacion.Tipo.PISCINA, c.getNombre());
        c.construirPiscina();
        j.sumarFortuna(-precioPiscina);
        j.sumarGastos(precioPiscina);

        return "Se ha edificado una piscina en " + c.getNombre() + ". La fortuna de " + j.getNombre() +
                " se reduce en " + Math.round(precioPiscina) + "€.";
    }

    private static String construirPista(Jugador j, Casilla c) {
        if (!c.hasHotel()) {
            return "No se puede edificar una pista de deporte: no existe hotel en " + c.getNombre() + ".";
        }
        if (!c.hasPiscina()) {
            return "No se puede edificar una pista de deporte: requiere piscina previa en " + c.getNombre() + ".";
        }
        if (c.hasPista()) {
            return "No se puede edificar otra pista de deporte en " + c.getNombre() + ".";
        }
        float precioPista = Solares.obtenerPrecioPista(c.getNombre());
        if (j.getFortuna() < precioPista) {
            return "La fortuna de " + j.getNombre() + " no es suficiente para edificar una pista en " + c.getNombre() + ".";
        }

        String id = Edificacion.generarId(Edificacion.Tipo.PISTA_DEPORTE, c.getNombre());
        c.construirPista();
        j.sumarFortuna(-precioPista);
        j.sumarGastos(precioPista);

        return "Se ha edificado una pista de deporte en " + c.getNombre() + ". La fortuna de " + j.getNombre() +
                " se reduce en " + Math.round(precioPista) + "€.";
    }

}
