#include <stdlib.h>
#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>
#include "../include/phase4.h"
// You are free to use your own logic. The following points are just for getting started
/* Final Result
	1)	The master process reads the "ReducerResult.txt"
	2) 	Print the contents to standard output which is redirected to "FinalResult.txt"
			i.e., on using printf, the statement should be written to "FinalResult.txt"
*/

void phase4(FILE *reducer) {
  //create the final results file
  int final_file = open("FinalResult.txt", O_WRONLY | O_CREAT, 0644);
  //redirect stdout to finalresults.txt
  dup2(final_file, 1);
  char str[255];
  //input data from reducerresults.txt to finalresults.txt
  while(fgets(str, 255, reducer) != NULL) {
    printf("%s", str);
  }
  close(final_file);
}
