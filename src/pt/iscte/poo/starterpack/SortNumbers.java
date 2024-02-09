package pt.iscte.poo.starterpack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.PrintWriter;

public class SortNumbers {
    public static void main(String[] args) {
    	
        List<Integer> numbers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("Scores.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                numbers.add(Integer.parseInt(line));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Collections.sort(numbers, Collections.reverseOrder());
        
        try (PrintWriter pw = new PrintWriter("Scores.txt")) {
            for (int number : numbers) {
                pw.println(number);
            }
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        // Print the sorted numbers
        for (int number : numbers) {
            System.out.println(number);
        }
    }
}