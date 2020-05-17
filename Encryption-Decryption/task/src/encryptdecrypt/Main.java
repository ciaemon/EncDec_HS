package encryptdecrypt;

import java.util.HashMap;

interface EncDec {
    String encrypt(String input, Algorithm algorithm);
    String decrypt(String input, Algorithm algorithm);
}

class ProcessorEncDec implements EncDec {


    @Override
    public String encrypt(String input, Algorithm algorithm) {
        String output = "";
        for (int i = 0; i < input.length(); i++) {
        output += algorithm.process(input.charAt(i));
        }
        return output;
    }

    @Override
    public String decrypt(String input, Algorithm algorithm) {
        algorithm.invert();
        return encrypt(input, algorithm);
    }
}


abstract class Algorithm {
    protected int key;
    protected boolean isEncrypt;

    Algorithm(int key, boolean isEncrypt) {
        this.key = key;
        this.isEncrypt = isEncrypt;
    }

    abstract char process(char in);

    public int getKey() {
        return key;
    }
    public void invert() {
        this.isEncrypt = !isEncrypt;
    }
}
/*
class ShiftAlgorithm extends Algorithm {

}
 */
class UnicodeAlgorithm extends Algorithm {
    public UnicodeAlgorithm(int key, boolean isEncrypt) {
        super(key, isEncrypt);
    }
    @Override
    char process(char in) {
        return isEncrypt ? (char) (in + key) : (char) (in - key);
    }

class LaunchParams {
        HashMap <String, String> parameters;
        HashMap<String, String> defaultParameters;
        LaunchParams(String[] args, String... defaultParams) {
        }

}
}

public class Main {
    final static java.util.Scanner scanner = new java.util.Scanner(System.in);
    public static void main(String[] args) {

    }
}
