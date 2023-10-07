import java.util.ArrayList;

class BinaryFormatException extends Exception {
    private String invalidBinary;

    public BinaryFormatException(String invalidBinary) {
        this.invalidBinary = invalidBinary;
    }

    public String getInvalidBinary() {
        return invalidBinary;
    }

    public String getMessage() {
        return "Invalid binary number: " + invalidBinary;
    }
}