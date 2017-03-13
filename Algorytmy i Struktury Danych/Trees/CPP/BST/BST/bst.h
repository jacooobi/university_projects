//
//  bst.h
//  BST
//
//  Created by Tomasz Kasperek on 07.04.2015.
//  Copyright (c) 2015 Tomasz Kasperek. All rights reserved.
//

#ifndef __BST__bst__
#define __BST__bst__

#include <stdio.h>
#include <iostream>
#include "node.h"

class BST
{
public:
    BST();
    ~BST();
    void add(int value);
    Node *search(int value);
    void remove(int value);
    void remove(Node *node);
    void show(Node *node);
    void print_sorted();
    void in_order(Node *node);
    void show();
    void clean();
    void from_array(int *array, int len);
    void clean(Node *node);
    int height(Node *node);
    int balance_factor(Node *node);
    void findForRemoval(Node*& node, int value);
    void balance();
    void print_root();
    Node *min();
private:
    Node *root;
    Node *removeNode(Node *node);
    bool mBalancing;
};

#endif /* defined(__BST__bst__) */
