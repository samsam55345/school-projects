/**
 * @file regular_bus.cc
 *
 *
 * @copyright 2020 Sami Frank, All rights reserved.
 */

#include "src/regular_bus.h"

// creates a bus of size 60
RegularBus::RegularBus(std::string name, Route * out, Route * in,
                         int capacity, double speed) : Bus(name, out, in) {
  name_ = name;
  outgoing_route_ = out;
  incoming_route_ = in;
  passenger_max_capacity_ = 60;
  speed_ = speed;
  distance_remaining_ = 0;
  next_stop_ = out->GetDestinationStop();
  unloader_ = new PassengerUnloader;
  loader_ = new PassengerLoader;
}
// reports bus data when called
void RegularBus::Report(std::ostream& out) {
  out << "Name: " << name_ << std::endl;
  out << "Total passengers: " << total_passenger << std::endl;
  out << "Speed: " << speed_ << std::endl;
  out << "Bus Type: regular" << std::endl;
  out << "Max Capacity: " << passenger_max_capacity_ << std::endl;
  out << "Distance to next stop: " << distance_remaining_ << std::endl;
  out << "\tPassengers (" << passengers_.size() << "): " << std::endl;
  for (std::list<Passenger *>::iterator it = passengers_.begin();
                                        it != passengers_.end(); it++) {
    (*it)->Report(out);
  }
}
