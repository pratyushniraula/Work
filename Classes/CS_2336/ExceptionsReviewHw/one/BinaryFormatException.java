import java.lang.Exception;

public class BinaryFormatException extends Exception {
   String errorMaker;

    //constructor
    public BinaryFormatException(String errorMaker) {
        super("BinaryFormatException");
        this.errorMaker = errorMaker;
    }
    //accessor
    public String getErrorMaker() {
        return errorMaker;
    }
    
    //getMessage method
    public String getMessage() {
        return "" + errorMaker;
    }

}
