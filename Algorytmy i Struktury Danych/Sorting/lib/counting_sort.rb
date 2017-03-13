require_relative 'shared'

def get_counts_array(arr)
  max = arr.max

  counts = Array.new(max + 1, 0)
  arr.each {|n| counts[n] += 1 }
  (1..max).each {|n| counts[n] += counts[n - 1]}

  counts  
end

def get_first_counts_array(arr)
  max = arr.max

  counts = Array.new(max + 1, 0)
  arr.each {|n| counts[n] += 1 }

  counts
end

def counting_sort_asc(arr)
  return false unless array_of_numbers?(arr)

  counts = get_counts_array(arr)
  result = []

  (arr.size - 1).downto(0).each do |n|
    result[counts[arr[n]] - 1] = arr[n]
    counts[arr[n]] -= 1
  end

  result
end

def counting_sort_desc(arr)
  return false unless array_of_numbers?(arr)

  counts = get_counts_array(arr)
  result = []

  (arr.size - 1).downto(0).each do |n|
    index = -(counts[arr[n]] - arr.size)
    result[index] = arr[n]
    counts[arr[n]] -= 1
  end

  result
end