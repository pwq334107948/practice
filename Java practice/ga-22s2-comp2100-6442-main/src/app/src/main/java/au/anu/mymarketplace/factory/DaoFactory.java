import ItemDaoImp;


public class DaoFactory {
    public static ItemDaoImp getItemDao() {
        return ItemDaoImp.getInstance();
    }

}
