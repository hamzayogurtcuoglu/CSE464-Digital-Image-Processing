
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        long startTime = System.nanoTime();


        HistogramEqualization heObject = new HistogramEqualization("images/images.txt");
        LBP localBinaryPatterns = new LBP(heObject);
        NearestNeighbour nearestNeighbour = new NearestNeighbour(localBinaryPatterns);

        
        long endTime = System.nanoTime();
        System.out.println("Took "+((endTime - startTime)/1000000000 )+ " second");

    }
}
