import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class compute_a_posteriori {
    public static void main(String[] args) {
        StringBuffer buffer = new StringBuffer();
        if (args.length > 0) {
            buffer.append("Observation sequence Q: " + args[0]);
            buffer.append("\nLength of Q: " + args[0].length());
            // Prior probabilities of picking up each bag
            double h_i[] = new double[] { 0.1, 0.2, 0.4, 0.2, 0.1 };
            // calculate_probability of picking a ch out of bag i.
            double ch[] = new double[] { 1.0, 0.75, 0.5, 0.25, 0 };
            // calculate_probability of picking a lm out of bag i.
            double lm[] = new double[] { 0, 0.25, 0.5, 0.75, 1.0 };
            /*
             * Iterate through each observation and calculate the probabilities
             */
            for (int i = 0; i < args[0].length(); i++) {

                double cherry_p = 0;
                double lime_p = 0;
                if (args[0].charAt(i) == 'C')
                    cherry_p = new compute_a_posteriori().calculate_probability(ch, h_i);
                if (args[0].charAt(i) == 'L')
                    lime_p = new compute_a_posteriori().calculate_probability(lm, h_i);
                buffer.append("\n\nAfter Observation " + (i + 1) + " = " + args[0].charAt(i) + ":\n");
                for (int j = 0; j < 5; j++) {
                    if (args[0].charAt(i) == 'C')
                        h_i[j] = ((ch[j] * h_i[j]) / cherry_p);
                    if (args[0].charAt(i) == 'L')
                        h_i[j] = ((lm[j] * h_i[j]) / lime_p);
                    buffer.append("\nP(h" + (j + 1) + " | Q) = " + new DecimalFormat("#.#####").format(h_i[j]));
                }
                cherry_p = new compute_a_posteriori().calculate_probability(ch, h_i);
                buffer.append("\n\ncalculate_probability that the next candy we pick will be C, given Q: "
                        + new DecimalFormat("#.#####").format(cherry_p));
                buffer.append("\ncalculate_probability that the next candy we pick will be L, given Q: "
                        + new DecimalFormat("#.#####").format(1 - cherry_p));
            }
            new compute_a_posteriori().bufferoutput_writer(buffer.toString());
        } else {
            System.out.println("No Observations made!");
        }
    }

    // Calculate posterior probabilities for each observation by appending to
    // previous probability values recursivley
    private double calculate_probability(double c[], double h[]) {
        double res = 0;
        for (int i = 0; i < 5; i++)
            res += c[i] * h[i];
        return res;
    }

    // Write the formatted output to the file "result.txt"
    private void bufferoutput_writer(String res) {
        try {
            File file = new File("result.txt");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(res);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}