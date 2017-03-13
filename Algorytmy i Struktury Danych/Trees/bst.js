'use strict';

function Node(val) {
  this.value = val;
  this.left = undefined;
  this.right = undefined;
}

Node.prototype.setRight = function(value) {
  this.right = new Node(value);
};

Node.prototype.setLeft = function(value) {
  this.left = new Node(value);
};

function BinaryTree(value) {
  if(value instanceof Array) {
    this.fromArray(value);
  } else {
    this.root = new Node(value);
  } 
}

BinaryTree.prototype.fromArray = function(arr) {
  this.root = new Node(arr.shift());
  var self = this;
  arr.forEach(function (element) {
    self.append(element);
  });
};

BinaryTree.prototype.append = function(value) {
  var iterator = this.root;
  while(iterator !== undefined) {
    if(iterator.value > value) {
      if(iterator.left !== undefined) {
        iterator = iterator.left;
      } else {
        // iterator.setLeft(value);
        iterator.left = new Node(value);
        return;
      } 
    } else {
      if(iterator.right !== undefined) {
        iterator = iterator.right;
      } else {
        // iterator.setRight(value);
        iterator.right = new Node(value);
        return;
      }
    }
  }
};

BinaryTree.prototype.min = function() {
  var iterator = this.root;
  while(iterator.left !== undefined) {
    iterator = iterator.left;
  }

  return iterator;
};

BinaryTree.prototype.clean = function() {
  this._clean(this.root);
};  

BinaryTree.prototype._clean = function (node) {
  if(node.left) this._clean(node.left);
  if(node.right) this._clean(node.right);
  node = undefined;
};  

var test = new BinaryTree([4,7,3,2,6,5,9]);
test.clean();
console.log(test);
// console.log(test.min().value);