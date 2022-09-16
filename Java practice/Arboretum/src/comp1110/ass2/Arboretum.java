package Arboretum.out.test.comp1110;

import java.util.*;

public class Arboretum {

    /**
     * A hiddenState string array is well-formed if it complies with the following rules:
     * <p>
     * hiddenState[0] - Deck
     * A number of 2-character card substrings sorted alphanumerically as defined below
     * For example: "a3a8b5b6c2c7d1d3d7d8m1"
     * <p>
     * hiddenState[1] - Player A's hand
     * 0th character is 'A'
     * Followed by 7, 8 or 9 2-character card substrings sorted alphanumerically.
     * For example a full hand String might look like: "Ab3b4c1j1m2m5m8"
     * <p>
     * hiddenState[2] - Player B's hand
     * 0th character is 'B'
     * Followed by 7, 8 or 9 2-character _card_ substrings
     * For example: "Ba6b7b8c8d2j2j8"
     * <p>
     * Where:
     * "card substring" - A 2-character string that represents a single card.
     * 0th character is 'a', 'b', 'c', 'd', 'j', or 'm' representing the card species.
     * 1st character is a sequential digit from '1' to '8', representing the value of the card.
     * For example: "d7"
     * <p>
     * "alphanumerical(ly)" - This means that cards are sorted first alphabetically and then numerically.
     * For example, "m2" appears before "m5" because 2 < 5
     * Whilst "b4" appears before "c1" because alphabetical ordering takes precedence over
     * numerical ordering.
     * <p>
     * Exceptions:
     * - If the deck is empty, hiddenState[0] will be the empty string ""
     * - If a player's hand is empty, then the corresponding hiddenState[i] will contain only the player's ID.
     * For example: if player A's hand is empty then hiddenState[1] will be "A" with no other characters.
     *
     * @param hiddenState the hidden state array.
     * @return true if the hiddenState array is well-formed, false if it is not well-formed.
     * @author Weiqiang PU
     * TASK 3
     */
    public static boolean isHiddenStateWellFormed(String[] hiddenState) {
        if (hiddenState == null || (hiddenState.length != 3 && hiddenState.length != 4 && hiddenState.length != 5)) {
            return false;
        } else {
            String[][] a = getValueFromHidden(hiddenState); //Extract card from the given string
            Arrays.sort(a[0]);
            Arrays.sort(a[1]);
            Arrays.sort(a[2]);
            if (Arrays.deepEquals(a, getValueFromHidden(hiddenState))) { //Check whether the given String is alphanumerical by comparing sorted to unsorted String
                //Check whether Deck、PlayerA、PlayerB meets the rules in turn
                if (hiddenState[0].isEmpty() || hiddenState[0].matches("^([abcdjm][1-8])*$")) {
                    if (hiddenState[1].equals("A") || hiddenState[1].matches("^A([abcdjm][1-8]){7,9}$")) {
                        return hiddenState[2].equals("B") || hiddenState[2].matches("^B([abcdjm][1-8]){7,9}$");
                    } else return false;
                } else return false;
            } else return false;
        }
    }


    /**
     * A sharedState string array is well-formed if it complies with the following rules:
     * <p>
     * sharedState[0] - a single character ID string, either "A" or "B" representing whose turn it is.
     * sharedState[1] - Player A's arboretum
     * 0th character is 'A'
     * Followed by a number of 8-character _placement_ substrings as defined below that occur in
     * the order they were played. Note: these are NOT sorted alphanumerically.
     * For example: "Ab1C00C00a4N01C00c3C00E01c6N02C00m7N02W01m4N01E01a5N02E01a2S01E01"
     * <p>
     * sharedState[2] - Player A's discard
     * 0th character is 'A'
     * Followed by a number of 2-character _card_ substrings that occur in the order they were
     * played. Note: these are NOT sorted alphanumerically.
     * For example: "Aa7c4d6"
     * <p>
     * sharedState[3] - Player B's arboretum
     * 0th character is 'B'
     * Followed by a number of 8-character _placement_ substrings that occur in the order they
     * were played. Note: these are NOT sorted alphanumerically.
     * For example: "Bj5C00C00j6S01C00j7S02W01j4C00W01m6C00E01m3C00W02j3N01W01"
     * <p>
     * sharedState[4] - Player B's discard
     * 0th character is 'B'
     * Followed by a number of 2-character _card_ substrings that occur in the order they were
     * played. Note: these are NOT sorted alphanumerically.
     * For example: "Bb2d4c5a1d5"
     * <p>
     * Where: "card substring" and "alphanumerical" ordering are defined above in the javaDoc for
     * isHiddenStateWellFormed and placement substrings are defined as follows:
     * <p>
     * "placement substring" - An 8-character string that represents a card placement in a player's arboretum.
     * - 0th and 1st characters are a 2-character card substring
     * - 2nd character is 'N' for North, 'S' for South, or 'C' for Centre representing the
     * direction of this card relative to the first card played.
     * - 3rd and 4th characters are a 2-digit number from "00" to "99" representing the distance
     * from the first card played to this card, in the direction of the 2nd character.
     * - 5th character is 'E' for East, 'W' for West, or 'C' for Centre representing the
     * direction of this card relative to the first card played.
     * - 6th and 7th characters are a 2-digit number =from "00" to "99" representing the distance
     * from the first card played to this card, in the direction of the 3rd character.
     * For example: "a1C00C00b3N01C00" says that card "a1" was played first and card "b3" was played
     * one square north of the first card (which was "a1").
     * <p>
     * Exceptions:
     * If a player's discard or arboretum are empty, (ie: there are no cards in them), then the corresponding string
     * should contain only the player ID.
     * For example:
     * - If Player A's arboretum is empty, then sharedState[1] would be "A" with no other characters.
     * - If Player B's discard is empty, then sharedState[4] would be "B" with no other characters.
     *
     * @param sharedState the shared state array.
     * @return true if the sharedState array is well-formed, false if it is not well-formed.
     * @author Weiqiang PU
     * TASK 4
     */
    public static boolean isSharedStateWellFormed(String[] sharedState) {
        if (sharedState == null || (sharedState.length != 5 && sharedState.length != 7 && sharedState.length != 9))
            return false;
        else {
            //Check whether index 0 - 4 meets the rules in turn
            if (sharedState[0].matches("^[ABCD]$")) {
                if (sharedState[1].matches("^A([abcdjm][1-8][CNS]\\d{2}[CEW]\\d{2})*$")) {
                    if (sharedState[2].matches("^A([abcdjm][1-8])*$")) {
                        if (sharedState[3].matches("B([abcdjm][1-8][CNS]\\d{2}[CEW]\\d{2})*$")) {
                            return sharedState[4].matches("^B([abcdjm][1-8])*$");
                        } else return false;
                    } else return false;
                } else return false;
            } else return false;
        }
    }

