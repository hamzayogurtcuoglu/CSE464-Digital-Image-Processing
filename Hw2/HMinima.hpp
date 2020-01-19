#ifndef HMINIMA_HPP
#define HMINIMA_HPP

#include <iostream>
#include <math.h>
#include <stdlib.h>
#include <stdio.h>
#include <string>
#include "image.hpp"
#include "png.hpp"


using namespace std;

int maxForward(const Image<unsigned char> & image,int x, int y, int c);

void checkNeighbours(int x, int y, int c,std::vector<int> f, int val, bool inverse,
                    Image<unsigned char> &image,const Image<unsigned char> & mask);

int maxBackward(const Image<unsigned char> &image,int x, int y, int c);

bool updateFifo(int x, int y, int c, bool inverse,const Image<unsigned char> &image
                ,const Image<unsigned char> &mask);

void checkNeighbours(int x, int y, int c,std::vector<int> f, int val, bool inverse,
                    Image<unsigned char> &image,const Image<unsigned char> & mask);
Image<unsigned char> AdditionC(const Image<unsigned char> &image,double length);

Image<unsigned char> FastGrayReconstruction(const Image<unsigned char> &image,
                const Image<unsigned char> &mask,int one,bool boolean);

int min(int a,int b){
        if (a>b)
        {
            return b;
        }
        return a;
    }


    int max(int a,int b){
        if (a>b)
        {
            return a;
        }
        return b;
    }


Image<unsigned char> HMinima(const Image<unsigned char> &image,int length){
    if (length > 0 && length < 255) {
            
            Image<unsigned char> output1 = AdditionC(image, (double)length * 0.00392156862745098D);
            //marker - output1       mask - image
            Image<unsigned char> output = FastGrayReconstruction(output1,image,1,true);
            return output;
    }
    return image; 

}

Image<unsigned char> AdditionC(const Image<unsigned char> &image,double length){
        Image<unsigned char> output(image.dimx(), image.dimy());
        int size = output.dimx()*output.dimy();

        for(int i = 0; i < size; ++i) {
             
            double p = 0.00392156862745098D * (double)(image.get_data()[i]);
            double pixel = 1.0D>(p + length)?
            (p + length):1.0D ;  
            output.get_data()[i] = ((int)round(255.0D * pixel ));   
       
        }    
        return output;
}


Image<unsigned char> FastGrayReconstruction(const Image<unsigned char> &image,
                const Image<unsigned char> &mask,int one,bool boolean){

        Image<unsigned char> output(image.dimx(), image.dimy());
        
        std::vector<int> f;

        for (int i = 0; i < image.dimx(); i++)
        {
            for (int j = 0; j < image.dimy(); j++)
            {
                output(i,j) = image(i,j);
            }
        }
        

        bool inverse = true;
        int y;
        int x;
        for(y = 0; y < image.dimy(); ++y) {
            for(x = 0; x < image.dimx(); ++x) {
                int val = maxForward(output,x, y, 0);
                output(x,y) = max( val , mask(x,y));
            }
        }

        for(y = image.dimy() - 1; y >= 0; --y) {
            for(x = image.dimx() - 1; x >= 0; --x) {
                int val = maxBackward(output,x, y, 0);
                output(x,y) =  max( val , mask(x,y));
                
                if (updateFifo(x, y, 0, inverse,output,mask)) {
                    f.push_back(x);
                    f.push_back(y);                 
                }
            }
        }
        int index = 0; 
        while(f.size()!=0 && index != f.size() ) {
            int x = f[index];
            index++;
            int y = f[index];
            checkNeighbours(x, y, 0, f, output(x,y), inverse,output,mask);
            index++;
        }

        return output;
    

}

int maxForward(const Image<unsigned char> & image,int x, int y, int c) {
        
        int val = image(x,y);
        if (x > 0) {
            val = min(val, image(x-1,y));
        }

        if (y > 0) {
            val = min(val, image(x,y-1));
        }

        if (false) {
            return val;
        } else {
            if (x > 0 && y > 0) {
                val = min(val, image(x-1,y-1));
            }

            if (x < image.dimx() - 1 && y > 0) {
                val = min(val, image(x+1,y-1));
            }

            return val;
        }
}
int maxBackward(const Image<unsigned char> &image,int x, int y, int c) {

       int val = image(x,y);

        if (x <image.dimx() - 1 ) {
            val = min(val, image(x+1,y));
        }

        if (y < image.dimy() - 1 ) {
            val = min(val, image(x,y+1));
        }

        if (false) {
            return val;
        } else {
            if (x < image.dimx()-1 && y<image.dimy() - 1) {
                val = min(val, image(x+1,y+1));
            }

            if (x > 0 && y < image.dimy()-1) {
                val = min(val, image(x-1,y+1));
            }

            return val;
        }
}

bool updateFifo(int x, int y, int c, bool inverse,const Image<unsigned char> &image
                ,const Image<unsigned char> &mask) {
       
        int val = image(x,y);
        
        if (x < image.dimy() - 1 && image(x+1,y) > val && image(x+1,y) > mask(x+1,y)) {         
            return true;
        } else if (y < image.dimy() - 1 && image(x,y+1) > val && image(x,y+1) > mask(x,y+1)) {
            return true;
        }else if (x < image.dimx() - 1 && y < image.dimy() - 1 && 
        image(x+1,y+1) > val && image(x+1,y+1) > mask(x+1,y+1)) {
            return true;
        } else {
            return x > 0 && y < image.dimy() - 1 && image(x-1,y+1) > val && image(x-1,y+1) > mask(x-1,y+1);
        }

}

void checkNeighbours(int x, int y, int c,std::vector<int> f, int val, bool inverse,
                    Image<unsigned char> &image,const Image<unsigned char> & mask) {
        
        if (x > 0) {
            image(x-1,y) =  max(val, mask(x-1,y));
            f.push_back(x-1);
            f.push_back(y);

        }

        if (y > 0) {
            image(x,y-1) =  max(val, mask(x,y-1));
            f.push_back(x);
            f.push_back(y-1);
        }

        if (x < image.dimx() - 1) {
            image(x+1,y) =  max(val, mask(x+1,y));
            f.push_back(x+1);
            f.push_back(y); 
        }

        if (y < image.dimy() - 1) {
            image(x+1,y) =  max(val, mask(x,y+1));
            f.push_back(x);
            f.push_back(y+1);
        }

        if (true) {
            if (x > 0 && y > 0) {
                image(x-1,y-1) =  max(val, mask(x-1,y-1));
                f.push_back(x-1);
                f.push_back(y-1); 
            }

            if (x > 0 && y < image.dimy() - 1) {
                image(x-1,y+1) =  max(val, mask(x-1,y+1));
                f.push_back(x-1);
                f.push_back(y+1); 
            }

            if (x < image.dimx() - 1 && y > 0) {
               image(x+1,y-1) =  max(val, mask(x+1,y-1));
                f.push_back(x+1);
                f.push_back(y-1); 
            }

            if (x < image.dimx() - 1 && y < image.dimy() - 1) {
                image(x+1,y+1) =  max(val, mask(x+1,y+1));
                f.push_back(x+1);
                f.push_back(y+1); 
            }

        }
    }
    
#endif 