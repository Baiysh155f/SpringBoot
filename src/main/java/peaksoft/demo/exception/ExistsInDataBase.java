package peaksoft.demo.exception;

import java.util.Random;

public class ExistsInDataBase extends RuntimeException {
    public ExistsInDataBase() {
        super();
    }
    public ExistsInDataBase(String message) {
        super(message);
    }

}