    /**
     * Given a deck string, draw a random card from the deck.
     * You may assume that the deck string is well-formed.
     *
     * @param deck the deck string.
     * @return a random cardString from the deck. If the deck is empty, return the empty string "".
     * @author Hanqin Liu
     * TASK 5
     */
    public static String drawFromDeck(String deck) {
        if (deck.length() == 0) {
            return "";
        } else {
            //initialize a new cardSting list, draw a random card from the new array list
            Random rand = new Random();
            ArrayList<String> cardList = new ArrayList<>();
            for (int i = 0; i < deck.length() - 1; i += 2) {
                cardList.add(deck.substring(i, i + 2));
            }
            int num = rand.nextInt(cardList.size());
            return cardList.get(num);
        }
    }

    /**
     * Determine whether this placement is valid for the current player. The "Turn String" determines who is making
     * this placement.
     * <p>
     * A placement is valid if the following conditions are met:
     * <p>
     * - The card is placed adjacent to a card that is already placed, or is placed in the position "C00C00" if this is
     * the first card placed.
     * - The card does not share a location with another card that has been placed by this player.
     * - The card being placed is currently in the hand of this player.
     * - The hand of this player has exactly 9 cards in it.
     * <p>
     * You may assume that the inputs to this method are valid and/or well-formed.
     *
     * @param gameState the game state array
     * @param placement the placement string of the card to be placed
     * @return false if the placement is valid, false if it is not valid.
     * @author Ziyao Jin
     * TASK 7
     */
    public static boolean isPlacementValid(String[][] gameState, String placement) {
        // check who is the current player and set corresponding index
        int handIndex, arboretumIndex;
        if (Objects.equals(gameState[0][0], "A")) {
            handIndex = 1;
            arboretumIndex = 1;
        } else {
            handIndex = 2;
            arboretumIndex = 3;
        }
        // check if the player has the card
        if (!gameState[1][handIndex].contains(placement.substring(0, 2))) return false;
        // check if the player has 9 cards in hand
        if (gameState[1][handIndex].length() != 19) return false;
        // check first card placement
        if (placement.substring(2).equals("C00C00")) {
            return gameState[0][arboretumIndex].length() == 1;
        }
        // check if the card is adjacent to another card
        for (var i = 1; i < gameState[0][arboretumIndex].length(); i += 8) {
            if (isAdjacent(placement.substring(2), gameState[0][arboretumIndex].substring(i + 2, i + 8))) {
                // check if the card shares a location with others
                if (!gameState[0][arboretumIndex].substring(i + 2, i + 8).equals(
                        placement.substring(2))) return true;
            }
        }
        return false;
    }

    /**
     * a helper method to distinguish whether the first position is neighbor of the second.
     *
     * @param newPosition a new position waiting to be checked
     * @param position the original position
     * @return a boolean
     * @author Ziyao Jin
     */
    public static boolean isAdjacent(String newPosition, String position) {
        // check center point
        if (Objects.equals(newPosition, "C00C00") && (
                Objects.equals(position, "C00E01") | Objects.equals(position, "N01C00")
                        | Objects.equals(position, "C00W01") | Objects.equals(position, "S01C00"))
        ) return true;
        // divide position and store
        boolean vertical = newPosition.substring(0, 3).equals(position.substring(0, 3));
        boolean horizontal = newPosition.substring(3).equals(position.substring(3));
        // check 00 and 01 condition
        if (horizontal && (newPosition.startsWith("00", 1)) &&
                (position.startsWith("N01") | position.startsWith("S01"))) return true;
        if (vertical && (newPosition.substring(4).equals("00")) &&
                (position.substring(3).equals("W01")
                        | position.substring(3).equals("E01"))) return true;
        // check 01 and 00 condition
        if ((newPosition.startsWith("01", 1)) && (position.startsWith("00", 1)) &&
                horizontal) return true;
        if ((newPosition.substring(4).equals("01"))
                && (position.substring(4).equals("00")) && vertical) return true;
        // the other normal conditions
        int horizontalDifference = Integer.parseInt(newPosition.substring(1, 3))
                - Integer.parseInt(position.substring(1, 3));
        int verticalDifference = Integer.parseInt(newPosition.substring(4))
                - Integer.parseInt(position.substring(4));
        return (newPosition.charAt(0) == position.charAt(0))
                && (newPosition.charAt(3) == position.charAt(3))
                && ((((horizontalDifference == 1) | (horizontalDifference == -1))
                && (newPosition.substring(4).equals(position.substring(4))))
                | (((verticalDifference == 1) | (verticalDifference == -1))
                && (newPosition.substring(1, 3).equals(position.substring(1, 3)))));
    }

