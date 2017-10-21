package nl.hva.dmci.ict.se.datastructures;

/**
 * @author Donny Rozendal & Perry van Ede
 * 
 */
public class Klas implements Comparable<Klas>{
    private String name;
    private int size;
    private Student firstStudent;
    private Student lastStudent;
    private Klas next;
    
    public Klas(String name) {
        this.name = name;
    }
    
    public void pushNext(Student student) {
        
    }
    
    public void increaseSize() {
        size++;
    }

    public Student getFirst() {
        return firstStudent;
    }

    public void setFirst(Student first) {
        this.firstStudent = first;
    }

    public Student getLast() {
        return lastStudent;
    }
    
    public void setLast(Student last) {
        this.lastStudent = last;
    }

    public Klas getNext() {
        return next;
    }

    public void setNext(Klas next) {
        this.next = next;
    }
    
    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int compareTo(Klas klas) {
        return this.name.compareTo(klas.name);
    }
}
    