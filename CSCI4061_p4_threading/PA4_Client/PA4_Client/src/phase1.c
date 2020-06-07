/* test machine: CSEL-KH4250-18

date: 12/04/19
name: Orion Grebe , Sami Frank
x500: grebe013 , fran0942 */

#define _BSD_SOURCE
#define _DEFAULT_SOURCE
//#include "../include/phase1.h"


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

int phase_one(char* filename, int numMappers)
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

//
// void recursiveTraverseFS(int mappers, char *basePath, FILE *fp[], int *toInsert, int *nFiles){
// 	//check if the directory exists
// 	DIR *dir = opendir(basePath);
// 	if(dir == DIRNULL){
// 		printf("Unable to read directory %s\n", basePath);
// 		exit(1);
// 	}
//
// 	char path[1000];
// 	struct dirent *dirContentPtr;
//
// 	//use https://www.gnu.org/software/libc/manual/html_node/Directory-Entries.html
// 	while((dirContentPtr = readdir(dir)) != DIRNULL){
// 		if (strcmp(dirContentPtr->d_name, ".") != 0 &&
//    	strcmp(dirContentPtr->d_name, "..") != 0 &&
//    	strcmp(dirContentPtr->d_name, ".DS_Store") != 0) {
// 			struct stat buf;
// 			 lstat(path, &buf);
// 			 if (S_ISLNK(buf.st_mode))
// 				 continue;
// 			if (dirContentPtr->d_type == DT_REG){
// 				//file
// 				char filePath[1000];
// 				strcpy(filePath, basePath);
// 				strcat(filePath, "/");
// 				strcat(filePath, dirContentPtr->d_name);
//
// 				//insert into the required mapper file
// 				fputs(filePath, fp[*toInsert]);
// 				fputs("\n", fp[*toInsert]);
//
// 				*nFiles = *nFiles + 1;
//
// 				//change the toInsert to the next mapper file
// 				*toInsert = (*toInsert + 1) % mappers;
// 			}else if (dirContentPtr->d_type == DT_DIR){
// 				//directory
// 				// basePath creation - Linux platform
// 				strcpy(path, basePath);
// 				strcat(path, "/");
// 				strcat(path, dirContentPtr->d_name);
// 				recursiveTraverseFS(mappers, path, fp, toInsert, nFiles);
// 			}
// 		}
// 	}
// }
//
// void traverseFS(int mappers, char *path){
//     FILE *fp[mappers];
//
//     pid_t p = fork();
//     if (p==0)
//         execl("/bin/rm", "rm", "-rf", "MapperInput", NULL);
//
//     wait(NULL);
//     //Create a folder 'MapperInput' to store Mapper Input Files
//     mkdir("MapperInput", ACCESSPERMS);
//
//     // open mapper input files to store paths of files to be processed by each mapper
//     int i;
//     for (i = 0; i < mappers; i++){
//         // create the mapper file name
//         char mapperCount[10];
//         sprintf(mapperCount, "%d", i + 1);
//         char mapInFileName[100] = "MapperInput/Mapper_";
//         strcat(mapInFileName, mapperCount);
//         strcat(mapInFileName, ".txt");
//         fp[i] = fopen(mapInFileName, "w");
//
//     }
//     //refers to the File to which the current file path should be inserted
//     int toInsert = 0;
//     int nFiles = 0;
//     recursiveTraverseFS(mappers, path, fp, &toInsert, &nFiles);
//
//     // close all the file pointers
//     for(i = 0; i < mappers; i++)
//         fclose(fp[i]);
//
//     if(nFiles == 0){
//         pid_t p1 = fork();
//         if (p1==0)
//             execl("/bin/rm", "rm", "-rf", "MapperInput", NULL);
//     }
//
//     if (nFiles == 0){
//         printf("The %s folder is empty\n", path);
//         exit(0);
//     }
// }