    /**
     * Determine whether the given gameState is valid.
     * A state is valid if the following conditions are met:
     * <p>
     * - There are exactly 48 cards in the game across the deck and each player's hand, arboretum and discard pile.
     * - There are no duplicates of any cards
     * - Every card in each player's arboretum is adjacent to at least one card played _before_ it.
     * - The number of card's in player B's arboretum is equal to, or one less than the number of cards in player
     * A's arboretum.
     * - Each player may have 0 cards in hand only if all cards are in the deck.
     * - Otherwise, a player has exactly 7 cards in their hand if it is not their turn.
     * - If it is a player's turn, they may have 7, 8, or 9 cards in hard.
     * - The number of cards in a player's discard pile is less than or equal to the number of cards in their arboretum.
     * <p>
     * You may assume that the gameState array is well-formed.
     *
     * @param gameState the game state array
     * @return true if the gameState is valid, false if it is not valid.
     * @author Ziyao Jin
     * TASK 8
     */
    public static boolean isStateValid(String[][] gameState) {
        // check 0 card in hand
        if (gameState[1][1].length() - 1 == 0 | gameState[1][2].length() - 1 == 0) {
            if (gameState[1][0].length() / 2 != 48) return false;
        } else {
            // check whose turn it is
            if (Objects.equals(gameState[0][0], "A")) {
                // check 7 or 8 or 9 cards in hand
                if ((gameState[1][1].length() - 1) / 2 != 7 && (gameState[1][1].length() - 1) / 2 != 8
                        && (gameState[1][1].length() - 1) / 2 != 9) return false;
                // check exact 7 cards in hand
                if ((gameState[1][2].length() - 1) / 2 != 7) return false;
            } else {
                if ((gameState[1][2].length() - 1) / 2 != 7 && (gameState[1][2].length() - 1) / 2 != 8
                        && (gameState[1][2].length() - 1) / 2 != 9) return false;
                if ((gameState[1][1].length() - 1) / 2 != 7) return false;
            }
        }
        // check number of cards in players' arboretum
        if ((gameState[0][3].length() - 1) / 8 > (gameState[0][1].length() - 1) / 8) return false;
        // check number of cards in players' discard pile
        if ((gameState[0][2].length() - 1) / 2 > (gameState[0][1].length() - 1) / 8) return false;
        if ((gameState[0][4].length() - 1) / 2 > (gameState[0][3].length() - 1) / 8) return false;
        // put all cards into one string
        StringBuilder allCards = new StringBuilder(gameState[1][0] + gameState[1][1].substring(1) +
                gameState[1][2].substring(1) + gameState[0][2].substring(1)
                + gameState[0][4].substring(1));
        for (var i = 1; i < gameState[0][1].length(); i += 8) {
            allCards.append(gameState[0][1], i, i + 2);
        }
        for (var i = 1; i < gameState[0][3].length(); i += 8) {
            allCards.append(gameState[0][3], i, i + 2);
        }
        // check exact 48 cards
        if (allCards.length() / 2 != 48) return false;
        // check no duplicates of any cards
        ArrayList<String> cardsList = new ArrayList<>();
        for (var i = 0; i < allCards.length(); i += 2) {
            if (cardsList.contains(allCards.substring(i, i + 2))) {
                return false;
            } else {
                cardsList.add(allCards.substring(i, i + 2));
            }
        }
        // check adjacent cards in each player's arboretum
        if (!checkBefore(gameState[0][1])) return false;
        return checkBefore(gameState[0][3]);
    }

    public static boolean checkBefore(String arboretumString) {
        ArrayList<String> arboretumList = new ArrayList<>();
        for (var i = 1; i < arboretumString.length(); i += 8) {
            arboretumList.add(arboretumString.substring(i + 2, i + 8));
        }
        if (arboretumList.size() == 1 && !Objects.equals(arboretumList.get(0), "C00C00")) return false;
        while (arboretumList.size() > 1) {
            boolean checkAdjacent = false;
            for (var i : arboretumList) {
                if (isAdjacent(arboretumList.get(arboretumList.size() - 1), i)) {
                    checkAdjacent = true;
                    arboretumList.remove(arboretumList.size() - 1);
                    break;
                }
            }
            if (!checkAdjacent) return false;
        }
        return true;
    }

