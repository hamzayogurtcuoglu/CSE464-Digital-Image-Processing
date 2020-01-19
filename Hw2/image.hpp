
#ifndef IMAGE_HPP
#define IMAGE_HPP

#include <stdlib.h>
#include <iostream>
#include <vector>


template<typename T>
class Image {
    private:
        int m_nDimx;
        int m_nDimy;
        int m_nImageSize;
    std::vector<T>m_vImage;
    public :

        Image(int dimX, int dimY, int value=0):
             m_nDimx(dimX), m_nDimy(dimY), m_nImageSize(dimX*dimY), m_vImage(dimX*dimY, value){}

        Image(Image const& copy):
            m_nDimx(copy.m_nDimx), m_nDimy(copy.m_nDimy), m_nImageSize(copy.m_nImageSize), m_vImage(copy.m_vImage){}

        T& operator ()(int x, int y) {
            return m_vImage[x + y * m_nDimx];
        }
        const T& operator ()(int x, int y) const {
            return m_vImage[x + y * m_nDimx];
        }

        T& operator ()(int i) {
            return m_vImage[i];
        }
        const T& operator ()(int i) const {
            return m_vImage[i];
        }

        int dimx() const {
            return m_nDimx;
        }

        int dimy() const {
            return m_nDimy;
        }

        const int image_size() const {
            return m_nImageSize;
        }

        std::vector<T>& get_data() {
            return m_vImage;
        }
        const std::vector<T>& get_data() const {
            return m_vImage;
        }

        T* get_pointer(){
            return m_vImage.data();
        }

        // 0 pixels are added border of image.
        Image<T> addBorder() const {

            Image<T> imageWithBorder(m_nDimx+2, m_nDimy+2);
            for (int y = 1 ; y < m_nDimy + 1 ; ++y){
                for (int x = 1 ; x < m_nDimx + 1 ; ++x){
                    imageWithBorder(x,y) = m_vImage[x-1 + (y-1) * m_nDimx];
                }
            }
            return imageWithBorder;
        }

        // Added pixels are removed.
        Image<T> remove_border() const {

            Image<T> imageWithoutBorder(m_nDimx-2, m_nDimy-2);
            for (int y = 1 ; y < m_nDimy - 1 ; ++y){
                for (int x = 1 ; x < m_nDimx - 1 ; ++x){
                    imageWithoutBorder(x-1,y-1) = m_vImage[x + y * m_nDimx];
                }
            }
            return imageWithoutBorder;

        }
};
#endif 