import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;

public class Aeroport {
    // Attributs de la classe
    private String IATA;
    private String name;
    private String country;
    private double latitude;
    private double longitude;

    // Constructeur
    public Aeroport(String IATA, String name, String country, double latitude, double longitude) {
        this.IATA = IATA;
        this.name = name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    // Getters
    public String getIATA() {
        return IATA;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    // Surcharge de la méthode toString
    @Override
    public String toString() {
        return "Aeroport{" +
                "IATA='" + IATA + '\'' +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    // Méthode principale pour les tests
    public static void main(String[] args) {
        // Création d'une instance d'Aeroport
        Aeroport aeroport = new Aeroport("CDG", "Charles de Gaulle", "France", 49.0097, 2.5479);

        // Tests
        System.out.println(aeroport); // Appelle la méthode toString automatiquement
        System.out.println("IATA: " + aeroport.getIATA());
        System.out.println("Latitude: " + aeroport.getLatitude());
        System.out.println("Longitude: " + aeroport.getLongitude());
    }
}
