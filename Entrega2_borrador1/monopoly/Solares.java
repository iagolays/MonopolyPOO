package monopoly;

import java.util.HashMap;
import java.util.Map;

/*
 * Clase para xestionar todos os datos dos solares do xogo Monopoly
 *
 */
public class Solares {

    /*Clase interna para gardar todos os datos dun solar
    É static porque non precisa acceder a ningún atributo nin metodo de Solares, só serve como modelo de datos
    (un conxunto de valores numéricos).
     */
    public static class DatosSolar {
        private float precio; // Prezo de compra do solar
        private float alquiler; // Aluguer base do solar
        private float valorCasa; // Valor para construír unha casa
        private float valorHotel; // Valor para construír un hotel
        private float valorPiscina; // Valor para construír unha piscina
        private float valorPista; // Valor para construír unha pista de deporte
        private float alquilerCasa; // Aluguer cando ten casa
        private float alquilerHotel; // Aluguer cando ten hotel
        private float alquilerPiscina; // Aluguer cando ten piscina
        private float alquilerPista; // Aluguer cando ten pista de deporte

        // Constructor para inicializar todos os valores dun solar
        public DatosSolar(float precio, float alquiler, float valorCasa, float valorHotel, float valorPiscina, float valorPista,
                          float alquilerCasa, float alquilerHotel, float alquilerPiscina, float alquilerPista) {

            this.precio = precio;
            this.alquiler = alquiler;
            this.valorCasa = valorCasa;
            this.valorHotel = valorHotel;
            this.valorPiscina = valorPiscina;
            this.valorPista = valorPista;
            this.alquilerCasa = alquilerCasa;
            this.alquilerHotel = alquilerHotel;
            this.alquilerPiscina = alquilerPiscina;
            this.alquilerPista = alquilerPista;
        }

        public float getPrecio() {
            return precio;
        }

        public float getAlquiler() {
            return alquiler;
        }

        public float getValorCasa() {
            return valorCasa;
        }

        public float getValorHotel() {
            return valorHotel;
        }

        public float getValorPiscina() {
            return valorPiscina;
        }

        public float getValorPista() {
            return valorPista;
        }

        public float getAlquilerCasa() {
            return alquilerCasa;
        }

        public float getAlquilerHotel() {
            return alquilerHotel;
        }

        public float getAlquilerPiscina() {
            return alquilerPiscina;
        }

        public float getAlquilerPista() {
            return alquilerPista;
        }
    }

    // Mapa onde gardamos todos os datos dos solares, é static porque pertence á clase e final porque non se pode cambiar a referencia do mapa (aínda que si se poden engadir elementos dentro).
    //HashMap é unha estrutura de datos que garda pares “string (clave) → DatosSolar (valor)”.
    public static final Map<String, DatosSolar> DATOS = new HashMap<>();


