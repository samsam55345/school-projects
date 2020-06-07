#define _GNU_SOURCE
#define _BSD_SOURCE
#define _DEFAULT_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <zconf.h>
#include <arpa/inet.h>
#include <ctype.h>
#include "../include/protocol.h"


FILE *logfp;

// void createLogFile(void) {
//     pid_t p = fork();
//     if (p==0)
//         execl("/bin/rm", "rm", "-rf", "log", NULL);
//
//     wait(NULL);
//
// }

// recycled from PA2
int * count_letters(char * filepath, int * alph_index, int ch) {
  FILE * fptr = fopen(filepath, "r");
  char * line = NULL;
  size_t len = 0;
  ssize_t read;
  // if the file is null, simply return alph_index
  if(fptr == NULL){
    return alph_index;
  }
  // while there are lines in the file, read
  while((read = getline(&line, &len, fptr)) != -1) {
    ch = line[0];
    // if we are at the end of the file, stop
    if(ch == EOF) {
      break;
    }
    // check if the character is caps or not, increment corresponding spot
    if('a' <= ch && ch <= 'z') {
      alph_index[ch-'a']++;
    }
    else if ('A' <= ch && ch <= 'Z') {
      alph_index[ch-'A']++;
    }
  }
  // close file
  fclose(fptr);
  // return array of counted letters
  return alph_index;
}