    /**
     * Determine whether the given player has the right to score the given species. Note: This does not check whether
     * a viable path exists. You may gain the right to score a species that you do not have a viable scoring path for.
     * See "Gaining the Right to Score" in `README.md`.
     * You may assume that the given gameState array is valid.
     * <p>
     * You may assume that the inputs to this method are valid and/or well-formed.
     *
     * @param gameState the game state array.
     * @param player    the player attempting to score.
     * @param species   the species that is being scored.
     * @return true if the given player has the right to score this species, false if they do not have the right.
     * @author Hanqin Liu
     * TASK 9
     */
    public static boolean canScore(String[][] gameState, char player, char species) {
        char playerOpponent = ' ';
        if (player == 'A') {
            playerOpponent = 'B';
        } else if (player == 'B') {
            playerOpponent = 'A';
        }
        int num = scoreFromHand(gameState, player, species);
        int num_2 = scoreFromHand(gameState, playerOpponent, species);
        //If a player has an "8" of a species in his hand and his opponent has a "1" of that species,
        // then the value "8" is considered to be "0" for the purpose of determining who has the highest total.
        if (storeFromHand(gameState, player).contains(species + "8") && storeFromHand(gameState, playerOpponent).contains(species + "1")) {
            num -= 8;
        } else if (storeFromHand(gameState, player).contains(species + "1") && storeFromHand(gameState, playerOpponent).contains(species + "8")) {
            num_2 -= 8;
        }
        return num > num_2 || num == num_2;
    }

    public static ArrayList<String> storeFromHand(String[][] gameState, char player) {
        //Draw the player's hand and store it in the ArrayList
        String hand = "";
        ArrayList<String> handCards = new ArrayList<>();
        if (player == 'A') {
            hand = gameState[1][1];
        } else if (player == 'B') {
            hand = gameState[1][2];
        }
        for (int i = 1; i < hand.length() - 1; i += 2) {
            handCards.add(hand.substring(i, i + 2));
        }
        return handCards;
    }

    public static int scoreFromHand(String[][] gameState, char player, char species) {
        //For a given specie, sum the values of such cards in the player's hand.
        String hand = "";
        if (player == 'A') {
            hand = gameState[1][1];
        } else if (player == 'B') {
            hand = gameState[1][2];
        }
        int sum = 0;
        for (int i = 1; i < hand.length(); i += 2) {
            if (hand.charAt(i) == species) {
                sum += Character.getNumericValue(hand.charAt(i + 1));
            }
        }
        return sum;
    }

    /**
     * Find all the valid placements for the given card for the player whose turn it is.
     * A placement is valid if it satisfies the following conditions:
     * 1. The card is horizontally or vertically adjacent to at least one other placed card.
     * 2. The card does not overlap with an already-placed card.
     * <p>
     * You may assume that the inputs to this method are valid and/or well-formed.
     *
     * @param gameState the game state array
     * @param card      the card to play
     * @return a set of valid card placement strings for the current player.
     * @author Ziyao Jin
     * TASK 10
     */
    public static Set<String> getAllValidPlacements(String[][] gameState, String card) {
        Set<String> placementSet = new HashSet<>();
        List<String> posList = new ArrayList<>();
        // get current turn
        int turn;
        if (Objects.equals(gameState[0][0], "A")) {
            turn = 1;
        } else {
            turn = 3;
        }
        // if there is no card in arboretum
        if ((Objects.equals(gameState[0][0], "A") && gameState[0][turn].length() == 1)
                | (Objects.equals(gameState[0][0], "B") && gameState[0][turn].length() == 1)) {
            placementSet.add(card + "C00C00");
            return placementSet;
        }
        // normal condition
        for (int i = 1; i < gameState[0][turn].length(); i += 8) {
            posList.add(gameState[0][turn].substring(i + 2, i + 8));
        }
        for (var i : posList) placementSet.addAll(findAdjacent(card, i));
        for (var i : posList) placementSet.remove(card + i);
        return placementSet;
    }

    public static Set<String> findAdjacent(String card, String posString) {
        // generate all adjacent placements
        Set<String> placementSet = new HashSet<>();
        Set<String> verticalSet = new HashSet<>();
        Set<String> horizontalSet = new HashSet<>();
        if (posString.startsWith("N01") | posString.startsWith("S01")) {
            verticalSet.add("C00");
            verticalSet.add(posString.charAt(0) + "02");
        } else if (posString.startsWith("C00")) {
            verticalSet.add("N01");
            verticalSet.add("S01");
        } else {
            String v1 = String.valueOf(Integer.parseInt(posString.substring(1, 3)) - 1);
            String v2 = String.valueOf(Integer.parseInt(posString.substring(1, 3)) + 1);
            buildPosition(posString, verticalSet, v1, v2, 0);
        }
        if (posString.substring(3).equals("E01") | posString.substring(3).equals("W01")) {
            horizontalSet.add("C00");
            horizontalSet.add(posString.charAt(3) + "02");
        } else if (posString.substring(3).equals("C00")) {
            horizontalSet.add("E01");
            horizontalSet.add("W01");
        } else {
            String h1 = String.valueOf(Integer.parseInt(posString.substring(4)) - 1);
            String h2 = String.valueOf(Integer.parseInt(posString.substring(4)) + 1);
            buildPosition(posString, horizontalSet, h1, h2, 3);
        }
        for (var i : verticalSet) placementSet.add(card + i + posString.substring(3));
        for (var i : horizontalSet) placementSet.add(card + posString.substring(0, 3) + i);
        return placementSet;
    }

