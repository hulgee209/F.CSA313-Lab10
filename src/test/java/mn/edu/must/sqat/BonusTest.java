package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * bonus(attendance, threshold) -- data-flow tests.
 * Line numbers refer to DataFlowTargets.java (31-56). DU table: README.md part B3.
 *
 * Stage 1: ALL-DEFS (minimum 2 tests).
 * score@49 reaches a use only when line 50 is FALSE, score@51 is defined only when
 * line 50 is TRUE -> one execution can not do both, so at least 2 tests are needed.
 */
class BonusTest {

    // ---------- Stage 1: all-defs ----------

    @Test
    @DisplayName("D1 [0,1,1,1,1,1] thr=10 -> 3 : score@49->54, score@54->56, streak@46->41, best@43->53T, present@40->50F")
    void d1_allDefs_absentThenLongStreak_belowThreshold() {
        assertEquals(3, DataFlowTargets.bonus(new int[] {0, 1, 1, 1, 1, 1}, 10));
    }

    @Test
    @DisplayName("D2 [1] thr=1 -> 5 : score@51->56, streak@36->41, best@37->42T, present@40->50T (exact threshold)")
    void d2_allDefs_singlePresent_exactThreshold() {
        assertEquals(5, DataFlowTargets.bonus(new int[] {1}, 1));
    }
}
