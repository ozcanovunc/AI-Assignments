import java.io.*;
import java.util.*;

public class CalculateNGram {

    public static final String ONE_GRAM_TRAINED_FILE_3 = "1Gram3.ser";
    public static final String ONE_GRAM_TRAINED_FILE_4 = "1Gram4.ser";
    public static final String ONE_GRAM_TRAINED_FILE_8 = "1Gram8.ser";
    
    public static final String TWO_GRAM_TRAINED_FILE_3 = "2Gram3.ser";
    public static final String TWO_GRAM_TRAINED_FILE_4 = "2Gram4.ser";
    public static final String TWO_GRAM_TRAINED_FILE_8 = "2Gram8.ser";

    public static final String THREE_GRAM_TRAINED_FILE_3 = "3Gram3.ser";
    public static final String THREE_GRAM_TRAINED_FILE_4 = "3Gram4.ser";
    public static final String THREE_GRAM_TRAINED_FILE_8 = "3Gram8.ser";

    /**
     * 
     * @param directory
     * Name of the directory which includes training text files for n-Gram
     * 
     * @param k
     * Take the first k characters of the word as the word itself
     * k must be 3, 4 or 8
     * 
     * @return 
     * Returns true if the operation is successful, false o/w
     */
    public static boolean calculateOneGram(String directory, int k) {

        String[] trainingSet = readAll(directory).split("\\s+");
        HashMap<String, Double> map = new HashMap<>();

        // Calculate the number of occurences
        for (int si = 0; si < trainingSet.length; ++si) {
            
            // Get first k characters
            String s = trainingSet[si].substring(
                    0, Math.min(trainingSet[si].length(), k));

            if (map.containsKey(s)) {
                map.put(s, map.get(s) + 1.0);
            }
            else {
                map.put(s, 1.0);
            }
        }
        
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            entry.setValue(entry.getValue() / trainingSet.length);
        }
        
        // Serializing trained 1-Gram
        switch (k) {
            case 3:
                return serializeObject(map, ONE_GRAM_TRAINED_FILE_3);
            case 4:
                return serializeObject(map, ONE_GRAM_TRAINED_FILE_4);
            case 8:
                return serializeObject(map, ONE_GRAM_TRAINED_FILE_8);
            default:
                return false;
        }
    }

    public static boolean calculateTwoGram(String directory, int k) {
        
        String[] trainingSet = readAll(directory).split("\\s+");
        HashMap<String, Double> map = new HashMap<>();

        // Calculate the number of occurences
        for (int si = 0; si < trainingSet.length - 1; ++si) {
            
            // Get first k characters
            String s1 = trainingSet[si].substring(
                    0, Math.min(trainingSet[si].length(), k));
            String s2 = trainingSet[si + 1].substring(
                    0, Math.min(trainingSet[si + 1].length(), k));
            
            String s = s1 + " " + s2;

            if (map.containsKey(s)) {
                map.put(s, map.get(s) + 1.0);
            }
            else {
                map.put(s, 1.0);
            }
        }
        
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            entry.setValue(entry.getValue() / trainingSet.length);
        }
        
        // Serializing trained 1-Gram
        switch (k) {
            case 3:
                return serializeObject(map, TWO_GRAM_TRAINED_FILE_3);
            case 4:
                return serializeObject(map, TWO_GRAM_TRAINED_FILE_4);
            case 8:
                return serializeObject(map, TWO_GRAM_TRAINED_FILE_8);
            default:
                return false;
        }
    }

    public static boolean calculateThreeGram(String directory, int k) {
        
        String[] trainingSet = readAll(directory).split("\\s+");
        HashMap<String, Double> map = new HashMap<>();

        // Calculate the number of occurences
        for (int si = 0; si < trainingSet.length - 2; ++si) {
            
            // Get first k characters
            String s1 = trainingSet[si].substring(
                    0, Math.min(trainingSet[si].length(), k));
            String s2 = trainingSet[si + 1].substring(
                    0, Math.min(trainingSet[si + 1].length(), k));
            String s3 = trainingSet[si + 2].substring(
                    0, Math.min(trainingSet[si + 2].length(), k));
            
            String s = s1 + " " + s2 + " " + s3;

            if (map.containsKey(s)) {
                map.put(s, map.get(s) + 1.0);
            }
            else {
                map.put(s, 1.0);
            }
        }
        
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            entry.setValue(entry.getValue() / trainingSet.length);
        }
        
        // Serializing trained 1-Gram
        switch (k) {
            case 3:
                return serializeObject(map, THREE_GRAM_TRAINED_FILE_3);
            case 4:
                return serializeObject(map, THREE_GRAM_TRAINED_FILE_4);
            case 8:
                return serializeObject(map, THREE_GRAM_TRAINED_FILE_8);
            default:
                return false;
        }
    }
        
    /**
     * 
     * @param file
     * 
     * @return 
     * The entire content of given "file" object
     */
    private static String readFile(File file) {
        
        try {
            // https://docs.oracle.com/javase/8/docs/technotes/guides/intl/encoding.doc.html
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file.getAbsolutePath()), "Cp1254"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }

            return sb.toString();
        }
        catch (Exception e) {
            return null;
        } 
    }
    
    /**
     * 
     * @param directory
     * 
     * @return 
     * The entire content of each file together in "directory"
     */
    private static String readAll(String directory) {
        
        StringBuilder sb = new StringBuilder();
        
        try {
            File dir = new File(directory);
            File[] allFiles = dir.listFiles();

            if (allFiles != null) {
                for (File file : allFiles) {

                    if (file.isDirectory()) {
                        sb.append(readAll(file.getAbsolutePath()));
                    }
                    // Get the contents of each file
                    else {
                        // Eliminate punctuation marks
                        sb.append(readFile(file).replaceAll("[^\\p{L}^\\d ]", ""));
                        sb.append(" ");
                    }
                }
            }
        }
        catch (Exception e) {
            
        }
        
        return sb.toString().toLowerCase();
    }
    
    /**
     * 
     * @param o
     * Object to serialize
     * 
     * @param outputFile
     * Output file name where we save the serialized object
     * 
     * @return 
     * Returns true if serialization is successful, false o/w
     */
    private static boolean serializeObject(Object o, String outputFile) {
        
        try {
            FileOutputStream fileOut = new FileOutputStream(outputFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(o);
            out.close();
            fileOut.close();
        }
        catch (Exception e) {
            return false;
        }

        return true;
    }
}