module BST
  class Node
    attr_reader :value
    attr_accessor :left, :right

    def initialize(val)
      @value = val
      @left = nil
      @right = nil
    end

  end

  class BinaryTree
    attr_reader :root

    def initialize(val)
      if val.kind_of?(Array)
        val = from_array(val)
      else
        @root = Node.new(val)
      end
    end

    def min
      iterator = root
      while iterator.left != nil
        iterator = iterator.left
      end

      iterator
    end

    def from_array(arr)
      @root = Node.new(arr.shift)
      arr.each do |value|
        append(value)
      end
    end

    def append(val)
      iterator = root
      while iterator != nil
        if iterator.value > val
          if iterator.left != nil
            iterator = iterator.left
          else
            iterator.left = Node.new(val)
            return
          end
        else
          if iterator.right != nil
            iterator = iterator.right
          else
            iterator.right = Node.new(val)
            return 
          end
        end
      end
    end
    alias_method :<<, :append

  end
end

example = [4,6,9,5,7,3,1]

tree = BST::BinaryTree.new(example)
puts tree.min.value