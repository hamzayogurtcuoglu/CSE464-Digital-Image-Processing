
#ifndef IMAGE_OPERATIONS_HPP
#define IMAGE_OPERATIONS_HPP

#include <iostream>
#include <queue>
#include <algorithm>
#include "image.hpp"


Image<unsigned char> minImages(const Image<unsigned char> &image1, const Image<unsigned char> &image2);
Image<unsigned char> imageSubstraction(const Image<unsigned char> &image1, const Image<unsigned char> &image2);
Image<unsigned char> dilationRectangle(const Image<unsigned char> &image);





Image<unsigned char> imageSubstraction(const Image<unsigned char> &image1, const Image<unsigned char> &image2){

    int dimX = image1.dimx();
    int dimY = image1.dimy();
    Image<unsigned char> result(dimX, dimY);

    for (int i = 0 ; i < dimX ; ++i){
        for (int j = 0 ; j < dimY ; ++j){
            result(i,j) = image1(i,j) - image2(i,j);
        }
    }
    return result;
}



Image<unsigned char> dilationRectangle(const Image<unsigned char> &image){

    Image<unsigned char> result(image.dimx(), image.dimy());

    int i, j;
    for (i = 0 ; i < image.dimx() ; ++i){
        for (j = 0 ; j < image.dimy() ; ++j){

            int xmin = i ;
            if (xmin < 0){
                xmin = 0;
            }

            int xmax = i ;
            if (xmax >= image.dimx()){
                xmax = image.dimx() -1;
            }

            int ymin = j ;
            if (ymin < 0){
                ymin = 0;
            }
            int ymax = j ;
            if (ymax >= image.dimy()){
                ymax = image.dimy() -1;
            }

            int y, x;
            int maxValue = 0;
            for (x = xmin ; x <= xmax ; ++x){
                for (y = ymin ; y <= ymax ; ++y){

                    if (maxValue < image(x,y)){
                        maxValue = image(x,y);
                    }
                }
            }
            result(i,j) = maxValue;
        }
    }
    
    return result;
}

Image<unsigned char> minImages(const Image<unsigned char> &image1, const Image<unsigned char> &image2){

    Image<unsigned char> result(image1.dimx(), image1.dimy());
    int i, j;
    for (i = 0; i<image1.dimx() ; ++i){
        for (j = 0 ; j<image1.dimy() ; ++j){
            result(i,j) = std::min(image1(i,j), image2(i,j));
        }
    }
    return result;
}

#endif 

