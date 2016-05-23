package ngram;

import javax.swing.*;

public class NGram {

    public static void main(String[] args) {
       
        CalculateProbability calc = new CalculateProbability();
                                
        while (true) {
            
            // N-Gram: 1, 2 or 3
            JComboBox<String> nGrams = new JComboBox<>(new String[]{"1", "2", "3"});
            
            // Value of: k 3, 4 or 8
            JComboBox<String> kVaules = new JComboBox<>(new String[]{"3", "4", "8"});

            // Sentence to be analyzed
            JTextField field3 = new JTextField(10);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(new JLabel("N-Grams:"));
            panel.add(nGrams);
            panel.add(new JLabel("Value of k:"));
            panel.add(kVaules);
            panel.add(new JLabel("Sentence:"));
            panel.add(field3);
            
            if (JOptionPane.showConfirmDialog(
                    null, panel, "HW03", JOptionPane.OK_CANCEL_OPTION) == 
                    JOptionPane.OK_OPTION) {

                double probability = calc.getProbability(
                        field3.getText(),
                        Integer.parseInt((String) nGrams.getSelectedItem()), 
                        Integer.parseInt((String) kVaules.getSelectedItem()));

                JOptionPane.showMessageDialog(
                        null, probability, "Probability", 
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }   
}