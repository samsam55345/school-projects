 test machine: CSEL-KH1260-09
 date: 10/09/19
 name: Orion Grebe, Sami Frank
 x500: grebe013, fran0942


# 4061_project1
project 1 for 4061

Purpose:
  - to parse through a makefile, retrieve the targets, dependencies,
    and recipes, then based on user input, print the information to
    do with those to the terminal or execute their recipes.

Compile:
  - Simply ender gcc main.c into the terminal and hit enter.

To specifically get into what the project does:
  - process_into_objects()
    - processes the file from the user after going through process_file
      into a linked list of target objects.
  - p_function()
    - has helper functions
      - p_function_helper(target makefile)
    - prints the target's, how many dependencies and recipes they have, as
      well as the names of them. Format is as follows.
        target ’all’ has 2 dependencies and 1 recipe
          Dependency 0 is dep1
          Dependency 1 is dep2
          Recipe 0 is ls -l
  - r_function()
    - creates a list of all targets, and all dependancies, loops through
    checks to see if target and dependancy match and if they do call the r_help function
    - has helper functions
      - make_block(char* name) - gets specific struct given a target name
      - r_help(char** targets, int targ, int * target_hit, int tcount,int dcount)
      - PARAMS:
          targets- list of target names
          targ- the index of the target we're checking
          target_hit- a list containing either a 0 or 1 to indicate if the recipe has been printed
          tcount- total number of targets
          dcount-total number of dependancies
    - prints the recipes of the makefile in order of need for execution
  - execute()
    - for however many targets exist, execute the recipes in order.
    - tar_to_exec[] has already been filled with the dependcy order
    - calls execute_target(targetname), executing the recipes of target
  - get_targ_lst()
      - gets a list targets to execute in order of when to execute
      - uses execute_help()
          - figures out how to list the targets in order
  - dfs_stack
    - linkedlist structure

Assumptions:
  - lines either started with a /t character or a regular character
  - lines ended with NULL
  - at least 1 target in file
  - targets have either 0 or more dependencies

Orion contributions:
  - programmed process_into_objects()
  - programmed p_function()
  - programmed main()
  - programmed execute_target()
  - programmed dfs_stack.h

 Sami contributions:
  - programmed r_function()
  - programmed r_help()
  - programmed execute()
  - programmed execute_help()
  - programmed get_targ_lst()
