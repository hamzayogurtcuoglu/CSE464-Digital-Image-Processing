
#ifndef SORT_HPP
#define SORT_HPP

struct sort_indice
{
    const std::vector<unsigned char>& vec;

    sort_indice(const std::vector<unsigned char>& vec): vec(vec) {}

    bool operator()(int a, int b) const { return vec[a] < vec[b]; }
};


void sort_argsort_images(Image<unsigned char> &image1,
                                 Image<unsigned char> &image2,
                                 Image<unsigned char> &image3,
                                 Image<unsigned char> &image4,
                                 Image<int> &indice1,
                                 Image<int> &indice2,
                                 Image<int> &indice3,
                                 Image<int> &indice4){

    for (int i = 0 ; i < image1.image_size() ; i++) {  // For each pixel of an image

        // Store the 4 intensities at the same position in the 4 images in vec
        std::vector<unsigned char> vec(4);
        vec[0] = image1(i);
        vec[1] = image2(i);
        vec[2] = image3(i);
        vec[3] = image4(i);


        // Store the initial image indice
        std::vector<unsigned char> indice(vec.size());
        for (int j = 0; j < vec.size() ; ++j){
            indice[j] = (unsigned char) j;
        }

        // Sort the 4 indices according to their image value
        std::sort(  indice.begin(), indice.end(), sort_indice(vec));
        indice1(i) = indice[0]; // indice of the min value of vec
        indice2(i) = indice[1];
        indice3(i) = indice[2];
        indice4(i) = indice[3]; // indice of the max value of vec

        
        image1(i) = vec[indice[0]]; // min value
        image2(i) = vec[indice[1]];
        image3(i) = vec[indice[2]];
        image4(i) = vec[indice[3]]; // max value
    }
}

bool my_sorting_function (const unsigned char *i,const unsigned char *j){
        return (*i<*j);
}

std::vector<int32_t> sortImageValues(unsigned char *image, int size)
{
    std::vector<int32_t> indexImage(size);
    std::vector<unsigned char*>indexPointerAdress(size);
    int32_t it;
    typename std::vector<unsigned char*>::iterator it2;
    typename std::vector<int32_t>::iterator it3;

    // Fill indexPointerAdress with memory adress of variables in image
    for (it=0,it2=indexPointerAdress.begin(); it!=size; ++it, ++it2)
    {
            *it2=&image[it];
    }

    // Sorting adresses according to intensity
    std::sort(indexPointerAdress.begin(),indexPointerAdress.end(),
              my_sorting_function);

    // Conversion from adresses to index of image
    for (it3=indexImage.begin(),it=0; it!=size; ++it,++it3)
    {
            *it3=static_cast<int32_t>(indexPointerAdress[it]-&image[0]);
    }
    
    
    return indexImage;
}


#endif 

