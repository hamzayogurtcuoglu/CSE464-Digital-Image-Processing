import java.util.Comparator;

public class EucComparator implements Comparator<Pixel> {
    @Override
    public int compare(Pixel s1, Pixel s2) {
        double normS1 = Math.sqrt
                (s1.getBlue()*s1.getBlue() +
                        s1.getRed()*s1.getRed()+
                        s1.getGreen()*s1.getGreen()
                );
        double normS2 = Math.sqrt
                (s2.getBlue()*s2.getBlue() +
                        s2.getRed()*s2.getRed()+
                        s2.getGreen()*s2.getGreen()
                );

        if ((normS1>normS2)) {
            return 1;
        }
        else
            return 0;
    }
}
