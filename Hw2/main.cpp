
#include <iostream>
#include <png.h>
#include <math.h>
#include "pathOpening.hpp"
#include "sortImageValues.hpp"
#include <stdlib.h>
#include <stdio.h>
#include <string>
#include "image.hpp"
#include "png.hpp"
#include "imageOperations.hpp"
#include "processImage.hpp"
#include "HMinima.hpp"

using namespace std;

int main(int argc, char **argv) {

    if (argc!=2) {
        fprintf(stderr, "usage: %s <image path> \n",argv[0]);
        exit(1);
    }
    Image<unsigned char> saltImage = read_2D_png_image<unsigned char>(argv[1]);
    Image<unsigned char> image = HMinima(saltImage,50);    
    
    // You can change minimum length of path.
    
    int pathLength = 30;
    Image<unsigned char> PathResult = processImage(image, pathLength);
    write_2D_png_image<unsigned char>(PathResult,  "PathResult1.png");
  return 0;
}
