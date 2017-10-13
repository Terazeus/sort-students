package nl.hva.dmci.ict.se.datastructures.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Schud waardes van een array door elkaar.
 * 
 * @author Nico Tromp
 *
 */
public class Schudder {

    /**
     * Schud de waardes van een array willekeurig door elkaar.
     * 
     * @param waardes
     *            het array dat geschud moet worden.
     */
    public static <T> void schud(T[] waardes) {
        List<T> waardesAlsLijst = Arrays.asList(waardes);
        Collections.shuffle(waardesAlsLijst);
        waardesAlsLijst.toArray(waardes);
    }
}
