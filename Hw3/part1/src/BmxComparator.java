import java.util.Comparator;

public class BmxComparator implements Comparator<Pixel> {

    @Override
    public int compare(Pixel s1, Pixel s2) {

        StringBuilder red= new StringBuilder(Integer.toBinaryString(s1.getRed()));
        while(red.length()<8){
            red.insert(0, '0');
        }
        StringBuilder green= new StringBuilder(Integer.toBinaryString(s1.getGreen()));
        while(green.length()<8){
            green.insert(0, '0');
        }
        StringBuilder blue= new StringBuilder(Integer.toBinaryString(s1.getBlue()));
        while(blue.length()<8){
            blue.insert(0, '0');
        }

        StringBuilder red2= new StringBuilder(Integer.toBinaryString(s2.getRed()));
        while(red2.length()<8){
            red2.insert(0, '0');
        }
        StringBuilder green2;
        green2 = new StringBuilder(Integer.toBinaryString(s2.getGreen()));
        while(green2.length()<8){
            green2.insert(0, '0');
        }

        StringBuilder blue2;
        blue2 = new StringBuilder(Integer.toBinaryString(s2.getBlue()));
        while(blue2.length()<8){
            blue2.insert(0, '0');
        }
        int i=0;
        StringBuilder firstBin;
        StringBuilder secondBin;
        firstBin = new StringBuilder();
        secondBin = new StringBuilder();
        while(i<8){
            firstBin.append(red.charAt(i)).append(green.charAt(i)).append(blue.charAt(i));
            secondBin.append(red2.charAt(i)).append(green2.charAt(i)).append(blue2.charAt(i));
            i++;
        }
        int a=Integer.parseInt(firstBin.toString(),2);
        int b=Integer.parseInt(secondBin.toString(),2);
        if (a<b) {
            return 1;
        }
        return 0;
    }
}
