//
//  node.cpp
//  BST
//
//  Created by Tomasz Kasperek on 07.04.2015.
//  Copyright (c) 2015 Tomasz Kasperek. All rights reserved.
//

#include "node.h"

Node::Node(int _value)
{
    left = 0;
    right = 0;
    value = _value;
}

Node::~Node()
{
//    left = 0;
//    right = 0;
//    value = NULL;
}