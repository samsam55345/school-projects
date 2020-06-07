/*test machine: CSELAB_machine_name * date: mm/dd/yy
* name: full_name1 , [full_name2]
* x500: id_for_first_name , [id_for_second_name] */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>
#include "header.h"
int line = 1;
//how to signal the end of file, aka no more data to the consumer
//i think this code is essentially complete beyond that?

void* producer(void* arg) {
  //int ID = *((int*) arg);
  //printf("ididididid %d \n", ID );
  struct Queue * queue = (struct Queue*)arg;
  // lock mutex
  char buffer[1024];
  pthread_mutex_lock(&queue->mutex);
  usleep(rand() % 2000);
  while(fgets(buffer, 1024, (FILE*)queue->fp)) {

    enQueue(queue, buffer, line);
    //memset(buffer, 0, 1024);
    if(queue->print != 0) {
      FILE *logtxt;
      logtxt = fopen("log.txt", "a");
      fprintf(logtxt, "producer on line: %d\n", line);
      fclose(logtxt);
    }
    pthread_cond_signal(&queue->can_consume);
    pthread_mutex_unlock(&queue->mutex);
    line++;
    pthread_mutex_lock(&queue->mutex);
    //pthread_cond_wait(&queue->can_consume, &queue->mutex);

  }
  fclose(queue->fp);
  queue->end++;
  //pthread_cond_wait(&queue->can_consume, &queue->mutex);
  if(queue->print != 0) {
    FILE *logtxt;
    logtxt = fopen("log.txt", "a");
    fprintf(logtxt, "-1\n");
    fclose(logtxt);
  }
  pthread_mutex_unlock(&queue->mutex);
  //pthread_exit(NULL);
  return 0;
}
