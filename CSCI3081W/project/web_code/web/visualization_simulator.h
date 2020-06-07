
#ifndef VISUALIZATION_SIMULATOR_H_
#define VISUALIZATION_SIMULATOR_H_

#include <vector>
#include <list>
#include <string>

#include "web_interface.h"
#include "src/IObserver.h"
#include "config_manager.h"
#include "src/bus_depot.h"
#include "src/util.h"
#include "src/file_writer_manager.h"

class Route;
class SmallBus;
class RegularBus;
class LargeBus;
class Bus;
class Stop;
class IObserver;
class Util;
// class BusWebObserver;

class VisualizationSimulator {
 public:
        VisualizationSimulator(WebInterface*, ConfigManager*);
        ~VisualizationSimulator();

        void Start(const std::vector<int>&, const int&);
        void Update();
        void Pause();
        void ClearListeners();
        void AddListener(std::string*, IObserver*);

 private:
        WebInterface* webInterface_;
        ConfigManager* configManager_;

        std::vector<int> busStartTimings_;
        std::vector<int> timeSinceLastBus_;
        int numTimeSteps_;
        int simulationTimeElapsed_;
        bool isPaused = false;

        std::vector<Route *> prototypeRoutes_;
        std::vector<Bus *> busses_;

        Strategy *strat_;
        int busId = 1000;
        std::string bus_file = "BusData.csv";
};

#endif // VISUALIZATION_SIMULATOR_H_
