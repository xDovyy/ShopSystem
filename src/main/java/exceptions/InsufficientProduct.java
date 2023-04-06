package exceptions;

public class InsufficientProduct extends Exception {
    Integer remainder;
    public InsufficientProduct(Integer remainder, String message){
        super("There is not enough of " + message + ". there is only " + remainder);
        this.remainder = remainder;
    }

}
