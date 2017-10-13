package nl.hva.dmci.ict.se.datastructures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * @author Donny Rozendal
 * 
 */
public class StudentTest {
    
    public static void main(String[] args) {
        final int studentNumberStart = 50080001;
        
        List<Student> school = new ArrayList<>();
        
        Random generator = new Random();
        int minGrade = 10;
        int maxGrade = 100;
        int derp1 = 1;
        for (int i = studentNumberStart; i < (studentNumberStart + 10000); i++) {
            int number = generator.nextInt(maxGrade - minGrade + 1) + minGrade;
            double grade = (double)number / 10;
            
            school.add(new Student(i, grade));
        }
        
        int[] count = new int[91];
        double temp;
        
        for (Student student : school) {
            temp = student.getCijfer();
            count[(int)(temp * 10 - 10)]++;
        }
        
        for (double i = 0; i < count.length; i++) {
            System.out.println((i / 10) + 1 + "; " + count[(int)i]);
        }
    }
    
}
