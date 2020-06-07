
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
#include "../include/phase1.h"

// You are free to use your own logic. The following points are just for getting started
/* 	Data Partitioning Phase - Only Master process involved
	1) 	Create 'MapperInput' folder
	2) 	Traverse the 'Sample' folder hierarchy and insert the text file paths
		to Mapper_i.txt in a load balanced manner
	3) 	Ensure to keep track of the number of text files for empty folder condition

*/

int count = 0;
int cur_count = 0;

void traverse(char* filename, FILE * fptr)
{
  DIR *dir;
  if ((dir = opendir(filename)))
  {
    struct dirent *entry;
    while ((entry = readdir(dir)) != NULL)
    {
        if (entry->d_type == DT_DIR)
        {
          char p[1024];
          if (strcmp(entry->d_name, ".") == 0 || strcmp(entry->d_name, "..") == 0)
              continue;
          //save path name
          snprintf(p, sizeof(p), "%s/%s", filename, entry->d_name);
          //traverse with new path
          traverse(p, fptr);
        }
        else
        {
          char p[1024];
          count++;
          snprintf(p, sizeof(p), "%s/%s", filename, entry->d_name);
          //print the path to filepaths.txt, which is currently open for writing
          fputs(p, fptr);
          fputs("\n", fptr);
        }
    }
   closedir(dir);
  }
}

void partition(int numMappers, FILE * mappers[numMappers])
{
  int per_file = count/numMappers;
  FILE *fptr;
  char buff[255];
  fptr = fopen("filepaths.txt", "r");
  for(int i = 0; i<numMappers; i++)
  {
    for(int j = 0; j < per_file; j++)
    {
      //partition the data structure where the file paths are saved
      //file paths are in txt file filepaths.txt
      fscanf(fptr, "%s", buff);
      fputs(buff, mappers[i]);
      fprintf(mappers[i], "\n");
      cur_count++;
    }
  }
  //check that we've hit all the filenames
  if(cur_count < count)
  {
    for(int i = 0; i<numMappers && cur_count != count; i++)
    {
      fscanf(fptr, "%s", buff);
      fputs(buff, mappers[i]);
      fprintf(mappers[i], "\n");
      cur_count++;
    }
  }
  //close files
  for(int i = 0; i<numMappers;i++)
  {
    fclose(mappers[i]);
  }
  //close filepaths.txt for reading
  fclose(fptr);
}

int phase1(char* filename, int numMappers)
{
  FILE * mappers[numMappers];
  FILE * fptr;
  fptr = fopen("filepaths.txt", "w");
  //traverse folder hierarchy provided by filename and get all the files.
  traverse(filename, fptr);
  //close filepaths.txt for writing
  fclose(fptr);
  //check that folder actually has files
  if(count == 0)
  {
    return -1;
  }
  //create MapperInput folder
  mkdir("MapperInput", S_IRWXU | S_IRWXG | S_IROTH | S_IXOTH);
  //create Mapper_i.txt files
  for(int i = 1; i<=numMappers; i++)
  {
    char cur[20];
    snprintf(cur, sizeof(cur), "Mapper_%d.txt", i);
    char * filepath = malloc(strlen("MapperInput") + strlen(cur) + 2);
    filepath = strcpy(filepath, "MapperInput");
    filepath = strcat(filepath, "/");
    filepath = strcat(filepath, cur);
    mappers[i-1] = fopen(filepath, "w");
  }
  //parition files into Mapper_i.txts
  partition(numMappers, mappers);
  return 0;
}