    static { // Bloque estático - execútase unha única vez, cando se carga a clase (antes de crear ningún obxecto).
        // Solar 1          O metodo put() engade un elemento novo ao mapa.
        DATOS.put("solar1", new DatosSolar(Valor.SOLAR1_PRECIO, Valor.SOLAR1_ALQUILER, Valor.SOLAR1_PRECIO_CASA, Valor.SOLAR1_PRECIO_HOTEL,
                Valor.SOLAR1_PRECIO_PISCINA, Valor.SOLAR1_PRECIO_PISTA, Valor.SOLAR1_ALQUILER_CASA, Valor.SOLAR1_ALQUILER_HOTEL,
                Valor.SOLAR1_ALQUILER_PISCINA, Valor.SOLAR1_ALQUILER_PISTA));

        // Solar 2
        DATOS.put("solar2", new DatosSolar(Valor.SOLAR2_PRECIO, Valor.SOLAR2_ALQUILER, Valor.SOLAR2_PRECIO_CASA, Valor.SOLAR2_PRECIO_HOTEL,
                Valor.SOLAR2_PRECIO_PISCINA, Valor.SOLAR2_PRECIO_PISTA, Valor.SOLAR2_ALQUILER_CASA, Valor.SOLAR2_ALQUILER_HOTEL,
                Valor.SOLAR2_ALQUILER_PISCINA, Valor.SOLAR2_ALQUILER_PISTA));

        // Solar 3
        DATOS.put("solar3", new DatosSolar(Valor.SOLAR3_PRECIO, Valor.SOLAR3_ALQUILER, Valor.SOLAR3_PRECIO_CASA, Valor.SOLAR3_PRECIO_HOTEL,
                Valor.SOLAR3_PRECIO_PISCINA, Valor.SOLAR3_PRECIO_PISTA, Valor.SOLAR3_ALQUILER_CASA, Valor.SOLAR3_ALQUILER_HOTEL,
                Valor.SOLAR3_ALQUILER_PISCINA, Valor.SOLAR3_ALQUILER_PISTA));

        // Solar 4
        DATOS.put("solar4", new DatosSolar(Valor.SOLAR4_PRECIO, Valor.SOLAR4_ALQUILER, Valor.SOLAR4_PRECIO_CASA, Valor.SOLAR4_PRECIO_HOTEL,
                Valor.SOLAR4_PRECIO_PISCINA, Valor.SOLAR4_PRECIO_PISTA, Valor.SOLAR4_ALQUILER_CASA, Valor.SOLAR4_ALQUILER_HOTEL,
                Valor.SOLAR4_ALQUILER_PISCINA, Valor.SOLAR4_ALQUILER_PISTA));

        // Solar 5
        DATOS.put("solar5", new DatosSolar(Valor.SOLAR5_PRECIO, Valor.SOLAR5_ALQUILER, Valor.SOLAR5_PRECIO_CASA, Valor.SOLAR5_PRECIO_HOTEL,
                Valor.SOLAR5_PRECIO_PISCINA, Valor.SOLAR5_PRECIO_PISTA, Valor.SOLAR5_ALQUILER_CASA, Valor.SOLAR5_ALQUILER_HOTEL,
                Valor.SOLAR5_ALQUILER_PISCINA, Valor.SOLAR5_ALQUILER_PISTA));

        // Solar 6
        DATOS.put("solar6", new DatosSolar(Valor.SOLAR6_PRECIO, Valor.SOLAR6_ALQUILER, Valor.SOLAR6_PRECIO_CASA, Valor.SOLAR6_PRECIO_HOTEL,
                Valor.SOLAR6_PRECIO_PISCINA, Valor.SOLAR6_PRECIO_PISTA, Valor.SOLAR6_ALQUILER_CASA, Valor.SOLAR6_ALQUILER_HOTEL,
                Valor.SOLAR6_ALQUILER_PISCINA, Valor.SOLAR6_ALQUILER_PISTA));

        // Solar 7
        DATOS.put("solar7", new DatosSolar(Valor.SOLAR7_PRECIO, Valor.SOLAR7_ALQUILER, Valor.SOLAR7_PRECIO_CASA, Valor.SOLAR7_PRECIO_HOTEL,
                Valor.SOLAR7_PRECIO_PISCINA, Valor.SOLAR7_PRECIO_PISTA, Valor.SOLAR7_ALQUILER_CASA, Valor.SOLAR7_ALQUILER_HOTEL,
                Valor.SOLAR7_ALQUILER_PISCINA, Valor.SOLAR7_ALQUILER_PISTA));

        // Solar 8
        DATOS.put("solar8", new DatosSolar(Valor.SOLAR8_PRECIO, Valor.SOLAR8_ALQUILER, Valor.SOLAR8_PRECIO_CASA, Valor.SOLAR8_PRECIO_HOTEL,
                Valor.SOLAR8_PRECIO_PISCINA, Valor.SOLAR8_PRECIO_PISTA, Valor.SOLAR8_ALQUILER_CASA, Valor.SOLAR8_ALQUILER_HOTEL,
                Valor.SOLAR8_ALQUILER_PISCINA, Valor.SOLAR8_ALQUILER_PISTA));

        // Solar 9
        DATOS.put("solar9", new DatosSolar(Valor.SOLAR9_PRECIO, Valor.SOLAR9_ALQUILER, Valor.SOLAR9_PRECIO_CASA, Valor.SOLAR9_PRECIO_HOTEL,
                Valor.SOLAR9_PRECIO_PISCINA, Valor.SOLAR9_PRECIO_PISTA, Valor.SOLAR9_ALQUILER_CASA, Valor.SOLAR9_ALQUILER_HOTEL,
                Valor.SOLAR9_ALQUILER_PISCINA, Valor.SOLAR9_ALQUILER_PISTA));

        // Solar 10
        DATOS.put("solar10", new DatosSolar(Valor.SOLAR10_PRECIO, Valor.SOLAR10_ALQUILER, Valor.SOLAR10_PRECIO_CASA, Valor.SOLAR10_PRECIO_HOTEL,
                Valor.SOLAR10_PRECIO_PISCINA, Valor.SOLAR10_PRECIO_PISTA, Valor.SOLAR10_ALQUILER_CASA, Valor.SOLAR10_ALQUILER_HOTEL,
                Valor.SOLAR10_ALQUILER_PISCINA, Valor.SOLAR10_ALQUILER_PISTA));

        // Solar 11
        DATOS.put("solar11", new DatosSolar(Valor.SOLAR11_PRECIO, Valor.SOLAR11_ALQUILER, Valor.SOLAR11_PRECIO_CASA, Valor.SOLAR11_PRECIO_HOTEL,
                Valor.SOLAR11_PRECIO_PISCINA, Valor.SOLAR11_PRECIO_PISTA, Valor.SOLAR11_ALQUILER_CASA, Valor.SOLAR11_ALQUILER_HOTEL,
                Valor.SOLAR11_ALQUILER_PISCINA, Valor.SOLAR11_ALQUILER_PISTA));

        // Solar 12
        DATOS.put("solar12", new DatosSolar(Valor.SOLAR12_PRECIO, Valor.SOLAR12_ALQUILER, Valor.SOLAR12_PRECIO_CASA, Valor.SOLAR12_PRECIO_HOTEL,
                Valor.SOLAR12_PRECIO_PISCINA, Valor.SOLAR12_PRECIO_PISTA, Valor.SOLAR12_ALQUILER_CASA, Valor.SOLAR12_ALQUILER_HOTEL,
                Valor.SOLAR12_ALQUILER_PISCINA, Valor.SOLAR12_ALQUILER_PISTA));

        // Solar 13
        DATOS.put("solar13", new DatosSolar(Valor.SOLAR13_PRECIO, Valor.SOLAR13_ALQUILER, Valor.SOLAR13_PRECIO_CASA, Valor.SOLAR13_PRECIO_HOTEL,
                Valor.SOLAR13_PRECIO_PISCINA, Valor.SOLAR13_PRECIO_PISTA, Valor.SOLAR13_ALQUILER_CASA, Valor.SOLAR13_ALQUILER_HOTEL,
                Valor.SOLAR13_ALQUILER_PISCINA, Valor.SOLAR13_ALQUILER_PISTA));

        // Solar 14
        DATOS.put("solar14", new DatosSolar(Valor.SOLAR14_PRECIO, Valor.SOLAR14_ALQUILER, Valor.SOLAR14_PRECIO_CASA, Valor.SOLAR14_PRECIO_HOTEL,
                Valor.SOLAR14_PRECIO_PISCINA, Valor.SOLAR14_PRECIO_PISTA, Valor.SOLAR14_ALQUILER_CASA, Valor.SOLAR14_ALQUILER_HOTEL,
                Valor.SOLAR14_ALQUILER_PISCINA, Valor.SOLAR14_ALQUILER_PISTA));

        // Solar 15
        DATOS.put("solar15", new DatosSolar(Valor.SOLAR15_PRECIO, Valor.SOLAR15_ALQUILER, Valor.SOLAR15_PRECIO_CASA, Valor.SOLAR15_PRECIO_HOTEL,
                Valor.SOLAR15_PRECIO_PISCINA, Valor.SOLAR15_PRECIO_PISTA, Valor.SOLAR15_ALQUILER_CASA, Valor.SOLAR15_ALQUILER_HOTEL,
                Valor.SOLAR15_ALQUILER_PISCINA, Valor.SOLAR15_ALQUILER_PISTA));

        // Solar 16
        DATOS.put("solar16", new DatosSolar(Valor.SOLAR16_PRECIO, Valor.SOLAR16_ALQUILER, Valor.SOLAR16_PRECIO_CASA, Valor.SOLAR16_PRECIO_HOTEL,
                Valor.SOLAR16_PRECIO_PISCINA, Valor.SOLAR16_PRECIO_PISTA, Valor.SOLAR16_ALQUILER_CASA, Valor.SOLAR16_ALQUILER_HOTEL,
                Valor.SOLAR16_ALQUILER_PISCINA, Valor.SOLAR16_ALQUILER_PISTA));

        // Solar 17
        DATOS.put("solar17", new DatosSolar(Valor.SOLAR17_PRECIO, Valor.SOLAR17_ALQUILER, Valor.SOLAR17_PRECIO_CASA, Valor.SOLAR17_PRECIO_HOTEL,
                Valor.SOLAR17_PRECIO_PISCINA, Valor.SOLAR17_PRECIO_PISTA, Valor.SOLAR17_ALQUILER_CASA, Valor.SOLAR17_ALQUILER_HOTEL,
                Valor.SOLAR17_ALQUILER_PISCINA, Valor.SOLAR17_ALQUILER_PISTA));

        // Solar 18
        DATOS.put("solar18", new DatosSolar(Valor.SOLAR18_PRECIO, Valor.SOLAR18_ALQUILER, Valor.SOLAR18_PRECIO_CASA, Valor.SOLAR18_PRECIO_HOTEL,
                Valor.SOLAR18_PRECIO_PISCINA, Valor.SOLAR18_PRECIO_PISTA, Valor.SOLAR18_ALQUILER_CASA, Valor.SOLAR18_ALQUILER_HOTEL,
                Valor.SOLAR18_ALQUILER_PISCINA, Valor.SOLAR18_ALQUILER_PISTA));

        // Solar 19
        DATOS.put("solar19", new DatosSolar(Valor.SOLAR19_PRECIO, Valor.SOLAR19_ALQUILER, Valor.SOLAR19_PRECIO_CASA, Valor.SOLAR19_PRECIO_HOTEL,
                Valor.SOLAR19_PRECIO_PISCINA, Valor.SOLAR19_PRECIO_PISTA, Valor.SOLAR19_ALQUILER_CASA, Valor.SOLAR19_ALQUILER_HOTEL,
                Valor.SOLAR19_ALQUILER_PISCINA, Valor.SOLAR1_ALQUILER_PISTA));

        // Solar 20
        DATOS.put("solar20", new DatosSolar(Valor.SOLAR20_PRECIO, Valor.SOLAR20_ALQUILER, Valor.SOLAR20_PRECIO_CASA, Valor.SOLAR20_PRECIO_HOTEL,
                Valor.SOLAR20_PRECIO_PISCINA, Valor.SOLAR20_PRECIO_PISTA, Valor.SOLAR20_ALQUILER_CASA, Valor.SOLAR20_ALQUILER_HOTEL,
                Valor.SOLAR20_ALQUILER_PISCINA, Valor.SOLAR20_ALQUILER_PISTA));

        // Solar 21
        DATOS.put("solar21", new DatosSolar(Valor.SOLAR21_PRECIO, Valor.SOLAR21_ALQUILER, Valor.SOLAR21_PRECIO_CASA, Valor.SOLAR21_PRECIO_HOTEL,
                Valor.SOLAR21_PRECIO_PISCINA, Valor.SOLAR21_PRECIO_PISTA, Valor.SOLAR21_ALQUILER_CASA, Valor.SOLAR21_ALQUILER_HOTEL,
                Valor.SOLAR21_ALQUILER_PISCINA, Valor.SOLAR21_ALQUILER_PISTA));

        // Solar 22
        DATOS.put("solar22", new DatosSolar(Valor.SOLAR22_PRECIO, Valor.SOLAR22_ALQUILER, Valor.SOLAR22_PRECIO_CASA, Valor.SOLAR22_PRECIO_HOTEL,
                Valor.SOLAR22_PRECIO_PISCINA, Valor.SOLAR22_PRECIO_PISTA, Valor.SOLAR22_ALQUILER_CASA, Valor.SOLAR22_ALQUILER_HOTEL,
                Valor.SOLAR22_ALQUILER_PISCINA, Valor.SOLAR22_ALQUILER_PISTA));
    }


