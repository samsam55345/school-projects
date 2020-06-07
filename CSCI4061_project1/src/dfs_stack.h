#ifndef __DFS_STACK_H
#define __DFS_STACK_H

#include <stdlib.h>
#include <stdio.h>
#include "graph.h"

//structure for a linkedlist
struct LinkedList{
  target data;
  struct LinkedList *next;
};

typedef struct LinkedList *node;

//creates a node
node createNode()
{
  node tmp;
  tmp = (node)malloc(sizeof(struct LinkedList));
  tmp -> next = NULL;
  return tmp;
}

//frees whatever memory that the nodes have been using
void freeMem(node root)
{
  node d = root;
  while(d != NULL)
  {
    node tmp = d;
    d = d -> next;
    free(tmp);
  }
}

//adds a node to the linked list
node addNode(node root, target content)
{
  int x = 0;
  node r = root;
  while(x == 0 && r != NULL)
  {
    if(r -> data.name == content.name)
    {
      x = 1;
    }
    else
    {
      r = r -> next;
    }
  }
  if(x != 1)
  {
    node tmp, nextNode;
    tmp = createNode();
    tmp -> data = content;
    if(root == NULL)
    {
      root = tmp;
    }
    else
    {
      nextNode = root;
      while(nextNode->next != NULL)
      {
        nextNode = nextNode->next;
      }
      nextNode -> next = tmp;
    }
  }
  return root;
}


#endif
