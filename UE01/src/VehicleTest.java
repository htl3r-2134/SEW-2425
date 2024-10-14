import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class VehicleTest extends Vehicle {

    public static List<Vehicle> readVehicles(Path file) throws IOException {
        List<Vehicle> VehiclesZero = new ArrayList<>();
        try {
            BufferedReader vehicles = Files.newBufferedReader(file, StandardCharsets.UTF_8);
            List<Vehicle> Vehicles = new ArrayList<>();


            String line;
            while ((line = vehicles.readLine()) != null) {
                String[] werte = line.split(";");
                if (werte.length > 3) {
                    throw new IOException("nur 3 Werte in einer Zeile erlaubt!");
                }
                for (int i = 0; i < werte.length; i++) {
                    for (int j = 0; j < werte[i].length(); j++) {
                        if (Character.isLetter(werte[i].charAt(j))) {
                            werte[i] = "";
                        }
                    }
                }
                if (werte.length == 1 && !werte[0].isEmpty()) {
                    Vehicles.add(new Bike(Integer.parseInt(werte[0])));
                } else if (werte.length == 2 && !werte[1].isEmpty()) {
                    Vehicles.add(new Car(Integer.parseInt(werte[0]), Integer.parseInt(werte[1])));
                } else if (werte.length == 3 && !werte[2].isEmpty()) {
                    Vehicles.add(new Truck(Integer.parseInt(werte[0]), Integer.parseInt(werte[1]), Boolean.parseBoolean(werte[2])));
                }
            }
            return Vehicles;
        } catch (NoSuchFileException e) {
            return VehiclesZero;
        }
    }

    public static int sumSeats(List<Vehicle> vehicles) {
        int seats = 0;
        for (Vehicle i : vehicles) {
            seats += i.getSeats();
        }
        return seats;
    }

    public static int sumCapacity(List<Vehicle> vehicles) {
        int capacity = 0;
        for (Vehicle i : vehicles) {
            capacity += i.getCapacity();
        }
        return capacity;
    }

    public static int countTailLifts(List<Vehicle> vehicles) {
        int hasTailLift = 0;
        for (Vehicle i : vehicles) {
            if (i.hasTailLift) {
                hasTailLift++;
            }
        }
        return hasTailLift;
    }


}
