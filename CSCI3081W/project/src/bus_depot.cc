/**
 * @file bus_depot.cc
 *
 * @copyright 2020 sami frank
 */
#include "src/bus_depot.h"

// gets current time to help decide which strategy to use
long int Strategy::currentTime() {
    const std::time_t now = std::time(nullptr);  // gets current time point
    // converts to local calendar_time
    const std::tm calendar_time =
                *std::localtime(std::addressof(now));
    return calendar_time.tm_hour;
}

// uses current time to decide which strategy to use
void Strategy::setStrategy(long int time) {
    long int ctime = static_cast<long int>(time);
    if (ctime >= 6 && ctime < 8) {
        strategy_ =  strategy1;  // strategy1 is an enum
    } else if (ctime >= 8 && ctime < 15) {
        strategy_ =  strategy2;  // strategy 2 is an enum
    } else if (ctime >= 15 && ctime < 20) {
        strategy_ =  strategy3;  // strategy 3 is an enum
    } else {
        strategy_ =  strategy4;  // is an enum
    }
}

// returns the enum position of strategyType
int Strategy::getStrategy() {
    return strategy_;
}

// cycles through bus sizes
static bool isSmall = true;
void Strategy1::setBusSize() {
    if (isSmall == true) {
        // size_ is an enum of BusType
        size_ = small;
        isSmall = false;
    } else {
        // send regular bus
        size_ = regular;
        isSmall = true;
    }
}
// returns enum position from enum BusType
int Strategy1::getBusSize() {
    return size_;
}
// cycles through bus sizes
static bool isReg = true;
void Strategy2::setBusSize() {
    if (isReg == true) {
        // send reg bus
        size_ = regular;
        isReg = false;
    } else {
        // send large bus
        size_ = large;
        isReg = true;
    }
}
// returns enum position from enum BusType
int Strategy2::getBusSize() {
    return size_;
}
// cycles through bus sizes
static int count = 0;
void Strategy3::setBusSize() {
    if (count == 0) {
        // send small
        size_ = small;
        count++;
    } else if (count == 1) {
        // send reg
        size_ = regular;
        count++;
    } else if (count == 2) {
        // send large
        size_ = large;
        count = 0;
    }
}
// returns enum position from enum BusType
int Strategy3::getBusSize() {
    return size_;
}
// sets bus size
void Strategy4::setBusSize() {
    // send small
    size_ = small;
}
// returns enum position from enum BusType
int Strategy4::getBusSize() {
    return size_;
}
