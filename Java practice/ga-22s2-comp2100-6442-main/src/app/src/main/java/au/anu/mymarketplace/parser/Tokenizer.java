import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Weiqiang Pu
 * @date 2022/10/6  14:25
 */

public class Tokenizer {
    private final ArrayList<String> tokens;
    private Token currentToken;     // The current token. The next token is extracted when next() is called.

    /**
     * Tokenizer class constructor
     * The constructor extracts the first token and save it to currentToken
     *
     * @author Weiqiang Pu u7424738
     */
    public Tokenizer(String text) {
        text = text.replace(" ", "");     // remove whitespace
        if (text.isEmpty()) {
            tokens = null;
            currentToken = null;   // if there's no string left, set currentToken null and return
        } else
            tokens = new ArrayList<>(Arrays.asList(text.split(";")));          // save input text (string) then split into array
        next();                 // extracts the first token.
    }

    /**
     * This function will find and extract a next token from {@code tokens} and save the token to {@code currentToken}.
     */
    public void next() {
        if (tokens.size() == 0) currentToken = null;
        else {
            if (tokens.get(0).startsWith("seller="))
                currentToken = new Token(tokens.get(0).substring(7), Token.Type.SELLER);
            else if (tokens.get(0).startsWith("tag="))
                currentToken = new Token(tokens.get(0).substring(4), Token.Type.TAG);
            else if (tokens.get(0).startsWith("$"))
                currentToken = new Token(tokens.get(0), Token.Type.PRICE);
            else if (tokens.get(0).startsWith("intext="))
                currentToken = new Token(tokens.get(0).substring(7), Token.Type.INTEXT);
            else if (tokens.get(0).startsWith("rating="))
                currentToken = new Token(tokens.get(0).substring(7), Token.Type.RATING);
            else currentToken = new Token(tokens.get(0), Token.Type.INTITLE);
            // Remove the extracted token from buffer
            tokens.remove(0);
        }
    }

    /**
     * Returns the current token extracted by {@code next()}
     *
     * @return type: Token
     */
    public Token current() {
        return currentToken;
    }

    /**
     * Check whether tokenizer still has tokens left
     *
     * @return type: boolean
     */
    public boolean hasNext() {
        return currentToken != null;
    }
}
