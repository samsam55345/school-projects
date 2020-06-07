/**
 * @file IObservable
 *
 * @copyright 2020 sami frank
 */
#ifndef IOBSERVABLE_H_
#define IOBSERVABLE_H_

#include <vector>
#include "src/IObserver.h"
class IObserver;

class IObservable{
 public:
    // IObservable(IObserver &observe);
    void RegisterObserver(IObserver*);
    void NotifyObservers(BusData*);
    // void NotifyObservers(IObserver*);
    void ClearObservers();
    std::vector<IObserver*> observer_;
 // private:
};

#endif // IOBSERVABLE_H_
