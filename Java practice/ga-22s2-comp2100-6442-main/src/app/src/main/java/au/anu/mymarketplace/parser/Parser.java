import au.anu.mymarketplace.Item;

/**
 * A valid query contains at least 1 command. Different commands are distinguished by symbol ";", all commands are case-insensitive.
 * For example:
 * 1. ASUS high performance; 2. $300; 3. type = computer; 4. seller = JD; 5. rating = 5; 6. intext = low cost
 * This example is searching an item which name contains "ASUS high performance"(case-insensitive), price is 300, type is computer, seller is JD and rating is 5.
 * <p>
 * No declaration means name fuzzy match. All items with the searched value in their name will be retrieved.
 * Example: Silent full-automatic washing machine
 * "$" means searching for a specified price
 * Example: $329
 * "Seller" means searching for a specified seller.
 * Example: Seller = Amazon
 * "intext" means searching for items containing a certain word somewhere in the description.
 * Example: intext = "High performance" + "Automatic cleaning function"
 * "tag" means searching for items with a specified type.
 * Example: type = mobile phone
 * "rating" means searching for items with a specified rating.
 * Example: rating = 5
 * <p>
 * the grammar rules:
 * <expression> ::= <command> | <command> ; <expression>
 *
 * @author WEIQIANG PU u7424738
 * @date 2022/10/6  17:25
 */
public class Parser extends Item {
    // The tokenizer (class field) this parser will use.
    Tokenizer tokenizer;

    /**
     * Parser class constructor
     * Simply sets the tokenizer field.
     */
    public Parser(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    /**
     *  convert the input string according to the expression
     */
    public void parseExp() {
        while (tokenizer.hasNext()) {
            if (tokenizer.current().getType().equals(Token.Type.SELLER)) {
                setSeller(tokenizer.current().getToken());
                tokenizer.next();
            } else if (tokenizer.current().getType().equals(Token.Type.TAG)) {
                System.out.println(tokenizer.current().getToken());
               if(tokenizer.current().getToken().equals("computer")) setTag(Tag.COMPUTER);
               else if (tokenizer.current().getToken().equals("cosmetics")) setTag(Tag.COSMETICS);
               else if (tokenizer.current().getToken().equals("food")) setTag(Tag.FOOD);
               else if (tokenizer.current().getToken().equals("furniture")) setTag(Tag.FURNITURE);
               else if (tokenizer.current().getToken().equals("maternalinfant")) setTag(Tag.MATERNAL_INFANT);
               else if (tokenizer.current().getToken().equals("men")) setTag(Tag.MEN);
               else if (tokenizer.current().getToken().equals("outdoor")) setTag(Tag.OUTDOOR);
               else if (tokenizer.current().getToken().equals("pet")) setTag(Tag.PET);
               else if (tokenizer.current().getToken().equals("toy")) setTag(Tag.TOY);
               else if (tokenizer.current().getToken().equals("women")) setTag(Tag.WOMEN);
               else if (tokenizer.current().getToken().equals("women")) setTag(Tag.STATIONARY);

               tokenizer.next();
            } else if (tokenizer.current().getType().equals(Token.Type.PRICE)) {
                setPrice(tokenizer.current().getToken());
                tokenizer.next();
            } else if (tokenizer.current().getType().equals(Token.Type.INTEXT)) {
                setDescription(tokenizer.current().getToken());
                tokenizer.next();
            } else if (tokenizer.current().getType().equals(Token.Type.RATING)) {
                setRating(Integer.parseInt(tokenizer.current().getToken()));
                tokenizer.next();
            } else if (tokenizer.current().getType().equals(Token.Type.INTITLE)) {
                setName(tokenizer.current().getToken());
                tokenizer.next();
            }
        }
    }
}
