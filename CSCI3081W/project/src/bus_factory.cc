/**
* @file bus_factory.cc

*
* @copyright 2019 3081 Staff and Sami Frank, All rights reserved.
*/

#include <random>
#include <vector>
#include <string>
#include "src/bus_factory.h"

/*
NOTE: Bus::Bus(std::string name, Route * out, Route * in,
                         int capacity, double speed) {
*/

/*******************************************************************************
 * Member Functions
 ******************************************************************************/
/*
Generate method will produce a random int between 1-3.
1 associates with generating a small sized bus
2 associates with generating a regular sized bus
3 associates with generating a large sized bus

*/
Bus * BusFactory::Generate(std::string id, Route* out,
     Route* in, int busSize) {
  // std::random_device dev2;
  // std::mt19937 rng(dev2());
  // std::uniform_int_distribution<std::mt19937::result_type> dist1(1, 3);
  // int rand_int = dist1(rng);


  // busSize is an enum
  // small = 0
  // regular = 1
  // large = 2
  if (busSize == 0) {
    // small bus capacity
    // capacity = 30;
    return new SmallBus(id, out, in);
} else if (busSize == 1) {
    // regular bus capacity
    // capacity = 60;
    return new RegularBus(id, out, in);
} else if (busSize == 2) {
    // large bus capacity
    // capacity = 90;
    return new LargeBus(id, out, in);
  }
}
