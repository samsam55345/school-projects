

#include <stdlib.h>
#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>
#include <dirent.h>
#include <string.h>
#include <unistd.h>
#include <stdio.h>
#include "../include/main.h"
#include "../include/phase1.h"
#include "../include/phase2.h"
#include "../include/phase3.h"
#include "../include/phase4.h"

// char * concat(const char *s1, char *s2){
//   char *result = malloc(strlen(s1)+strlen(s2)+1);
//   strcpy(result,s1);
//   strcat(result, s2);
//   return result;
// 	//free(result);
// }

int main(int argc, char *argv[]){
	if(argc>3) {
		printf("Error, too many commands given. Expected 3 but found %d. Please provide a folder name and integer.\n", argc);
		exit(-1);
	}
	else if(argc<3) {
		printf("Error, not enough commands given. Expected 3 but found %d. \nPlease provide a folder name and integer.\n", argc);
		exit(-1);
	}
	else {
		//check that path provided to Folder exists
		DIR *dir;
		if (!(dir = opendir(argv[1]))) {
			printf("Error, folder not found\nPlease make sure you give the whole path to the folder.\n");
			exit(-1);
		}
		closedir(dir);
		int numMappers = atoi(argv[2]);
		//checking that valid number of mappers was given
		if(numMappers < 1) {
			printf("Error, number of mappers is below 1.\nPlease input a number greater than 0 but less than 33.\n");
			exit(-1);
		}
		else if(numMappers > 32) {
			printf("Error, number of mappers is above 32.\nPlease input a number greater than 0 but less than 33.\n");
			exit(-1);
		}
		//can safely run phase1 now
		int c = phase1(argv[1], numMappers);
		//checking if there are files in folder hierarchy
		if(c == -1) {
			printf("Error, no files found in folder hierarchy.\n");
			exit(-1);
		}
		
		/*this starts the process of phase 2.*/
		char * mappers[1000];//might cause error if not big enough
		struct dirent *de;
		DIR *dr = opendir("MapperInput");
		if(dr == NULL) {
			printf("%s\n","could not open" );
		  exit(-1);
		}
		int count = 0;
	  while((de = readdir(dr)) != NULL){
			if(de->d_type == DT_DIR){
				if((strcmp(de->d_name, "..") == 0) || (strcmp(de->d_name, ".") ==0)){
					continue;
				}

			}
			else{
				mappers[count] = de->d_name;
				count++;

			}
	  }
		/*creates an int array of 2*nummappers because each mapper needs int fd[2]*/
		int fd[2*numMappers];
		for(int i = 0; i< numMappers; i++) {
			pipe(&fd[2*i]);
		}
		int reducerInput[26]= {0};
		int buff[26];
		/*for each mapper find the correct file path and send that through phase 2*/
		for(int j = 0; j < numMappers; j++) {
			char *filep = "MapperInput/";
			char *mapper_i = (char*)malloc(strlen(filep)+strlen(mappers[j])+1);
			strcpy(mapper_i,filep);
			strcat(mapper_i, mappers[j]);
			int *alph_index = malloc(26*sizeof(int));
			for(int i = 0; i<26; i++){
				alph_index[i] = 0;
			}
			int * count_arr = malloc(26*sizeof(int));
			count_arr = phase2(alph_index,mapper_i);
			write(fd[2*j +1], &count_arr[0], sizeof(int)*26);
			read(fd[2*j], &buff[0], sizeof(int)*26);
			for(int e=0; e<26; e++) {
					reducerInput[e] += buff[e];
			}
			free(mapper_i);
			free(alph_index);
			free(count_arr);
			close(fd[2*j]);
			close(fd[2*j+1]);
		}
		phase3(reducerInput);
		closedir(dr);
		FILE *reducer = fopen("ReducerResult.txt", "r");
		if(reducer == NULL) {
			printf("Error, reducer results do not exist\n");
			exit(-1);
		}
		phase4(reducer);
		fclose(reducer);
	}
}
