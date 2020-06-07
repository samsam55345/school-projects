#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <unistd.h>
#include <fcntl.h>
#include <dirent.h>
#include <string.h>
#include <time.h>
#include "../include/phase2.h"

// You are free to use your own logic. The following points are just for getting started
/* 	Map Function
	1)	Each mapper selects a Mapper_i.txt to work with
	2)	Creates a list of letter, wordcount from the text files found in the Mapper_i.txt
	3)	Send the list to Reducer via pipes with proper closing of ends
*/

/*given a file name and alph_index which is an int array holding 0-25 corresponding to 
each letter in the alphabet and an int ch to correspond to the ascii characters in a line.
this will count each letter of the first word*/
int * count_letters(char * filepath, int * alph_index, int ch) {
  FILE * fptr = fopen(filepath, "r");
  char * line = NULL;
  size_t len = 0;
  ssize_t read;
  //need to exit gracefully from function
  if(fptr == NULL){
    exit(-1);
  }
  while((read = getline(&line, &len, fptr)) != -1) {
    ch = line[0];
    if(ch == EOF) {
      break;
    }
    if('a' <= ch && ch <= 'z') {
      alph_index[ch-'a']++;
    }
    else if ('A' <= ch && ch <= 'Z') {
      alph_index[ch-'A']++;
    }
  }
  fclose(fptr);
  return alph_index;
}

/*this opens the mapper that was called and calls count letters*/
int *phase2(int* alph_index, char* mapper_i) {
  int ch;
  FILE *ptr =fopen(mapper_i, "r");
  char buff[256];
  while(1){
    if(fscanf(ptr, "%s", buff) != EOF) {
      if(strstr(buff, ".txt") != NULL) {
        count_letters(buff, alph_index, ch);
      }
      memset(buff, 0, 256);
    }
    else {
      break;
    }
  }

  fclose(ptr);
  return alph_index;
}
