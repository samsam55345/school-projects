/**
 * @file util.h
 *
 * @copyright 2020 Sami Frank, All rights reserved.
*/

#ifndef UTIL_H_
#define UTIL_H_
#include <vector>
#include <string>
#include <iostream>
#include <sstream>

class Util {
 public:
    std::vector<std::string> processOutput(std::ostream&);
};


#endif //  UTIL_H_
