package ngram;

import java.io.*;
import java.util.*;

public class CalculateProbability {
    
    private HashMap<String, Double> oneGram3 = null;
    private HashMap<String, Double> oneGram4 = null;
    private HashMap<String, Double> oneGram8 = null;
    private HashMap<String, Double> twoGram3 = null;
    private HashMap<String, Double> twoGram4 = null;
    private HashMap<String, Double> twoGram8 = null;
    private HashMap<String, Double> threeGram3 = null;
    private HashMap<String, Double> threeGram4 = null;
    private HashMap<String, Double> threeGram8 = null;

    private static final String TRAINING_DIRECTORY = "1150haber\\raw_texts";

    public CalculateProbability() {
        
        // 1-GRAM
        if (!new File(CalculateNGram.ONE_GRAM_TRAINED_FILE_3).exists()) {
            CalculateNGram.calculateOneGram(TRAINING_DIRECTORY, 3);
        }
        if (!new File(CalculateNGram.ONE_GRAM_TRAINED_FILE_4).exists()) {
            CalculateNGram.calculateOneGram(TRAINING_DIRECTORY, 4);
        }
        if (!new File(CalculateNGram.ONE_GRAM_TRAINED_FILE_8).exists()) {
            CalculateNGram.calculateOneGram(TRAINING_DIRECTORY, 8);
        }
        
        // 2-GRAM
        if (!new File(CalculateNGram.TWO_GRAM_TRAINED_FILE_3).exists()) {
            CalculateNGram.calculateTwoGram(TRAINING_DIRECTORY, 3);
        }
        if (!new File(CalculateNGram.TWO_GRAM_TRAINED_FILE_4).exists()) {
            CalculateNGram.calculateTwoGram(TRAINING_DIRECTORY, 4);
        }
        if (!new File(CalculateNGram.TWO_GRAM_TRAINED_FILE_8).exists()) {
            CalculateNGram.calculateTwoGram(TRAINING_DIRECTORY, 8);
        }     
        
        // 3-GRAM
        if (!new File(CalculateNGram.THREE_GRAM_TRAINED_FILE_3).exists()) {
            CalculateNGram.calculateThreeGram(TRAINING_DIRECTORY, 3);
        }
        if (!new File(CalculateNGram.THREE_GRAM_TRAINED_FILE_4).exists()) {
            CalculateNGram.calculateThreeGram(TRAINING_DIRECTORY, 4);
        }
        if (!new File(CalculateNGram.THREE_GRAM_TRAINED_FILE_8).exists()) {
            CalculateNGram.calculateThreeGram(TRAINING_DIRECTORY, 8);
        }       
        
        oneGram3 = (HashMap) deserializeObject(CalculateNGram.ONE_GRAM_TRAINED_FILE_3);
        oneGram4 = (HashMap) deserializeObject(CalculateNGram.ONE_GRAM_TRAINED_FILE_4);
        oneGram8 = (HashMap) deserializeObject(CalculateNGram.ONE_GRAM_TRAINED_FILE_8);
        
        twoGram3 = (HashMap) deserializeObject(CalculateNGram.TWO_GRAM_TRAINED_FILE_3);
        twoGram4 = (HashMap) deserializeObject(CalculateNGram.TWO_GRAM_TRAINED_FILE_4);
        twoGram8 = (HashMap) deserializeObject(CalculateNGram.TWO_GRAM_TRAINED_FILE_8);
 
        threeGram3 = (HashMap) deserializeObject(CalculateNGram.THREE_GRAM_TRAINED_FILE_3);
        threeGram4 = (HashMap) deserializeObject(CalculateNGram.THREE_GRAM_TRAINED_FILE_4);
        threeGram8 = (HashMap) deserializeObject(CalculateNGram.THREE_GRAM_TRAINED_FILE_8);        
    }
    
    public double getProbability(String sentence, int nGram, int k) {

        String[] words = sentence.split("\\s+");
        
        switch (nGram) {
            case 1:
                return getProbabilityOneGram(sentence, k);
            case 2:
                return getProbabilityTwoGram(sentence, k);
            case 3:
                return getProbabilityThreeGram(sentence, k);
            default:
                return 0.0;
        }
    }
    
    private double getProbabilityOneGram(String sentence, int k) {

        String[] words = sentence.split("\\s+");
        double probability = 1;
        HashMap<String, Double> map;
        
        // Choose the appropriate hashmap
        switch (k) {
            case 3:
                map = oneGram3;
                break;
            case 4:
                map = oneGram4;
                break;
            case 8:
                map = oneGram8;
                break;
            default:
                return 0.0;
        }
        
        for (int si = 0; si < words.length; ++si) {
            
            // Get first k characters
            String s = words[si].substring(0, Math.min(words[si].length(), k));
            
            if (map.containsKey(s)) {
                probability *= map.get(s);
            }
        }
        
        return probability;
    }

    private double getProbabilityTwoGram(String sentence, int k) {
        return 0.0;
    }

    private double getProbabilityThreeGram(String sentence, int k) {
        return 0.0;
    }
    
    private static Object deserializeObject(String inputFile) {
        
        Object o = null;
        
        try {
            FileInputStream fileIn = new FileInputStream(inputFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            o = in.readObject();
            in.close();
            fileIn.close();
        }
        finally {
            return o;
        }
    }
}