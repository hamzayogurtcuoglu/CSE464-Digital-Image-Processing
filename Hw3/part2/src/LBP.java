

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LBP{

    double[][] imageFeatures;

    public LBP(HistogramEqualization heObject) throws FileNotFoundException {
        imageFeatures = new double[heObject.imagesNumber][256];
        Scanner def = new Scanner(new File("images/defs.txt"));
        Integer.valueOf(def.nextLine());
        String[] tokens;
        String s;
        for (int i = 0; i<heObject.imagesNumber;i++){
            s = def.nextLine();
            tokens = s.split(" ");
            BufferedImage temp = heObject.imagesHEList.get(i);
            imageFeatures[i] = extractFeatureLBPAccordingToRadious(temp,tokens[3]);
        }
    }
    public double[] extractFeatureLBPAccordingToRadious(BufferedImage src,String rotation){
        double[] histogram = new double[256];
        for (int i = 0;i<256;i++){
            histogram[0] = 0;
        }
        histogram = extractFeatureLBP1R(src,rotation,histogram);
        histogram = extractFeatureLBP2R(src,rotation,histogram);
        //histogram = extractFeatureLBP3R(src,rotation,histogram);

        return histogram;
    }


    public double[] extractFeatureLBP1R(BufferedImage src,String rotation, double[] histogram){
        for (int i = 0; i<src.getWidth();i++){
            for (int j = 0;j<src.getHeight();j++) {
                int gray1, gray2, gray3, gray4, gray5, gray6, gray7, gray8;
                int gray = src.getRGB(i , j ) & 0xFF;
                int sum = 0;

                if ( !(i - 1 < 0 || j - 1 < 0) ) {
                    gray1 = src.getRGB(i - 1, j - 1) & 0xFF;
                    if (gray<=gray1) {
                        if (rotation.equals("00")||rotation.equals("10")||rotation.equals("05")||rotation.equals("15"))
                            sum += 1;
                        else if (rotation.equals("30") || rotation.equals("45")||rotation.equals("60"))
                            sum += 128;
                        else if (rotation.equals("90")||rotation.equals("75"))
                            sum += 64;
                    }
                }
                if (!(i - 1 < 0)) {
                    gray2 = src.getRGB(i - 1, j) & 0xFF;
                    if (gray<=gray2){
                        if (rotation.equals("00")||rotation.equals("10")||rotation.equals("05")||rotation.equals("15"))
                            sum += 2;
                        else if (rotation.equals("30") || rotation.equals("45")||rotation.equals("60"))
                            sum += 1;
                        else if (rotation.equals("90")||rotation.equals("75"))
                            sum += 128;

                    }

                }
                if ( !(i-1<0 || j+1==src.getHeight()) ) {
                    gray3 = src.getRGB(i - 1, j + 1) & 0xFF;
                    if (gray<=gray3){
                        if (rotation.equals("00")||rotation.equals("10")||rotation.equals("05")||rotation.equals("15"))
                            sum += 4;
                        else if (rotation.equals("30") || rotation.equals("45")||rotation.equals("60"))
                            sum += 2;
                        else if (rotation.equals("90")||rotation.equals("75"))
                            sum += 1;
                    }

                }

                if (!(j+1==src.getHeight()))
                {
                    gray4 = src.getRGB(i, j + 1) & 0xFF;
                    if (gray<=gray4){
                        if (rotation.equals("00")||rotation.equals("10")||rotation.equals("05")||rotation.equals("15"))
                            sum += 8;
                        else if (rotation.equals("30") || rotation.equals("45")||rotation.equals("60"))
                            sum += 4;
                        else if (rotation.equals("90")||rotation.equals("75"))
                            sum += 2;

                    }

                }
                if ( !(i+1 == src.getWidth()  || j+1 == src.getHeight()) ) {
                    gray5 = src.getRGB(i + 1, j + 1) & 0xFF;
                    if (gray<=gray5){
                        if (rotation.equals("00")||rotation.equals("10")||rotation.equals("05")||rotation.equals("15"))
                            sum += 16;
                        else if (rotation.equals("30") || rotation.equals("45")||rotation.equals("60"))
                            sum += 8;
                        else if (rotation.equals("90")||rotation.equals("75"))
                            sum += 4;

                    }

                }
                if (!(i+1 == src.getWidth() )){
                    gray6 = src.getRGB(i + 1, j) & 0xFF;
                    if (gray<=gray6){
                        if (rotation.equals("00")||rotation.equals("10")||rotation.equals("05")||rotation.equals("15"))
                            sum += 32;
                        else if (rotation.equals("30") || rotation.equals("45")||rotation.equals("60"))
                            sum += 16;
                        else if (rotation.equals("90")||rotation.equals("75"))
                            sum += 8;
                    }
                }
                if ( !(i+1 == src.getWidth() || j-1<0) ) {
                    gray7 = src.getRGB(i + 1, j - 1) & 0xFF;
                    if (gray<=gray7){
                        if (rotation.equals("00")||rotation.equals("10")||rotation.equals("05")||rotation.equals("15"))
                            sum += 64;
                        else if (rotation.equals("30") || rotation.equals("45")||rotation.equals("60"))
                            sum += 32;
                        else if (rotation.equals("90")||rotation.equals("75"))
                            sum += 16;
                    }
                }
                if (!(j-1<0) ){
                    gray8 = src.getRGB(i, j - 1) & 0xFF;
                    if (gray<=gray8){
                        if (rotation.equals("00")||rotation.equals("10")||rotation.equals("05")||rotation.equals("15"))
                            sum += 128;
                        else if (rotation.equals("30") || rotation.equals("45")||rotation.equals("60"))
                            sum += 64;
                        else if (rotation.equals("90")||rotation.equals("75"))
                            sum += 32;
                    }
                }
                histogram[sum]++;
            }
        }
        return histogram;
    }

    //////////////////////////2RRRRRRRRRRRRRRRRRRRRRRRRRRRR
    public double[] extractFeatureLBP2R(BufferedImage src,String rotation, double[] histogram){
        for (int i = 0; i<src.getWidth();i++){
            for (int j = 0;j<src.getHeight();j++) {
                int gray1, gray2, gray3, gray4, gray5, gray6, gray7, gray8;
                int gray = src.getRGB(i , j ) & 0xFF;
                int sum = 0;

                if ( !(i - 2 < 0 || j - 2 < 0) ) {
                    gray1 = src.getRGB(i - 2, j - 2) & 0xFF;
                    if (gray<=gray1) {
                        if (rotation.equals("00")||rotation.equals("10")||rotation.equals("05"))
                            sum += 1;
                        else if (rotation.equals("15")||rotation.equals("30"))
                            sum += 128;
                        else if ( rotation.equals("45"))
                            sum += 64;
                        else if (rotation.equals("60")||rotation.equals("75"))
                            sum+= 32;
                        else if (rotation.equals("90"))
                            sum += 16;
                    }
                }
                if ( !(i - 2 < 0 || j - 1 < 0 )) {
                    gray2 = src.getRGB(i - 2, j-1) & 0xFF;
                    if (gray<=gray2){
                        if (rotation.equals("00")||rotation.equals("10")||rotation.equals("05"))
                            sum += 2;
                        else if (rotation.equals("15")||rotation.equals("30"))
                            sum += 1;
                        else if ( rotation.equals("45"))
                            sum += 128;
                        else if (rotation.equals("60")||rotation.equals("75"))
                            sum+= 64;
                        else if (rotation.equals("90"))
                            sum += 32;

                    }
                }

                if (!(i - 2 < 0)) {
                    gray2 = src.getRGB(i - 2, j) & 0xFF;
                    if (gray<=gray2){
                        if (rotation.equals("00")||rotation.equals("10")||rotation.equals("05"))
                            sum += 4;
                        else if (rotation.equals("15")||rotation.equals("30"))
                            sum += 2;
                        else if ( rotation.equals("45"))
                            sum += 1;
                        else if (rotation.equals("60")||rotation.equals("75"))
                            sum+= 128;
                        else if (rotation.equals("90"))
                            sum += 64;
                    }
                }


                if (!(i - 2 < 0 || j + 1 >= src.getWidth())) {
                    gray2 = src.getRGB(i - 2, j+1) & 0xFF;
                    if (gray<=gray2){
                        if (rotation.equals("00")||rotation.equals("10")||rotation.equals("05"))
                            sum += 8;
                        else if (rotation.equals("15")||rotation.equals("30"))
                            sum += 4;
                        else if ( rotation.equals("45"))
                            sum += 2;
                        else if (rotation.equals("60")||rotation.equals("75"))
                            sum+= 1;
                        else if (rotation.equals("90"))
                            sum += 128;
                    }
                }

                if (!(i - 2 < 0 || j + 2 >= src.getWidth())) {
                    gray2 = src.getRGB(i - 2, j+2) & 0xFF;
                    if (gray<=gray2){
                        if (rotation.equals("00")||rotation.equals("10")||rotation.equals("05"))
                            sum += 16;
                        else if (rotation.equals("15")||rotation.equals("30"))
                            sum += 8;
                        else if ( rotation.equals("45"))
                            sum += 4;
                        else if (rotation.equals("60")||rotation.equals("75"))
                            sum+= 2;
                        else if (rotation.equals("90"))
                            sum += 1;
                    }
                }


                if (!(i - 1 < 0 || j + 2 >= src.getWidth())) {
                    gray2 = src.getRGB(i - 1, j+2) & 0xFF;
                    if (gray<=gray2){
                        if (rotation.equals("00")||rotation.equals("10")||rotation.equals("05"))
                            sum += 32;
                        else if (rotation.equals("15")||rotation.equals("30"))
                            sum += 16;
                        else if ( rotation.equals("45"))
                            sum += 8;
                        else if (rotation.equals("60")||rotation.equals("75"))
                            sum+= 4;
                        else if (rotation.equals("90"))
                            sum += 2;
                    }
                }


                if (!(j + 2 >= src.getWidth())) {
                    gray2 = src.getRGB(i , j+2) & 0xFF;
                    if (gray<=gray2){
                        if (rotation.equals("00")||rotation.equals("10")||rotation.equals("05"))
                            sum += 64;
                        else if (rotation.equals("15")||rotation.equals("30"))
                            sum += 32;
                        else if ( rotation.equals("45"))
                            sum += 16;
                        else if (rotation.equals("60")||rotation.equals("75"))
                            sum+= 8;
                        else if (rotation.equals("90"))
                            sum += 4;
                    }
                }


                if (!(i + 1 >= src.getHeight() || j + 2 >= src.getWidth())) {
                    gray2 = src.getRGB(i + 1, j+2) & 0xFF;
                    if (gray<=gray2){
                        if (rotation.equals("00")||rotation.equals("10")||rotation.equals("05"))
                            sum += 128;
                        else if (rotation.equals("15")||rotation.equals("30"))
                            sum += 64;
                        else if ( rotation.equals("45"))
                            sum += 32;
                        else if (rotation.equals("60")||rotation.equals("75"))
                            sum+= 16;
                        else if (rotation.equals("90"))
                            sum += 8;
                    }
                }

                if (!(i + 2 >= src.getHeight() || j + 2 >= src.getWidth())) {
                    gray2 = src.getRGB(i + 2, j+2) & 0xFF;
                    if (gray<=gray2){
                        if (rotation.equals("00")||rotation.equals("10")||rotation.equals("05"))
                            sum += 1;
                        else if (rotation.equals("15")||rotation.equals("30"))
                            sum += 128;
                        else if ( rotation.equals("45"))
                            sum += 64;
                        else if (rotation.equals("60")||rotation.equals("75"))
                            sum+= 32;
                        else if (rotation.equals("90"))
                            sum += 16;
                    }
                }


                if (!(i + 2 >= src.getHeight() || j + 1 >= src.getWidth())) {
                    gray2 = src.getRGB(i + 2, j+1) & 0xFF;
                    if (gray<=gray2){
                        if (rotation.equals("00")||rotation.equals("10")||rotation.equals("05"))
                            sum += 2;
                        else if (rotation.equals("15")||rotation.equals("30"))
                            sum += 1;
                        else if ( rotation.equals("45"))
                            sum += 128;
                        else if (rotation.equals("60")||rotation.equals("75"))
                            sum+= 64;
                        else if (rotation.equals("90"))
                            sum += 32;
                    }
                }


                if (!(i + 2 >= src.getHeight() || j  >= src.getWidth())) {
                    gray2 = src.getRGB(i + 2, j) & 0xFF;
                    if (gray<=gray2){
                        if (rotation.equals("00")||rotation.equals("10")||rotation.equals("05"))
                            sum += 4;
                        else if (rotation.equals("15")||rotation.equals("30"))
                            sum += 2;
                        else if ( rotation.equals("45"))
                            sum += 1;
                        else if (rotation.equals("60")||rotation.equals("75"))
                            sum+= 128;
                        else if (rotation.equals("90"))
                            sum += 64;
                    }
                }

                if (!(i + 2 >= src.getHeight() || j-1 < 0)) {
                    gray2 = src.getRGB(i + 2, j-1) & 0xFF;
                    if (gray<=gray2){
                        if (rotation.equals("00")||rotation.equals("10")||rotation.equals("05"))
                            sum += 8;
                        else if (rotation.equals("15")||rotation.equals("30"))
                            sum += 4;
                        else if ( rotation.equals("45"))
                            sum += 2;
                        else if (rotation.equals("60")||rotation.equals("75"))
                            sum+= 1;
                        else if (rotation.equals("90"))
                            sum += 128;
                    }
                }


                if (!(i + 2 >= src.getHeight() || j-2 < 0)) {
                    gray2 = src.getRGB(i + 2, j-2) & 0xFF;
                    if (gray<=gray2){
                        if (rotation.equals("00")||rotation.equals("10")||rotation.equals("05"))
                            sum += 16;
                        else if (rotation.equals("15")||rotation.equals("30"))
                            sum += 8;
                        else if ( rotation.equals("45"))
                            sum += 4;
                        else if (rotation.equals("60")||rotation.equals("75"))
                            sum+= 2;
                        else if (rotation.equals("90"))
                            sum += 1;
                    }
                }


                if (!(i + 1 >= src.getHeight() || j-2 < 0)) {
                    gray2 = src.getRGB(i + 1, j-2) & 0xFF;
                    if (gray<=gray2){
                        if (rotation.equals("00")||rotation.equals("10")||rotation.equals("05"))
                            sum += 32;
                        else if (rotation.equals("15")||rotation.equals("30"))
                            sum += 16;
                        else if ( rotation.equals("45"))
                            sum += 8;
                        else if (rotation.equals("60")||rotation.equals("75"))
                            sum+= 4;
                        else if (rotation.equals("90"))
                            sum += 2;
                    }
                }


                if (!(j-2 < 0)) {
                    gray2 = src.getRGB(i , j-2) & 0xFF;
                    if (gray<=gray2){
                        if (rotation.equals("00")||rotation.equals("10")||rotation.equals("05"))
                            sum += 64;
                        else if (rotation.equals("15")||rotation.equals("30"))
                            sum += 32;
                        else if ( rotation.equals("45"))
                            sum += 16;
                        else if (rotation.equals("60")||rotation.equals("75"))
                            sum+= 8;
                        else if (rotation.equals("90"))
                            sum += 4;
                    }
                }


                if (!(i - 1 < 0 || j-2 < 0)) {
                    gray2 = src.getRGB(i - 1, j-2) & 0xFF;
                    if (gray<=gray2){
                        if (rotation.equals("00")||rotation.equals("10")||rotation.equals("05"))
                            sum += 128;
                        else if (rotation.equals("15")||rotation.equals("30"))
                            sum += 64;
                        else if ( rotation.equals("45"))
                            sum += 32;
                        else if (rotation.equals("60")||rotation.equals("75"))
                            sum+= 16;
                        else if (rotation.equals("90"))
                            sum += 8;
                    }
                }

                histogram[sum%256]++;
            }
        }
        return histogram;
    }

    public double[] extractFeatureLBP3R(BufferedImage src,String rotation, double[] histogram){
        for (int i = 0; i<src.getWidth();i++){
            for (int j = 0;j<src.getHeight();j++) {
                int gray1, gray2;
                int gray = src.getRGB(i , j ) & 0xFF;
                int sum = 0;

                if ( !(i - 3 < 0 || j - 3 < 0) ) {
                    gray1 = src.getRGB(i - 3, j - 3) & 0xFF;
                    if (gray<=gray1) {
                        if (rotation.equals("00")||rotation.equals("05"))
                            sum += 1;
                        else if (rotation.equals("10") || rotation.equals("15") )
                            sum += 128;
                        else if (rotation.equals("30"))
                            sum += 64;
                        else if ( rotation.equals("45"))
                            sum += 32;
                        else if (rotation.equals("60"))
                            sum+= 16;
                        else if (rotation.equals("75"))
                            sum+=8;
                        else if (rotation.equals("90"))
                            sum += 4;
                    }
                }


                if ( !(i - 3 < 0 || j - 2 < 0) ) {
                    gray1 = src.getRGB(i - 3, j - 2) & 0xFF;
                    if (gray<=gray1) {
                        if (rotation.equals("00")||rotation.equals("05"))
                            sum += 2;
                        else if (rotation.equals("10") || rotation.equals("15") )
                            sum += 1;
                        else if (rotation.equals("30"))
                            sum += 128;
                        else if ( rotation.equals("45"))
                            sum += 64;
                        else if (rotation.equals("60"))
                            sum+= 32;
                        else if (rotation.equals("75"))
                            sum+=16;
                        else if (rotation.equals("90"))
                            sum += 8;
                    }
                }


                if ( !(i - 3 < 0 || j - 1 < 0) ) {
                    gray1 = src.getRGB(i - 3, j - 1) & 0xFF;
                    if (gray<=gray1) {
                        if (rotation.equals("00")||rotation.equals("05"))
                            sum += 4;
                        else if (rotation.equals("10") || rotation.equals("15") )
                            sum += 2;
                        else if (rotation.equals("30"))
                            sum += 1;
                        else if ( rotation.equals("45"))
                            sum += 128;
                        else if (rotation.equals("60"))
                            sum+= 64;
                        else if (rotation.equals("75"))
                            sum+=32;
                        else if (rotation.equals("90"))
                            sum += 16;
                    }
                }


                if ( !(i - 3 < 0 ) ) {
                    gray1 = src.getRGB(i - 3, j ) & 0xFF;
                    if (gray<=gray1) {
                        if (rotation.equals("00")||rotation.equals("05"))
                            sum += 8;
                        else if (rotation.equals("10") || rotation.equals("15") )
                            sum += 4;
                        else if (rotation.equals("30"))
                            sum += 2;
                        else if ( rotation.equals("45"))
                            sum += 1;
                        else if (rotation.equals("60"))
                            sum+= 128;
                        else if (rotation.equals("75"))
                            sum+=64;
                        else if (rotation.equals("90"))
                            sum += 32;
                    }
                }


                if ( !(i - 3 < 0 || j + 1 >= src.getHeight()) ) {
                    gray1 = src.getRGB(i - 3, j + 1) & 0xFF;
                    if (gray<=gray1) {
                        if (rotation.equals("00")||rotation.equals("05"))
                            sum += 16;
                        else if (rotation.equals("10") || rotation.equals("15") )
                            sum += 8;
                        else if (rotation.equals("30"))
                            sum += 4;
                        else if ( rotation.equals("45"))
                            sum += 2;
                        else if (rotation.equals("60"))
                            sum+= 1;
                        else if (rotation.equals("75"))
                            sum+=128;
                        else if (rotation.equals("90"))
                            sum += 64;
                    }
                }


                if ( !(i - 3 < 0 || j + 2 >= src.getHeight()) ) {
                    gray1 = src.getRGB(i - 3, j + 2) & 0xFF;
                    if (gray<=gray1) {
                        if (rotation.equals("00")||rotation.equals("05"))
                            sum += 32;
                        else if (rotation.equals("10") || rotation.equals("15") )
                            sum += 16;
                        else if (rotation.equals("30"))
                            sum += 8;
                        else if ( rotation.equals("45"))
                            sum += 4;
                        else if (rotation.equals("60"))
                            sum+= 2;
                        else if (rotation.equals("75"))
                            sum+=1;
                        else if (rotation.equals("90"))
                            sum +=128;
                    }
                }


                if ( !(i - 3 < 0 || j + 3 >= src.getHeight()) ) {
                    gray1 = src.getRGB(i - 3, j + 3) & 0xFF;
                    if (gray<=gray1) {
                        if (rotation.equals("00")||rotation.equals("05"))
                            sum += 64;
                        else if (rotation.equals("10") || rotation.equals("15") )
                            sum += 32;
                        else if (rotation.equals("30"))
                            sum += 16;
                        else if ( rotation.equals("45"))
                            sum += 8;
                        else if (rotation.equals("60"))
                            sum+= 4;
                        else if (rotation.equals("75"))
                            sum+=2;
                        else if (rotation.equals("90"))
                            sum += 1;
                    }
                }

                if ( !(i - 2 < 0 || j + 3 >= src.getHeight()) ) {
                    gray1 = src.getRGB(i - 2, j + 3) & 0xFF;
                    if (gray<=gray1) {
                        if (rotation.equals("00")||rotation.equals("05"))
                            sum += 128;
                        else if (rotation.equals("10") || rotation.equals("15") )
                            sum += 64;
                        else if (rotation.equals("30"))
                            sum += 32;
                        else if ( rotation.equals("45"))
                            sum += 16;
                        else if (rotation.equals("60"))
                            sum+= 8;
                        else if (rotation.equals("75"))
                            sum+=4;
                        else if (rotation.equals("90"))
                            sum += 2;
                    }
                }

                if ( !(i - 1 < 0 || j + 3 >= src.getHeight()) ) {
                    gray1 = src.getRGB(i - 1, j + 3) & 0xFF;
                    if (gray<=gray1) {
                        if (rotation.equals("00")||rotation.equals("05"))
                            sum += 1;
                        else if (rotation.equals("10") || rotation.equals("15") )
                            sum += 128;
                        else if (rotation.equals("30"))
                            sum += 64;
                        else if ( rotation.equals("45"))
                            sum += 32;
                        else if (rotation.equals("60"))
                            sum+= 16;
                        else if (rotation.equals("75"))
                            sum+=8;
                        else if (rotation.equals("90"))
                            sum += 4;
                    }
                }

                if ( !( j + 3 >= src.getHeight()) ) {
                    gray1 = src.getRGB(i , j + 3) & 0xFF;
                    if (gray<=gray1) {
                        if (rotation.equals("00")||rotation.equals("05"))
                            sum += 2;
                        else if (rotation.equals("10") || rotation.equals("15") )
                            sum += 1;
                        else if (rotation.equals("30"))
                            sum += 128;
                        else if ( rotation.equals("45"))
                            sum += 64;
                        else if (rotation.equals("60"))
                            sum+= 32;
                        else if (rotation.equals("75"))
                            sum+=16;
                        else if (rotation.equals("90"))
                            sum += 8;
                    }
                }

                if ( !(i + 1 >= src.getWidth() || j + 3 >= src.getHeight()) ) {
                    gray1 = src.getRGB(i + 1, j + 3) & 0xFF;
                    if (gray<=gray1) {
                        if (rotation.equals("00")||rotation.equals("05"))
                            sum += 4;
                        else if (rotation.equals("10") || rotation.equals("15") )
                            sum += 2;
                        else if (rotation.equals("30"))
                            sum += 1;
                        else if ( rotation.equals("45"))
                            sum += 128;
                        else if (rotation.equals("60"))
                            sum+= 64;
                        else if (rotation.equals("75"))
                            sum+=32;
                        else if (rotation.equals("90"))
                            sum += 16;
                    }
                }
                if ( !(i + 2 >= src.getWidth() || j + 3 >= src.getHeight()) ) {
                    gray1 = src.getRGB(i + 2, j + 3) & 0xFF;
                    if (gray<=gray1) {
                        if (rotation.equals("00")||rotation.equals("05"))
                            sum += 8;
                        else if (rotation.equals("10") || rotation.equals("15") )
                            sum += 4;
                        else if (rotation.equals("30"))
                            sum += 2;
                        else if ( rotation.equals("45"))
                            sum += 1;
                        else if (rotation.equals("60"))
                            sum+= 128;
                        else if (rotation.equals("75"))
                            sum+=64;
                        else if (rotation.equals("90"))
                            sum += 32;
                    }
                }


                if ( !(i + 3 >= src.getWidth() || j + 3 >= src.getHeight()) ) {
                    gray1 = src.getRGB(i + 3, j + 3) & 0xFF;
                    if (gray<=gray1) {
                        if (rotation.equals("00")||rotation.equals("05"))
                            sum += 16;
                        else if (rotation.equals("10") || rotation.equals("15") )
                            sum += 8;
                        else if (rotation.equals("30"))
                            sum += 4;
                        else if ( rotation.equals("45"))
                            sum += 2;
                        else if (rotation.equals("60"))
                            sum+= 1;
                        else if (rotation.equals("75"))
                            sum+=128;
                        else if (rotation.equals("90"))
                            sum += 64;
                    }
                }

                if ( !(i + 3 >= src.getWidth() || j + 2 >= src.getHeight()) ) {
                    gray1 = src.getRGB(i + 3, j + 2) & 0xFF;
                    if (gray<=gray1) {
                        if (rotation.equals("00")||rotation.equals("05"))
                            sum += 32;
                        else if (rotation.equals("10") || rotation.equals("15") )
                            sum += 16;
                        else if (rotation.equals("30"))
                            sum += 8;
                        else if ( rotation.equals("45"))
                            sum += 4;
                        else if (rotation.equals("60"))
                            sum+= 2;
                        else if (rotation.equals("75"))
                            sum+=1;
                        else if (rotation.equals("90"))
                            sum += 128;
                    }
                }

                if ( !(i + 3 >= src.getWidth() || j + 1 >= src.getHeight()) ) {
                    gray1 = src.getRGB(i + 3, j + 1) & 0xFF;
                    if (gray<=gray1) {
                        if (rotation.equals("00")||rotation.equals("05"))
                            sum += 64;
                        else if (rotation.equals("10") || rotation.equals("15") )
                            sum += 32;
                        else if (rotation.equals("30"))
                            sum += 16;
                        else if ( rotation.equals("45"))
                            sum += 8;
                        else if (rotation.equals("60"))
                            sum+= 4;
                        else if (rotation.equals("75"))
                            sum+=2;
                        else if (rotation.equals("90"))
                            sum += 1;
                    }
                }


                if ( !(i + 3 >= src.getWidth()) ) {
                    gray1 = src.getRGB(i + 3, j ) & 0xFF;
                    if (gray<=gray1) {
                        if (rotation.equals("00")||rotation.equals("05"))
                            sum += 128;
                        else if (rotation.equals("10") || rotation.equals("15") )
                            sum += 64;
                        else if (rotation.equals("30"))
                            sum += 32;
                        else if ( rotation.equals("45"))
                            sum += 16;
                        else if (rotation.equals("60"))
                            sum+= 8;
                        else if (rotation.equals("75"))
                            sum+=4;
                        else if (rotation.equals("90"))
                            sum += 2;
                    }
                }


                if ( !((i + 3 >= src.getWidth())||j-1<0) ) {
                    gray1 = src.getRGB(i + 3, j-1 ) & 0xFF;
                    if (gray<=gray1) {
                        if (rotation.equals("00")||rotation.equals("05"))
                            sum += 1;
                        else if (rotation.equals("10") || rotation.equals("15") )
                            sum += 128;
                        else if (rotation.equals("30"))
                            sum += 64;
                        else if ( rotation.equals("45"))
                            sum += 32;
                        else if (rotation.equals("60"))
                            sum+= 16;
                        else if (rotation.equals("75"))
                            sum+=8;
                        else if (rotation.equals("90"))
                            sum += 4;
                    }
                }


                if ( !((i + 3 >= src.getWidth())||j-2<0) ) {
                    gray1 = src.getRGB(i + 3, j-2 ) & 0xFF;
                    if (gray<=gray1) {
                        if (rotation.equals("00")||rotation.equals("05"))
                            sum += 2;
                        else if (rotation.equals("10") || rotation.equals("15") )
                            sum += 1;
                        else if (rotation.equals("30"))
                            sum += 128;
                        else if ( rotation.equals("45"))
                            sum += 64;
                        else if (rotation.equals("60"))
                            sum+= 32;
                        else if (rotation.equals("75"))
                            sum+=16;
                        else if (rotation.equals("90"))
                            sum += 8;
                    }
                }


                if ( !((i + 3 >= src.getWidth())||j-3<0) ) {
                    gray1 = src.getRGB(i + 3, j-3 ) & 0xFF;
                    if (gray<=gray1) {
                        if (rotation.equals("00")||rotation.equals("05"))
                            sum += 4;
                        else if (rotation.equals("10") || rotation.equals("15") )
                            sum += 2;
                        else if (rotation.equals("30"))
                            sum += 1;
                        else if ( rotation.equals("45"))
                            sum += 128;
                        else if (rotation.equals("60"))
                            sum+= 64;
                        else if (rotation.equals("75"))
                            sum+=32;
                        else if (rotation.equals("90"))
                            sum += 16;
                    }
                }


                if ( !((i + 2 >= src.getWidth())||j-3<0) ) {
                    gray1 = src.getRGB(i + 2, j-3 ) & 0xFF;
                    if (gray<=gray1) {
                        if (rotation.equals("00")||rotation.equals("05"))
                            sum += 8;
                        else if (rotation.equals("10") || rotation.equals("15") )
                            sum += 4;
                        else if (rotation.equals("30"))
                            sum += 2;
                        else if ( rotation.equals("45"))
                            sum += 1;
                        else if (rotation.equals("60"))
                            sum+= 128;
                        else if (rotation.equals("75"))
                            sum+= 64;
                        else if (rotation.equals("90"))
                            sum += 32;
                    }
                }


                if ( !((i + 1 >= src.getWidth())||j-3<0) ) {
                    gray1 = src.getRGB(i + 1, j-3 ) & 0xFF;
                    if (gray<=gray1) {
                        if (rotation.equals("00")||rotation.equals("05"))
                            sum += 16;
                        else if (rotation.equals("10") || rotation.equals("15") )
                            sum += 8;
                        else if (rotation.equals("30"))
                            sum += 4;
                        else if ( rotation.equals("45"))
                            sum += 2;
                        else if (rotation.equals("60"))
                            sum+= 1;
                        else if (rotation.equals("75"))
                            sum+= 128;
                        else if (rotation.equals("90"))
                            sum += 64;
                    }
                }



                if ( !(j-3<0) ) {
                    gray1 = src.getRGB(i , j-3 ) & 0xFF;
                    if (gray<=gray1) {
                        if (rotation.equals("00")||rotation.equals("05"))
                            sum += 32;
                        else if (rotation.equals("10") || rotation.equals("15") )
                            sum += 16;
                        else if (rotation.equals("30"))
                            sum += 8;
                        else if ( rotation.equals("45"))
                            sum += 4;
                        else if (rotation.equals("60"))
                            sum+= 2;
                        else if (rotation.equals("75"))
                            sum+= 1;
                        else if (rotation.equals("90"))
                            sum += 128;
                    }
                }

                if ( !(i + 1 >= src.getWidth() || j - 3 <0) ) {
                    gray1 = src.getRGB(i + 1, j - 3) & 0xFF;
                    if (gray<=gray1) {
                        if (rotation.equals("00")||rotation.equals("05"))
                            sum += 64;
                        else if (rotation.equals("10") || rotation.equals("15") )
                            sum += 32;
                        else if (rotation.equals("30"))
                            sum += 16;
                        else if ( rotation.equals("45"))
                            sum += 8;
                        else if (rotation.equals("60"))
                            sum+= 4;
                        else if (rotation.equals("75"))
                            sum+=2;
                        else if (rotation.equals("90"))
                            sum += 1;
                    }
                }

                if ( !(i + 2 >= src.getWidth() || j - 3 <0) ) {
                    gray1 = src.getRGB(i + 2, j - 3) & 0xFF;
                    if (gray<=gray1) {
                        if (rotation.equals("00")||rotation.equals("05"))
                            sum += 128;
                        else if (rotation.equals("10") || rotation.equals("15") )
                            sum += 64;
                        else if (rotation.equals("30"))
                            sum += 32;
                        else if ( rotation.equals("45"))
                            sum += 16;
                        else if (rotation.equals("60"))
                            sum+= 8;
                        else if (rotation.equals("75"))
                            sum+=4;
                        else if (rotation.equals("90"))
                            sum += 2;
                    }
                }
                histogram[sum%256]++;
            }
        }
        return histogram;
    }
}