    //metodos publicos para poder acceder aos datos

    // Metodo simple para obter datos dun solar
    public static DatosSolar obtenerDatos(String nombreSolar) {
        return DATOS.get(nombreSolar.toLowerCase());
    }

    // Metodo para obter o prezo
    public static float obtenerPrecio(String nombreSolar) {
        DatosSolar datos = obtenerDatos(nombreSolar);
        if (datos != null) {
            return datos.precio;
        }
        return 0; // Se non existe, devolvemos 0
    }

    // Metodo para obter o aluguer
    public static float obtenerAlquiler(String nombreSolar) {
        DatosSolar datos = obtenerDatos(nombreSolar);
        if (datos != null) {
            return datos.alquiler;
        }
        return 0; // Se non existe, devolvemos 0
    }

    // Metodo para obter o prezo da casa, para proximas entregas
    public static float obtenerPrecioCasa(String nombreSolar) {
        DatosSolar datos = obtenerDatos(nombreSolar);
        if (datos != null) {
            return datos.valorCasa;
        }
        return 0; // Se non existe, devolvemos 0
    }

    // Metodo para obter precio hotel, para proximas entregas
    public static float obtenerPrecioHotel(String nombreSolar) {
        DatosSolar datos = obtenerDatos(nombreSolar);
        if (datos != null) {
            return datos.valorHotel;
        }
        return 0; // Se non existe, devolvemos 0
    }

