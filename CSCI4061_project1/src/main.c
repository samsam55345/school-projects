/** Using the data structures that model the graph, execute the recipes by forking a new
process per recipe. Your program should determine which recipes are eligible to run,
execute those recipes, wait for any recipe to finish, then repeat this process until
all recipes finished executing.
Instead, if a project is run with a specific target, say dep2, ./mymake makefile in dep2,
your program must only print and then fork/exec one recipe: gcc -o file3.o -c file3.c */
#define _GNU_SOURCE
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>

#include "graph.h"
#include "dfs_stack.h"

//global variable to hold the makefile target objects
node root;
int count_for_targ = 0;
char * tar_to_exec[LINE_SIZE];

//Parse the input makefile to determine targets, dependencies, and recipes
int process_file(char *fname)
{
	FILE* fp = fopen(fname, "r");
	char line[LINE_SIZE];
	int i = 0;

	if (!fp) {
		printf("Failed to open the file: %s \n", fname);
		return -1;
	}

	//Read the contents and store in lines
	while (fgets(line, LINE_SIZE, fp))
		strncpy(lines[i++], line, strlen(line));

	fclose(fp);
	return 0;
}

//parse lines into tokens. takes a char[] array, char[] delimiter array, char[][] array
int makeargv(const char *s, const char *delimiters, char ***argvp) {
   int error;
   int i;
   int numtokens;
   const char *snew;
   char *t;

   if ((s == NULL) || (delimiters == NULL) || (argvp == NULL)) {
      errno = EINVAL;
      return -1;
   }
   *argvp = NULL;
   snew = s + strspn(s, delimiters);         /* snew is real start of string */
   if ((t = malloc(strlen(snew) + 1)) == NULL)
      return -1;
   strcpy(t, snew);
   numtokens = 0;
   if (strtok(t, delimiters) != NULL)     /* count the number of tokens in s */
      for (numtokens = 1; strtok(NULL, delimiters) != NULL; numtokens++) ;

                             /* create argument array for ptrs to the tokens */
   if ((*argvp = malloc((numtokens + 1)*sizeof(char *))) == NULL) {
      error = errno;
      free(t);
      errno = error;
      return -1;
   }
                        /* insert pointers to tokens into the argument array */
   if (numtokens == 0)
      free(t);
   else {
      strcpy(t, snew);
      **argvp = strtok(t, delimiters);
      for (i = 1; i < numtokens; i++)
          *((*argvp) + i) = strtok(NULL, delimiters);
    }
    *((*argvp) + numtokens) = NULL;             /* put in final NULL pointer */
    return numtokens;
}

//process lines into target objects
void process_into_objects()
{
	int x = 0;
	char *delimiters = " \n";
	int linesValidLength = 0;
	//find out how many lines in lines[][] have content we need
	for(int i = 0; i<128; i++)
	{
		if(lines[i][0] != '\0')
		{
			linesValidLength++;
		}
	}
	//iterate through valid lines
	for(int i = 0; i<linesValidLength; i++)
	{
		//create a target and set dep and recipe count to 0
		int flag = 0;
		char* recipe = "";

		target makefile;
		//set recipe and dep count
		makefile.dep_count = 0;
		makefile.recipe_count = 0;
		char **argvp;
		//tokenize the current line
		makeargv(lines[i], delimiters, &argvp);
		int z = 0;
		//iterate through argvp until null
		while(argvp[z] !=  NULL)
		{
			//checking if we have a target
			if(argvp[z][strlen(argvp[z])-1] == ':')
			{
				//accounts for if we have something like "clean : "
				if(strlen(argvp[z]) == 1)
				{
					makefile.name = argvp[z-1];
				}
				//accounts for is we have something like "clean: "
				else
				{
					argvp[z][strlen(argvp[z])-1] = '\0';
					makefile.name = argvp[z];
				}
				flag++;
			}
			//if the line doesn't start with a tab and name has been set we know we're in the dependencies

			else if(lines[i][0] != '\t' && flag != 0)
			{
				//printf("dependency found\n");
				makefile.depend[makefile.dep_count] = argvp[z];
				makefile.dep_count = makefile.dep_count+1;
			}
			z++;
		}

		//iterate through future lines
		int x = i+1;
		if(x<linesValidLength)
		{
			//if it begins with a tab, then we know its a recipe
			while(lines[x][0] == '\t')
			{
				makefile.recipe[makefile.recipe_count] = lines[x];
				makefile.recipe_count = makefile.recipe_count+1;
				x++;
			}
		}
		//add makefile to linked list
		if(root == NULL)
		{
			root = createNode();
			root -> data = makefile;
		}
		else
		{
			addNode(root, makefile);
		}
	}
	printf("\n");
}
//helper for p_function.
void p_function_helper(target makefile)
{
	//go through the dependencies
	for(int i = 0; i < makefile.dep_count; i++)
	{
		printf("\tDependency %d is %s\n", i, makefile.depend[i]);
	}
	//go through the recipes
	for(int i = 0; i < makefile.recipe_count; i++)
	{
		printf("\tRecipe %d is %s ", i, makefile.recipe[i]);
	}
	printf("\n");
}

