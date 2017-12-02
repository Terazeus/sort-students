package nl.hva.dmci.ict.se.datastructures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;

/**
 * @author Donny Rozendal
 * @author Hugo Thunnissen
 *
 * This program takes a list of students and grades, and sorts them using the
 * Merge Sorting method.
 */
public class MergeSort {
    public static final int AMOUNT = 100;
    public static final int CUTOFF = 10;
    
    public static void main(String[] args) {
        // First make a list of students with random grades.
        final int NUMBER_OF_STUDENTS = Integer.parseInt(args[0]);
        int studentNumberStart = 50060001;
        List<Student> school = new ArrayList<>();
        
        for (int i = 0; i < NUMBER_OF_STUDENTS; i++) {            
            school.add(new Student(studentNumberStart, randomGrade()));
            studentNumberStart++;
        }
        
        // Shuffle the list.
        Collections.shuffle(school);
        //System.out.println("Before sorting:\n");
        //print(school);
        System.out.println("Before sorting:");
        print(school);
        
        //Merge sort the list and time it.
        long total = 0;
        for (int i = 0; i < AMOUNT; i++) {
            long startTime = System.nanoTime();
            mergeSort(school);
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            System.out.println("\nThe sorting took: " + duration + " ns");
            total = total + duration;
        }
        long average;
        System.out.println("The average execution time is " + total / (AMOUNT * 1000000) + " ms");
       


        //school = mergeSort(school);
        //System.out.println("\nAfter sorting:\n");
        //print(school);

        school = mergeSort(school);
        
        System.out.println("\nAfter sorting:");
        print(school);

        /**
         * Testing the Binary search tree
         */
        StudentBinarySearchTree bst = new StudentBinarySearchTree();
        Set<Double> grades = new HashSet<>();

        school.forEach((Student student) -> {
            bst.put(student.getCijfer(), student.getStudentNumber());
            grades.add(student.getCijfer());
        });

        final List<Student> locSchool = school;
        grades.forEach((Double grade) -> {
            System.out.printf("==== Studenten met cijfer %f ====\n\n", grade);

            System.out.println("Voor BST:");
            filter(locSchool, (Student student) -> {
                return grade == student.getCijfer();
            }).forEach((Student student) -> {
                System.out.printf(
                        "Studentnr: %d cijfer: %f\n", 
                        student.getStudentNumber(), 
                        student.getCijfer());
            });

            System.out.println("\n\nNa BST:");
            bst.get(grade).forEach((Integer snr) -> {
                System.out.printf( "Studentnr: %d cijfer: %f\n", snr, grade);
            });

        });

    }

    /**
     * Filter a list  of students for students that match a predicate.
     *
     * @param students
     * @param filter
     *
     * @return filteredStudents
     */
    public static List<Student> filter (List<Student> students, Predicate<Student> filter) {
       List<Student> filteredStudents = new ArrayList<>();
        students.forEach((Student student) -> {
            if (filter.test(student)){
                filteredStudents.add(student);
            }
        });

        return filteredStudents;
    }

    /**
     * This method uses the Random class to generate a random double with two
     * decimals.
     * @return : The random grade.
     */
    public static double randomGrade() {
        final Random GENERATOR = new Random();
        int minGrade = 10;
        int maxGrade = 100;
        
        int number = GENERATOR.nextInt(maxGrade - minGrade + 1) + minGrade;
        double grade = (double) number / 10;
        return grade;
    }
    /**
     * This is a recursive method which constantly splits the list of students
     * in half to smaller sublists, and then merges all those lists with the
     * merge method.
     * @param school : The list of students to be sorted.
     * @return : The sorted list of students.
     */
    public static List<Student> mergeSort(List<Student> school) {
        int size = school.size();
        if (size <= CUTOFF) {
            return insertionSort(school);
        } else {
            int center = school.size() / 2;
            List<Student> left = new ArrayList<>();
            List<Student> right = new ArrayList<>();
            for (int i = 0; i < center; i++) {
                left.add(school.get(i));
            }
            for (int i = center; i < school.size(); i++) {
                right.add(school.get(i));
            }
            left = mergeSort(left);
            right = mergeSort(right);
            return merge(left, right);
        }
    }

    /**
     * Insertionsort as List of Students.
     *
     * @param  school
     * @return sortedList
     */
    public static List<Student> insertionSort(List<Student> school) {
        int i = 1;
        while (i < school.size()) {
            int j = i;
            while (j > 0 && school.get(j-1).compareTo(school.get(j)) > 0){
                Student sw = school.get(j);
                school.set(j, school.get(j-1));
                school.set(j-1, sw);
                j--;
            }
            i++;
        }

        return school;
    }

    /**
     * This methods merges two sublists using the compareTo method in the
     * Student class.
     * @param left : The left sublist of students.
     * @param right : The right sublist of students.
     * @return The merged list of the two sublists.
     */
    public static List<Student> merge(List<Student> left, List<Student> right) {
        List<Student> merge = new ArrayList<>();
        int leftIndex = 0;
        int rightIndex = 0;
        int total = left.size() + right.size();
        
        for (int i = 0; i < total; i++) {
            if (leftIndex >= left.size()) {
                merge.add(right.get(rightIndex++));
            }
            else if (rightIndex >= right.size()) {
                merge.add(left.get(leftIndex++));
            }
            else if (left.get(leftIndex).compareTo(right.get(rightIndex)) == -1) {
                merge.add(left.get(leftIndex++));
            }
            else {
                merge.add(right.get(rightIndex++));
            }
        }
        return merge;
    }
    
    public static void print(List<Student> school) {
        school.forEach((Student student) -> {
            System.out.println(student);
        });
    }

    private static class StudentBinarySearchTree extends BinarySearchTree<Double, ArrayList<Integer>> {

        public void put (Double key, Integer value) {
            root = put(root, key, value);
        }

        private Node put (Node node, Double key, Integer value) {
            System.out.println("Putting");
            if (node == null)
                return new Node(new ArrayList<Integer>(Arrays.asList(value)), key, 1);

            int comparison = node.getKey().compareTo(key);

            if (comparison > 0) {
                node.setLeft(put(node.getLeft(), key, value));
            } else if (comparison < 0) {
                node.setRight(put(node.getRight(), key, value));
            } else {
                node.getValue().add(value);
            }
            node.setSubTreeAmount(size(node.getLeft()) + size(node.getRight()) + 1);
            return node;
        }
    }
}