    public static void buildPosition(String posString, Set<String> stringSet, String string1,
                                     String string2, Integer index) {
        // combine vertical and horizontal information together
        if (string1.length() == 1) {
            stringSet.add(posString.charAt(index) + "0" + string1);
        } else {
            stringSet.add(posString.charAt(index) + string1);
        }
        if (string2.length() == 1) {
            stringSet.add(posString.charAt(index) + "0" + string2);
        } else {
            stringSet.add(posString.charAt(index) + string2);
        }
    }

    /**
     * Find all viable scoring paths for the given player and the given species if this player has the right to
     * score this species.
     * <p>
     * A "scoring path" is a non-zero number of card substrings in order from starting card to ending card.
     * For example: "a1a3b6c7a8" is a path of length 5 starting at "a1" and ending at "a8".
     * <p>
     * A path is viable if the following conditions are met:
     * 1. The player has the right to score the given species.
     * 2. Each card along the path is orthogonally adjacent to the previous card.
     * 3. Each card has value greater than the previous card.
     * 4. The path begins with the specified species.
     * 5. The path ends with the specified species.
     * <p>
     * You may assume that the inputs to this method are valid and/or well-formed.
     *
     * @param gameState the game state array
     * @param player    the given player
     * @param species   the species the path must start and end with.
     * @return a set of all viable scoring paths if this player has the right to score this species, or null if this
     * player does not have the right to score this species. If the player has no viable scoring paths (but has the
     * right to score this species), return the empty Set.
     * @author Ziyao Jin
     * TASK 12
     */
    public static Set<String> getAllViablePaths(String[][] gameState, char player, char species) {
        // return null if the player does not have the right to score this species
        if (!canScore(gameState, player, species)) return null;
        // distinguish player and put arboretum into one string
        var allCards = player == 'A' ? gameState[0][1] : gameState[0][3];
        // put all cards and selected species into two sets
        Set<String> cardsSet = new HashSet<>();
        Set<String> speciesSet = new HashSet<>();
        for (int i = 1; i < allCards.length(); i += 8) {
            cardsSet.add(allCards.substring(i, i + 8));
            if (allCards.charAt(i) == species) speciesSet.add(allCards.substring(i, i + 8));
        }
        // record viable paths
        Set<String> placementOutput = new HashSet<>();
        Set<String> cardOutput = new HashSet<>();
        while (!pathFinder(speciesSet, cardsSet).isEmpty()) {
            var resultSet = pathFinder(speciesSet, cardsSet);
            for (var i : resultSet) {
                if (i.length() > 8) {
                    if (i.charAt(0) == i.charAt(i.length() - 8)) placementOutput.add(i);
                }
                speciesSet = resultSet;
            }
        }
        // change the output format from "a2S01E01a3C00E01" to "a2a3"
        for (var i : placementOutput) {
            StringBuilder tempCard = new StringBuilder();
            for (int j = 0; j < i.length(); j += 8) {
                tempCard.append(i, j, j + 2);
            }
            cardOutput.add(tempCard.toString());
        }
        return cardOutput;
    }

    /**
     * Generate one-step-further path based on the given path.
     * <p>
     * This is a helper method made for getAllViablePaths().
     *
     * @param speciesSet a set of cards with selected species
     * @param cardsSet   a set of all cards in the arboretum
     * @return a set of one-step-further path
     * @author Ziyao Jin
     * TASK 12
     */
    public static Set<String> pathFinder(Set<String> speciesSet, Set<String> cardsSet) {
        Set<String> resultSet = new HashSet<>();
        for (var specie : speciesSet) {
            // get the last card of current path
            String lastOfSpecie = specie.substring(specie.length() - 8);
            for (var neighbor : findAdjacent(lastOfSpecie.substring(0, 2), lastOfSpecie.substring(2))) {
                for (var card : cardsSet) {
                    // check if the card has neighbor in current arboretum
                    if (card.substring(2).equals(neighbor.substring(2))) {
                        // check if the card has value greater than the previous card
                        if (card.charAt(1) > lastOfSpecie.charAt(1)) resultSet.add(specie + card);
                    }
                }
            }
        }
        return resultSet;
    }

    /**
     * Find the highest score of the viable paths for the given player and species.
     * <p>
     * You may assume that the inputs to this method are valid and/or well-formed.
     *
     * @param gameState the game state array
     * @param player    the given player
     * @param species   the species to score
     * @return the score of the highest scoring viable path for this player and species.
     * If this player does not have the right to score this species, return -1.
     * If this player has the right to score this species but there is no viable scoring path, return 0.
     * @author Ziyao Jin
     * TASK 13
     */
    public static int getHighestViablePathScore(String[][] gameState, char player, char species) {
        // check can score or not
        if (!canScore(gameState, player, species)) return -1;
        // check if there is viable scoring path
        if (Objects.requireNonNull(getAllViablePaths(gameState, player, species)).isEmpty()) return 0;
        var allPaths = getAllViablePaths(gameState, player, species);
        List<Integer> scoreList = new ArrayList<>();

        assert allPaths != null;
        for (var i : allPaths) {
            int score = 0;
            int cardNum = i.length() / 2;
            // 1 point for each card
            score += cardNum;
            // additional point for same species
            boolean checkSame = true;
            for (int j = 0; j < i.length(); j += 2) {
                if (i.charAt(j) != species) {
                    checkSame = false;
                    break;
                }
            }
            if (cardNum >= 4 && checkSame) score += cardNum;
            // additional point if the path begins with "1"
            if (i.charAt(1) == '1') score += 1;
            // additional points if the path ends with "8"
            if (i.charAt(i.length() - 1) == '8') score += 2;
            // store in list
            scoreList.add(score);
        }
        // get the highest score
        int highest = scoreList.get(0);
        for (var i : scoreList) {
            if (i > highest) highest = i;
        }
        return highest;
    }

