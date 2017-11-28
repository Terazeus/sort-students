package nl.hva.dmci.ict.se.datastructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Donny Rozendal
 * This program takes a list of students and grades, and sorts them using the
 * Merge Sorting method.
 */
public class MergeSort {
    public static final int AMOUNT = 100;
    
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
        
        // Merge sort the list and time it.
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
        
        //System.out.println("\nAfter sorting:\n");
        //print(school);
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
        if (size <= 1) {
            return school;
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
        for (int i = 0; i < school.size()/3 + 1; i++) {
            Student first = school.get(i);
            Student second = school.get(i + school.size()/3 + 1);
            Student third;
            try {
                third = school.get(i + 2 * (school.size() / 3 + 1));
            } catch (Exception e) {
                third = null;
            }
            
            System.out.println(first + "  " + second + "  " + third);
        }
    }
}
