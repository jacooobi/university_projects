//
//  bst.cpp
//  BST
//
//  Created by Tomasz Kasperek on 07.04.2015.
//  Copyright (c) 2015 Tomasz Kasperek. All rights reserved.
//

#include "bst.h"

using namespace std;

BST::BST()
{
    root = 0;
}

BST::~BST() {}

void BST::add(int value)
{
    if(root == 0) {
        root = new Node(value);
        return;
    }
    
    Node *iterator = root;
    
    while(iterator!=0) {
        if(iterator->value > value) {
            if(iterator->left != 0) {
                iterator = iterator->left;
            } else {
                iterator->left = new Node(value);
                return;
            }
        } else {
            if(iterator->right != 0) {
                iterator = iterator->right;
            } else {
                iterator->right = new Node(value);
                return;
            }
        }
    }
}

Node *BST::search(int value)
{
    Node *iterator = root;
    while(iterator!=0) {
        if(iterator->value == value) {
            return iterator;
        } else {
            if(iterator->value > value) {
                iterator = iterator->left;
            } else {
                iterator = iterator->right;
            }
        }
    }
    
    return 0;
}

Node *BST::min()
{
    Node *iterator = root;
    while(iterator->left != 0) {
        iterator = iterator->left;
    }
    
    return iterator;
}

void BST::remove(int value)
{
    if( root->value == value) {
        root = removeNode(root);
    } else {
        Node *iterator = root;
        
        while(iterator != 0) {
            if(iterator->value > value) {
                if(iterator->left && iterator->left->value == value) {
                    iterator->left = removeNode(iterator->left);
                    break;
                }
                else {
                    iterator = iterator->left;
                }
            }
            else {
                if(iterator->right && iterator->right->value == value) {
                    iterator->right = removeNode(iterator->right);
                    break;
                }
                else {
                    iterator = iterator->right;
                }
            }
        }
    }
}

void BST::print_sorted()
{
    if(root == 0) return;
//    cout << "[ ";
    in_order(root);
//    cout << "]" << endl;
}

void BST::in_order(Node *node)
{
    if(node->left != 0) in_order(node->left);
    node->value;
    if(node->right != 0) in_order(node->right);
}

Node *BST::removeNode(Node *node)
{
    Node *returnedNode;
    Node *iterator;
    Node *parent;
    
    if(node->left == 0 && node->right == 0) returnedNode = 0;
    else if(node->left != 0 && node->right == 0) returnedNode = node->left;
    else if(node->left == 0 && node->right != 0) returnedNode = node->right;
    else {
        parent = node;
        iterator = node->right;
        
        while(iterator->left != 0) {
            parent = iterator;
            iterator = iterator->left;
        }
        
        returnedNode = iterator;
        returnedNode->left = node->left;
        parent->left = returnedNode->right;
        
        if (node->right != returnedNode) returnedNode->right = node->right;
        else returnedNode->right = 0;
    }
    
    delete node;
    return returnedNode;
}

void BST::clean()
{
    clean(root);
    root = 0;
}

void BST::clean(Node *node)
{
    if(node == NULL) return;
    clean(node->left);
    clean(node->right);
    
    delete node;
}

void BST::print_root()
{
    if (root == 0) return;
    cout << root->value << endl;
}

void BST::show(Node *node)
{
    if(node != 0) {
        cout << node->value << endl;
        show(node->left);
        show(node->right);
    }
}

int BST::height(Node *node)
{
    int h = 0;
    if (node) {
        int left_height = this->height(node->left);
        int right_height = this->height(node->right);
        h = max(left_height, right_height) + 1;
    }
    return h;
}

int BST::balance_factor(Node *node)
{
    int lHeight = height(node->left);
    int rHeight = height(node->right);
    int balanceFactor = lHeight - rHeight;
    
    return balanceFactor;
}

void BST::balance()
{
    int balance = balance_factor(root);
    if (!mBalancing)
    {
        while (balance > 1 || balance < -1)
        {
            cout << "balancing " << balance << endl;
            mBalancing = true;
            int root_value = root->value;
            remove(root);
            add(root_value);
            balance = balance_factor(root);
            mBalancing = false;
        }
    }
}

void BST::findForRemoval(Node*& node, int value)
{
    if (node == NULL) 	// empty tree or not in the tree
        return;
    if (value < node->value)          // left
        findForRemoval(node->left, value);
    else if (value > node->value)     // right
        findForRemoval(node->right, value);
    else                            // removing
        remove(node);
}


void BST::remove(Node *node)
{
    Node* temp;
    temp = node;
    if (node->left == NULL)
    {
        node = node->right;
        delete temp;
    }
    else if (node->right == NULL)
    {
        node = node->left;
        delete temp;
    }
    else // both left & right exist
    {
        temp = node->left;
        while (temp->right != NULL)
            temp = temp->right;
        node->value = temp->value;
        findForRemoval(node->left, temp->value);
    }
}

void BST::show()
{
    show(root);
}

void BST::from_array(int *array, int len)
{
    for(int i = 0; i < len; i++) {
        add(array[i]);
    }
}