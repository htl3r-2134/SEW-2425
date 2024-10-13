package Cocktails;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CocktailBar {
    public static Set<String> getAvailableDrinks(String filename) throws IOException {
        BufferedReader in = Files.newBufferedReader(Path.of(filename), StandardCharsets.UTF_8);
        Map<String, Set<String>> drinks = new HashMap<>();
        Set<String> availableCocktails = new HashSet<>();
        String line;
        while ((line = in.readLine()) != null) {
            Set<String> ingredients = new TreeSet<>();
            if (!line.contains("#")) {
                if (line.contains(":")) {
                    if (line.split(":")[1].contains(",")) {
                            for (int i = 0; i < line.split(":")[1].split(",").length; i++) {
                                ingredients = new TreeSet<>();
                                    for (Map.Entry<String, Set<String>> drinks2: drinks.entrySet()) {
                                        if (drinks2.getKey().equals(line.split(":")[1].split(",")[i])) {
                                            ingredients.addAll(drinks2.getValue());
                                            break;
                                        }
                                    }
                                    ingredients.add(line.split(":")[0].toLowerCase().trim());
                                drinks.put(line.split(":")[1].split(",")[i].trim(), ingredients);
                            }
                    } else {
                        for (Map.Entry<String, Set<String>> drinks2: drinks.entrySet()) {
                            if (drinks2.getKey().equals(line.split(":")[1])) {
                                ingredients.addAll(drinks2.getValue());
                                break;
                            }
                        }
                        ingredients.add(line.split(":")[0].toLowerCase().trim());
                        drinks.put(line.split(":")[1].trim(), ingredients);
                    }
                } else if (line.contains(",") || !line.contains(",") && !line.contains(":")) {
                    for (Map.Entry<String, Set<String>> drinks2 : drinks.entrySet()) {
                        int truevalues = 0;
                        for (int i = 0; i < line.split(",").length; i++) {
                            if (drinks2.getValue().contains(line.split(",")[i].toLowerCase().trim())) {
                                truevalues++;
                            }
                        }
                        if (truevalues == drinks2.getValue().size()) {
                            availableCocktails.add(drinks2.getKey());
                        }
                    }
                }
            }
        }
        return availableCocktails;
    }
}
