package Arboretum.out.test.comp1110;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class D2DGetSharedStateTest {
    private final String[][] validSharedPiece = {{"A", "A", "A", "B", "B"}, //null string
            {"A", "Ab1C00C00a4N01C00c3C00E01c6N02C00m7N02W01m4N01E01a5N02E01a2S01E01c8N02W02c1S01C00b6N03E01m8N03W01m1S02E01", "Aa3", "Bj5C00C00j6S01C00j7S01W01j4C00W01m6C00E01m3C00W02j3N01W01d2N02W01d7S02C00b8S02E01b3N01C00d1N03W01d8S03C00", "Bc3"}, //long string
            {"A", "Aa1C00C00", "Aa3c4", "Bd1C00C00", "Bd4"} //short string
    };

    private final String[][][] expectedValidSharedState = {{{"A"}, {}, {}, {}, {}}, //null
            {{"A"}, {"b1", "C00", "C00", "a4", "N01", "C00", "c3", "C00", "E01", "c6", "N02", "C00", "m7", "N02", "W01", "m4", "N01", "E01", "a5", "N02", "E01", "a2", "S01", "E01", "c8", "N02", "W02", "c1", "S01", "C00", "b6", "N03", "E01", "m8", "N03", "W01", "m1", "S02", "E01"}, {"a3"}, {"j5", "C00", "C00", "j6", "S01", "C00", "j7", "S01", "W01", "j4", "C00", "W01", "m6", "C00", "E01", "m3", "C00", "W02", "j3", "N01", "W01", "d2", "N02", "W01", "d7", "S02", "C00", "b8", "S02", "E01", "b3", "N01", "C00", "d1", "N03", "W01", "d8", "S03", "C00"}, {"c3"}},
            {{"A"}, {"a1", "C00", "C00"}, {"a3", "c4"}, {"d1", "C00", "C00"}, {"d4"}}
    };
    private final String[][] invalidSharedPiece = {{"Ba2", "Ab1C00C00a4N01C00c3C00E0", "Aa3c", "Bj5C00C00j6", "Bd4"}, // Invalid turnID, invalid Arboretum and invalid player A's discard
            {"", "b1C00C00a4N01C00c3C00E0", "a3c", "j5C00C00j6", "d4"}, // no turnID, no player ID
    };
    private final String[][][] expectedInvalidSharedState = {{{"B"}, {"b1", "C00", "C00", "a4", "N01", "C00"}, {"a3"}, {"j5", "C00", "C00"}, {"d4"}},
            {{}, {"1C", "00C", "00a", "4N", "01C", "00c"}, {"3c"}, {"5C", "00C", "00j"}, {}}
    };

    @Test
    public void validSharedStateTest() {
        for (int i = 0; i < validSharedPiece.length; i++) {
            Assertions.assertArrayEquals(expectedValidSharedState[i], Arboretum.getValueFromShared(validSharedPiece[i]));
        }
    }

    @Test
    public void invalidStringTest() {
        for (int i = 0; i < invalidSharedPiece.length; i++) {
            Assertions.assertArrayEquals(expectedInvalidSharedState[i], Arboretum.getValueFromShared(invalidSharedPiece[i]));
        }
    }

}
