/**
 * @file bus_depot.h
 *
 * @copyright 2020 sami frank
 */
#ifndef BUS_DEPOT_H_
#define BUS_DEPOT_H_

#include <iostream>
#include <ctime>

class Strategy;
class Strategy1;
class Strategy2;
class Strategy3;
class Strategy4;

class Strategy {
public:
    // virtual ~Strategy(){};
    static long int currentTime();
    void setStrategy(long int);
    int getStrategy();
    enum strategyType {strategy1, strategy2, strategy3, strategy4}strategy_;
    enum BusType {small, regular, large};
    // strategyType strategy_;
    BusType size_;
};
class Strategy1 : public Strategy {
public:
    Strategy1() {size_ = (BusType) small;}
    void setBusSize();
    int getBusSize();
};
class Strategy2 : public Strategy {
public:
    Strategy2() {size_ = (BusType) regular;}
    void setBusSize();
    int getBusSize();
};
class Strategy3 : public Strategy {
public:
    Strategy3() {size_ = (BusType) regular;}
    void setBusSize();
    int getBusSize();
};
class Strategy4 : public Strategy {
public:
    Strategy4() {size_ = (BusType) small;}
    void setBusSize();
    int getBusSize();
};

#endif // BUS_DEPOT_H_
