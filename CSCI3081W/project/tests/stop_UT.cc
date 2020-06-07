#include <gtest/gtest.h>

#include <iostream>
#include <string>
#include <vector>
#include <list>

#include "../src/passenger_loader.h"
#include "../src/passenger_unloader.h"
#include "../src/bus.h"
#include "../src/passenger.h"
#include "../src/stop.h"

using namespace std;

/******************************************************
* TEST FEATURE SetUp
*******************************************************/
class StopTests : public ::testing::Test {
protected:
  Stop *stop, *stop1;

  virtual void TearDown() {
    delete stop;
    delete stop1;

    stop = NULL;
    stop1 = NULL;

  }
};


/*******************************************************************************
 * Test Cases
 ******************************************************************************/
TEST_F(StopTests, Constructor) {
  stop = new Stop(5); //default is westbound coffman union stop
  stop1 = new Stop(1, 5.0, 5.0);

  // default long,lat  in stop.h
  EXPECT_EQ(stop->GetLongitude(), 44.973723);
  EXPECT_EQ(stop->GetLatitude(), -93.235365);
  EXPECT_EQ(stop1->GetLongitude(), 5.0);
  EXPECT_EQ(stop1->GetLatitude(), 5.0);
  EXPECT_EQ(stop1->GetId(), 1);
  TearDown();
}
