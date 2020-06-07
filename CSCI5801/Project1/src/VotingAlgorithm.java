/**
 *
 */
import java.util.List;
/**
 * An interface to be used by the Plurality class and STV class.
 * @author Sami Frank
 *
 */
public interface VotingAlgorithm {
    public void distributeVotes();
    public void evaluate();
}
