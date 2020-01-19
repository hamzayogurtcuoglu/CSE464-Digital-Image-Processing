import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.Scanner;

public class Main {

    /**
     * Loading picture to 2D Pixel array, it will be represented as a
     * 2D array (with W columns and H rows) of 3D 8bit unsigned integer valued vectors.
     * First if no such a argument
     * User will write a pathname in commandline
     *
     * @param args first index is got for picture path
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        int kernelSize = 0;
        BufferedImage bi;
        String pathnmame = null;
        int strategyMethod = 0;
        try {
             bi = ImageIO.read(new File(args[0]));
        }catch (Exception e){
            try {

                Scanner myObj = new Scanner(System.in);  // Create a Scanner object
                System.out.println("\n"+
                                "Enter picture path ( Example: image.jpg ) : "
                                   );

                pathnmame = myObj.nextLine();  // Read user input
                bi = ImageIO.read(new File(pathnmame));
                System.out.println("\n"+
                        "Enter kernel size : (Example: 3x3 -> 3, 5x5 -> 5 ... ) : "
                );
                kernelSize = myObj.nextInt();


                if (kernelSize%2 != 1){
                    System.out.println("Just enter odd size of kernel -> 3 , 5 , 7 ...");
                    return;
                }
                System.out.println("\nChoose Your Compare Method : \n");
                System.out.println("Marginal Strategy -> 1 ");
                System.out.println("Lexicographical Ordering -> 2 ");
                System.out.println("Bitmix Ordering -> 3 ");
                System.out.println("Norm Based Ordering -> 4\n");
                strategyMethod = myObj.nextInt();

            }catch (Exception e1){
                System.out.println("No such a picture");
                return;
            }
        }

        Comparator<Pixel> comparator = null;
        switch (strategyMethod){
            case 1 :
                comparator = null;
                break;
            case 2 :
                comparator = new LexComparator();
                break;
            case 3 :
                comparator = new BmxComparator();
                break;
            case 4:
                comparator = new EucComparator();
                break;
            default:
                return;
        }

        int padding = (int)(kernelSize/2);
        Pixel [][] paddingImg = paddingImage(padding,bi);
        Pixel [][] outputImage = medianFilter(bi,kernelSize,padding,paddingImg,comparator);
        writeImage(pathnmame,bi,outputImage);
        MSE(padding,paddingImg,outputImage);
    }
    public static void MSE(int padding,Pixel [][] orginal,Pixel [][] output){

        double sumR = 0.0;
        double sumG = 0.0;
        double sumB = 0.0;

        for (int i = 0;i<output.length;i++){
            for (int j = 0;j<output[0].length;j++){
                sumR += (orginal[i+padding][j+padding].getRed() - output[i][j].getRed())*
                        (orginal[i+padding][j+padding].getRed() - output[i][j].getRed());

                sumG += (orginal[i+padding][j+padding].getGreen() - output[i][j].getGreen())*
                        (orginal[i+padding][j+padding].getGreen() - output[i][j].getGreen());

                sumB += (orginal[i+padding][j+padding].getBlue() - output[i][j].getBlue())*
                        (orginal[i+padding][j+padding].getBlue() - output[i][j].getBlue());
            }
        }

        System.out.println("Mean Squared Error : ");
        System.out.println("Red : " + sumR/(output.length*output[0].length));
        System.out.println("Green : " + sumG/(output.length*output[0].length));
        System.out.println("Blue : " + sumB/(output.length*output[0].length));


    }

    public static Pixel [][] paddingImage(int padding,BufferedImage bi){
        Pixel [][] PixelArray = new Pixel[bi.getWidth()+padding*2][bi.getHeight()+padding*2];

        for (int x = 0; x < bi.getWidth()+padding; x++) {
            for (int y = 0; y < bi.getHeight()+padding; y++) {
                PixelArray[x][y] = new Pixel();

                if (padding<=x&&bi.getWidth()>x&&
                        padding<=y&&bi.getHeight()>y
                ) {
                    int pixel = bi.getRGB(x, y);
                    PixelArray[x][y].setRed((pixel >> 16) & 0xff);
                    PixelArray[x][y].setGreen((pixel >> 8) & 0xff);
                    PixelArray[x][y].setBlue((pixel) & 0xff);
                }
            }
        }
        return PixelArray;
    }

    public static Pixel [][] medianFilter(BufferedImage bi, int kernelSize, int padding,
                                          Pixel [][] paddingImg, Comparator<Pixel> order){

        Pixel [][] PixelArray2 = new Pixel[bi.getWidth()][bi.getHeight()];
        Pixel [] kernelCompare = new Pixel[kernelSize*kernelSize];

        for (int x = 0; x < bi.getWidth(); x++) {
            for (int y = 0; y < bi.getHeight(); y++) {
                int index = 0;
                for (int i = padding*-1;i <=padding;i++){
                    for (int j = padding*-1; j <= padding;j++) {
                        if (paddingImg[x + i+padding][y + j+padding] != null) {
                            kernelCompare[index] = paddingImg[x + i + padding][y + j + padding];
                            index++;
                        }else{
                            kernelCompare[index] = paddingImg[x][y];
                            index++;
                        }
                    }
                }
                int[] R = new int[kernelSize*kernelSize];
                int[] B = new int[kernelSize*kernelSize];
                int[] G = new int[kernelSize*kernelSize];
                if (order != null) {
                    int n = kernelCompare.length;
                    for (int i = 0; i < n - 1; i++)
                        for (int j = 0; j < n - i - 1; j++)
                            if (order.compare(kernelCompare[j], kernelCompare[j + 1]) == 1) {
                                // swap arr[j+1] and arr[i]
                                Pixel temp = kernelCompare[j];
                                kernelCompare[j] = kernelCompare[j + 1];
                                kernelCompare[j + 1] = temp;
                            }
                    PixelArray2[x][y] = kernelCompare[(int) (kernelSize * kernelSize / 2 + 1)];
                }else{
                    for (int i = 0;i<kernelSize*kernelSize;i++ ){
                        R[i] = kernelCompare[i].getRed();
                        G[i] = kernelCompare[i].getGreen();
                        B[i] = kernelCompare[i].getBlue();
                    }
                    int n = kernelCompare.length;
                    for (int i = 0; i < n - 1; i++)
                        for (int j = 0; j < n - i - 1; j++)
                            if (R[j]>R[j + 1]) {
                                int temp = R[j];
                                R[j] = R[j + 1];
                                R[j + 1] = temp;
                            }
                    for (int i = 0; i < n - 1; i++)
                        for (int j = 0; j < n - i - 1; j++)
                            if (G[j]>G[j + 1]) {
                                int temp = G[j];
                                G[j] = G[j + 1];
                                G[j + 1] = temp;
                            }
                    for (int i = 0; i < n - 1; i++)
                        for (int j = 0; j < n - i - 1; j++)
                            if (B[j]>B[j + 1]) {
                                int temp = B[j];
                                B[j] = B[j + 1];
                                B[j + 1] = temp;
                            }
                    Pixel newPixel = new Pixel();
                    newPixel.setRed(R[(kernelSize * kernelSize / 2 + 1)]);
                    newPixel.setGreen(G[(kernelSize * kernelSize / 2 + 1)]);
                    newPixel.setBlue(B[(kernelSize * kernelSize / 2 + 1)]);
                    PixelArray2[x][y] =newPixel;
                }
            }
        }
        return PixelArray2;
    }
    public static void writeImage(String pathnmame,BufferedImage bi,Pixel [][] outputImage) throws IOException {
        File f=new File(pathnmame); //Input Photo File
        File output=new File("output.jpg");
        BufferedImage img=ImageIO.read(f);
        for (int x = 0; x < bi.getWidth(); x++) {
            for (int y = 0; y < bi.getHeight(); y++) {
                img.setRGB(x,y,new Color( ((outputImage[x][y].getRed())),
                        ((outputImage[x][y].getBlue()))
                        ,(outputImage[x][y].getGreen())).getRGB());
            }
        }
        ImageIO.write(img,"jpg",output);
    }
}
