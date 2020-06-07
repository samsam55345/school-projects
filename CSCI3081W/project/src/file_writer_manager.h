/**
* @file file_writer_manager.h
*
* @copyright 2020 Sami Frank, All rights reserved.
* SINGLETON
*/

#ifndef FILE_WRITER_MANAGER_H_
#define FILE_WRITER_MANAGER_H_

#include "src/file_writer.h"
class FileWriter;

class FileWriterManager{
 private:
    FileWriter *file_writer = NULL;
 public:
    FileWriter* getInstance() {
        if (!file_writer) {
            file_writer = new FileWriter();
        }
        return file_writer;}
};



#endif //  FILE_WRITER_MANAGER_H_