//prints how many dependencies and recipies
void p_function()
{
	//save the root into node tmp
	node tmp = root;
	//iterate until tmp equals null
	while(tmp != NULL)
	{
		//get the makefile that tmp stores
		target makefile = tmp->data;
		//check how large the dep count is for grammer purposes
		if(makefile.dep_count > 1 || makefile.dep_count == 0)
		{
			//check how large the recipe count is for grammar purposes
			if(makefile.recipe_count > 1 || makefile.recipe_count == 0)
			{
				printf("Target '%s' has %d dependencies and %d recipes.\n", makefile.name, makefile.dep_count, makefile.recipe_count);
				//call helper to iterate through recipes and dependencies and print them
				p_function_helper(makefile);
			}
			else
			{
				printf("Target '%s' has %d dependencies and %d recipe.\n",  makefile.name, makefile.dep_count, makefile.recipe_count);
				//call helper to iterate through recipes and dependencies and print them
				p_function_helper(makefile);
			}
		}
		else
		{
			//check how large the recipe count is for grammar purposes
			if(makefile.recipe_count > 1 || makefile.recipe_count == 0)
			{
				printf("Target '%s' has %d dependency and %d recipes.\n", makefile.name, makefile.dep_count, makefile.recipe_count);
				//call helper to iterate through recipes and dependencies and print them
				p_function_helper(makefile);
			}
			else
			{
				printf("Target '%s' has %d dependency and %d recipe.\n", makefile.name, makefile.dep_count, makefile.recipe_count);
				//call helper to iterate through recipes and dependencies and print them
				p_function_helper(makefile);
			}
		}
		//set tmp to be the next node in the list
		tmp = tmp->next;
	}
}

target make_block(char * name){
	node temp = root;
	int x = 0;
	target makefile;
	while(temp != NULL && x!=1){
		makefile = temp ->data;
		if(strcmp(name, makefile.name) ==0){
			x++;
    }
		temp = temp-> next;

	}
	return makefile;
}

