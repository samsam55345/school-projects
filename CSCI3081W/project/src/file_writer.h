/**
* @file file_writer.h
*
* @copyright 2020 Sami Frank, All rights reserved.
*/

#ifndef FILE_WRITER_H_
#define FILE_WRITER_H_

#include <string>
#include <vector>
#include <iostream>
#include <fstream>

class FileWriter {
 public:
  void Write(std::string, std::vector<std::string>);
 private:
  std::ofstream passData;
  std::ofstream busData;
};
#endif //  FILE_WRITER_H
