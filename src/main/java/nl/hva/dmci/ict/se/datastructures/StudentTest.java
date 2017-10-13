package nl.hva.dmci.ict.se.datastructures;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Donny Rozendal
 * 
 */
public class StudentTest {
    
    public static void main(String[] args) {
        final int studentNumberStart = 50080001;
        final int numberOfStudents = 10000;
        
        List<Student> school = new ArrayList<>();
        String[] klassen = KlasGenerator.maakKlassen(numberOfStudents);
        
        /**
         * Assign students to school with random grade and ascending student
         * number.
         */
        Random generator = new Random();
        int minGrade = 10;
        int maxGrade = 100;
        
        for (int i = 0; i < numberOfStudents; i++) {
            int number = generator.nextInt(maxGrade - minGrade + 1) + minGrade;
            double grade = (double)number / 10;
            
            school.add(new Student(i + studentNumberStart, klassen[i], grade));
        }
        
        int[] count = new int[91];
        double temp;
        /**
         * Gets every student grade and increases its corresponding spot in the
         * count array.
         */
        for (Student student : school) {
            temp = student.getCijfer();
            count[(int)(temp * 10 - 10)]++;
        }
        /**
         * Prints the results.
         */
        for (double i = 0; i < count.length; i++) {
            System.out.println((i / 10) + 1 + "; " + count[(int)i]);
        }
        
        /**
         * Writes the results to a .CSV file.
         */
        try {
            printCSV(count);
        } catch (Exception e) {
            System.out.println("File not found!");
        }
        
        /**
         * Compares every student in the list to the next one, according
         * to the compareTo method in the Student class, and then swaps if
         * necessary. This sorting method is called bubble sorting.
         */
        for (int h = 0; h < school.size(); h++) {
            for (int i = 0; i < school.size() - 1; i++) {
                if (school.get(i).compareTo(school.get(i + 1)) == 1) {
                    Student tmp = school.get(i);
                    school.set(i, school.get(i + 1));
                    school.set(i + 1, tmp);
                }
            }
        }
        school.forEach((student) -> {
            System.out.println(student);
        });
        
        Recursion check = new Recursion();
        if (check.isStijgend(school)) {
            System.out.println("Deze rij is stijgend!");
        } else {
            System.out.println("Deze rij is niet stijgend!");
        }
        
        
        
        
    }
    
    public static void printCSV(int[] count) throws FileNotFoundException{
        PrintWriter pw = new PrintWriter(new File("test.csv"));
        for (double i = 0; i < count.length; i++) {
            pw.write((i / 10) + 1 + "; " + count[(int)i] + "\n");
        }
        pw.close();
        System.out.println("File written!");
    }
    
}
