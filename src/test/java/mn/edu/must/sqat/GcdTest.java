package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * gcd(x, y) -- all-DU-pairs coverage.
 * Line numbers refer to DataFlowTargets.java:
 *   13 def x,y | 15 p-use y | 16 def tmp, c-use x,y | 17 def x, c-use y
 *   18 def y, c-use tmp | 20 c-use x
 * 13 DU pairs in total (x:4, y:8, tmp:1), see README.md part A.
 */
class GcdTest {

    @Test
    @DisplayName("G1 gcd(7,0)=7 : x(13->20), y(13->15F) -- zero iterations")
    void g1_zeroIterations_x13to20_y13to15F() {
        assertEquals(7, DataFlowTargets.gcd(7, 0));
    }

    @Test
    @DisplayName("G2 gcd(0,5)=5 : one iteration -- x(13->16), y(13->15T,13->16,13->17), tmp(16->18), y(18->15F), x(17->20)")
    void g2_oneIteration_firstPassPairs() {
        assertEquals(5, DataFlowTargets.gcd(0, 5));
    }

    @Test
    @DisplayName("G3 gcd(12,8)=4 : two iterations -- x(17->16), y(18->15T), y(18->16), y(18->17) + all first-pass pairs")
    void g3_twoIterations_loopCarriedPairs() {
        assertEquals(4, DataFlowTargets.gcd(12, 8));
    }

    @Test
    @DisplayName("G4 gcd(8,12)=4 : x<y, three iterations -- loop-carried pairs again (x(17->16), y(18->15T,16,17))")
    void g4_swappedArguments_threeIterations() {
        assertEquals(4, DataFlowTargets.gcd(8, 12));
    }
}
