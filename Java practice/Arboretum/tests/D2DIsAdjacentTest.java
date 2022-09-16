package Arboretum.out.test.comp1110;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class D2DIsAdjacentTest {
    private final String[][] validLocation = new String[][]{
            {"C00E01", "C00C00"}, {"C00C00", "C00E01"}, // is neighbor of center
            {"N01C00", "C00C00"}, {"C00C00", "N01C00"},
            {"C00W01", "C00C00"}, {"C00C00", "C00W01"},
            {"S01C00", "C00C00"}, {"C00C00", "S01C00"},
            {"S01E01", "S01C00"}, {"S01C00", "S01E01"}, // is neighbor of different vertical direction
            {"N01W01", "N01C00"}, {"N01C00", "N01W01"},
            {"C00W01", "S01W01"}, {"S01W01", "C00W01"}, // is neighbor of different horizontal direction
            {"C00E01", "N01E01"}, {"N01E01", "C00E01"},
            {"S02E02", "S02E01"}, {"S02E01", "S02E02"}, // is neighbor of same direction
            {"N02E03", "N02E02"}, {"N02E02", "N02E03"}
    };
    private final String[][] invalidLocation = new String[][]{
            {"C00C00", "N01E02"}, {"N01E02", "C00C00"}, // not neighbor of center
            {"C00C00", "S02E02"}, {"S02E02", "C00C00"},
            {"N01W02", "N01E02"}, {"N01E02", "N01W02"}, // not neighbor of different vertical direction
            {"S01W02", "S01E01"}, {"S01E01", "S01W02"},
            {"N01E01", "S01E01"}, {"S01E01", "N01E01"}, // not neighbor of different horizontal direction
            {"N02W01", "S02W01"}, {"S02W01", "N02W01"},
            {"S02E02", "S02E05"}, {"S02E05", "S02E02"}, // not neighbor of same direction
            {"N08E02", "N02E02"}, {"N02E02", "N08E02"}
    };

    private String errorString(String[] location) {
        return "New location: " + location[0] + " Location: " + location[1];
    }

    @Test
    public void validLocationTest(){
        for (String[] strings : validLocation) {
            Assertions.assertTrue(Arboretum.isAdjacent(strings[0], strings[1]), errorString(strings));
        }
    }

    @Test
    public void invalidLocationTest(){
        for (String[] strings : invalidLocation) {
            Assertions.assertFalse(Arboretum.isAdjacent(strings[0], strings[1]), errorString(strings));
        }
    }
}
