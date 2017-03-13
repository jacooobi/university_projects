require_relative 'shared'

def merge_sort(arr)
  return false unless array_of_numbers?(arr)

  return arr if arr.size <= 1
  left = merge_sort arr[0, arr.size / 2]
  right = merge_sort arr[arr.size / 2, arr.size]

  merge(left, right)
end

def merge(left, right)
  res = []

  while left.size > 0 and right.size > 0
    if left[0] <= right[0]
      res << left.shift
    else
      res << right.shift
    end
  end

  res.concat(left).concat(right)
end