void r_help(char** targets, int targ, int *target_hit, int tcount,int dcount){
	//targets- list of target names, targ- the index of the target we're checking
	//target_hit- a list containing either a 0 or 1 to indicate if the recipe has been printed
	//tcount- total number of targets, dcount-total number of dependancies
	//get targets information
	int c = 0;
	//makes block out of target to be printed
	target x = make_block(targets[targ]);
	//no dependancies and hasnt been printed yet, print
	if(x.dep_count == 0 && target_hit[targ] == 0){// print recipes no dependancies

		for(int v = 0; v< x.recipe_count; v++){
			printf("%s\n",x.recipe[v] );
		}

		target_hit[targ] = 1;
	}
	//checks to see if dependancy is a target
	//if no dependacies are a target, print, else look at found dependancy

	if(x.dep_count == 1 && target_hit[targ] == 0){
		int target = 0;
		int targ_match =0;

		for(int y = 0; y< tcount; y++){
			if(strcmp(x.depend[c], targets[y]) != 0){
				target++;
			}
			//store index of targets[y] to use l8r
			if(strcmp(x.depend[c], targets[y]) == 0)
				targ_match = y;
		}
		//dependancy was in target
		if(target != tcount){
			r_help(targets, targ_match, target_hit, tcount,dcount);

		}
		else if(target == tcount){//dependancy not in target
			for(int j = 0; j< x.recipe_count; j++){
				printf("%s\n", x.recipe[j]);
			}
			target_hit[targ] = 1;
		}
	}
	//more than 1 dependancy, sees what dependancy is also
	//a target if any and either prints or calls r_help again

	if(x.dep_count > 1 && target_hit[targ] == 0){
		//check to see if x.depends[] are in target.depend (x.depend)
		//int tar[x.dep_count];
		int num = 0;
		//go through each dependancy
		for(int c = 0; c<x.dep_count; c++){
			int target = 0;
			for(int y = 0; y< tcount; y++){
				//if x.depend[c] is not in targets[y]
				if(strcmp(x.depend[c], targets[y]) != 0){
					target++;
				}
			}
			if(target == tcount){//dependancy not in target
				num++;
			}
		}
			if(num == x.dep_count && target_hit[targ] == 0){//all dependancies are not in target
				for(int z = 0; z < x.recipe_count; z++){
					printf("%s\n", x.recipe[z]);
				}
				target_hit[targ] = 1;
			}
			else if (num != x.dep_count && target_hit[targ] == 0){// there's at least one dependancy that matches a target
				for(int e = 0; e< x.dep_count; e++){
					for(int f = 0; f< tcount; f++){
						if(strcmp(x.depend[e], targets[f])==0){
							r_help(targets, f, target_hit, tcount, dcount);
					}
			}
		}
			//will print after its dependancies print
			for(int z =0; z < x.recipe_count; z++){
				printf("%s\n", x.recipe[z] );
			}
			target_hit[targ] = 1;
		}
	}
}

void r_function()
{
	//creates a list of all possible targets and their dependancies
	node tmp = root;
	char* deps[LINE_SIZE];
	char * targets[LINE_SIZE];
	int target_hit[LINE_SIZE];
	char * tar_to_exec[LINE_SIZE];
	int tcount = 0; //total number of targets
	int dcount = 0; //total number of dependancies
	while(tmp != NULL){
	//get makefile tmp stores
		target makefile = tmp -> data;
		targets[tcount] = makefile.name;
		target_hit[tcount] = 0;
		tcount++;
		if(makefile.dep_count >0 ){ //then we have a dependency
			//adds dependancies to an array
			for(int i = 0; i < makefile.dep_count; i++){
				deps[dcount] = makefile.depend[i];
				dcount++;
			}
		}
		tmp = tmp->next;
	}
	//compares dependacies to target. if dependancy is a target, look at those dependancies
	int targ;
	int depen;
	for(depen=0; depen< dcount;depen++){
		for(targ=0; targ< tcount; targ++){
			if(strcmp(deps[depen], targets[targ]) ==0){
				r_help(targets, targ, target_hit, tcount, dcount);
			}
		}
	}

//print first block
	target d = make_block(targets[0]);
	for(int e = 0; e< d.recipe_count; e++){
		printf("%s\n", d.recipe[e] );
	}
}

