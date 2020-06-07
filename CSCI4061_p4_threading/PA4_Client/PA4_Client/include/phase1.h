#ifndef PHASE1_H
#define PHASE1_H

#include <stdio.h>
#include <unistd.h>
#include <dirent.h>
#include <string.h>
#include <stdlib.h>
#include <sys/stat.h>

#define DIRNULL NULL
#define FILENULL NULL

int phase_one(char* filename, int numMappers);
void partition(int numMappers, FILE * mappers[numMappers]);
void traverse(char* filename, FILE * fptr);

#endif
