package nl.hva.dmci.ict.se.datastructures;

/**
 * @author Donny Rozendal
 * 
 */
public class Student {
    
    private int studentNumber;
    private double cijfer;

    public Student(int studentNummer, double cijfer) {
        if (studentNummer >= 50080001) {
            this.studentNumber = studentNummer;
        } else {
            throw new IllegalArgumentException("Het studentnummer moet groter zijn dan 5008001");
        }
        if (cijfer >= 1.0 && cijfer <= 10.0) {
            this.cijfer = cijfer;
        } else {
            throw new IllegalArgumentException("Het cijfer moet tussen 1.0 en 10.0 liggen!");
        }
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public double getCijfer() {
        return cijfer;
    }
    
    
    
    @Override
    public String toString() {
        return "[" + studentNumber + "; " + cijfer + "]";
    }
}
