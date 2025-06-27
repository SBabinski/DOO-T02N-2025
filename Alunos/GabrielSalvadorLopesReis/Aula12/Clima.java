package Aula12;

import java.util.Arrays;

public class Clima {
    String resolvedAddress;
    Days[] days;

    @Override
    public String toString() {
        return resolvedAddress + "\n" + Arrays.toString(days);
    }
}
