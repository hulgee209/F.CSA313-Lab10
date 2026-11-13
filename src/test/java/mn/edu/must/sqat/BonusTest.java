package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    // ---------- Stage 2: all-DU-pairs (adds the pairs D1, D2 left uncovered) ----------

    @Test
    @DisplayName("P1 null attendance -> IAE : attendance@31->32T")
    void p1_nullAttendance_attendance31to32T() {
        assertThrows(IllegalArgumentException.class, () -> DataFlowTargets.bonus(null, 1));
    }

    @Test
    @DisplayName("P2 threshold=-1 -> IAE : threshold@31->32T")
    void p2_negativeThreshold_threshold31to32T() {
        assertThrows(IllegalArgumentException.class, () -> DataFlowTargets.bonus(new int[] {1}, -1));
    }

    @Test
    @DisplayName("P3 [] thr=0 -> 5 : present@35->50T, best@37->53F, attendance@31->38F (no iteration)")
    void p3_emptyArray_thresholdZero_present35to50T() {
        assertEquals(5, DataFlowTargets.bonus(new int[] {}, 0));
    }

    @Test
    @DisplayName("P4 [0,0,0] thr=1 -> 0 : present@35->50F, score@49->56, streak@46 killed by 46 (no use)")
    void p4_allAbsent_present35to50F_score49to56() {
        assertEquals(0, DataFlowTargets.bonus(new int[] {0, 0, 0}, 1));
    }

    @Test
    @DisplayName("P5 [1,0,1] thr=3 -> 0 : streak@41->42F, best@43->42F, best@43->53F, score@49->56")
    void p5_streakRestarts_streak41to42F_best43to42F() {
        assertEquals(0, DataFlowTargets.bonus(new int[] {1, 0, 1}, 3));
    }

    @Test
    @DisplayName("P6 [1,1,1,1,1] thr=5 -> 8 : score@51->54, score@54->56, best@43->53T (streak exactly 5)")
    void p6_fiveStreak_meetsThreshold_score51to54() {
        assertEquals(8, DataFlowTargets.bonus(new int[] {1, 1, 1, 1, 1}, 5));
    }
}
