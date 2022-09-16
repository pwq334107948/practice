package RedBlackTree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Weiqiang Pu
 * @date 2022/9/15  14:25
 */
public class RedBlackTreeTest {
    RedBlackTree<Integer,String> tree;

    @BeforeEach
    public void setUp() {
        tree = new RedBlackTree<Integer, String>();
        tree.insert(7,"G");
        tree.insert(5,"E");
        tree.insert(9,"I");
    }

    @Test
    @Timeout(1000)
    public void testInsert() {
        assertEquals("7 5 9", tree.preOrder());
    }

    @Test@Timeout(1000)
    public void testInsertDuplicate() {
        tree.insert(9,"D");
        assertEquals("7 5 9", tree.preOrder());
    }

    @Test@Timeout(1000)
    public void testNodeRed() {
        assertSame(tree.get(9).getColour(), RedBlackTree.Colour.RED);
    }

    @Test@Timeout(1000)
    public void testInsertNewNode() {
        tree.insert(12,"L");
        assertEquals("7 5 9 12", tree.preOrder());
    }

    @Test@Timeout(1000)
    public void testNodeRedToBlack() {
        tree.insert(12,"L");
        assertTrue(tree.get(5).getColour() == RedBlackTree.Colour.BLACK && tree.get(9).getColour() == RedBlackTree.Colour.BLACK);
    }

    @Test@Timeout(1000)
    public void testInsertNewNodeRed() {
        tree.insert(12,"L");
        assertSame(tree.get(12).getColour(), RedBlackTree.Colour.RED);
    }

    @Test@Timeout(1000)
    public void testInsertNewNodeNoColourChange() {
        tree.insert(12,"L");
        tree.insert(8,"H");
        assertTrue(tree.get(5).getColour() == RedBlackTree.Colour.BLACK
                && tree.get(9).getColour() == RedBlackTree.Colour.BLACK
                && tree.get(8).getColour() == RedBlackTree.Colour.RED
                && tree.get(12).getColour() == RedBlackTree.Colour.RED);
    }


    @Test@Timeout(1000)
    public void testInsertRotateRight() {
        tree.insert(12,"L");
        tree.insert(8,"H");
        tree.insert(11,"K");
        tree.insert(10,"J");

        assertEquals("7 5 9 8 11 10 12", tree.preOrder());
    }

    @Test@Timeout(1000)
    public void testInsertRotateRightRightLeft() {
        tree.insert(12,"L");
        tree.insert(11,"K");

        assertEquals("7 5 11 9 12", tree.preOrder());
    }

    @Test@Timeout(1000)
    public void testSearch1() {
        assertEquals(7, (int) tree.get(7).getKey());
    }

    @Test@Timeout(1000)
    public void testSearch2() {
        assertEquals(5, (int) tree.get(5).getKey());
    }

    @Test@Timeout(1000)
    public void testSearch3() {
        assertNull(tree.get(-3));
    }

    @Test@Timeout(1000)
    public void testSearch4() {
        assertNull(tree.get(26));
    }

    @Test@Timeout(1000)
    public void DeleteNullTest() {
        assertNull(tree.delete(11));
    }

    @Test@Timeout(1000)
    public void DeleteLeafWithoutRotationTest() {
        tree.insert(12,"L");
        tree.insert(8,"H");
        tree.insert(11,"K");

        assertEquals("K",tree.delete(11));
        assertEquals("7 5 9 8 12", tree.preOrder());
    }

    @Test@Timeout(1000)
    public void DeleteLeafWithRotationTest() {
        tree.insert(12,"L");
        tree.insert(8,"H");
        tree.insert(11,"K");
        tree.delete(8);

        assertEquals("7 5 11 9 12", tree.preOrder());
    }

    @Test@Timeout(1000)
    public void DeleteNodeOnlyHaveOneLeafTest() {
        tree.insert(12,"L");
        tree.insert(8,"H");
        tree.insert(11,"K");
        tree.delete(12);

        assertEquals("7 5 9 8 11", tree.preOrder());
    }

    @Test@Timeout(1000)
    public void DeleteNodeHaveTwoLeavesTest() {
        tree.insert(12,"L");
        tree.insert(8,"H");
        tree.insert(11,"K");
        tree.insert(10,"J");
        tree.delete(11);

        assertEquals("7 5 9 8 12 10", tree.preOrder());
    }

    @Test@Timeout(1000)
    public void ClearTest() {
        tree.clear();
        assertNull(tree.get(7));
    }
}
