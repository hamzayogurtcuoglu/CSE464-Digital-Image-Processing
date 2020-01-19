
#ifndef PROCESS_HPP
#define PROCESS_HPP

#include <vector>
#include <iostream>
#include <limits>
#include <stdlib.h>
#include <math.h>

#include "png.hpp"
#include "pathOpening.hpp"
#include "image.hpp"
#include "imageOperations.hpp"
#include "sortImageValues.hpp"

Image<unsigned char> processImage(const Image<unsigned char> &image,int minLength){

    int dimX = image.dimx();
    int dimY = image.dimy();

    // Extend the image with 0
    Image<unsigned char> imageWithBorder = image.addBorder();

    // Dilate image with rectangle
    Image<unsigned char> dilatedImage = dilationRectangle(imageWithBorder);
   
   // Orientation vectors 
    std::vector<int> orientation1(2);
    std::vector<int> orientation2(2);
    std::vector<int> orientation3(2);
    std::vector<int> orientation4(2);
    
    orientation1[0] = 0;
    orientation1[1] = 1;
    orientation2[0] = 1;
    orientation2[1] = 0;
    orientation3[0] = 1;
    orientation3[1] = 1;
    orientation4[0] = -1;
    orientation4[1] = 1;

    // Compute the path openings (PO) in each orientation
    Image<unsigned char> orientedPathOpenning1(dimX, dimY);
    Image<unsigned char> orientedPathOpenning2(dimX, dimY);
    Image<unsigned char> orientedPathOpenning3(dimX, dimY);
    Image<unsigned char> orientedPathOpenning4(dimX, dimY);

    // sort index of imageWithBorder (required for the PO algorithm).
    std::vector<int32_t> indexImage=
    sortImageValues(dilatedImage.get_pointer(),dilatedImage.image_size());

    //process opath opening each orientation vectors
    pathOpening(dilatedImage, minLength, indexImage, orientation1, orientedPathOpenning1);
    pathOpening(dilatedImage, minLength, indexImage, orientation2, orientedPathOpenning2);
    pathOpening(dilatedImage, minLength, indexImage, orientation3, orientedPathOpenning3);
    pathOpening(dilatedImage, minLength, indexImage, orientation4, orientedPathOpenning4);

    // Min with the initial image (required for the robust version of PO)
    orientedPathOpenning1 = minImages(image, orientedPathOpenning1);
    orientedPathOpenning2 = minImages(image, orientedPathOpenning2);
    orientedPathOpenning3 = minImages(image, orientedPathOpenning3);
    orientedPathOpenning4 = minImages(image, orientedPathOpenning4);

    // Rank the PO responses
    Image<unsigned char> poOri1Sorted(orientedPathOpenning1);
    Image<unsigned char> poOri2Sorted(orientedPathOpenning2);
    Image<unsigned char> poOri3Sorted(orientedPathOpenning3);
    Image<unsigned char> poOri4Sorted(orientedPathOpenning4);

    Image<int> indicePoSorted1(dimX, dimY);
    Image<int> indicePoSorted2(dimX, dimY);
    Image<int> indicePoSorted3(dimX, dimY);
    Image<int> indicePoSorted4(dimX, dimY);

    sort_argsort_images(poOri1Sorted,
                        poOri2Sorted,
                        poOri3Sorted,
                        poOri4Sorted,
                        indicePoSorted1,
                        indicePoSorted2,
                        indicePoSorted3,
                        indicePoSorted4);

    Image<unsigned char> justPathImage = imageSubstraction(poOri4Sorted ,poOri1Sorted);
    
    write_2D_png_image<unsigned char>(poOri4Sorted,  "PathResult2.png");
    return justPathImage;
}
#endif 

