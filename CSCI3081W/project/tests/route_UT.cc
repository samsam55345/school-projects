#include "gtest/gtest.h"

#include <list>
#include <iostream>
#include <string>
#include <random>

#include "../src/bus.h"
#include "../src/passenger_generator.h"
#include "../src/data_structs.h"
#include "../src/passenger_loader.h"
#include "../src/passenger_unloader.h"
#include "../src/passenger.h"
#include "../src/stop.h"
#include "../src/route.h"
#include "../src/random_passenger_generator.h"


using namespace std;

//class RandomPassengerGenerator;

class RouteTests : public ::testing::Test {
protected:
  PassengerLoader* pass_loader;
  PassengerUnloader* pass_unloader;
  Passenger *passenger, *passenger1, *passenger2;
//  Stop *stop, *stop1, *stop2;
//  Stop ** stops,stops1;
  Route *route, *route1;
  list<double> CC_probs = {0.35,0.05,0.01,0.01,0.02,0.0};
  Stop *stop_WB_1, *stop_WB_2, *stop_WB_3, *stop_WB_4, *stop_WB_5, *stop_WB_6;
  Stop *stop, *stop1;
  Stop ** CC_WB_stops;
  double * distances;
  list<Stop *> stops_list;
  list<Stop*> stops1_list;
  Stop ** CC_stops;

  // stop_CC_WB_1 = new Stop(6, 47, -96); //st paul 2
  // stop_CC_WB_2 = new Stop(7, 46, -95); //st paul 1
  // stop_CC_WB_3 = new Stop(8, 45, -94); //before transit
  // stop_CC_WB_4 = new Stop(9, 44.973820, -93.227117); //Oak St & Washington Ave
  // stop_CC_WB_5 = new Stop(10); //student union station
  // stop_CC_WB_6 = new Stop(11, 43, -92.5); //West bank station
  // CC_WB_stops_list.push_back(&stop_CC_WB_1);
  // CC_WB_stops_list.push_back(&stop_CC_WB_2);
  // CC_WB_stops_list.push_back(&stop_CC_WB_3);
  // CC_WB_stops_list.push_back(&stop_CC_WB_4);
  // CC_WB_stops_list.push_back(&stop_CC_WB_5);
  // CC_WB_stops_list.push_back(&stop_CC_WB_6);
  // init = RandomPassengerGenerator(CC_WB_probs, CC_WB_stops_list);
  // RandomPassengerGenerator generator = init->GeneratePassengers();
  virtual void SetUp(){
    pass_loader = new PassengerLoader();
    pass_unloader = new PassengerUnloader();
    //stop = new Stop(1, 44.972392, -93.243774);//blegen
    //stop1 = new Stop(2, 44.973580, -93.235071);//coffman
    //stop2 = new Stop(3, 44.975392, -93.226632); //oak
    //list<Stop*> stops = {stop, stop1, stop2};
  //  list<Stop*> stops1 = {stop, stop1};
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
    stops_list = {CC_WB_stops[0],CC_WB_stops[1],CC_WB_stops[2],CC_WB_stops[3],CC_WB_stops[4], CC_WB_stops[5]};
    distances = new double[1] {10};
    stops1_list = {CC_stops[0], CC_stops[1]};
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

  }
};


TEST_F(RouteTests, Constructor){
    SetUp();
    Route * route = new Route("test route", CC_WB_stops, distances, 6, new RandomPassengerGenerator(CC_probs, stops_list));
    EXPECT_EQ(route->GetName(), "test route");
    //EXPECT_EQ(route->GetStops(), CC_WB_stops);
    EXPECT_EQ(route->GetTotalRouteDistance(), 10.0);
    EXPECT_EQ(route->GetStops().size(), 6);
    delete route;
    TearDown();
}

TEST_F(RouteTests, LastStop){
    SetUp();
    Route * route = new Route("test lstStop", CC_WB_stops, distances, 6, new RandomPassengerGenerator(CC_probs, stops_list));
    for(int i = 0; i< 6; i++){
        route->NextStop();
    }
    EXPECT_EQ(route->IsAtEnd(), true);
    delete route;
    TearDown();
}
