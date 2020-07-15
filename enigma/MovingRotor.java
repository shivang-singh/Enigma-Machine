package enigma;

import static enigma.EnigmaException.*;

/** Class that represents a rotating rotor in the enigma machine.
 *  @author Shivang Singh
 */
class MovingRotor extends Rotor {

    /** A rotor named NAME whose permutation in its default setting is
     *  PERM, and whose notches are at the positions indicated in NOTCHES.
     *  The Rotor is initally in its 0 setting (first character of its
     *  alphabet).
     */
    MovingRotor(String name, Permutation perm, String notches) {
        super(name, perm);
        _notches = notches;
    }

    /** Returns if the rotor rotates. */
    boolean rotates() {
        return true;
    }

    @Override
    boolean atNotch() {
        for (int i = 0; i < getNotches().length(); i++) {
            if (getNotches().charAt(i) == alphabet().toChar(setting())) {
                return true;
            }
        }
        return false;
    }
    /** Returns notches of rotor. */
    public String getNotches() {
        return _notches;
    }

    @Override
    void advance() {
        set(permutation().wrap((setting() + 1)));
    }
    /** Notches for rotor. */
    private String _notches;

}
