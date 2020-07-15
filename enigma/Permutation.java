package enigma;


import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.ArrayList;
import java.util.List;

import static enigma.EnigmaException.*;

/** Represents a permutation of a range of integers starting at 0 corresponding
 *  to the characters of an alphabet.
 *  @author Shivang Singh
 */
class Permutation {

    /** Set this Permutation to that specified by CYCLES, a string in the
     *  form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     *  is interpreted as a permutation in cycle notation.  Characters in the
     *  alphabet that are not included in any cycle map to themselves.
     *  Whitespace is ignored. */
    Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        cycles = cycles.trim();
        Matcher m = Pattern.compile(regex).matcher(cycles);
        Matcher m2 = Pattern.compile("\\(([^()]*)\\)").matcher(cycles);
        if (!m.matches()) {
            throw EnigmaException.error("Bad Cycle");
        }
        while (m2.find()) {
            myCycles.add(m2.group(1));
        }
        for (int i = 0; i < myCycles.size(); i++) {
            if (myCycles.get(i).indexOf('\n') != -1
                    || myCycles.get(i).indexOf('\t') != -1
                    || myCycles.get(i).indexOf(' ') != -1) {
                throw EnigmaException.error("whitespace within cycle");
            }
        }
        for (String str: myCycles) {
            if (str.contains(" ")) {
                throw EnigmaException.error("space error");
            }
            for (int j = 0; j < str.length(); j++) {
                if (!_alphabet.contains(str.charAt(j))) {
                    throw EnigmaException.error(
                            "Letter in cycle not in alphabet");
                }
            }
        }
        for (int i = 0; i < _alphabet.size(); i++) {
            int countOfLetter = 0;
            for (String str: myCycles) {
                if (str.indexOf(_alphabet.toChar(i)) != -1) {
                    for (int j = str.indexOf(_alphabet.toChar(i))
                            + 1; j < str.length(); j++) {
                        if (str.charAt(j) == _alphabet.toChar(i)) {
                            throw EnigmaException.error(
                                    "character appears "
                                            + "multiple times in same cycle");
                        }
                    }
                    countOfLetter++;
                }
            }
            if (countOfLetter > 1) {
                throw EnigmaException.error("Repetitions in cycle");
            }
        }
    }

    /** Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     *  c0c1...cm. */
    private void addCycle(String cycle) {

    }
    /** Returns myCycles. */
    List<String> getMyCycles() {
        return myCycles;
    }

    /** Return the value of P modulo the size of this permutation. */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /** Returns the size of the alphabet I permute. */
    int size() {
        return _alphabet.size();
    }

    /** Return the result of applying this permutation to P modulo the
     *  alphabet size. */
    int permute(int p) {
        wrappedInt = wrap(p);
        charBefPerm = _alphabet.toChar(wrappedInt);
        charAfterPerm = charBefPerm;
        for (String str: myCycles) {
            if (str.indexOf(charBefPerm) != -1) {
                charAfterPerm = str.charAt(
                        (str.indexOf(charBefPerm) + 1)
                                % str.length());
                break;
            }
        }

        return _alphabet.toInt(charAfterPerm);
    }

    /** Return the result of applying the inverse of this permutation
     *  to  C modulo the alphabet size. */
    int invert(int c) {
        wrappedInt = wrap(c);
        charBefInv = _alphabet.toChar(wrappedInt);
        charAfterInv = charBefInv;
        for (String str: myCycles) {
            if (str.indexOf(charBefInv) != -1) {
                if (str.indexOf(charBefInv) == 0) {
                    charAfterInv = str.charAt(str.length() - 1);
                } else {
                    charAfterInv = str.charAt(
                            (str.indexOf(charBefInv) - 1) % str.length());
                }
            }
        }
        return _alphabet.toInt(charAfterInv);
    }

    /** Return the result of applying this permutation to the index of P
     *  in ALPHABET, and converting the result to a character of ALPHABET. */
    char permute(char p) {
        charBefPerm = p;
        charAfterPerm = charBefPerm;
        for (String str: myCycles) {
            if (str.indexOf(charBefPerm) != -1) {
                charAfterPerm = str.charAt(
                        (str.indexOf(charBefPerm) + 1) % str.length());
            }
        }
        if (!alphabet().contains(p)) {
            throw EnigmaException.error(
                    "can't permute something that doesn't exist");
        }
        return charAfterPerm;
    }

    /** Return the result of applying the inverse of this permutation to C. */
    char invert(char c) {
        charBefInv = c;
        charAfterInv = c;
        for (String str: myCycles) {
            if (str.indexOf(charBefInv) != -1) {
                if (str.indexOf(charBefInv) == 0) {
                    charAfterInv = str.charAt(str.length() - 1);
                } else {
                    charAfterInv = str.charAt(
                            (str.indexOf(charBefInv) - 1)
                                    % str.length());
                }
            }
        }
        if (!alphabet().contains(c)) {
            throw EnigmaException.error(
                    "can't permute something that doesn't exist");
        }
        return charAfterInv;
    }

    /** Return the alphabet used to initialize this Permutation. */
    Alphabet alphabet() {
        return _alphabet;
    }

    /** Return true iff this permutation is a derangement (i.e., a
     *  permutation for which no value maps to itself). */
    boolean derangement() {
        int permSize = 0;
        for (String l: myCycles) {
            if (l.length() == 1) {
                return false;
            } else {
                permSize += l.length();
            }
        }
        if (permSize != alphabet().size()) {
            return false;
        }
        return true;
    }

    /** Alphabet of this permutation. */
    private Alphabet _alphabet;
    /** Pattern to use with later matcher. */
    private Pattern pattern;
    /** Integer after wrap. */
    private int wrappedInt;
    /** Character before permutation. */
    private char charBefPerm;
    /** Character after permutation. */
    private char charAfterPerm;
    /** Character before an inversion. */
    private char charBefInv;
    /** Character after an inversion. */
    private char charAfterInv;
    /** Regex expression to match against.  */
    private String regex = "(\\([^\\s\\(\\)]+\\)\\s*)*";
    /** List of this permutation's cycles. */
    private List<String> myCycles = new ArrayList<String>();
}
