/**
 * @file IObserver
 *
 * @copyright 2020 sami frank
 */
#ifndef IOBSERVER_H_
#define IOBSERVER_H_

#include <iostream>

#include "src/data_structs.h"
// #include "src/bus.h"

class Bus;
class IObserver{
 public:
    virtual void Notify(BusData *);
};

#endif // IOBSERVER_H_
