import java.util.ArrayList;

import Item;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is the unit for testing the sort method and specialized search methods
 *
 * @author Zeyu Zhang u7394442
 */

public class SortAndSearchTest {



    @Test(timeout = 1000)
    public void testInsertDuplicate() {
        Item testItem1 = new Item("Dell", "$10,434,456.3232");
        Item testItem2 = new Item("Dell", "$20,434,456.3232");
        Item testItem3 = new Item("Dell", "$30,434,456.3232");
        Item testItem4 = new Item("Dell", "$40,434,456.3232");
        Item testItem5 = new Item("Dell", "$50,434,456.3232");
        Item testItem6 = new Item("Dell", "$60,434,456.3232");
        Item testItem7 = new Item("Dell", "$70,434,456.3232");
        Item testItem8 = new Item("Dell", "$80,434,456.3232");
        Item testItem9 = new Item("Dell", "$90,434,456.3232");


        ArrayList<Item> testArr = new ArrayList<>();
        testArr.add(testItem6);
        testArr.add(testItem1);
        testArr.add(testItem7);
        testArr.add(testItem9);
        testArr.add(testItem5);
        testArr.add(testItem2);
        testArr.add(testItem3);
        testArr.add(testItem4);
        testArr.add(testItem8);

        ArrayList<Item> ds = new ArrayList<>();
        ds.add(testItem9);
        ds.add(testItem8);
        ds.add(testItem7);
        ds.add(testItem6);
        ds.add(testItem5);
        ds.add(testItem4);
        ds.add(testItem3);
        ds.add(testItem2);
        ds.add(testItem1);

        ArrayList<Item> as = new ArrayList<>();
        as.add(testItem1);
        as.add(testItem2);
        as.add(testItem3);
        as.add(testItem4);
        as.add(testItem5);
        as.add(testItem6);
        as.add(testItem7);
        as.add(testItem8);
        as.add(testItem9);


        RedBlackTree testT = arrayToTree2(testArr);

        Assert.assertEquals(returnItem(testT, 70434450.0), testItem7);

        Assert.assertEquals(sortDescent(testArr), ds);

        Assert.assertEquals(sortAscent(testArr), as);

    }




    public static Item returnItem(RedBlackTree t,Double d) {
        return (Item) t.returnItem2(t.returnNode(d));
    }


    static RedBlackTree arrayToTree2(ArrayList<Item> items) {

        RedBlackTree testTree = new RedBlackTree();
        for (int i = 0; i < items.size(); i++) {
            testTree.insert(Double.valueOf(items.get(i).getPrice().replace("$", "").replace(",", "")),items.get(i));
        }
        return testTree;
    }

    private static ArrayList<Item> sortAscent(ArrayList<Item> list) {
        /**
         * This function has been called privately, I cannot call this function.
         *
         * Moreover, this function is also been set as non-static.
         *
         * Hence, even setup an instance, there are still errors based on the reasons above.
         *
         * DO NOT REMOVE THIS METHOD, otherwise you fix it!
         */

        ArrayList<Item> arr = new ArrayList<>();
        ArrayList<Item> cloned = new ArrayList<>(list);

        int[] a = new int[cloned.size()];

        double[] b = new double[cloned.size()];

        for (int i = 0; i < a.length; i++) {
            a[i] = i;
            b[i] = Double.parseDouble(cloned.get(i).getPrice().replace("$", "").replace(",", ""));
        }

        for (int i = cloned.size(); i > 0; i--) {
            int maxIndex = 0;
            int j = 1;
            while (j < i) {
                maxIndex = b[maxIndex] > b[j] ? maxIndex : j;
                j++;
            }

            // swap b
            double bmaxIndex = b[maxIndex];
            double bj_1 = b[j - 1];
            b[maxIndex] = bj_1;
            b[j - 1] = bmaxIndex;

            // swap a
            int amaxIndex = a[maxIndex];
            int aj_1 = a[j - 1];
            a[maxIndex] = aj_1;
            a[j - 1] = amaxIndex;

        }
        for (int i = 0; i < b.length; i++) {
            arr.add(cloned.get(a[i]));
        }
        return arr;
    }

    private static ArrayList<Item> sortDescent(ArrayList<Item> list) {

        /**
         * This function has been called privately, I cannot call this function.
         *
         * Moreover, this function is also been set as non-static.
         *
         * Hence, even setup an instance, there are still errors based on the reasons above.
         *
         * DO NOT REMOVE THIS METHOD, otherwise you fix it!
         */

        ArrayList<Item> arr = new ArrayList<>();
        ArrayList<Item> cloned = new ArrayList<>(list);


        int[] a = new int[cloned.size()];

        double[] b = new double[cloned.size()];

        for (int i = 0; i < a.length; i++) {
            a[i] = i;
            b[i] = Double.parseDouble(cloned.get(i).getPrice().replace("$", "").replace(",", ""));
        }

        for (int i = cloned.size(); i > 0; i--) {
            int maxIndex = 0;
            int j = 1;
            while (j < i) {
                maxIndex = b[maxIndex] < b[j] ? maxIndex : j;
                j++;
            }

            // swap b
            double bmaxIndex = b[maxIndex];
            double bj_1 = b[j - 1];
            b[maxIndex] = bj_1;
            b[j - 1] = bmaxIndex;

            // swap a
            int amaxIndex = a[maxIndex];
            int aj_1 = a[j - 1];
            a[maxIndex] = aj_1;
            a[j - 1] = amaxIndex;

        }

        for (int i = 0; i < b.length; i++) {
            arr.add(cloned.get(a[i]));
        }

        return arr;

    }


}






