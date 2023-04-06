package program;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Shop {
    private String name;
    private String address;
    private List<Product> products;

    public Shop(String name, String address, List<Product> products) {
        this.name = name;
        this.address = address;
        this.products = products;
    }

    public Shop() {
    }

    public Double shopBalance(){
        double sum = 0.00;
        for(Product product:products) sum += product.getPrice() * product.getRemainder();
        return sum;
    }

    public static List<Shop> sortByName(List<Shop> shops){
        return shops.stream().sorted(Comparator.comparing(Shop::getName)).collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
