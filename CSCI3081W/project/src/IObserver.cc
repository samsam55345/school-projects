/**
 * @file IObserver.cc
 *
 *
 * @copyright 2020 Sami Frank, All rights reserved.
 */
#include "src/IObserver.h"
#include <vector>
#include <string>

void IObserver::Notify(BusData * info){
    std::cout << "Bus " << info->id << "\n";
    std::cout << "-----------------------------\n";
    std::cout << "  * Position: (" << info->position.x << "," << info->position.y << ")\n";
    std::cout << "  * Passengers: " << info->num_passengers << "\n";
    std::cout << "  * Capacity: " << info->capacity << "\n";
}
