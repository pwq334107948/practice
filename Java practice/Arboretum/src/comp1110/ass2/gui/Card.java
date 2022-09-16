package Arboretum.out.test.comp1110;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class Card extends ImageView {
    /**
     * a card constructor, set size and image
     *
     * @param species card value and species
     * @author Weiqiang PU
     */
    Card(String species) {
        setImage(new Image(Objects.requireNonNull(Game.class.getResource("assets/Card/" + species + ".png")).toString()));
        setFitHeight(120);
        setFitWidth(76);
    }
}