void execute_help(char** targets, int targ, int* target_hit, int tcount, int dcount){
	int c = 0;
	target x = make_block(targets[targ]);
	if(x.dep_count == 0 && target_hit[targ] == 0){// add recipes no dependancies to list of things to execute
		tar_to_exec[count_for_targ] = x.name;
		count_for_targ++;
		target_hit[targ] = 1;
	}
	//more than 1 dependancy, sees what dependancy is also
	//a target if any and either adds to target to execute list or calls execute_help again
	if(x.dep_count == 1 && target_hit[targ] == 0){
		int target = 0;
		int targ_match =0;

		for(int y = 0; y< tcount; y++){
			//if x.depend[c] is not in targets[y]
			if(strcmp(x.depend[c], targets[y]) != 0){
				target++;
			}
			//store index of targets[y] to use l8r
			if(strcmp(x.depend[c], targets[y]) == 0)
				targ_match = y;
		}
		//dependancy was in target
		if(target != tcount){
			execute_help(targets, targ_match, target_hit, tcount,dcount);
			//printf("x.depend[c] %s\n",x.depend[c] );

		}
		else if(target == tcount){//dependancy not in target
			tar_to_exec[count_for_targ] = x.name;
			count_for_targ++;
			target_hit[targ] = 1;
		}
	}
	//more than 1 dependancy, sees what dependancy is also
	//a target if any and either adds to list of targets to execute or calls execute_help again
	if(x.dep_count > 1 && target_hit[targ] == 0){
		//check to see if x.depends[] are in target.depend (x.depend)
		int num = 0;
		//go through each dependancy
		for(int c = 0; c<x.dep_count; c++){
			int target = 0;
			for(int y = 0; y< tcount; y++){
				//if x.depend[c] is not in targets[y]
				if(strcmp(x.depend[c], targets[y]) != 0){
					target++;
				}
			}
			if(target == tcount){//dependancy not in target
				num++;
			}
		}
			if(num == x.dep_count && target_hit[targ] == 0){//all dependancies are not in target
				tar_to_exec[count_for_targ] = x.name;
				count_for_targ++;
				target_hit[targ] = 1;
			}
			else if (num != x.dep_count && target_hit[targ] == 0){// there's at least one dependancy that matches a target
				for(int e = 0; e< x.dep_count; e++){
					for(int f = 0; f< tcount; f++){
						if(strcmp(x.depend[e], targets[f])==0){
							execute_help(targets, f, target_hit, tcount, dcount);
					}
			}
		}
			//will add to list after its dependancies are added
			tar_to_exec[count_for_targ] = x.name;
			count_for_targ++;
			target_hit[targ] = 1;
		}
	}
}


char** get_targ_lst(target x)
{
	node tmp = root;
	char** deps =  x.depend;
	char * targets[LINE_SIZE];
	int target_hit[LINE_SIZE];
	int tcount = 0; //total number of targets
	int dcount = x.dep_count; //total number of dependancies
	while(tmp != NULL){
	//get makefile tmp stores
		target makefile = tmp -> data;
		targets[tcount] = makefile.name;
		target_hit[tcount] = 0;
		tcount++;
		tmp = tmp->next;
	}
	int targ;
	int depen;
	int flag = 0;
	for(depen=0; depen< dcount;depen++){
		for(targ=0; targ< tcount; targ++){
			if(strcmp(deps[depen], targets[targ]) ==0){
				flag++;
				execute_help(targets, targ, target_hit, tcount, dcount);
			}
		}
		//eiter no dependancies or they arent targets
		if(flag == 0){
			tar_to_exec[count_for_targ] = x.name;
		}
	}
	tar_to_exec[count_for_targ]=x.name;
return tar_to_exec;
}

//executes recipes of single target
void execute_target(char *targetname)
{
	int x = 0;
	node r = root;
	//finds correct target in linked list
	while(x == 0 && r != NULL)
	{
		int d = strcmp(r->data.name, targetname);
		if(d == 0)
		{
			x = 1;
		}
		else
		{
			r = r -> next;
		}
	}
	char *delimiter = "\t\n";
	char *delimiter2 = "\t \n";
	pid_t cpid;
	for(int i = 0; i < r->data.recipe_count; i++)
	{
		char **argvp;
		char **path;
		cpid = fork();
		if(cpid == 0)
		{
			makeargv(r->data.recipe[i], delimiter2, &path);
			makeargv(r->data.recipe[i], delimiter, &argvp);
			printf("\n");
			//execute the recipe
			execvp(*path, argvp);
			break;
		}
		wait(NULL);
	}

}

