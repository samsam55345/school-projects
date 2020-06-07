#include "point2.h"
#include <cmath>
#include <iostream>
#include <cstdlib>
#include <string>

float Point2::DistanceBetween(Point2 obj){
  float dist_between;

  dist_between = pow((obj.x - this-> x ), 2.0) + pow((obj.y - this-> y), 2);
  dist_between = sqrt(dist_between);
  return dist_between;

}

int Point2::Quadrant(){
  float pointX, pointY;
  pointX = this-> x;
  pointY = this-> y;

  if(pointX > 0 && pointY > 0){
    return 1;
  }
  else if(pointX < 0 && pointY > 0){
    return 2;
  }
  else if(pointX < 0 && pointY < 0){
    return 3;
  }
  else if(pointX > 0 && pointY < 0){
    return 4;
  }
  else{
    return -1;
  }
}

void Point2::Print(){
  std::cout << "[" << (this-> x) << ", " << (this-> y) << "]";

}
