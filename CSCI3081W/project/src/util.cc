/**
 * @file util.cc
 *
 * @copyright 2020 Sami Frank, All rights reserved.
*/

#include "src/util.h"
#include <string>
#include <algorithm>
#include <iterator>
#include <vector>


std::vector<std::string> Util :: processOutput(std::ostream& s) {
    std::stringstream foo;
    std::string str;
    foo << s.rdbuf();
    str = foo.str();
    std::vector<std::string> results;
    std::string::size_type beg = 0;
    char delimiter = ' ';

    for (auto end = 0; (end = str.find(delimiter, end)) != std::string::npos;
        end++) {
        results.push_back(str.substr(beg, end-beg));
        beg = end + 1;
    }
    results.push_back(str.substr(beg));

    return results;
}
