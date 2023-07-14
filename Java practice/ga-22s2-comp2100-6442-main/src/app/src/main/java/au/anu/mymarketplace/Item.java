import java.io.Serializable;

/**
 * @author Weiqiang Pu
 * @date 2022/9/16  21:25
 */
public class Item implements Comparable<Item>, Serializable {
    private String name, seller, price, description, image;
    private int rating, sn, sales, stock;
    private Tag tag;

    //Item object contains different attributes.
    public Item(int sn, String name, String seller, String price, int rating, int sales, Tag tag, String description, String imagePath, int stock) {
        this.name = name;
        this.seller = seller;
        this.price = price;
        this.rating = rating;
        this.sn = sn;
        this.sales = sales;
        this.tag = tag;
        this.description = description;
        this.image = imagePath;
        this.stock = stock;
    }

    public Item() {
        // Default constructor required for calls to DataSnapshot.getValue(Item.class)
    }

    public Item(String name, String price) {
        /**
         * This constructor is for Sort and Search Test
         * DO NOT REMOVE THIS! Otherwise, fix the Sort and Search Test
         * @author Zeyu Zhang u7394442
         */
        this.name = name;
        this.price = price;
    }

    @Override
    public int compareTo(Item i) {
        return Integer.compare(hashCode(), i.hashCode());
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }


    public int getSales() {
        return sales;
    }


    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public int getStock() {
        return stock;
    }


    public enum Tag {
        COMPUTER, COSMETICS, FOOD, FURNITURE, MATERNAL_INFANT, MEN, OUTDOOR, PET, STATIONARY, TOY, WOMEN
    }
}
