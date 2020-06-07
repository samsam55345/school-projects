#include "gtest/gtest.h"

#include <list>
#include <iostream>
#include <string>


#include "../src/data_structs.h"

#include "../src/passenger.h"
#include "../src/passenger_loader.h"
#include "../src/passenger_unloader.h"
#include "../src/route.h"
#include "../src/random_passenger_generator.h"
#include "../src/stop.h"



using namespace std;

//SetUp
class BusTests : public ::testing::Test {
public:
  // PassengerGenerator *generator;
  PassengerLoader* pass_loader;
  PassengerUnloader* pass_unloader;
  Passenger *passenger, *passenger1, *passenger2;
  Route *route,*route1;

  list<double> CC_probs = {0.35,0.05,0.01,0.01,0.02,0.0};
  Stop *stop_WB_1, *stop_WB_2, *stop_WB_3, *stop_WB_4, *stop_WB_5, *stop_WB_6;
  //Stop stop_WB_1, stop_WB_2, stop_WB_3, stop_WB_4, stop_WB_5, stop_WB_6;

  Stop *stop, *stop1;
  Stop ** CC_WB_stops;
  Stop ** CC_stops;
  double * distances;


  virtual void SetUp(){
    pass_loader = new PassengerLoader();
    pass_unloader = new PassengerUnloader();


    Stop stop(1, 44.972392, -93.243774);//blegen
    Stop stop1(2, 44.973580, -93.235071);//coffman
    Stop stop_WB_1(6, 47, -96); //st paul 2
    Stop stop_WB_2(7, 46, -95); //st paul 1
    Stop stop_WB_3(8, 45, -94); //before transit
    Stop stop_WB_4(9, 44.973820, -93.227117); //Oak St & Washington Ave
    Stop stop_WB_5(10); //student union station
    Stop stop_WB_6(11, 43, -92.5); //West bank station


    CC_WB_stops = new Stop *[6] {&stop_WB_1,&stop_WB_2,&stop_WB_3,&stop_WB_4,&stop_WB_5,&stop_WB_6};
    CC_stops = new Stop *[2] {&stop,&stop1};

    list<Stop *> stops_list = {CC_WB_stops[0],CC_WB_stops[1],CC_WB_stops[2],CC_WB_stops[3],CC_WB_stops[4], CC_WB_stops[5]};
    distances = new double[1] {10};

    // stop2 = new Stop(3, 44.975392, -93.226632); //oak
    // list<Stop*> stops = {stop, stop1, stop2};
    list<Stop*> stops1_list = {CC_stops[0], CC_stops[1]};
    route = new Route("test route", CC_WB_stops, distances, 6, new RandomPassengerGenerator(CC_probs, stops_list));
    route1 = new Route("test route 1", CC_stops, distances, 2, new RandomPassengerGenerator(CC_probs, stops1_list));

  }

  virtual void TearDown() {
    delete pass_loader;
    delete pass_unloader;
    delete stop;
    delete stop1;
    delete stop_WB_1;
    delete stop_WB_2;
    delete stop_WB_3;
    delete stop_WB_4;
    delete stop_WB_5;
    delete stop_WB_6;

    stop = NULL;
    stop1 = NULL;
    pass_loader = NULL;
    pass_unloader = NULL;
    stop_WB_1 = NULL;
    stop_WB_2 = NULL;
    stop_WB_3 = NULL;
    stop_WB_4 = NULL;
    stop_WB_5 = NULL;
    stop_WB_6 = NULL;
    delete route;
    delete route1;
    route = NULL;
    route1 = NULL;

    for(int i = 0; i < 6; i++){
      delete CC_WB_stops[i];
    }
    delete[] CC_WB_stops;
    CC_WB_stops = NULL;

    for(int i = 0; i < 2; i++){
      delete CC_stops[i];
    }
    delete[] CC_stops;
    CC_stops = NULL;
    delete distances;
    distances = NULL;


  }
};

TEST_F(BusTests, Constructor){
    SetUp();

    Bus * bus = new Bus("testConstructor", route, route1, 55, 2.0);
    Bus * bus1 = new Bus("testConstructor1", route, route1);

    EXPECT_EQ(bus->GetName() , "testConstructor");
    //cout << "made it" << endl;
    EXPECT_EQ(bus1->GetName(), "testConstructor1");
    //cout << "made it" << endl;
    EXPECT_EQ(bus->GetCapacity() , 55);
    //cout << "made it" << endl;

    EXPECT_EQ(bus1->GetCapacity() , 60);

    //cout << "made it" << endl;

    delete bus;
    //cout << "made it" << endl;

    delete bus1;
    bus = NULL;
    bus1 = NULL;
    //cout << "made it1" << endl;

    TearDown();
    //cout << "made it2" << endl;

}
TEST_F(BusTests, TripComplete){
    SetUp();
    Bus * bus = new Bus("testIsComplete", route, route1, 55, 2.0);
    Bus * bus1 = new Bus("test1", route, route1);

    cout << "made trip complete" << endl;
    for(int i = 0; i < 6; i++){
        bus->Update();
    }
    bool exp_val = true;
    EXPECT_EQ(bus->IsTripComplete(), exp_val);

    for(int i = 0; i < 2; i++){
        bus1->Update();
    }
    bool exp_val1 = false;
    EXPECT_EQ(bus1->IsTripComplete(), exp_val1);

    delete bus;
    delete bus1;
    bus = NULL;
    bus1 = NULL;

    TearDown();
}

TEST_F(BusTests, Move){
    SetUp();
    Bus * bus = new Bus("testMove", route, route1, 55, 2.0);
    bool exp = true;
    bool ret = bus->Move();
    EXPECT_EQ(ret, exp);
    delete bus;
    bus = NULL;
    TearDown();

}
