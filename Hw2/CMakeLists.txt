
cmake_minimum_required(VERSION 3.0)
project(171044086_Hamza_YOĞURTCUOĞLU)

# CMake can set the standard in a cross-platform way for you
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_BUILD_TYPE Debug)

set(SOURCE_FILES main.cpp )
add_executable(exe ${SOURCE_FILES})

target_link_libraries(exe "-std=c++98" "-lpng")