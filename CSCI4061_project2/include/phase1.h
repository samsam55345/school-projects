// Add Guard to header file (google what are guards)
// Function prototypes to
//			Traverse the Folder
//			Partition the text file paths to 'm' files
#ifndef PHASE1_H
#define PHASE1_H

#include <stdio.h>
#include <stdlib.h>

int phase1(char*, int);
void traverse(char*, FILE*);
void parition(int, FILE*);
void count_files(char*);

#endif
