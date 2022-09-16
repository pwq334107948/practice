package Arboretum.out.test.comp1110;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PositionCard extends Rectangle {
    private final String posV;

    private final String posH;

    /**
     * a special card which can contain position information
     *
     * @param posV vertical position
     * @param posH horizontal position
     * @author Ziyao Jin
     */
    public PositionCard(String posV, String posH) {
        super(76, 120, Color.WHITE);
        this.posV = posV;
        this.posH = posH;
    }

    public String getPosV() {
        return posV;
    }

    public String getPosH() {
        return posH;
    }
}