    /**
     * When the game is ended, calculate the player's total score.
     * <p>
     *
     * @param gameState the game state array
     * @param player    the given player
     * @return Total score of all viable paths in this player's Arboretum that have scoring rights.
     * If this player does not have the right to score this species, return -1.
     * If this player has the right to score this species but there is no viable scoring path, return 0.
     * The above two cases are not counted in the total score.
     * @author Hanqin Liu
     */
    public static int getTotalViablePathScore(String[][] gameState, char player) {
        int score = 0;
        char[] species = {'a', 'b', 'c', 'd', 'j', 'm'};
        for (char c : species) {
            if (getHighestViablePathScore(gameState, player, c) > 0) {
                score += getHighestViablePathScore(gameState, player, c);
            }
        }
        return score;
    }

    /**
     * Find the winner of the game.
     * <p>
     *
     * @param gameState the game state array
     * @return Returns a string that outputs the state at the end of the game.
     * @author Hanqin Liu
     */
    public static String getWinner(String[][] gameState) {
        HashSet<Object> speciesSetA = new HashSet<>();
        HashSet<Object> speciesSetB = new HashSet<>();
        if (getTotalViablePathScore(gameState, 'A') > getTotalViablePathScore(gameState, 'B')) {
            return "The winner is player A!";
        } else if (getTotalViablePathScore(gameState, 'A') < getTotalViablePathScore(gameState, 'B')) {
            return "The winner is player B!";
        } else {

            for (int i = 1; i < gameState[0][1].length(); i += 8) {
                speciesSetA.add(gameState[0][1].charAt(i));
            }
            for (int i = 1; i < gameState[0][3].length(); i += 8) {
                speciesSetB.add(gameState[0][3].charAt(i));
            }
            if (speciesSetA.size() > speciesSetB.size()) {
                return "The winner is player A!";
            } else if (speciesSetA.size() < speciesSetB.size()) {
                return "The winner is player B!";
            } else {
                return "The game is a tie!";
            }
        }
    }

    /**
     * AI Part 1:
     * Decide whether to draw a card from the deck or a discard pile.
     * Note: This method only returns the choice, it does not update the game state.
     * If you wish to draw a card from the deck, return "D".
     * If you wish to draw a card from a discard pile, return the card string of the (top) card you wish to draw.
     * You may count the number of cards in a players' hand to determine whether this is their first or final draw
     * for the turn.
     * <p>
     * Note: You may only draw the top card of a players discard pile. ie: The last card that was added to that pile.
     * Note: There must be cards in the deck (or alternatively discard) to draw from the deck (or discard) respectively.
     * <p>
     * You may assume that the inputs to this method are valid and/or well-formed.
     *
     * @param gameState the game state array
     * @return "D" if you wish to draw from the deck, or the card string of the card you wish to draw from a discard
     * pile.
     * @author Hanqin Liu
     * @author Weiqiang PU
     * TASK 14
     */
    public static String chooseDrawLocation(String[][] gameState) {
        switch ((int) (Math.random() * 3)) { //randomly choose draw location
            case 0 -> {
                if (gameState[1][0].length() > 0) return "D"; //if there is card in draw location, draw it
                else return chooseDrawLocation(gameState); //if not, re-draw
            }
            case 1 -> {
                if (gameState[0][2].length() > 1) return gameState[0][2].substring(gameState[0][2].length() - 2);
                else return chooseDrawLocation(gameState);
            }
            case 2 -> {
                if (gameState[0][4].length() > 1) return gameState[0][4].substring(gameState[0][4].length() - 2);
                else return chooseDrawLocation(gameState);
            }
            default -> {
                return null;
            }
        }
    }


    /**
     * AI Part 2:
     * Generate a moveString array for the player whose turn it is.
     * <p>
     * A moveString array consists of two parts;
     * moveString[0] is the valid card _placement_ string for the card you wish to place.
     * moveString[1] is the card string of the card you wish to discard.
     * <p>
     * For example: If I want to play card "a1" in location "N01E02" and discard card "b4" then my moveString[] would
     * be as follows:
     * moveString[0] = "a1N01E02"
     * moveString[1] = "b4"
     * <p>
     * You may assume that the inputs to this method are valid and/or well-formed.
     *
     * @param gameState the game state array
     * @return a valid move for this player.
     * @author Weiqiang PU
     * TASK 15
     */
    public static String[] generateMove(String[][] gameState) {
        int turnControl = getHandIndex(gameState); //check whose turn
        ArrayList<String> handCard = new ArrayList<>(Arrays.asList(getValueFromHidden(gameState[1])[turnControl]));
        //generate random placement
        int randomPlaceCard = (int) (Math.random() * 8);
        String placeCard = handCard.get(randomPlaceCard);
        String[] validPlacements = getAllValidPlacements(gameState, placeCard).toArray(new String[0]);
        String generatePlace = validPlacements[(int) (Math.random() * validPlacements.length)];
        handCard.remove(randomPlaceCard); //remove placed card from hand
        return new String[]{generatePlace, handCard.get((int) (Math.random() * 7))}; //generate discard
    }

