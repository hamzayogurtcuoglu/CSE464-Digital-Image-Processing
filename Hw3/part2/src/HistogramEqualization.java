import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class HistogramEqualization {

    List<BufferedImage> imagesHEList;
    List<String> imagesPath;
    int imagesNumber;

    HistogramEqualization(String in){
        try {
             imagesHEList = new ArrayList<BufferedImage>();
             imagesPath = new ArrayList<String>();
             Scanner scanner = new Scanner(new File(in));
             imagesNumber = Integer.valueOf(scanner.nextLine());
             for (int i = 0 ; i<imagesNumber;i++) {
                 String imagePath = scanner.nextLine();
                 File f1 = new File(imagePath);
                 imagesPath.add(imagePath);
                 File f2 = new File("output.png");
                 BufferedImage image1 = getGrayscaleImage(ImageIO.read(f1));
                 BufferedImage image2 = Hequalize(image1);
                 imagesHEList.add(image2);
                 ImageIO.write(image2, "png", f2);
             }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    BufferedImage Hequalize(BufferedImage src){
        BufferedImage nImg = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster wr = src.getRaster();
        WritableRaster er = nImg.getRaster();

        int totpix= wr.getWidth()*wr.getHeight();
        int[] histogram = new int[256];
        for (int x = 0; x < wr.getWidth(); x++) {
            for (int y = 0; y < wr.getHeight(); y++) {
                histogram[wr.getSample(x, y, 0)]++;
            }
        }

        int[] chistogram = new int[256];
        chistogram[0] = histogram[0];
        for(int i=1;i<256;i++){
            chistogram[i] = chistogram[i-1] + histogram[i];
        }

        float[] arr = new float[256];
        for(int i=0;i<256;i++){
            arr[i] =  (float)((chistogram[i]*255.0)/(float)totpix);
        }

        for (int x = 0; x < wr.getWidth(); x++) {
            for (int y = 0; y < wr.getHeight(); y++) {
                int nVal = (int) arr[wr.getSample(x, y, 0)];
                er.setSample(x, y, 0, nVal);
            }
        }
        nImg.setData(er);
        return nImg;
    }
    BufferedImage getGrayscaleImage(BufferedImage src) {
        BufferedImage gImg = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster wr = src.getRaster();
        WritableRaster gr = gImg.getRaster();
        for(int i=0;i<wr.getWidth();i++){
            for(int j=0;j<wr.getHeight();j++){
                gr.setSample(i, j, 0, wr.getSample(i, j, 0));
            }
        }
        gImg.setData(gr);
        return gImg;
    }
}
