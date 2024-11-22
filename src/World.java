import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import static java.lang.Double.parseDouble;

public class World {
    // Attribut: liste des aéroports
    private ArrayList<Aeroport> list;

    // Constructeur
    public World(String fileName) {
        list = new ArrayList<>();
        try {
            BufferedReader buf = new BufferedReader(new FileReader(fileName));
            String s = buf.readLine();

            while (s != null) {
                s = s.replaceAll("\"", ""); // Enlever les guillemets
                String[] fields = s.split(",");

                // Vérification que c'est un "large_airport"
                if (fields[1].equals("large_airport")) {
                    try {
                        // Création d'un objet Aeroport
                        Aeroport aeroport = new Aeroport(
                                fields[9],// IATA
                                fields[2], // Nom
                                fields[5],// Pays
                                Double.parseDouble(fields[12]), // Latitude
                                Double.parseDouble(fields[11])  // Longitude
                        );
                        list.add(aeroport); // Ajout à la liste
                    } catch (Exception e) {
                        System.out.println("Erreur de parsing pour l'aéroport : " + s);
                    }
                }
                s = buf.readLine();
            }
            buf.close();
        } catch (Exception e) {
            System.out.println("Peut-être que le fichier n'est pas là ?");
            e.printStackTrace();
        }
    }

    // Méthode pour trouver un aéroport par code IATA
    public Aeroport findByCode(String IATA) {
        for (Aeroport aeroport : list) {
            if (aeroport.getIATA().equals(IATA)) {
                return aeroport;
            }
        }
        return null; // Retourne null si non trouvé
    }

    // Méthode pour calculer la distance entre deux points (en km)
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta1 = Math.toRadians(lat1);
        double theta2 = Math.toRadians(lat2);
        double deltaTheta = Math.toRadians(lat2 - lat1);
        double deltaLambda = Math.toRadians(lon2 - lon1);

        double a = Math.sin(deltaTheta / 2) * Math.sin(deltaTheta / 2)
                + Math.cos(theta1) * Math.cos(theta2)
                * Math.sin(deltaLambda / 2) * Math.sin(deltaLambda / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double earthRadius = 6371.0; // Rayon de la Terre en kilomètres
        return earthRadius * c;
    }

    // Trouver l'aéroport le plus proche
    public Aeroport findNearestAirport(double lat, double lon) {
        Aeroport nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (Aeroport aeroport : list) {
            double dist = distance(lat, lon, aeroport.getLatitude(), aeroport.getLongitude());
            if (dist < minDistance) {
                minDistance = dist;
                nearest = aeroport;
            }
        }
        return nearest;
    }

    // Getter pour la liste des aéroports
    public ArrayList<Aeroport> getList() {
        return list;
    }

    // Méthode main pour les tests
    public static void main(String[] args){
        World w = new World ("src/data/airport-codes_no_comma.csv");
        System.out.println("Found "+w.getList().size()+" airports.");
        Aeroport paris = w.findNearestAirport(48.866,2.316);
        Aeroport cdg = w.findByCode("CDG");
        double distance = w.distance(48.866, 2.316,paris.getLongitude(),paris.getLatitude());
        System.out.println(paris);
        System.out.println(distance);
        double distanceCDG = w.distance(48.866,2.316,cdg.getLongitude(),cdg.getLatitude());
        System.out.println(cdg);
        System.out.println(distanceCDG);

    }
}
