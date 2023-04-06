package program;

import java.util.*;

public class Order {
    private String orderNr;
    private HashMap<String, Integer> orderedProducts;
    public enum Type{
        PURCHASE,
        SALE
    }
    private Type type;

    public Order(String orderNr, HashMap<String, Integer> orderedProducts, Type type) {
        this.orderNr = orderNr;
        this.orderedProducts = orderedProducts;
        this.type = type;
    }

    public static List<Order> filterOrderByType(List<Order> orders, Type type){
        List<Order> result = new ArrayList<>();
        for (Order order:orders) if (order.type.equals(type)) result.add(order);
        return result;
    }

    public Shop makeOrder(Shop shop){
        switch (type){
            case PURCHASE: return purchaseProducts(shop, orderedProducts);
            case SALE: try{return sellProducts(shop, orderedProducts);}
            catch (InsufficientProduct e){
                e.printStackTrace();
                return shop;}
            default: throw new IllegalArgumentException();
        }
    }

    public Shop purchaseProducts(Shop shop, HashMap<String, Integer> orders){
        List<Product> products = shop.getProducts();
        for (int i=0; i<products.size(); i++){
            Product temp = products.get(i);
            if (orders.containsKey(temp.getBarcode())){
                temp.setRemainder(temp.getRemainder() + orders.get(temp.getBarcode()));
                products.set(i, temp);
            }
        }
        shop.setProducts(products);
        return shop;
    }

    public Shop sellProducts(Shop shop, HashMap<String, Integer> orders) throws InsufficientProduct {
        List<Product> products = shop.getProducts();
        for (int i=0; i<products.size(); i++){
            Product temp = products.get(i);
            if (orders.containsKey(temp.getBarcode())){
                if (temp.getRemainder() - orders.get(temp.getBarcode()) >= 0){
                    temp.setRemainder(temp.getRemainder() - orders.get(temp.getBarcode()));
                    products.set(i, temp);
                }
                else throw new InsufficientProduct(temp.getRemainder(), temp.getName());
            }
        }
        shop.setProducts(products);
        return shop;
    }

    public String getOrderNr() {
        return orderNr;
    }

    public void setOrderNr(String orderNr) {
        this.orderNr = orderNr;
    }

    public HashMap<String, Integer> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(HashMap<String, Integer> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
