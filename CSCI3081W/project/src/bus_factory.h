/**
 * @file bus_factory.h
 *
 * @copyright 2020 Sami Frank, All rights reserved.
 */
#ifndef SRC_BUS_FACTORY_H_
#define SRC_BUS_FACTORY_H_

// #include <random>
// #include <vector>
#include <string>
#include "src/bus.h"
#include "src/small_bus.h"
#include "src/regular_bus.h"
#include "src/large_bus.h"
#include "src/bus_depot.h"

class BusFactory : Strategy {
 public:
    static Bus * Generate(std::string, Route*, Route*, int);
};
#endif  // SRC_BUS_FACTORY_H_
