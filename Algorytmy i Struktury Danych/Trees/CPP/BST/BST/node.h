//
//  node.h
//  BST
//
//  Created by Tomasz Kasperek on 07.04.2015.
//  Copyright (c) 2015 Tomasz Kasperek. All rights reserved.
//

#ifndef __BST__node__
#define __BST__node__

#include <stdio.h>

class Node
{
public:
    Node(int _value);
    ~Node();
    Node *left;
    Node *right;
    int value;
    
};

#endif /* defined(__BST__node__) */
