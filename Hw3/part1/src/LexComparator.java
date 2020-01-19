import java.util.Comparator;

public class LexComparator implements Comparator<Pixel> {
    @Override
    public int compare(Pixel s1, Pixel s2) {
        if ((s1.getRed()>s2.getRed())) {
            return 1;
        }else if((s1.getRed()==s2.getRed())){
            if ((s1.getGreen()>s2.getGreen())) {
                return 1;
            }else if ((s1.getGreen()==s2.getGreen())){
                if ((s1.getBlue()>s2.getBlue())) {
                    return 1;
                }
                return 0;
            }
            return 0;
        }
        return 0;
    }
}
