require 'benchmark'
require_relative 'Trees/avl.rb'

samples = [5000, 10000, 15000, 20000, 25000, 30000, 35000, 40000, 45000, 50000]
round_result = 2

samples.each do |n|
  define_method("reversed_array_#{n}") do
    (10...n).to_a.reverse
  end
end

def write_to_file(path, result, val)
  output = File.open(path, 'a')

  output.puts "Sample size - #{val}"
  output.puts "Stworzenie struktury: " + result[:structure].to_s
  output.puts "Wyszukanie minimum:   " + result[:min].to_s
  output.puts "Wypisanie in-order:   " + result[:in_order].to_s
  output.puts

  output.close
end

5.times do |n|
  samples.each do |val|
    result = {}
    tree = AvlTree.new

    result[:structure] = (Benchmark.realtime do
      tree.construct(send("reversed_array_#{val}"))
    end * 1000).round(round_result)

    result[:min] = (Benchmark.realtime do
      tree.min_node
    end * 1000).round(round_result)

    result[:in_order] = (Benchmark.realtime do
      tree.nodes_inorder
    end * 1000).round(round_result)

    write_to_file("./report_avl.txt", result, val)
  end
end



