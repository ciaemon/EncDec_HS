package encryptdecrypt;

import java.util.HashMap;
import java.util.LinkedHashMap;

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
}
class LaunchParams {
    private LinkedHashMap <String, String> parameters;
    private LinkedHashMap<String, String> defaultParameters;
        private LaunchParams() {
        this.parameters = new LinkedHashMap<>();
        this.defaultParameters = new LinkedHashMap<>();
    }
        public LaunchParams(String[] args, String... defaultParams) {
            LaunchParams result = new LaunchParams();
            result.setDefaultParameters(defaultParams);
            result.setParameters(args);
            for (var entry : result.defaultParameters.entrySet()) {
                result.parameters.putIfAbsent(entry.getKey(), entry.getValue());
            }
            this.parameters = result.parameters;
            this.defaultParameters = result.defaultParameters;
        }

    public void setParameters(String[] args) {
        parameters.clear();
        for (int i = 0; i < args.length; i += 2) {
            parameters.put(args[i], args[i + 1]);
        }
    }
    public void setDefaultParameters(String... defaultParams) {
        defaultParameters.clear();
        for (int i = 0; i < defaultParams.length; i += 2) {
            defaultParameters.put(defaultParams[i], defaultParams[i + 1]);
        }
    }
    public String getValue(String arg) {
            return parameters.get(arg);
    }

    }






public class Main {

    final static java.util.Scanner scanner = new java.util.Scanner(System.in);
    public static void main(String[] args) {
        String s = "-mode enc -in road_to_treasure.txt -out protected.txt -alg unicode";



    }
}
