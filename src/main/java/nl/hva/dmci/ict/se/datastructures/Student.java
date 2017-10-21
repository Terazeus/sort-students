package nl.hva.dmci.ict.se.datastructures;

/**
 * @author Donny Rozendal
 * 
 */
public class Student implements Comparable<Student>{
    
    private int studentNumber;
    private Klas klas;
    private double cijfer;
    private Student next;

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

    public void setKlas(Klas klas) {
        this.klas = klas;
    }

    public double getCijfer() {
        return cijfer;
    }

    public Student getNext() {
        return next;
    }

    public void setNext(Student next) {
        this.next = next;
    }
    
    @Override
    public String toString() {
        return "[" + studentNumber + "; " + cijfer + "]";
    }

    @Override
    public int compareTo(Student student) {
        if (this.cijfer > student.cijfer) return 1;
        else if (this.cijfer < student.cijfer) return -1;
        else {
            if (this.studentNumber > student.studentNumber) return 1;
            else return -1;
        }
    }
}
