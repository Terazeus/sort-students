package nl.hva.dmci.ict.se.datastructures;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Donny Rozendal
 * 
 */
public class StudentTest {
    
    private static HashMap<String, Klas> groupMap = new HashMap<>();
    private static Klas firstGroup;
    private static Klas lastGroup;
    private static final Random generator = new Random();
    private static int studentNumberStart = 50080001;
    
    public static void main(String[] args) {
//        int studentNumberStart = 50080001;
        final int numberOfStudents = 321;
        
        List<Student> school = new ArrayList<>();
        
        /**
         * Gets all the groups, and adds them to a HashMap, so that we know
         * how many different groups there are.
         */
        String[] groups = KlasGenerator.maakKlassen(numberOfStudents);
        
        for (String group : groups) {
            groupMap.put(group, new Klas(group));
        }
        
        /**
         * Makes a linked list of the groups.
         */
//        Klas firstGroup = null;
//        Klas lastGroup = null;
        for (Klas klas : groupMap.values()) {
            if (firstGroup == null) {
                firstGroup = klas;
            } else if(lastGroup == null) {
                firstGroup.setNext(klas);
                klas.setPrevious(firstGroup);
                lastGroup = klas;
            } else {
                lastGroup.setNext(klas);
                klas.setPrevious(lastGroup);
                lastGroup = klas;
            }
        }
        /**
         * Assign students to school with random grade and ascending student
         * number.
         */
//        Random generator = new Random();
//        int minGrade = 10;
//        int maxGrade = 100;
        
        for (int i = 0; i < numberOfStudents; i++) {
//            int number = generator.nextInt(maxGrade - minGrade + 1) + minGrade;
//            double grade = (double)number / 10;
            
            school.add(new Student(studentNumberStart, randomGrade()));
            studentNumberStart++;
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
                    tempKlas.increaseSize();
                } else if(tempKlas.getLast() == null) {
                    tempKlas.setLast(school.get(studentIndex));
                    tempKlas.getFirst().setNext(tempKlas.getLast());
                    studentIndex++;
                    tempKlas.increaseSize();
                } else {
                    tempKlas.getLast().setNext(school.get(studentIndex));
                    tempKlas.setLast(tempKlas.getLast().getNext());
                    studentIndex++;
                    tempKlas.increaseSize();
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
        
//        Recursion check = new Recursion();
//        if (check.isStijgend(school)) {
//            System.out.println("Deze rij is stijgend!");
//        } else {
//            System.out.println("Deze rij is niet stijgend!");
//        }
        /**
         * Orders the groups
         */
        for (int i = 0; i < groupMap.size(); i++) {
            for (Klas tempKlas = firstGroup; tempKlas.getNext() != null; tempKlas = tempKlas.getNext()) {
                if (tempKlas.compareTo(tempKlas.getNext()) > 0) {
                    if (tempKlas == firstGroup) {
                        Klas tempNext = tempKlas.getNext();
                        tempKlas.setNext(tempKlas.getNext().getNext());
                        tempKlas.setPrevious(tempNext);
                        tempKlas.getPrevious().setPrevious(null);
                        tempKlas.getPrevious().setNext(tempKlas);
                        tempKlas.getNext().setPrevious(tempKlas);
                        firstGroup = tempKlas.getPrevious();
                    } else if (tempKlas.getNext() == lastGroup) {
                        Klas tempPrevious = tempKlas.getPrevious();
                        tempKlas.setPrevious(tempKlas.getNext());
                        tempKlas.setNext(null);
                        tempKlas.getPrevious().setNext(tempKlas);
                        tempKlas.getPrevious().setPrevious(tempPrevious);
                        tempKlas.getPrevious().getPrevious().setNext(tempKlas.getPrevious());
                        lastGroup = tempKlas;
                        break;
                    } else {
                        Klas tempPrevious = tempKlas.getPrevious();
                        tempKlas.setPrevious(tempKlas.getNext());
                        tempKlas.setNext(tempKlas.getNext().getNext());
                        tempKlas.getPrevious().setNext(tempKlas);
                        tempKlas.getPrevious().setPrevious(tempPrevious);
                        tempKlas.getPrevious().getPrevious().setNext(tempKlas.getPrevious());
                        tempKlas.getNext().setPrevious(tempKlas);
                    }
                }
            }
        }
        System.out.println("");
        System.out.println("Add a new student? y/n");
        Scanner scan = new Scanner(System.in);
        String answer = scan.next();
        if (answer.equals("y")) {
            addStudent();
        }
        if (answer.equals("n")) {
            System.out.println(":(");
        }
    }
    
    public static double randomGrade() {
        int minGrade = 10;
        int maxGrade = 100;
        
        int number = generator.nextInt(maxGrade - minGrade + 1) + minGrade;
        double grade = (double) number / 10;
        return grade;
    }
    
    public static void printCSV(int[] count) throws FileNotFoundException{
        PrintWriter pw = new PrintWriter(new File("test.csv"));
        for (double i = 0; i < count.length; i++) {
            pw.write((i / 10) + 1 + "; " + count[(int)i] + "\n");
        }
        pw.close();
        System.out.println("File written!");
    }
    
    public static void addStudent() {
        studentNumberStart++;
        Student student = new Student(studentNumberStart, randomGrade());
        Klas group = firstGroup;
        
        for (int i = 0; i < groupMap.size(); i++) {
            if (group.getSize() < 32) {
                break;
            } else {
                group = group.getNext();
            }
        }
        StuNumbComparator snc = new StuNumbComparator();
        Student temp = group.getFirst();
        for (int i = 0; i < group.getSize(); i++) {
            if (snc.compare(student, temp) == 1) {
                if (temp.getNext() == null || snc.compare(student, temp.getNext()) == -1) {
                    Student tempNext = temp.getNext();
                    temp.setNext(student);
                    student.setNext(tempNext);
                    if (student.getNext() == null) {
                        group.setLast(student);
                    }
                } else {
                    temp = temp.getNext();
                }
            } else {
                student.setNext(temp);
                group.setFirst(student);
            }
        }
        
        System.out.println("Student added to group: " + group);
        System.out.println("");
    }
    
    public static class StuNumbComparator implements Comparator<Student> {

        @Override
        public int compare(Student s1, Student s2) {            
            Integer n1 = s1.getStudentNumber();
            Integer n2 = s2.getStudentNumber();
            
            return n1.compareTo(n2);
        }
        
    }
}
