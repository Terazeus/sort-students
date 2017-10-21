package nl.hva.dmci.ict.se.datastructures;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * @author Donny Rozendal
 * 
 */
public class StudentTest {
    
    public static void main(String[] args) {
        final int studentNumberStart = 50080001;
        final int numberOfStudents = 10;
        
        List<Student> school = new ArrayList<>();
        
        /**
         * Gets all the groups, and adds them to a HashMap, so that we know
         * how many different groups there are.
         */
        String[] groups = KlasGenerator.maakKlassen(numberOfStudents);
        HashMap<String, Klas> groupMap = new HashMap<>();
        for (String group : groups) {
            groupMap.put(group, new Klas(group));
        }
        
        /**
         * Makes a linked list of the groups.
         */
        Klas firstGroup = null;
        Klas lastGroup = null;
        for (Klas klas : groupMap.values()) {
            if (firstGroup == null) {
                firstGroup = klas;
            } else if(lastGroup == null) {
                firstGroup.setNext(klas);
                lastGroup = klas;
            } else {
                lastGroup.setNext(klas);
                lastGroup = klas;
            }
        }
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
            
            school.add(new Student(i + studentNumberStart, grade));
        }
        /**
         * Assigns the students to the groups in the linked list.
         */
        int studentIndex = 0;
        for (int i = 0; i < (numberOfStudents / groupMap.size()); i++) {
            Klas tempKlas = firstGroup;
            for (int j = 0; j < groupMap.size(); j++) {
                if (tempKlas.getFirst() == null) {
                    tempKlas.setFirst(school.get(studentIndex));
                    studentIndex++;
                } else if(tempKlas.getLast() == null) {
                    tempKlas.setLast(school.get(studentIndex));
                    tempKlas.getFirst().setNext(tempKlas.getLast());
                    studentIndex++;
                } else {
                    tempKlas.getLast().setNext(school.get(studentIndex));
                    tempKlas.setLast(tempKlas.getLast().getNext());
                    studentIndex++;
                }
                tempKlas = tempKlas.getNext();
            }
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
        /**
         * Orders the groups
         */
        //Klas tempKlas2 = firstGroup;
        for (Klas tempKlas2 = firstGroup; tempKlas2.getNext() != null; tempKlas2 = tempKlas2.getNext()) {
            if (tempKlas2.compareTo(tempKlas2.getNext()) > 0) {
                if (tempKlas2 == firstGroup) {
                    firstGroup = tempKlas2.getNext();
                    tempKlas2.setNext(tempKlas2.getNext().getNext());
                    tempKlas2.getNext().setNext(tempKlas2);
                } else if (tempKlas2.getNext() == lastGroup) {
                    tempKlas2.getNext().setNext(tempKlas2);
                    tempKlas2.setNext(null);
                    lastGroup = tempKlas2;
                } else {
                    tempKlas2.setNext(tempKlas2.getNext().getNext());
                    tempKlas2.getNext().setNext(tempKlas2);
                }
            }
        }
        System.out.println("");
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