    // Metodo para obter precio piscina, para proximas entregas
    public static float obtenerPrecioPiscina(String nombreSolar) {
        DatosSolar datos = obtenerDatos(nombreSolar);
        if (datos != null) {
            return datos.valorPiscina;
        }
        return 0; // Se non existe, devolvemos 0
    }

    // Metodo para obter precio pista, para proximas entregas
    public static float obtenerPrecioPista(String nombreSolar) {
        DatosSolar datos = obtenerDatos(nombreSolar);
        if (datos != null) {
            return datos.valorPista;
        }
        return 0; // Se non existe, devolvemos 0
    }

    // Metodo para obter alquiler casa, para proximas entregas
    public static float obtenerAlquilerCasa(String nombreSolar) {
        DatosSolar datos = obtenerDatos(nombreSolar);
        if (datos != null) {
            return datos.alquilerCasa;
        }
        return 0; // Se non existe, devolvemos 0
    }

    // Metodo para obter alquiler hotel, para proximas entregas
    public static float obtenerAlquilerHotel(String nombreSolar) {
        DatosSolar datos = obtenerDatos(nombreSolar);
        if (datos != null) {
            return datos.alquilerHotel;
        }
        return 0; // Se non existe, devolvemos 0
    }

    // Metodo para obter alquiler Piscina, para proximas entregas
    public static float obtenerAlquilerPiscina(String nombreSolar) {
        DatosSolar datos = obtenerDatos(nombreSolar);
        if (datos != null) {
            return datos.alquilerPiscina;
        }
        return 0; // Se non existe, devolvemos 0
    }

    // Metodo para obter alquiler pista, para proximas entregas
    public static float obtenerAlquilerPista(String nombreSolar) {
        DatosSolar datos = obtenerDatos(nombreSolar);
        if (datos != null) {
            return datos.alquilerPista;
        }
        return 0; // Se non existe, devolvemos 0
    }
}