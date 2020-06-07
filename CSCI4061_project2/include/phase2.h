// Add Guard to header file
// Function prototypes for
//			map function that will go through text files in the Mapper_i.txt to get the (letter, wordcount)
#ifndef PHASE2_H
#define PHASE2_H

#include <stdio.h>
#include <stdlib.h>

int *phase2(int* alph_index, char* mapper_i);
int * count_letters(char * filepath, int * alph_index, int ch);

#endif
