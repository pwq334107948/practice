package Arboretum.out.test.comp1110;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Timeout(
        value = 1000L,
        unit = TimeUnit.MILLISECONDS
)
public class D2DScoreFromHandTest {
    public D2DScoreFromHandTest(){
    }

    public static String[][][] END_GAME_STATES = {
            {{"B", "Ab1C00C00a4N01C00c3C00E01c6N02C00m7N02W01m4N01E01a5N02E01a2S01E01c8N02W02c1S01C00b6N03E01m8N03W01m1S02E01a8N04E01c2N01W01b4N02E02", "Aa3c4", "Bj5C00C00j6S01C00j7S01W01j4C00W01m6C00E01m3C00W02j3N01W01d2N02W01d7S02C00b8S02E01b3N01C00d1N03W01d8S03C00b2N02C00j8S01E01", "Bd4"},
                    {"", "Aa7c5c7d3d5j1m5", "Ba1a6b5b7d6j2m2"}},
            {{"A", "Ad8C00C00d1C00W01d7C00E01c2N01E01d4S01E01j4C00E02b3S01C00b1N01C00m6N01W01j6S02C00b8S02W01c8N01E02d5S02E01b4S03E01a6N02E02a5N02E01d6N01E03", "Am4", "Bc7C00C00m5C00W01a7C00W02m3N01W01c1N02W01a8C00W03c4S01W01b5N01W02m8N01W03c6S01W02j8S01W03b2N01C00m2S02W01j3S01C00a2S01E01b7S01W04", "B"},
                    {"", "Aa3c3d2d3j1m1m7", "Ba1a4b6c5j2j5j7"}},
            {{"B", "Ac1C00C00b2C00E01b3N01E01b4N01E02b5N02E02c7N03E02m1S01E01j1C00E02j4N02E01b8N02E03m4N01C00j5N02C00j6N02W01j7N03W01d2N01W01j2N03E01", "Ac2j3", "Ba4C00C00a3S01C00a2S01W01d1S02W01m6C00W01m2S01E01d7N01W01c8N02W01a5N01C00c5C00W02a6N02C00m7N03C00m8N03W01a7N02E01b1S01W02", "Bd3"},
                    {"", "Aa8b6c3c4d8j8m5", "Ba1b7c6d4d5d6m3"}},
            {{"A", "Aa2C00C00c1C00W01j3N01C00m4N01E01j5N02E01a6N03E01c7N03C00m8N04C00j2N01W01j1N01W02c4N02W02c5N02W01c6N02C00c8N03W01", "Am1c2", "Bd1C00C00d2N01C00d3N01E01b4N02E01b5N03E01b1N01W01d6N03E02a1N02C00a8N03C00d4N01E02d8N04E02b6N04E01b8N04C00d5N02E02", "Bm2b3j4m3"},
                    {"", "Aa4a7b2c3j7m6m7", "Ba3a5b7d7j6j8m5"}},
    };

    static final char[] validChars = {'a', 'b', 'c', 'd', 'j', 'm'};

    public static int[][][] SCORING_SPECIES = {{{7, 0, 12, 8, 1, 5},{7, 12, 0, 6, 2, 2}},{{3, 0, 3, 5, 1, 8},{5, 6, 5, 0, 14, 0}},
            {{8, 6, 7, 8, 8, 5}, {1, 7, 6, 15, 0, 3}}, {{11, 2, 3, 0, 7, 13}, {8, 7, 0, 7, 14, 5}}};

    private String errorPrefix(String[][] state) {
        return "Arboretum.canScore(" + System.lineSeparator() + "sharedState: " + Arrays.toString(state[0]) + System.lineSeparator() +
                "hiddenState: " + Arrays.toString(state[1]) + ")"
                + System.lineSeparator();
    }

    private void test(String[][] state, char s, char p, int expected) {
        String errorPrefix = errorPrefix(state);
        int out = Arboretum.scoreFromHand(state, p, s);
        assertEquals(expected, out,
                errorPrefix + " for player \"" + p + "\" and species \"" + s + "\" expected " + expected + " but you " +
                        "returned" +
                        " " + out);
    }

    @Test
    public void testGeneral() {
        for (int i = 0; i < END_GAME_STATES.length; i++) {
            for (int p = 0; p < 2; p++) {
                char player = (char) (p + 'A');
                for (int j = 0; j < validChars.length; j++) {
                    test(END_GAME_STATES[i], validChars[j], player, SCORING_SPECIES[i][p][j]);
                }
            }
        }
    }
}
