import org.junit.Assert;
import org.junit.Test;
import program.Order;
import program.Product;
import program.Shop;

import java.util.*;

public class ProgramTests {

    private static Shop shop = new Shop("Maxima", "Aušros g. 26, Utena", Product.importProducts("testProductList.csv"));
    private static Shop shop1 = new Shop("Iki", "Aušros g. 26, Utena", Product.importProducts("testProductList.csv"));
    private static Shop shop2 = new Shop("Norfa", "Aušros g. 26, Utena", Product.importProducts("testProductList.csv"));
    private static Shop shop3 = new Shop("Rimi", "Aušros g. 26, Utena", Product.importProducts("testProductList.csv"));
    HashMap<String, Integer> orderMap = new HashMap<>(){
        {
            put("0000000001", 100);
            put("0000000002", 100);
            put("0000000003", 100);
            put("0000000010", 100);
            put("0000000011", 100);
            put("0000000012", 100);
        }
    };
    List<Order> orders = List.of(
            new Order("001", orderMap, Order.Type.PURCHASE),
            new Order("002", orderMap, Order.Type.PURCHASE),
            new Order("003", orderMap, Order.Type.PURCHASE),
            new Order("004", orderMap, Order.Type.SALE),
            new Order("005", orderMap, Order.Type.SALE),
            new Order("006", orderMap, Order.Type.SALE)
    );

    @Test
    public void importProductsTest(){
        List<Product> temp = List.of(
                new Product("Carrots",0.49,"0000000001",200),
                new Product("Apples",0.69,"0000000002",150),
                new Product("Chips",2.41, "0000000003",300),
                new Product("Eggs",1.10,"0000000004",70),
                new Product("Bread",2.49,"0000000005",100),
                new Product("Fish",4.49,"0000000006",200),
                new Product("Pork",4.49,"0000000007",200),
                new Product("Beef",4.49,"0000000008",200),
                new Product("Chicken",4.49,"0000000009",200),
                new Product("Cheese",4.49,"0000000010",200),
                new Product("Milk",4.49,"0000000011",200),
                new Product("Water",4.49,"0000000012",200),
                new Product("Soda",4.49,"0000000013",0),
                new Product("Juice",4.49,"0000000014",200),
                new Product("Pizza",4.49,"0000000015",0)
        );
        List<Product> temp2 = Product.importProducts("testProductList.csv");
        for (int i=0; i< temp.size(); i++){
            if (!temp.get(i).getBarcode().equals(temp2.get(i).getBarcode())) Assert.fail();
            if (!temp.get(i).getName().equals(temp2.get(i).getName())) Assert.fail();
            if (!Objects.equals(temp.get(i).getPrice(), temp2.get(i).getPrice())) Assert.fail();
            if (!Objects.equals(temp.get(i).getRemainder(), temp2.get(i).getRemainder())) Assert.fail();
        }
        Assert.assertTrue(true);
    }

    @Test
    public void shopBalanceTest(){
        Assert.assertEquals(8434.5, shop.shopBalance(), 0.01);
    }

    @Test
    public void filterByOrderTypeTest(){
        List<Order> temp = Order.filterOrderByType(orders, Order.Type.PURCHASE);
        for (Order order:temp){
            if (!order.getType().equals(Order.Type.PURCHASE)) Assert.fail();
        }
        temp = Order.filterOrderByType(orders, Order.Type.SALE);
        for (Order order:temp){
            if (!order.getType().equals(Order.Type.SALE)) Assert.fail();
        }
        Assert.assertTrue(true);
    }

    @Test
    public void sortByNameTest(){
        List<Shop> shops = List.of(
                shop, shop1, shop2, shop3
        );
        String[] temp = new String[]{"Iki", "Maxima", "Norfa", "Rimi"};
        shops = Shop.sortByName(shops);
        for (int i=0; i<4; i++){
            if (!shops.get(i).getName().equals(temp[i])) Assert.fail();
        }
        Assert.assertTrue(true);
    }

    @Test
    public void makeOrderTest(){

        Order tempOrder = orders.get(1);
        List<Product> tempProducts = tempOrder.makeOrder(shop).getProducts();
        if (tempProducts.get(0).getRemainder() != 300) Assert.fail();
        if (tempProducts.get(1).getRemainder() != 250) Assert.fail();
        if (tempProducts.get(2).getRemainder() != 400) Assert.fail();
        if (tempProducts.get(9).getRemainder() != 300) Assert.fail();
        if (tempProducts.get(10).getRemainder() != 300) Assert.fail();
        if (tempProducts.get(11).getRemainder() != 300) Assert.fail();
        if (tempProducts.get(7).getRemainder() != 200) Assert.fail();

        tempOrder = orders.get(4);
        tempProducts = tempOrder.makeOrder(shop).getProducts();
        if (tempProducts.get(0).getRemainder() != 200) Assert.fail();
        if (tempProducts.get(1).getRemainder() != 150) Assert.fail();
        if (tempProducts.get(2).getRemainder() != 300) Assert.fail();
        if (tempProducts.get(9).getRemainder() != 200) Assert.fail();
        if (tempProducts.get(10).getRemainder() != 200) Assert.fail();
        if (tempProducts.get(11).getRemainder() != 200) Assert.fail();
        if (tempProducts.get(7).getRemainder() != 200) Assert.fail();

        tempProducts = tempOrder.makeOrder(shop).getProducts();
        if (shop.shopBalance() == tempOrder.makeOrder(shop).shopBalance()){
            Assert.assertTrue(true);
        }

    }

}
