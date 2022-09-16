package Arboretum.out.test.comp1110;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class D2DCheckBeforeTest {
    private final String[] validArboretum = new String[]{
            "A",
            "Aa6C00C00",
            "Aa6C00C00d6S01C00",
            "Aa6C00C00d6S01C00b3C00W01d8N01W01",
            "Aa6C00C00d6S01C00b3C00W01d8N01W01j6N01C00d1S01W01a8S02W01m6S02W02j2C00W02d4S03W02c5S01W02a7S03W01b7S02C00j5S02W03",
            "B",
            "Bc7C00C00",
            "Bc7C00C00b8C00E01",
            "Bc7C00C00b8C00E01b1S01C00d3C00W01",
            "Bc7C00C00b8C00E01b1S01C00d3C00W01c2C00W02m7S01E01m2S01W01m1S01W02b2N01W01"
    };

    private final String[] invalidArboretum = new String[]{
            "Bc2S02E02",
            "Ac3S02E02",
            "Ac1C00C00c2C00E02",
            "Bc1C00C00c2C00E02"
    };

    private String errorString(String arboretumString){
        return "Incorrect Arboretum: " + arboretumString;
    }

    @Test
    public void validArboretumTest(){
        for (String s : validArboretum) {
            Assertions.assertTrue(Arboretum.checkBefore(s), errorString(s));
        }
    }

    @Test
    public void invalidArboretumTest(){
        for (String s : invalidArboretum) {
            Assertions.assertFalse(Arboretum.checkBefore(s), errorString(s));
        }
    }
}
