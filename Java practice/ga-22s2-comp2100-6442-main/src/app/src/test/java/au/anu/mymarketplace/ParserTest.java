import org.junit.Test;

import static org.junit.Assert.*;

import Parser;
import Tokenizer;

 /**
  * @author WEIQIANG PU u7424738
  * @date 2022/10/6  16:25
  */

public class ParserTest {

    private static final String SIMPLE_CASE = "HP high performance computer";
    private static final String MID_CASE = "HP high performance computer; $6,200.00";
    private static final String COMPLEX_CASE1 = "HP high performance computer; $6,200.00; tag = maternal infant; seller = JD; intext = good quality; rating = 4";
    private static final String COMPLEX_CASE2 = "seller = JD; $6,200.00; intext = good quality; HP high performance computer; rating = 4; tag = maternal infant";
    
    
    @Test(timeout=1000)
    public void testSimleCase() {
        Parser parser = new Parser(new Tokenizer(SIMPLE_CASE));
        parser.parseExp();

        assertEquals("HPhighperformancecomputer",parser.getName());
    }
    
    @Test(timeout=1000)
    public void testMidCase() {
        Parser parser = new Parser(new Tokenizer(MID_CASE));
        parser.parseExp();

        assertEquals("HPhighperformancecomputer",parser.getName());
        assertEquals("$6,200.00",parser.getPrice());
    }


    @Test(timeout=1000)
    public void testComplexCase1() {
        Parser parser = new Parser(new Tokenizer(COMPLEX_CASE1));
        parser.parseExp();
        assertEquals("HPhighperformancecomputer",parser.getName());
        assertEquals("$6,200.00",parser.getPrice());
        assertEquals(Item.Tag.MATERNAL_INFANT,parser.getTag());
        assertEquals("JD",parser.getSeller());
        assertEquals("goodquality",parser.getDescription());
        assertEquals(4, parser.getRating());
    }

    @Test(timeout=1000)
    public void testComplexCase2() {
        Parser parser = new Parser(new Tokenizer(COMPLEX_CASE2));
        parser.parseExp();
        assertEquals("HPhighperformancecomputer",parser.getName());
        assertEquals("$6,200.00",parser.getPrice());
        assertEquals(Item.Tag.MATERNAL_INFANT,parser.getTag());
        assertEquals("JD",parser.getSeller());
        assertEquals("goodquality",parser.getDescription());
        assertEquals(4, parser.getRating());
    }

}


