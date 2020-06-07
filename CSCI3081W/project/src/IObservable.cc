/**
 * @file IObservable.cc
 *
 *
 * @copyright 2020 Sami Frank, All rights reserved.
 */
#include "src/IObservable.h"
// #include <vector>
#include <string>

void IObservable::RegisterObserver(IObserver* observer) {
    observer_.push_back(observer);
}
void IObservable::NotifyObservers(BusData *bd) {
    for (auto* obs : observer_) {
        obs->Notify(bd);
    }
}
void IObservable::ClearObservers() {
    observer_.clear();
}
