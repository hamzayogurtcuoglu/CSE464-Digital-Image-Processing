
#ifndef PATHOPENING_HPP
#define PATHOPENING_HPP

#include <iostream>
#include <string>
#include <vector>
#include <queue>
#include <algorithm>
#include <iterator>
#include <cassert>

#include "image.hpp"
#include "sortImageValues.hpp"
#include "imageOperations.hpp"
#include "png.hpp"

void createNeighbourhood2D(int nb_col,
                           const std::vector<int> & orientation,
                           std::vector<int> & upList,
                           std::vector<int> & downList){


    int col_shift = orientation[0];
    int line_shift = orientation[1];

    if((line_shift == 1 && col_shift == 0) ||
      (line_shift == -1 && col_shift == 0)) {

       upList.push_back( - nb_col - 1);
       upList.push_back( - nb_col + 1);
       upList.push_back( - nb_col);

       downList.push_back( nb_col + 1);
       downList.push_back( nb_col - 1);
       downList.push_back( nb_col);
    }

    if((line_shift == 0 && col_shift == 1) ||
       (line_shift == 0 && col_shift == -1) ) {

       upList.push_back( -nb_col + 1);
       upList.push_back( nb_col + 1);
       upList.push_back( 1);

       downList.push_back( nb_col - 1);
       downList.push_back( -nb_col - 1);
       downList.push_back( -1);
    }

    if((line_shift == 1 && col_shift == 1) ||
      (line_shift == -1 && col_shift == -1)) {

           upList.push_back( -1);
           upList.push_back( nb_col);
           upList.push_back( nb_col - 1);

           downList.push_back( 1 );
           downList.push_back( -nb_col  );
           downList.push_back( -nb_col + 1);
   }

    if((line_shift == -1 && col_shift == 1) ||
      (line_shift == 1 && col_shift == -1)) {

           upList.push_back(1);
           upList.push_back( nb_col + 1);
           upList.push_back( nb_col);

           downList.push_back( -1);
           downList.push_back( -nb_col - 1);
           downList.push_back( -nb_col);
    }
}

void propagate(int32_t p, std::vector<int>& lambda, std::vector<int>& nf,
        std::vector<int>& nb, std::vector<bool>& b,std::queue<int32_t> & Qc){

    std::queue<int32_t> Qq;
    lambda[p] = 0;

    std::vector<int>::iterator it;
    for (it = nf.begin(); it != nf.end();++it)
    {
        if ((p+*it)<lambda.size() and b[p+*it])
        {
            Qq.push(p+*it);
        }
    }

    while (not Qq.empty())
    {
        int32_t q = Qq.front();
        Qq.pop();
        int l=0;
        for (it = nb.begin(); it != nb.end(); ++it)
        {
            l=std::max(lambda[q+*it],l);
        }
        l+=1;

        if (l < lambda[q])
        {
            lambda[q] = l;
            Qc.push(q);
            for (it = nf.begin(); it != nf.end(); ++it)
            {
                if (b[q+*it])
                {
                    Qq.push(q+*it);
                }
            }
        }
    }
}


void pathOpening(const Image<unsigned char> &dilatedImage,
           int minLength,
           std::vector<int32_t> &indexImage,
           std::vector<int> &orientations,
           Image<unsigned char> &outputWithoutBorder) {

    int dimY = dilatedImage.dimy();
    int dimX = dilatedImage.dimx();

    Image<unsigned char> outputWithBorder = dilatedImage;

    std::vector<bool>black(dilatedImage.image_size(),1);

    for (int y = 0 ; y < dimY ; ++y){
            black[y*dimX]=0;

    }

    for (int y = 0 ; y < dimY ; ++y){
            black[y*dimX+dimX-1]=0;
    }

    for (int x = 0 ; x < dimX ; ++x){
            black[x]=0;
    }

    for (int x = 0 ; x < dimX ; ++x){
            black[(dimY-1)*dimX+x]=0;
    }


    std::vector<int>np;
    std::vector<int>nm;
    createNeighbourhood2D(dimX, orientations, np, nm);

    std::vector<int>Lp(dilatedImage.image_size(),minLength);
    std::vector<int>Lm(dilatedImage.image_size(),minLength);


    std::queue<int32_t> Qc;

    std::vector<int32_t>::iterator it;
    int indice;
    for (it = indexImage.begin(), indice = 0 ; it != indexImage.end() ; ++it , ++indice)
    {
        if (black[*it])
        {
            propagate(*it, Lm, np, nm, black, Qc);
            propagate(*it, Lp, nm, np, black, Qc);

            while (not Qc.empty())
            {
                int32_t q = Qc.front();
                Qc.pop();
                if (Lp[q] + Lm[q]-1 < minLength)
                {
                    outputWithBorder.get_data()[q] =
                            outputWithBorder.get_data()[*it];
                    black[q] = 0;
                    Lp[q] = 0;
                    Lm[q] = 0;
                }
            }
        }
    }

    outputWithoutBorder = outputWithBorder.remove_border();
    return ;

}

#endif 