    /**
     * A convenient way to get the index of corresponding hand card.
     *
     * @param gameState a given game state.
     * @return the index of corresponding hand card
     * @author Weiqiang PU
     */
    private static int getHandIndex(String[][] gameState) {
        int turnControl = 0; //check whose turn
        switch (gameState[0][0]) {
            case "A" -> turnControl = 1;
            case "B" -> turnControl = 2;
            case "C" -> turnControl = 3;
            case "D" -> turnControl = 4;
        }
        return turnControl;
    }

    /**
     * Decide whether to draw a card from the deck or a discard pile.
     *
     * @param gameState the game state array
     * @return "D" if you wish to draw from the deck, or the card string of the card you wish to draw from a discard
     * pile.
     * @author Ziyao Jin
     */
    public static String advancedAiLocation(String[][] gameState) {
        // get current turn
        int arboretumIndex;
        if (Objects.equals(gameState[0][0], "A")) {
            arboretumIndex = 1;
        } else {
            arboretumIndex = 3;
        }
        // get current cards in arboretum
        ArrayList<String> arboretumCard = new ArrayList<>();
        for (int i = 1; i < gameState[0][arboretumIndex].length(); i+=8) {
            arboretumCard.add(gameState[0][arboretumIndex].substring(i, i + 1));
        }
        // find distinct species
        Set<String> cardSpecies = new HashSet<>();
        for (var i : arboretumCard) {
            cardSpecies.add(i.substring(0,1));
        }
        // check if cards in discard piles are useful
        // If not, draw from deck
        if (gameState[0][2].length() > 1 &&
                cardSpecies.contains(gameState[0][2].substring(
                        gameState[0][2].length() - 2, gameState[0][2].length() - 1))) {
            return gameState[0][2].substring(gameState[0][2].length() - 2);
        } else if (gameState[0][4].length() > 1 &&
                cardSpecies.contains(gameState[0][4].substring(
                        gameState[0][4].length() - 2, gameState[0][4].length() - 1))) {
            return gameState[0][4].substring(gameState[0][4].length() - 2);
        } else {
            if (gameState[1][0].length() > 0) {
                return "D"; //if there is card in draw location, draw it
            } else if (gameState[0][2].length() > 1) {
                return gameState[0][2].substring(gameState[0][2].length() - 2);
            } else {
                return gameState[0][4].substring(gameState[0][4].length() - 2);
            }
        }
    }

    /**
     * Generate a moveString array for the player whose turn it is.
     *
     * @param gameState the game state array
     * @return a valid move for this player.
     * @author Ziyao Jin
     */
    public static String[] advancedAiMove(String[][] gameState) {
        //check whose turn
        int turnControl = getHandIndex(gameState);
        ArrayList<String> handCard = new ArrayList<>(Arrays.asList(getValueFromHidden(gameState[1])[turnControl]));
        int arboretumIndex;
        if (Objects.equals(gameState[0][0], "A")) {
            arboretumIndex = 1;
        } else {
            arboretumIndex = 3;
        }
        // get moves with the highest score
        var allMoves = new ArrayList<ArrayList<String>>();
        var goodMoves = new ArrayList<ArrayList<String>>();
        for (var card : handCard) {
            var validPlacements = getAllValidPlacements(gameState, card);
            for (var i : validPlacements) {
                gameState[0][arboretumIndex] += i;
                var score = getTotalViablePathScore(gameState, gameState[0][0].charAt(0));
                var combinedList = new ArrayList<String>();
                combinedList.add(i);
                combinedList.add(String.valueOf(score));
                allMoves.add(combinedList);
                gameState[0][arboretumIndex] = gameState[0][arboretumIndex].substring(
                        0, gameState[0][arboretumIndex].length()-8);
            }
        }
        var highestScore = getTotalViablePathScore(gameState, gameState[0][0].charAt(0));
        for (var i : allMoves) if (Integer.parseInt(i.get(1)) > highestScore) highestScore = Integer.parseInt(i.get(1));
        for (var i : allMoves) if (Integer.parseInt(i.get(1)) == highestScore) goodMoves.add(i);
        // find the move with the smallest card number
        var output = findBest(goodMoves);

        //discard
        assert output != null;
        handCard.remove(output.get(0).substring(0, 2));
        ArrayList<String> arboretumCard = new ArrayList<>();
        for (int i = 1; i < gameState[0][arboretumIndex].length(); i+=8) {
            arboretumCard.add(gameState[0][arboretumIndex].substring(i, i + 1));
        }
        arboretumCard.add(output.get(0).substring(0,1));
        var discard = chooseBestDiscard(handCard, arboretumCard);

        return new String[]{output.get(0), discard};
    }

    private static String chooseBestDiscard(ArrayList<String> handCard, ArrayList<String> arboretumCard) {
        // do not discard useful cards(cards in current arboretum)
        Set<String> cardSpecies = new HashSet<>();
        ArrayList<String> handCopy = new ArrayList<>(handCard);
        for (var i : arboretumCard) {
            cardSpecies.add(i.substring(0,1));
        }
        handCopy.removeIf(i -> cardSpecies.contains(i.substring(0, 1)));
        // discard a random card if all useful
        if (!handCopy.isEmpty()) return handCopy.get(0);
        return handCard.get((int) (Math.random() * 7));
    }

