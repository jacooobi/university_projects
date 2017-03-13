require 'benchmark'

def read_from_file(path = nil)
  path = gets.chomp if path.nil?
  arr = []

  f = File.open(path, 'r')

  f.each_line do |line|
    line.split(' ').each { |number| arr << number.to_i }
  end

  f.close
  arr
end

def read_from_keyboard
  arr = []

  while n = gets.chomp do
    break if n == 'q'
    arr << n.to_i
  end

  arr
end

def generate_numbers(n)
  arr = []

  n.times do
    arr << Random.rand(1000)
  end

  arr
end

def rand_array(max, size)
  arr = []
  max -= 10

  size.times do
    arr << (10 + Random.rand(max))
  end

  arr
end

def array_of_numbers?(arr)
  return false unless arr.kind_of?(Array)
  arr.all? {|i| i.is_a? Fixnum}
end

def benchmark_sorting(name)
  Benchmark.bm(40) do |x|
    x.report(name) { yield }
  end
end
