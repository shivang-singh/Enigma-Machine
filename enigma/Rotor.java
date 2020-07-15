package enigma;

import static enigma.EnigmaException.*;

/** Superclass that represents a rotor in the enigma machine.
 *  @author Shivang Singh
 */
class Rotor {

    /** A rotor named NAME whose permutation is given by PERM. */
    Rotor(String name, Permutation perm) {
        _name = name;
        _permutation = perm;
    }

    /** Return my name. */
    String name() {
        return _name;
    }

    /** Return my alphabet. */
    Alphabet alphabet() {
        return _permutation.alphabet();
    }

    /** Return my permutation. */
    Permutation permutation() {
        return _permutation;
    }

    /** Return the size of my alphabet. */
    int size() {
        return permutation().size();
    }

    /** Return true iff I have a ratchet and can move. */
    boolean rotates() {
        return false;
    }

    /** Return true iff I reflect. */
    boolean reflecting() {
        return false;
    }

    /** Return my current setting. */
    int setting() {
        return this._position;
    }

    /** Sets a Ringstellung.
     * @param start is The ringstellung for this rotor.
     * */
    void setMyRing(char start) {
        ringSetting = alphabet().toInt(start);
    }

    /** Returns Ringstellung. */
    int getRingStellung() {
        return ringSetting;
    }

    /** Set setting() to POSN.  */
    void set(int posn) {
        this._position = permutation().wrap(posn);
    }

    /** Set setting() to character CPOSN. */
    void set(char cposn) {
        if (this.alphabet().toInt(cposn) != -1) {
            this._position = permutation().wrap(
                    _permutation.alphabet().toInt(cposn));
        } else {
            throw EnigmaException.error("Character not in alphabet");
        }
    }

    /** Return the conversion of P (an integer in the range 0..size()-1)
     *  according to my permutation. */
    int convertForward(int p) {
        int afterTrans = permutation().permute(p
                + setting() - getRingStellung());
        return permutation().wrap(afterTrans - setting() + getRingStellung());
    }

    /** Return the conversion of E (an integer in the range 0..size()-1)
     *  according to the inverse of my permutation. */
    int convertBackward(int e) {
        int afterTrans = permutation().invert(e
                + setting() - getRingStellung());
        return permutation().wrap(afterTrans - setting() + getRingStellung());
    }

    /** Returns true iff I am positioned to allow the rotor to my left
     *  to advance. */
    boolean atNotch() {
        return false;
    }

    /** Advance me one position, if possible. By default, does nothing. */
    void advance() {
    }

    @Override
    public String toString() {
        return "Rotor " + _name;
    }

    /** My name. */
    private final String _name;

    /** The permutation implemented by this rotor in its 0 position. */
    private Permutation _permutation;
    /** This rotor's current setting. */
    private int _position;
    /** This rotor's current ringSetting. */
    private int ringSetting;

}
