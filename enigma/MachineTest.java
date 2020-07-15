package enigma;
import org.junit.Rule;
import org.junit.rules.Timeout;


/** The suite of all JUnit tests for the Machine class.
 *  @author Shivang Singh
 */
public class MachineTest {
    /*Testing time Limit*/
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /*Testing Utilities*/
    Alphabet alpha = new Alphabet();

}
