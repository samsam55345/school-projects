/*test machine: CSELAB_machine_name * date: mm/dd/yy
* name: full_name1 , [full_name2]
* x500: id_for_first_name , [id_for_second_name] */



/*
  LOG FILE HOW TO GET CORRECT CONSUMER NUMBER
  SOMETIMES MY PTHREAD_JOIN HANGS. why???
*/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <dirent.h>
#include <pthread.h>
#include "header.h"

// pthread.h included in header.h
// the master thread initializes the shared queue, result histogram,
// producer thread and consumer threads;
// after producer and all consumers complete their work, the master
// thread writes the final result into the output file.
// PA3 takes only one file as your input file.
// But PA3’s input file is more “natural”, which contains multiple words in
// each line and the file contains multiple lines.
// in PA3, you only have one process but multiple threads. PA3 is mainly
// focused on the usage of threads and thread-safe data structures.

/**
4.1 Master Initialization
should perform the initialization on data structure and launch the producer/consumers
thread.
master will wait for all threads to join back (4.4 Master Finalize).
master thread will write the final result (global histogram) into the output file named “result.txt”.
output format should be: "%c: %d\n", and it will loop through the global histogram
from ‘a’ to ‘z’
*/


/**
4.5 Log Printout
The program will also print a log file if the argument option is specified. The
log file should be named as “log.txt”. The producer and consumers should print
their execution information into the log:
Producer:
1. Print when the producer is launched
2. Print for the current line number (starts from 0)
Consumer:
1. Print "consumer %d\n" when launched, with the consumer id (0 to
number of consumers minus 1)
2. Print "consumer %d: %d\n" for the consumer id and the line number
it currently works on.
There are some other notes when writing the log:
- The print library functions are usually thread-safe, so you don’t need to use
a lock or worry about messy printing (unless you meet that).
- Since the execution order of threads is nondeterministic, you usually will not
get a stable log print out.
- EOF notification should be printed out as line number “-1”.
*/
//#consumer filename [option]
/**
Options have only 2 possibilities: “-p”, “-bp”.
1) “-p” means printing, the program will generate log in this case.
2) “-bp” means both bounded buffer and log printing.
3) "b" buffer
*/


int main(int argc, char *argv[]) {
  if(argc == 4) {
    if(strcmp(argv[3], "-b") == 0) {
      printf("Extra credit not supported!\n");
      exit(-1);
    }
    else if(strcmp(argv[3], "-bp") == 0) {
      printf("Extra credit not supported! Running -p argument instead.\n");
    }
    else if(strcmp(argv[3], "-p") != 0) {
      printf("Invalid option provided. Please provide either -p or -bp.\n");
      exit(-1);
    }
  }
  else if(argc != 3) {
    printf("Error, wrong number of command line arguments. Expected 4 or 3 but found %d.\n", argc);
    printf("Please provide number of consumers, filename and/or an option (-p or -bp).\n");
		exit(-1);
  }
  else if(*argv[1]<1) {
    printf("Error, not enough consumers. Please have at least 1.\n");
    exit(-1);
  }
  struct Queue* queue = makeQueue(argv[2]);

//  printf("end %d\n", queue->end );
  if(queue->fail != 0) {
    printf("Error, file does not exist.\n");
    exit(-1);
  }
  queue->print = 0;
  if(argc>3) {
    if(strcmp(argv[3], "-p") == 0 || strcmp(argv[3], "-bp") == 0) {
      queue->print++;
    }
  }
  int num_consumer = atoi(argv[1]);
  pthread_t cons[num_consumer];
  pthread_t prod;
  FILE *logtxt;

  //check print flag
  if(queue->print != 0) {
    logtxt = fopen("log.txt", "a");
    fprintf(logtxt, "Producer\n");
    fclose(logtxt);
    //prints out that the producer has been launched
    //printf("Producer\n");
  }
  //create producer
  pthread_create(&prod, NULL, &producer, (void *)queue);


  //int lines_per_consumer = queue->lines_of_file / num_consumer;

  int consumer_num = 0;

  //create consumers
  for(int i = 0; i<num_consumer; i++) {
    //sets the consumer number
    // pthread_mutex_lock(&queue->mutex);
    consumer_num = i;
    // pthread_mutex_unlock(&queue->mutex);

    //checks print flag
    if(queue->print != 0) {
      //prints out consumer number upon launch
      logtxt = fopen("log.txt", "a");

      fprintf(logtxt, "consumer %d\n", consumer_num);
      fclose(logtxt);
      //printf("consumer %d\n", consumer_num);
    }
    pthread_create(&cons[i], NULL, &consumer, (void *)queue);
  }


  pthread_join(prod, NULL);

  for(int i = 0; i<num_consumer; i++) {
    //printf("waiting...\n");

    pthread_join(cons[i], NULL);
    //printf("%s\n", "weighted" );
  }

  FILE *result;
  result = fopen("result.txt", "w");
  char c = 'a';
  int i = 0;
  while(i<26){
    fputc(c,result);
    fprintf(result, ": %d\n", count_let[i]);
    c++;
    i++;
  }
  fclose(result);
  return 0;
}
