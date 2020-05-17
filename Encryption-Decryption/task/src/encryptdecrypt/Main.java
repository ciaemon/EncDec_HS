package encryptdecrypt;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

class ProcessEncDecFactory {
    public static ProcessorEncDec makeTask(LaunchParams lp) throws FileNotFoundException {
       ProcessorEncDec task = new ProcessorEncDec();
       switch (lp.getValue("-alg")) {
           case "shift" :
               task.setAlgorithm(new ShiftAlgorithm(
                       Integer.parseInt(lp.getValue("-key")),
                       lp.getValue("-mode").equals("enc"))
               );
               break;
           case "unicode" :
               task.setAlgorithm(new UnicodeAlgorithm(
                       Integer.parseInt(lp.getValue("-key")),
                       lp.getValue("-mode").equals("enc"))
               );
               break;

        }
        if (lp.contains("-in")) {
            File inp = new File(lp.getValue("-in"));
            task.setInputStream(new Scanner(inp));
        } else task.setInputStream(new Scanner(lp.getValue("-data")));

        if (lp.contains("-out")) {
            File outp = new File(lp.getValue("-out"));
            task.setPrintStream(new PrintStream(outp));
        } else task.setPrintStream(System.out);

       return task;
    }
}
class ProcessorEncDec {
Scanner inputStream;
PrintStream printStream;
Algorithm algorithm;


    public String encrypt(String input) {
        String output = "";
        for (int i = 0; i < input.length(); i++) {
        output += algorithm.process(input.charAt(i));
        }
        return output;
    }


       public void process() {
       printStream.print(encrypt(inputStream.nextLine()));
       printStream.close();
       inputStream.close();
    }

    public void setInputStream(Scanner inputStream) {
        this.inputStream = inputStream;
    }

    public void setPrintStream(PrintStream printStream) {
        this.printStream = printStream;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
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

class ShiftAlgorithm extends Algorithm {

    public ShiftAlgorithm(int key, boolean isEncrypt) {
        super(key, isEncrypt);
    }

    @Override
    char process(char in) {
        int newKey = isEncrypt ? key : -key;
        if (in >= 'a' && in <= 'z') {
            return (char) rounding('a', 'z', in + newKey);
        }
        if (in >= 'A' && in <= 'Z') {
            return (char) rounding('A', 'Z', in + newKey);
        }
        return in;
    }
    private int rounding(int start, int end, int value) {
        int length = end - start + 1;
        if (value >= start) {
            return start + (value - start) % length;
        } else {
            return end - (end - value) % length;
        }

    }

}

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
    public boolean contains(String arg) {
            return parameters.containsKey(arg);
    }

    }


public class Main {

    final static java.util.Scanner scanner = new java.util.Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException {
        String[] args1 = "-mode dec -data Govmywo_dy_rizobcusvv! -key 10 -alg shift".split(" ");
        LaunchParams lp = new LaunchParams(args, "-alg", "shift", "-mode", "enc", "-key", "0");

        ShiftAlgorithm a = new ShiftAlgorithm(1, false);

        try {
            ProcessEncDecFactory.makeTask(lp).process();
        } catch (Exception e) {
            System.out.println("Error");
        }


    }
}
