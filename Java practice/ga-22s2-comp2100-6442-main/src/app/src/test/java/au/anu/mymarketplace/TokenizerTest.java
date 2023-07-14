import static org.junit.Assert.assertEquals;

import org.junit.Test;

import Token;
import Tokenizer;

/**
 * @author Weiqiang Pu
 * @date 2022/10/6  11:25
 */

public class TokenizerTest {

    private static final String name = "HP high performance computer";
    private static final String price = "$6,200.00";
    private static final String tag = "tag = maternal infant";
    private static final String rating = "rating = 4";
    private static final String intext = "intext = power save";
    private static Tokenizer tokenizer;

    @Test(timeout = 1000)
    public void testNameToken() {
        tokenizer = new Tokenizer(name);

        // check the type of the first token
        assertEquals("wrong token type", Token.Type.INTITLE, tokenizer.current().getType());

        // check the actual token value"
        assertEquals("wrong token value", "HPhighperformancecomputer", tokenizer.current().getToken());
    }

    @Test(timeout = 1000)
    public void testPriceToken() {
        tokenizer = new Tokenizer(price);

        // check the type of the first token
        assertEquals("wrong token type", Token.Type.PRICE, tokenizer.current().getType());

        // check the actual token value
        assertEquals("wrong token value", "$6,200.00", tokenizer.current().getToken());
    }

    @Test(timeout = 1000)
    public void testFirstToken() {
        tokenizer = new Tokenizer(rating + ";" + tag);

        // check the type of the first token
        assertEquals("wrong token type", Token.Type.RATING, tokenizer.current().getType());
        // check the actual token value
        assertEquals("wrong token value", "4", tokenizer.current().getToken());
    }

    @Test(timeout = 1000)
    public void testMidTokenResult() {
        tokenizer = new Tokenizer(rating + ";" + tag + ";" + intext);

        // test first token INT(12)
        assertEquals(new Token("4", Token.Type.RATING), tokenizer.current());

        // test second token *
        tokenizer.next();
        assertEquals(new Token("maternalinfant", Token.Type.TAG), tokenizer.current());

        // test third token INT(5)
        tokenizer.next();
        assertEquals(new Token("powersave", Token.Type.INTEXT), tokenizer.current());
    }
}