    private static ArrayList<String> findBest(ArrayList<ArrayList<String>> moveList) {
        // find the card with the smallest card number
        int smallest = Integer.MAX_VALUE;
        for (var i : moveList) {
            if (Integer.parseInt(i.get(0).substring(1,2)) < smallest) {
                smallest = Integer.parseInt(i.get(0).substring(1,2));
            }
        }
        for (var i : moveList) {
            if (Integer.parseInt(i.get(0).substring(1,2)) == smallest) return i;
        }
        return null;
    }

    /**
     * Extracting deck and hand card value from given string
     *
     * @param gameStatePiece the game state array
     * @return an array of array that stores card value
     * @author Weiqiang PU
     */
    public static String[][] getValueFromHidden(String[] gameStatePiece) {
        String[][] getValueFromHidden = new String[gameStatePiece.length][];
        for (int a = 0; a < gameStatePiece.length; a++) { //extract card substring
            ArrayList<String> getState = new ArrayList<>();
            for (int b = a == 0 ? 0 : 1; b < gameStatePiece[a].length() - 1; b += 2) {
                getState.add(gameStatePiece[a].substring(b, b + 2));
            }
            getValueFromHidden[a] = getState.toArray(new String[0]);
        }
        return getValueFromHidden;
    }

    /**
     * Extracting turnID, player's arboretum and discard from the given string
     * <p>
     * sharedState[0] - a single character ID string, either "A" or "B" representing whose turn it is.
     * <p>
     * sharedState[1] - Player A's arboretum
     * 0th character is 'A'
     * Followed by a number of 8-character _placement_ substrings as defined below that occur in the order they were played. Note: these are NOT sorted alphanumerically.
     * For example: "Ab1C00C00a4N01C00c3C00E01c6N02C00m7N02W01m4N01E01a5N02E01a2S01E01"
     * <p>
     * sharedState[2] - Player A's discard
     * 0th character is 'A'
     * Followed by a number of 2-character _card_ substrings that occur in the order they were played. Note: these are NOT sorted alphanumerically.
     * For example: "Aa7c4d6"
     * <p>
     * sharedState[3] - Player B's arboretum
     * 0th character is 'B'
     * Followed by a number of 8-character _placement_ substrings that occur in the order they were played. Note: these are NOT sorted alphanumerically.
     * For example: "Bj5C00C00j6S01C00j7S02W01j4C00W01m6C00E01m3C00W02j3N01W01"
     * <p>
     * sharedState[4] - Player B's discard
     * 0th character is 'B'
     * Followed by a number of 2-character _card_ substrings that occur in the order they were played. Note: these are NOT sorted alphanumerically.
     * For example: "Bb2d4c5a1d5"
     * <p>
     * For example:
     * gameStatePiece = {"A",
     * "Ab1C00C00a4N01C00c3C00E01c6N02C00m7N02W01m4N01E01a5N02E01a2S01E01c8N02W02c1S01C00b6N03E01m8N03W01m1S02E01", "Aa3",
     * "Bj5C00C00j6S01C00j7S01W01j4C00W01m6C00E01m3C00W02j3N01W01d2N02W01d7S02C00b8S02E01b3N01C00d1N03W01d8S03C00", "B"}
     * <p>
     * getValueFromShared = {{"A"},
     * {"b1","C00","C00","a4","N01“,”C00“,”c3“,”C00“,”E01“,”c6“,”N02","C00","m7","N02","W01","m4","N01","E01","a5","N02","E01","a2","S01","E01","c8","N02","W02","c1","S01","C00","b6","N03","E01","m8","N03","W01","m1","S02","E01"},
     * {"a3"},
     * {"j5","C00","C00","j6","S01","C00","j7","S01","W01","j4","C00","W01","m6","C00","E01","m3","C00","W02","j3","N01","W01","d2","N02","W01","d7","S02","C00","b8","S02","E01","b3","N01","C00","d1","N03","W01","d8","S03","C00"},
     * {""}}
     *
     * @param gameStatePiece the game state array
     * @return an array of array that stores card value
     * @author Weiqiang PU
     */
    public static String[][] getValueFromShared(String[] gameStatePiece) {
        String[][] getValueFromShared = new String[gameStatePiece.length][];
        getValueFromShared[0] = gameStatePiece[0].isBlank() ? new String[0] : new String[]{gameStatePiece[0].substring(0, 1)}; //get turnID
        for (int a = 2; a <= gameStatePiece.length - 1; a += 2) { //extract discard card
            ArrayList<String> getState = new ArrayList<>();
            for (int b = 1; b < gameStatePiece[a].length() - 1; b += 2) {
                getState.add(gameStatePiece[a].substring(b, b + 2));
            }
            getValueFromShared[a] = getState.toArray(new String[0]);
        }
        for (int c = 1; c < gameStatePiece.length - 1; c += 2) { //extract arboretum and direction
            ArrayList<String> getState = new ArrayList<>();
            for (int d = 1; d < gameStatePiece[c].length() - 7; d += 8) {
                getState.add(gameStatePiece[c].substring(d, d + 2)); //tree piece and value
                getState.add(gameStatePiece[c].substring(d + 2, d + 5)); //direction1
                getState.add(gameStatePiece[c].substring(d + 5, d + 8)); //direction2
            }
            getValueFromShared[c] = getState.toArray(new String[0]);
        }
        return getValueFromShared;
    }
}

