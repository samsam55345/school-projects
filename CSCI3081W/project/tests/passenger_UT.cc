/*
 * Students: Use this file as a guide to writing your own unit tests.
 * See the project instructions for more information on which classes
 * and methods must be tested.
 */

/*******************************************************************************
 * Includes
 ******************************************************************************/
#include <gtest/gtest.h>

#include <iostream>
#include <string>
#include <list>
// #include <string>

#include "../src/passenger_loader.h"
#include "../src/passenger_unloader.h"
#include "../src/passenger.h"
#include "../src/stop.h"

using namespace std;

/******************************************************
* TEST FEATURE SetUp
*******************************************************/
class PassengerTests : public ::testing::Test {
protected:
  PassengerLoader* pass_loader;
  PassengerUnloader* pass_unloader;
  Passenger *passenger, *passenger1, *passenger2;

  virtual void SetUp(){
    pass_loader = new PassengerLoader();
    pass_unloader = new PassengerUnloader();
  }

  virtual void TearDown() {
    delete pass_loader;
    delete pass_unloader;
    // delete passenger;
    // delete passenger1;
    // delete passenger2;

    // passenger = NULL;
    // passenger1 = NULL;
    // passenger2 = NULL;
    pass_loader = NULL;
    pass_unloader = NULL;
  }
};


/*******************************************************************************
 * Test Cases
 ******************************************************************************/
TEST_F(PassengerTests, Constructor) {
  passenger = new Passenger();
  EXPECT_EQ(passenger->IsOnBus(), false);
  passenger->GetOnBus();
  EXPECT_EQ(passenger->IsOnBus(), true);
  delete passenger;
}

TEST_F(PassengerTests, TimeOnBus) {
    passenger = new Passenger(12, "John Doe");
    passenger->GetOnBus();
    for (int i = 0; i < 5; i++) {
        passenger->Update();
    }
    EXPECT_EQ(passenger->GetTotalWait(), 6);
    delete passenger;
}

// tests report method name_
TEST_F(PassengerTests, ReportCheck) {
    passenger = new Passenger(12, "John Doe");
    passenger1 = new Passenger(11, "Apple Bee");
    string expected_out_1("Name: John Doe");
    string expected_out_2("Name: Apple Bee");
    testing::internal::CaptureStdout();
    passenger->Report(std::cout);
    std::string output = testing::internal::GetCapturedStdout();

    testing::internal::CaptureStdout();
    passenger1->Report(std::cout);
    std::string output1 = testing::internal::GetCapturedStdout();

    int p1 = output.find(expected_out_1);
    int p2 = output1.find(expected_out_2);

    // greater than or equal to
    EXPECT_GE(p1, 0);
    EXPECT_GE(p2, 0);

    std::string expected_out_3 = "Destination: 12";
    std::string expected_out_4 = "Destination: 11";

    int p3 = output.find(expected_out_3);
    int p4 = output1.find(expected_out_4);

    EXPECT_GE(p3, 0);
    EXPECT_GE(p4, 0);
    delete passenger;
    delete passenger1;
}

// tests GetDestination method
TEST_F(PassengerTests, DestinationCheck){
    passenger = new Passenger(1, "sami frank");
    int expected_dest_id = 1;
    EXPECT_EQ(passenger->GetDestination(), expected_dest_id);
    delete passenger;
}
;
