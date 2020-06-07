#ifndef Point2

class Point2{
  float x,y;
public:
  Point2(){
    x = 0;
    y = 0;
  }
  Point2(float x1, float y1){
    x = x1;
    y = y1;
  }
  float DistanceBetween(Point2 obj);
  int Quadrant();
  void Print();

};

#endif
