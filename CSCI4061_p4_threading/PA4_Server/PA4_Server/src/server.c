#include <stdio.h>
#include <netdb.h>
#include <netinet/in.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <zconf.h>
#include <pthread.h>
#include <signal.h>
#include <arpa/inet.h>
#include <unistd.h>
#include "../include/protocol.h"
#define _BSD_SOURCE
#define _DEFAULT_SOURCE


int update_status[33][3] = {0};
// void *count_letters(int * az_list, int * lst);
int az_list[26] = {0};

void * count_letters(void * args){
  //what does client_sock send
  // int lst[REQUEST_MSG_SIZE] = {}
  //  (unsigned int(*) [REQUEST_MSG_SIZE]) args;
  int (*lst)[REQUEST_MSG_SIZE] = (int (*)[REQUEST_MSG_SIZE])args;
  for(int i = 2; i < 28; i++){
    az_list[i-2] += *lst[i];
  }
  return 0;
}
int num_updates(int update_status[33][3]){
  int return_val = 0;
  //possible error?
  for(int i = 0; i< 33; i++){
    return_val += update_status[i][1];
  }
  return return_val;
}

int main(int argc, char *argv[]) {

    int server_port, socket_des, f;
    int client_sock;
    struct sockaddr_in server, client;

    if (argc == 2) { // 1 arguments
        server_port = atoi(argv[1]);
    } else {
        printf("Invalid or less number of arguments provided\n");
        printf("./server <server Port>\n");
        exit(0);
    }

    // Server (Reducer) code
    //create socket
    socket_des = socket(AF_INET, SOCK_STREAM, 0);
    if(socket_des == 0){
      printf("%s\n", "can't create socket" );
    }
    else{printf("%s\n", "socket created" );}

    //prepare the sockaddr_in Struct
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = INADDR_ANY;
    server.sin_port = htons(server_port);


    //bind
    if(bind(socket_des, (struct sockaddr *)&server, sizeof(server)) < 0){
      perror("bind failed");
      return(-1);
    }
    else{ printf("%s\n", "bind done" );}

    listen(socket_des, MAX_CONCURRENT_CLIENTS);

    //incoming connnection
    printf("%s\n", "server is listening" );
    f = sizeof(struct sockaddr_in);
    pthread_t thread_id;
    client_sock = accept(socket_des, (struct sockaddr *)&client, (socklen_t*)&f);

    if (client_sock < 0){
      perror("accept failed");
      return(-1);
    }
    else{
      printf("%s\n", "client connected" );
    }
    while(1){
      int readval[LONG_RESPONSE_MSG_SIZE] = {0};


      read(client_sock, readval, REQUEST_MSG_SIZE);
      printf("mapper id = %d\n", readval[1] );
      //HOW DO I FIND THE CLIENT IP AND CLIENT PORT
      printf("%s %d:%d\n", "open connection from", server.sin_addr.s_addr, server.sin_port );

      // if(pthread_create(&thread_id, NULL, count_letters, (void*) &client_sock) < 0){
      //   perror("couldnt create thread");
      //   return(-1);
      // }

      //CHECKIN
      if(readval[1] <= 0){
        printf("%d\n", readval[1] );
        printf("%s\n", "mapper ID is not greater than zero" );
        exit(-1);
      }
      if(readval[0] == 1){
        printf("%s\n", "hit here");
        //SET CHECKIN TO 1
        if(update_status[readval[1]][2] == 0){
          update_status[readval[1]][2] = 1;
          write(client_sock, &readval[1], RESPONSE_MSG_SIZE);
        }
        else if(update_status[readval[1]][2] != 0){
          printf("%s\n", "Mapper is already checked in" );
        }

      }
      else if(readval[0] == 2){
        //make thread call count letters
        update_status[readval[1]][1]++;
        if(pthread_create(&thread_id, NULL, &count_letters, (void*) &client_sock) < 0){
          perror("couldnt create thread");
          return(-1);
        }
        pthread_join(thread_id, NULL);

      }
      else if(readval[0] == 3){
        //send AZ list
        write(client_sock, az_list, 26*sizeof(int));
      }
      if(readval[0] == 4){
        //get mapper updates
        if(update_status[readval[1]][1] > 0){
          write(client_sock, &update_status[readval[1]][1], sizeof(int));
        }
        else{
          printf("%s\n", "table has never been updated" );
        }
      }
      else if(readval[0] == 5){
        //get all updates
        int total = num_updates(update_status);
        write(client_sock, &total, sizeof(int));

      }
      else if(readval[0] == 6){
        //checkout
        if(update_status[readval[1]][2] != 0){
          update_status[readval[1]][2] = 0;

        }
        else{
          printf("%s\n", "mapper already checked out/never checked in" );
        }
      }
      else if (readval[0] < 1 && readval[0] > 6){
        printf("%s\n", "unknown request code");
        printf("%d\n", readval[0]);
        exit(-1);
      }


      close(client_sock);


      memset(&readval, 0, 1024);
    }



    return 0;
}
