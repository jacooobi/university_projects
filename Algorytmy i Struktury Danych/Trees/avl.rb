class AvlTree
  attr_accessor :root

  def initialize
    @root = nil
  end

  def construct(arr)
    arr.each { |a| add(a) }
  end

  def add(v)
    n = AvlNode.new(v)
    insert_avl(@root, n)
  end

  def remove(v)
    remove_avl(@root, v)
  end

  def nodes_inorder
    ret = []
    inorder(root, ret)
    ret
  end

  def remove_nodes(n, arr)
    arr.each do |a|
      remove(a)
    end
  end

  def min_node
    node = @root
    until node.left.nil?
      node = node.left
    end
    node.value
  end

  def remove_post_order(node)
    unless node.nil?
      remove_post_order(node.left)
      remove_post_order(node.right)
      remove_no_balance(node) unless node == self.root
    end
  end

  private

  def insert_avl(node, new_node)
    if node.nil?
      @root = new_node
    else
      if new_node.value < node.value
        if node.left.nil?
          node.left = new_node
          new_node.parent = node

          recursive_balance(node)
        else
          insert_avl(node.left, new_node)
        end
      else
        if new_node.value > node.value
          if node.right.nil?
            node.right = new_node
            new_node.parent = node

            recursive_balance(node)
          else
            insert_avl(node.right, new_node)
          end
        end
      end
    end
  end

  def update_balance(cur)
    cur.balance = height(cur.right) - height(cur.left)
    cur.balance
  end

  def recursive_balance(current_node)
    balance = update_balance(current_node)

    case balance
      when -2
        current_node = (height(current_node.left.left) >= height(current_node.left.right)) ? rotate_right(current_node) : double_rotate_left_right(current_node)
      when 2
        current_node = (height(current_node.right.right) >= height(current_node.right.left)) ? rotate_left(current_node) : double_rotate_right_left(current_node)
    end

    if current_node.parent.nil?
      @root = current_node
    else
      recursive_balance(current_node.parent)
    end
  end

  def remove_avl(node, value)
    unless node.nil?
      remove_avl(node.left, value) if node.value > value
      remove_avl(node.right, value) if node.value < value
      remove_found_node(node) if (node.value == value)
    end
  end

  def remove_found_node(node)
    if node.left.nil? or node.right.nil?
      if node.parent.nil?
        @root = nil
        node = nil
        return
      end
      r = node
    else
      r = successor(node)
      node.value = r.value
    end

    if r.left.nil?
      p = r.right
    else
      p = r.left
    end

    unless p.nil?
      p.parent = r.parent
    end

    if r.parent.nil?
      this.root = p
    else
      if r == r.parent.left
        r.parent.left = p
      else
        r.parent.right = p
      end
    end

    recursive_balance(r.parent)
    r = nil
  end

  def remove_no_balance(node)
    if node.left.nil? or node.right.nil?
      if node.parent.nil?
        @root=nil
        node=nil
        return
      end
      r = node
    end

    if r.left.nil?
      p = r.right
    else
      p = r.left
    end

    unless p.nil?
      p.parent = r.parent
    end

    if r.parent.nil?
      this.root = p
    else
      if r == r.parent.left
        r.parent.left = p
      else
        r.parent.right = p
      end
    end
    r = nil
  end

  def rotate_left(node)
    v = node.right # wez srodkowy
    v.parent = node.parent # przypisz do rodzica poczatkowego
    node.right = v.left # przypisanie lewego dziecka v jako prawe node'a

    node.right.parent = node unless node.right.nil?

    v.left = node # zamiana a \ b \ c - b / a \ c
    node.parent = v

    unless v.parent.nil?
      v.parent.right = v if v.parent.right==node
      v.parent.left = v if v.parent.left==node
    end

    update_balance(node)
    update_balance(v)
    v
  end

  def rotate_right(node)
    v = node.left
    v.parent = node.parent
    node.left = v.right

    node.left.parent=node unless node.left.nil?

    v.right = node
    node.parent = v

    unless v.parent.nil?
      v.parent.right = v if v.parent.right==node
      v.parent.left = v if v.parent.left==node
    end

    update_balance(node)
    update_balance(v)
    v
  end

  def double_rotate_left_right(node)
    node.left = rotate_left(node.left)
    rotate_right(node)
  end

  def double_rotate_right_left(node)
    node.right = rotate_right(node.right)
    rotate_left(node)
  end

  def successor(node)
    if node.right.nil?
      p = node.parent
      while not p.nil? and node == p.right
        node = p
        p = node.parent
      end
      p
    else
      r = node.right
      until r.left.nil?
        r = r.left
      end
      r
    end
  end

  def height(current_node)
    return -1 if current_node.nil?

    if current_node.left.nil? and current_node.right.nil?
      0
    else
      if current_node.left.nil?
        return 1 + height(current_node.right)
      else
        if current_node.right.nil?
          return 1 + height(current_node.left)
        else
          return 1 + maximum(height(current_node.left), height(current_node.right))
        end
      end
    end
  end

  def maximum(a, b)
    (a >= b) ? a : b
  end

  def inorder(node, out_list)
    unless node.nil?
      inorder(node.left, out_list)
      out_list.push(node.value)
      inorder(node.right, out_list)
    end
  end
end

class AvlNode
  attr_accessor :left, :right, :balance, :value, :parent

  def initialize(k)
    @left = nil
    @right = nil
    @parent = nil
    @balance = 0
    @value = k
  end
end


# tree = AvlTree.new
# tree.construct([6,8,1,3,10,167,382])
# puts tree.nodes_inorder
# puts tree.min_node
# tree.remove_nodes(4,[1,3,399,382])
# puts tree.nodes_inorder
# tree.remove_post_order(tree.root)
# puts tree.root.value


