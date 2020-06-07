/**
 * @file file_writer.cc
 *
 *
 * @copyright 2020 Sami Frank, All rights reserved.
*/

#include "src/file_writer.h"
#include <iostream>
#include <fstream>


void FileWriter :: Write(std::string s, std::vector<std::string> v ) {
    if (s.compare("PassData.csv")) {
        passData.open(s);
        for (std::vector<std::string>::iterator it = v.begin();
            it != v.end(); ++it) {
            if (passData.fail()) {
                std::cout << "Error writing to PassData.csv" << std::endl;
            }
            passData << *it << ",";
        }
        passData.close();
    } else if (s.compare("BusData.csv")) {
        busData.open(s);
        for (std::vector<std::string>::iterator it = v.begin();
            it != v.end(); ++it) {
            if (busData.fail()) {
                std::cout << "Error writing to BusData.csv" << std::endl;
            }
            busData << *it << ",";
        }
        busData.close();
    }
}
