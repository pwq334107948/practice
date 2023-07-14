import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * @author Weiqiang Pu
 * @date 2022/9/15  14:25
 */
public class RedBlackTreeTest {
    RedBlackTree<Integer, String> tree;

    @Before
    public void setUp() {
        tree = new RedBlackTree<>();
        tree.insert(7, "G");
        tree.insert(5, "E");
        tree.insert(9, "I");
    }

    @Test(timeout = 1000)
    public void testInsert() {
        Assert.assertEquals("7 5 9", tree.preOrder());
    }

    @Test(timeout = 1000)
    public void testInsertDuplicate() {
        tree.insert(9, "D");
        Assert.assertEquals("7 5 9", tree.preOrder());
    }

    @Test(timeout = 1000)
    public void testNodeRed() {
        Assert.assertSame(tree.get(9).getColour(), RedBlackTree.Colour.RED);
    }

    @Test(timeout = 1000)
    public void testInsertNewNode() {
        tree.insert(12, "L");
        Assert.assertEquals("7 5 9 12", tree.preOrder());
    }

    @Test(timeout = 1000)
    public void testNodeRedToBlack() {
        tree.insert(12, "L");
        Assert.assertTrue(tree.get(5).getColour() == RedBlackTree.Colour.BLACK && tree.get(9).getColour() == RedBlackTree.Colour.BLACK);
    }

    @Test(timeout = 1000)
    public void testInsertNewNodeRed() {
        tree.insert(12, "L");
        Assert.assertSame(tree.get(12).getColour(), RedBlackTree.Colour.RED);
    }

    @Test(timeout = 1000)
    public void testInsertNewNodeNoColourChange() {
        tree.insert(12, "L");
        tree.insert(8, "H");
        Assert.assertTrue(tree.get(5).getColour() == RedBlackTree.Colour.BLACK
                && tree.get(9).getColour() == RedBlackTree.Colour.BLACK
                && tree.get(8).getColour() == RedBlackTree.Colour.RED
                && tree.get(12).getColour() == RedBlackTree.Colour.RED);
    }


    @Test(timeout = 1000)
    public void testInsertRotateRight() {
        tree.insert(12, "L");
        tree.insert(8, "H");
        tree.insert(11, "K");
        tree.insert(10, "J");

        Assert.assertEquals("7 5 9 8 11 10 12", tree.preOrder());
    }

    @Test(timeout = 1000)
    public void testInsertRotateRightRightLeft() {
        tree.insert(12, "L");
        tree.insert(11, "K");

        Assert.assertEquals("7 5 11 9 12", tree.preOrder());
    }

    @Test(timeout = 1000)
    public void testSearch1() {
        Assert.assertEquals(7, (int) tree.get(7).getKey());
    }

    @Test(timeout = 1000)
    public void testSearch2() {
        Assert.assertEquals(5, (int) tree.get(5).getKey());
    }

    @Test(timeout = 1000)
    public void testSearch3() {
        Assert.assertNull(tree.get(-3));
    }

    @Test(timeout = 1000)
    public void testSearch4() {
        Assert.assertNull(tree.get(26));
    }

    @Test(timeout = 1000)
    public void DeleteNullTest() {
        Assert.assertNull(tree.delete(11));
    }

    @Test(timeout = 1000)
    public void DeleteLeafWithoutRotationTest() {
        tree.insert(12, "L");
        tree.insert(8, "H");
        tree.insert(11, "K");

        Assert.assertEquals("K", tree.delete(11));
        Assert.assertEquals("7 5 9 8 12", tree.preOrder());
    }

    @Test(timeout = 1000)
    public void DeleteLeafWithRotationTest() {
        tree.insert(12, "L");
        tree.insert(8, "H");
        tree.insert(11, "K");
        tree.delete(8);

        Assert.assertEquals("7 5 11 9 12", tree.preOrder());
    }

    @Test(timeout = 1000)
    public void DeleteNodeOnlyHaveOneLeafTest() {
        tree.insert(12, "L");
        tree.insert(8, "H");
        tree.insert(11, "K");
        tree.delete(12);

        Assert.assertEquals("7 5 9 8 11", tree.preOrder());
    }

    @Test(timeout = 1000)
    public void DeleteNodeHaveTwoLeavesTest() {
        tree.insert(12, "L");
        tree.insert(8, "H");
        tree.insert(11, "K");
        tree.insert(10, "J");
        tree.delete(11);

        Assert.assertEquals("7 5 9 8 12 10", tree.preOrder());
    }

    @Test(timeout = 1000)
    public void ClearTest() {
        tree.clear();
        Assert.assertNull(tree.get(7));
    }
}
