/**
 * @file visualization_simulator.h
 *
 * @copyright 2019 3081 Staff, All rights reserved.
 */
#include "web_code/web/visualization_simulator.h"

#include "src/bus.h"
#include "src/bus_depot.h"
#include "src/bus_factory.h"
#include "src/small_bus.h"
#include "src/regular_bus.h"
#include "src/large_bus.h"
#include "src/route.h"


VisualizationSimulator::VisualizationSimulator(WebInterface* webI,
                                                ConfigManager* configM) {
    webInterface_ = webI;
    configManager_ = configM;
}

VisualizationSimulator::~VisualizationSimulator() {
}

void VisualizationSimulator::Start(const std::vector<int>& busStartTimings,
                                    const int& numTimeSteps) {
    busStartTimings_ = busStartTimings;
    numTimeSteps_ = numTimeSteps;

    timeSinceLastBus_.resize(busStartTimings_.size());
    for (int i = 0; i < static_cast<int>(timeSinceLastBus_.size()); i++) {
        timeSinceLastBus_[i] = 0;
    }

    simulationTimeElapsed_ = 0;

    prototypeRoutes_ = configManager_->GetRoutes();
    for (int i = 0; i < static_cast<int>(prototypeRoutes_.size()); i++) {
        prototypeRoutes_[i]->Report(std::cout);

        prototypeRoutes_[i]->UpdateRouteData();
        webInterface_->UpdateRoute(prototypeRoutes_[i]->GetRouteData());
    }
}

// used to set a variable to be looked at in update.
// simulator should not update when isPaused == true.
void VisualizationSimulator::Pause() {
    if (isPaused == true) {
        isPaused = false;
    } else {
        isPaused = true;
    }
}

void VisualizationSimulator::Update() {
    if (isPaused == false) {
        simulationTimeElapsed_++;

        std::cout << "~~~~~~~~~~ The time is now " << simulationTimeElapsed_;
        std::cout << "~~~~~~~~~~" << std::endl;

        std::cout << "~~~~~~~~~~ Generating new busses if needed ";
        std::cout << "~~~~~~~~~~" << std::endl;

        // Check if we need to generate new busses
        /*
        IMLIMENT BUS FACTORY HERE!!!!!!
        UPDATES TO USE BUS DEPOT
        */
        // creates an instance of strategy
        Strategy *busDepot = new Strategy();
        Strategy1 * strat1 = NULL;
        Strategy2 * strat2 = NULL;
        Strategy3 * strat3 = NULL;
        Strategy4 * strat4 = NULL;
        // sets to certain strategy based on current time
        busDepot->setStrategy(busDepot->currentTime());
        int busSize;
        if (busDepot->getStrategy() == 0) {
            // strategy1
            strat1 = new Strategy1();
        } else if (busDepot->getStrategy() == 1) {
            // strategy2
            strat2 = new Strategy2();
        } else if (busDepot->getStrategy() == 2) {
            // strategy3
            strat3 = new Strategy3();
        } else {
            // strategy4
            strat4 = new Strategy4();
        }


        for (int i = 0; i < static_cast<int>(timeSinceLastBus_.size()); i++) {
            // Check if we need to make a new bus
            if (0 >= timeSinceLastBus_[i]) {
                Route * outbound = prototypeRoutes_[2 * i];
                Route * inbound = prototypeRoutes_[2 * i + 1];

                if (strat1 != NULL) {
                    strat1->setBusSize();
                    busSize = strat1->getBusSize();
                } else if (strat2 != NULL) {
                    strat2->setBusSize();
                    busSize = strat2->getBusSize();
                } else if (strat3 != NULL) {
                    strat3->setBusSize();
                    busSize = strat3->getBusSize();
                } else {
                    strat4->setBusSize();
                    busSize = strat4->getBusSize();
                }

                // added busSize to BusFactory::Generate which allowed me
                // to keep the brunt of my code, but just check what
                // bus to Generate based on a parameter instead of randomly.
                busses_.push_back(BusFactory::Generate(std::to_string(busId),
                        outbound->Clone(), inbound->Clone(), busSize));
                busId++;

                timeSinceLastBus_[i] = busStartTimings_[i];
            } else {
                timeSinceLastBus_[i]--;
            }
        }

        std::cout << "~~~~~~~~~ Updating busses ";
        std::cout << "~~~~~~~~~" << std::endl;

        // Update busses
        double total_pass = 0;
        for (int i = static_cast<int>(busses_.size()) - 1; i >= 0; i--) {
            busses_[i]->Update();

            if (busses_[i]->IsTripComplete()) {
                webInterface_->UpdateBus(busses_[i]->GetBusData(), true);
                // sets up the use of being able to add to BusData.csv
                Util util;
                FileWriterManager fwm;
                FileWriter* fw;
                fw = fwm.getInstance();

                std::vector<std::string> v = util.processOutput(std::cout);
                fw->Write(bus_file, v);
                busses_.erase(busses_.begin() + i);
                continue;
            }

            webInterface_->UpdateBus(busses_[i]->GetBusData());

            busses_[i]->Report(std::cout);
            total_pass += (double) busses_[i]->total_passenger;
        }

        std::cout << "~~~~~~~~~ Updating routes ";
        std::cout << "~~~~~~~~~" << std::endl;
        // Update routes
        for (int i = 0; i < static_cast<int>(prototypeRoutes_.size()); i++) {
            prototypeRoutes_[i]->Update();

            webInterface_->UpdateRoute(prototypeRoutes_[i]->GetRouteData());

            prototypeRoutes_[i]->Report(std::cout);
          }
      }
}
void VisualizationSimulator::ClearListeners() {
    if (isPaused == false) {
        for (std::vector<Bus *>::iterator iter = busses_.begin();
                iter != busses_.end(); iter++) {
                    (*iter) -> ClearObservers();
        }
    }
}

void VisualizationSimulator::AddListener(std::string* id, IObserver* observer) {
    for (std::vector<Bus *>::iterator iter = busses_.begin();
            iter != busses_.end(); iter++) {
            if (((*iter) -> GetName()) ==  *id) {
                (*iter) -> RegisterObserver(observer);
            }
    }
}