//executes list of targets found from tar_to_exec function
void execute(char** tar_to_exec){
	for(int i = 0; i <= count_for_targ; i++){
		execute_target(tar_to_exec[i]);
	}
}


//Validate the input arguments, bullet proof the program
int main(int argc, char *argv[])
{
	//2 commandline arguments given
	if (argc == 2)
	{
		//$./mymake -p error case
		if(!strncmp(argv[1], "-p", 2))
		{
			printf("Error. Argument '-p' given, but no file target.\n");
			exit(EXIT_FAILURE);
		}
		//$./mymake -r error case
		else if(!strncmp(argv[1], "-r", 2))
		{
			printf("Error. Argument '-r' given, but no file target.\n");
			exit(EXIT_FAILURE);
		}
		//$./mymake Makefile
		else if(process_file(argv[1]) == 0)
		{
			process_into_objects();
			target x = make_block(root->data.name);
			execute(get_targ_lst(x));
		}
		else
		{
			printf("Error with filename: %s. Unable to process file.\n", argv[1]);
			printf("Please check that you have spelled the filename correctly or that it exists.\n");
			exit(EXIT_FAILURE);
		}
	}
	//3 commandline arguments given
	else if (argc == 3)
	{
		//./mymake -p filename
		//checking for -p flag
		if (!strncmp(argv[1], "-p", 2))
		{
			//$./mymake -p Makefile
			if (process_file(argv[2]) == 0)
			{
				process_into_objects();
				p_function();
			}
			else
			{
				printf("Error with filename: %s. Unable to process file with -p argument.\n", argv[2]);
				printf("Please check that you have spelled the filename correctly or that it exists.\n");
				exit(EXIT_FAILURE);
			}
		}
		//./mymake -r filename
		//checking for -r flag
		else if (!strncmp(argv[1], "-r", 2))
		{
			//$./mymake -r Makefile
			if (process_file(argv[2]) == 0)
			{
				process_into_objects();
				r_function();
				//process lines into graph object
				//execute -r argument
				//check return value for error
				//TODO
			}
			else
			{
				printf("Error with file: %s. Unable to process file with -r argument.\n", argv[2]);
				printf("Please check that you have spelled the filename correctly or that it exists.\n");
				exit(EXIT_FAILURE);
			}
		}
		//$./mymake Makefile target
		//checking for specific argument from user. process_file returns -1 when error occurs
		else if (process_file(argv[1]) == 0)
		{
			process_into_objects();
			int x = 0;
		  node r = root;
		  while(x == 0 && r != NULL)
		  {
				int d = strcmp(r->data.name, argv[2]);
		    if(d == 0)
		    {
		      x = 1;
		    }
		    else
		    {
		      r = r -> next;
		    }
		  }
			if(x==0)
			{
				if((strcmp(argv[2], "-p") == 0) || (strcmp(argv[2], "-r") == 0))
				{
					printf("Error, incorrect order for flag.\n");
					printf("Valid flag input is ./mymake -p Makefile or ./mymake -r Makefile\n");
				}
				else
				{
					printf("Error, target not found in file.\n");
				}

				exit(EXIT_FAILURE);
			}
			else
			{
				target x = make_block(argv[2]);
				execute(get_targ_lst(x));
			}
			//process lines into object graph
			//execute specific target in file provided
			//check return value for error
		}
		else
		{
			printf("Error with commandline argument: %s. Unable to process file or execute target. \n", argv[1]);
			printf("Please try again.\n");
			exit(EXIT_FAILURE);
		}
	}
	//more than 3 commandline arguments error
	else
	{
		printf("Error with commandline arguments provided. Expected 2 or 3, found %d.\n", argc);
		printf("Valid inputs are:\n./mymake Makefile [target]\n./mymake Makefile\n./mymake -p Makefile\n./mymake -r Makefile\n");
		exit(EXIT_FAILURE);
	}
	freeMem(root);
	exit(EXIT_SUCCESS);
}
