package ngram;

public class NGram {

    public static void main(String[] args) {
       
        CalculateProbability calc = new CalculateProbability();
        double probability = calc.getProbability("hava çok güzel", 1, 3);
        System.out.println(probability);
    }   
}