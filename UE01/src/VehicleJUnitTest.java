import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

class VehicleJUnitTest {

    private List<Vehicle> testVehicles;
    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        testVehicles = Arrays.asList(
                new Bike(1),
                new Car(5, 500),
                new Truck(2, 10000, true),
                new Bike(2),
                new Car(7, 700),
                new Truck(3, 20000, false)
        );
    }

    @Test
    void testBikeCreation() {
        Bike bike = new Bike(2);
        assertEquals(2, bike.getSeats());
        assertEquals(0, bike.getCapacity());
    }

    @Test
    void testCarCreation() {
        Car car = new Car(5, 500);
        Assertions.assertEquals(5, car.getSeats());
        Assertions.assertEquals(500, car.getCapacity());
    }

    @Test
    void testTruckCreation() {
        Truck truck = new Truck(3, 15000, true);
        Assertions.assertEquals(3, truck.getSeats());
        Assertions.assertEquals(15000, truck.getCapacity());
        Assertions.assertTrue(truck.hasTailLift());
    }

    @Test
    void testBikeToString() {
        Bike bike = new Bike(1);
        assertEquals("Bike: seats: 1", bike.toString());
    }

    @Test
    void testCarToString() {
        Car car = new Car(5, 500);
        Assertions.assertEquals("Car: seats: 5, capacity: 500 kg", car.toString());
    }

    @Test
    void testTruckToString() {
        Truck truck = new Truck(2, 10000, true);
        Assertions.assertEquals("Truck: seats: 2, capacity: 10000 kg, has tail lift", truck.toString());
    }



    @Test
    void testSumSeats() {
        Assertions.assertEquals(20, VehicleTest.sumSeats(testVehicles));
    }

    @Test
    void testSumCapacity() {
        Assertions.assertEquals(31200, VehicleTest.sumCapacity(testVehicles));
    }

    @Test
    void testCountTailLifts() {
        Assertions.assertEquals(1, VehicleTest.countTailLifts(testVehicles));
    }

    @Test
    void testReadVehiclesValidFile() throws IOException {
        Path testFile = tempDir.resolve("test_vehicles.csv");
        Files.writeString(testFile, "1\n5;500\n2;10000;1\n");
        List<Vehicle> vehicles = VehicleTest.readVehicles(testFile);
        Assertions.assertEquals(3, vehicles.size());
        Assertions.assertTrue(vehicles.get(0) instanceof Bike);
        Assertions.assertTrue(vehicles.get(1) instanceof Car);
        Assertions.assertTrue(vehicles.get(2) instanceof Truck);
    }

    @Test
    void testReadVehiclesInvalidLine() throws IOException {
        Path testFile = tempDir.resolve("test_invalid.csv");
        Files.writeString(testFile, "1\n5;500\ninvalid\n2;10000;1\n");
        List<Vehicle> vehicles = VehicleTest.readVehicles(testFile);
        Assertions.assertEquals(3, vehicles.size());
    }

    @Test
    void testReadVehiclesEmptyFile() throws IOException {
        Path testFile = tempDir.resolve("test_empty.csv");
        Files.writeString(testFile, "");
        List<Vehicle> vehicles = VehicleTest.readVehicles(testFile);
        Assertions.assertTrue(vehicles.isEmpty());
    }

    @Test
    void testSumSeatsEmptyList() {
        Assertions.assertEquals(0, VehicleTest.sumSeats(List.of()));
    }

    @Test
    void testSumCapacityEmptyList() {
        Assertions.assertEquals(0, VehicleTest.sumCapacity(List.of()));
    }

    @Test
    void testCountTailLiftsEmptyList() {
        Assertions.assertEquals(0, VehicleTest.countTailLifts(List.of()));
    }

    @Test
    void testReadVehiclesNonexistentFile() throws IOException {
        Path nonexistentFile = tempDir.resolve("nonexistent.csv");
        List<Vehicle> vehicles = VehicleTest.readVehicles(nonexistentFile);
        Assertions.assertTrue(vehicles.isEmpty());
    }



    @Test
    void testVehiclePolymorphism() {
        List<Vehicle> vehicles = Arrays.asList(
                new Bike(1),
                new Car(5, 500),
                new Truck(2, 10000, true)
        );
        Assertions.assertEquals(8, VehicleTest.sumSeats(vehicles));
        Assertions.assertEquals(10500, VehicleTest.sumCapacity(vehicles));
    }

    @Test
    void testLargeNumberOfVehicles() {
        List<Vehicle> largeList = new ArrayList<Vehicle>();
        for (int i = 0; i < 10000; i++) {
            largeList.add(new Car(4, 400));
        }
        Assertions.assertEquals(40000, VehicleTest.sumSeats(largeList));
        Assertions.assertEquals(4000000, VehicleTest.sumCapacity(largeList));
    }

    @Test
    void testEdgeCaseValues() {
        Bike zeroBike = new Bike(0);
        Car zeroCar = new Car(0, 0);
        Truck zeroTruck = new Truck(0, 0, false);
        List<Vehicle> edgeCases = Arrays.asList(zeroBike, zeroCar, zeroTruck);
        Assertions.assertEquals(0, VehicleTest.sumSeats(edgeCases));
        Assertions.assertEquals(0, VehicleTest.sumCapacity(edgeCases));
        Assertions.assertEquals(0, VehicleTest.countTailLifts(edgeCases));
    }
}