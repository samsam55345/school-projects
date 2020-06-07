

#include <stdlib.h>
#include <string.h>
#include <pthread.h>
//#include "main.c"
#include "header.h"
// pthread.h included in header.h
// the consumers will read from the shared queue, compute the
// “word count statistics” for its data pieces and synchronize
// the result with a global histogram;
/**
The consumer will repeatedly check the queue for a new package, work on it for
word count statistics, and then go back to get the next package. This will
continue until receiving the EOF notification, then it will terminate. Besides
package handling, it’s the consumer’s responsibility to synchronize the result
with the global final histogram. Then after all consumers finish their work, the
master thread could summarize it and generate the output.
The word count statistics will check the beginning character of a word. The
definition of a word is a continuous a-zA-Z character (shown in Fig. 3 below).
Note that, all other characters are treated as space. The characters like “-”,
“_” are not connecting words. Same as PA2, we don’t differentiate between
uppercase and lowercase letters.
*/

int num_consumer = 0;
int count_let[26] ={0};
int consumer_number = 0;

//int consumer_number;
//int thread;

//int count_let[26] = {0};
//how to know which consumer we're on; saving it in the queue isnt working
//how to know if producer is done
void* consumer(void* arg) {
  //printf("con num %d\n",consumer_number );
  //thread = consumer_number;
  // struct thread_id * thread = (struct thread_id *)arg;
  // struct Queue * queue = (struct thread_id*)thread->queue;
  struct Queue * queue = (struct Queue*)arg;
  pthread_mutex_lock(&queue->mutex);
  usleep(rand() % 2000);
  // lock mutex
  // check to see if there is data to consume
  //nothing in the queue
  //  while(queue->front ==NULL) {

  while(queue->front == NULL && queue->end == 0){
    pthread_cond_wait(&queue->can_consume, &queue->mutex);
  }

  if(queue->front == NULL && queue->rear != NULL){
    //printf("%s\n", "in iff" );
    pthread_cond_signal(&queue->can_consume);
  }
  if(queue->front == NULL && queue->rear == NULL && queue->end ==1){
    //printf("%s\n", "reached");
    pthread_mutex_unlock(&queue->mutex);
    pthread_exit(NULL);
  }

  pthread_mutex_unlock(&queue->mutex);
  //pthread_mutex_unlock(&queue->mutex);
  if((queue->end == 0) || (queue->end ==1 && queue->rear !=NULL)) {
    //printf("%s\n", "in if");
    //pthread_mutex_lock(&queue->mutex);

    //printf("\t\t\tConsumer does stuff!\n");
    // if we get to this point, that means there's something in the queue to use
    pthread_mutex_lock(&queue->mutex);
    int count = 0;
    //consumer_number++;
    //pthread_mutex_unlock(&queue->mutex);

    while(queue->front != NULL) {
      //pthread_mutex_lock(&queue->mutex);
      struct Node *item = deQueue(queue);
      if(queue->print != 0) {
        FILE * logtxt;
        logtxt = fopen("log.txt", "a");
        //pthread_mutex_lock(&queue->mutex);
        fprintf(logtxt, "consumer %d: %d\n", consumer_number ,count);
        //pthread_mutex_unlock(&queue->mutex);

        fclose(logtxt);
        //printf("consumer %d: %d\n", consumer_number, item->line);

      }
      //pthread_mutex_lock(&queue->mutex);
      consumer_number++;
      pthread_mutex_unlock(&queue->mutex);
      pthread_mutex_lock(&queue->mutex);
      count_letters(count_let, item->buf);
      pthread_mutex_unlock(&queue->mutex);

      count++;
      pthread_mutex_lock(&queue->mutex);

    }
    pthread_mutex_unlock(&queue->mutex);
    num_consumer++;
  }
  else if( queue->end != 0 || (queue->rear == NULL)){
    pthread_exit(NULL);

  }


  return 0;


}

void count_letters(int* count_let, char * buf){
  int ch;
  //char delim[] = "' ',\t,\n,\v,\f,\r,.,-,_,1,2,3,4,5,6,7,8,9,0,!,@,#,$,^,&,*,(,),+,=,/,;,:,{,},`,~,?,|,[,], >, <,'\"', ',' ";
  char delim[] = " \n\v\f\r\t.-_1234567890!@#$^&*()+=/,'\"'{}[]' ';:`~?<>";
  char * rest = buf;
  char *ptr = __strtok_r(buf,delim,&rest);
  while(ptr != NULL){
    ch = ptr[0];
    if('a' <= ch && ch <= 'z'){
      count_let[ch-'a']++;
    }
    else if ('A' <= ch && ch <= 'Z'){
      count_let[ch-'A']++;
    }
    ptr = __strtok_r(NULL,delim,&rest);

  }


}
