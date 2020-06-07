#include "../include/phase3.h"
#include <stdio.h>
#include <unistd.h>
// You are free to use your own logic. The following points are just for getting started
/* Reduce Function
	1)	The reducer receives the list from 'm' mappers via pipes
	2)	Ensure proper closing of pipe ends
	3) 	Combine the lists to create a single list
	4) 	Write the list to "ReducerResult.txt" in the current folder
*/

/*puts an array with all the counts into a ReducerResult.txt*/
void phase3(int * arr) {
  int ch;
  FILE *ReducerResult;
  ReducerResult = fopen("ReducerResult.txt", "w+");

  int c = 'A';
  int x = 0;
  while(x < 26) {
    fputc(c,ReducerResult);
    fputs(" ", ReducerResult);
    fprintf(ReducerResult, "%d\n", arr[x] );
    c++;
    x++;
  }
  fclose(ReducerResult);
}