int main(int argc, char *argv[]) {
    int mappers;
    char folderName[100] = {'\0'};
    char *server_ip;
    int server_port;

    if (argc == 5) { // 4 arguments
        strcpy(folderName, argv[1]);
        mappers = atoi(argv[2]);
        server_ip = argv[3];
        server_port = atoi(argv[4]);
        if (mappers > MAX_MAPPER_PER_MASTER) {
            printf("Maximum number of mappers is %d.\n", MAX_MAPPER_PER_MASTER);
            printf("./client <Folder Name> <# of mappers> <server IP> <server Port>\n");
            exit(1);
        }
    } else {
        printf("Invalid or less number of arguments provided\n");
        printf("./client <Folder Name> <# of mappers> <server IP> <server Port>\n");
        exit(1);
    }

    // create log file
    // createLogFile();
    mkdir("log", S_IRWXU|S_IRGRP|S_IXGRP);
    logfp = fopen("log/log_client.txt", "w");
    FILE * final_file = fopen("log/log_client.txt", "w+");
    //redirect stdout to log_client.txt
    //dup2(final_file, 1);
    // phase1 - File Path Partitioning
    phase_one(folderName, mappers);
    // create socket
    int sockfd;
    if ((sockfd = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        fprintf(final_file,"Socket creation error");
        //printf("\n Socket creation error \n");
        exit(-1);
    }
    struct sockaddr_in serv_addr;
    memset(&serv_addr, '0', sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_addr.s_addr = INADDR_ANY;
    serv_addr.sin_port = htons(server_port);
    serv_addr.sin_addr.s_addr = inet_addr(server_ip);

    // Phase2 - Mapper Clients's Deterministic Request Handling
    int id = 1;
    //////
    //printf("%d!!\n", id);
    int pid;
    for(int i = 1; i <= mappers; i++) {
      // create the client processes + assign them their unique id
    //  printf("%d\n", i);
      pid = fork();

      // break if child
      if (pid == 0) {
        id = i;
        break;
      }
    }

    //////
    //if(id >= 0) printf("child %d\n", id);

    // if we are in a child process, connect to server
    if(pid == 0) {
      fprintf(final_file, "[%d] open connection", id);
      //printf("[%d] open connection", id);
      if (connect(sockfd, (struct sockaddr *)&serv_addr, sizeof(serv_addr)) < 0) {
          //printf(" test %d\n", connect(sockfd, (struct sockaddr *)&serv_addr, sizeof(serv_addr)));
          fprintf(final_file,"\nConnection Failed\n");
          exit(-1);
      }
      // else{
      //   printf("%s\n", "succeed");
      //   printf("%d\n", id );
      // }
      fprintf(final_file, "\n");
      // set up request and response arrays
      int request[REQUEST_MSG_SIZE] = {1, id, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
      int short_response[RESPONSE_MSG_SIZE] = {0};
      int long_response[LONG_RESPONSE_MSG_SIZE] = {0};

      // check in
      write(sockfd, request, REQUEST_MSG_SIZE);
      // receive response
      read(sockfd, short_response, RESPONSE_MSG_SIZE);
      // check response
      fprintf(final_file,"[%d] CHECKIN: %d %d\n", id, short_response[1], short_response[2]);
      //update azlist
      request[0] = 2;
      int ch = 0;
      char * mapper_i = "MapperInput/Mapper_";
      char map_id = id;
      strcat(mapper_i, &map_id);
      strcat(mapper_i, ".txt");
      FILE *ptr =fopen(mapper_i, "r");
      int num_lines = 0;
      //need to count how many lines in mapper_i.txt
      char c;
      for (c = getc(ptr); c != EOF; c = getc(ptr)) {
        if (c == '\n') {
          num_lines++;
        }
      }
      char buff[256];
      int alph_index[26] = {0};
      // loop for however many lines are in mapper_i.txt
      // don't execute any of the following code if there are 0 lines
      if(num_lines > 0) {
        for(int i = 0; i < num_lines; i++) {
          while(1){
            // get the next file
            if(fscanf(ptr, "%s", buff) != EOF) {
              // check that its a file
              if(strstr(buff, ".txt") != NULL) {
                // count letters in file
                count_letters(buff, alph_index, ch);
                int x = 2;
                // save into request array
                for(int i = 0; i < 26; i++) {
                  request[x] += alph_index[i];
                  x++;
                }
              }
              memset(buff, 0, 256);
            }
            else {
              break;
            }
          }
          //send to server
          write(sockfd, request, REQUEST_MSG_SIZE);
          //receive response
          read(sockfd, short_response, RESPONSE_MSG_SIZE);
        }
      }
      // check final response
      fprintf(final_file, "[%d] UPDATE_AZLIST: %d\n", id, num_lines);
      // get az azList
      request[0] = 3;
      write(sockfd, request, REQUEST_MSG_SIZE);
      read(sockfd, long_response, LONG_RESPONSE_MSG_SIZE);
      // check response
      fprintf(final_file, "[%d] GET_AZLIST: %d %d %d %d %d %d %d %d %d %d %d %d", long_response[2], long_response[3], long_response[4], long_response[5], long_response[6], long_response[7], long_response[8], long_response[9], long_response[10], long_response[11], long_response[12], long_response[13], long_response[14]);
      fprintf(final_file, " %d %d %d %d %d %d %d %d %d %d %d %d %d\n", long_response[15], long_response[16], long_response[17], long_response[18], long_response[19], long_response[20], long_response[21], long_response[22], long_response[23], long_response[24], long_response[25], long_response[26], long_response[27]);
      //get mapper updates
      request[0] = 4;
      write(sockfd, request, REQUEST_MSG_SIZE);
      read(sockfd, short_response, RESPONSE_MSG_SIZE);
      // check response
      fprintf(final_file, "[%d] GET_MAPPER_UPDATES: %d %d\n", id, short_response[1], short_response[2]);
      // get all updates
      request[0] = 5;
      write(sockfd, request, REQUEST_MSG_SIZE);
      read(sockfd, short_response, RESPONSE_MSG_SIZE);
      // check response
      fprintf(final_file, "[%d] GET_ALL_UPDATES: %d %d\n", id, short_response[1], short_response[2]);
      //checkout
      request[0] = 6;
      write(sockfd, request, REQUEST_MSG_SIZE);
      read(sockfd, short_response, RESPONSE_MSG_SIZE);
      // check response
      fprintf(final_file, "[%d] CHECKOUT: %d %d\n", id, short_response[1], short_response[2]);
      // close
      fprintf(final_file, "[%d] close connection\n", id);
      shutdown(sockfd, 2);
      // exit process
      return 0;
    }
    // close files
    fclose(logfp);
    fclose(final_file);
    // end master process
    return 0;
}
