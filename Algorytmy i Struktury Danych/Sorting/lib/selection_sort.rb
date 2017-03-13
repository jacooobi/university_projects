require_relative 'shared.rb'

def selection_sort(a)
  for i in 0...(a.length - 1)
    min = i

    for j in (i+1)...(a.length)
      min = j if a[j] < a[min]
    end

    a[i], a[min] = a[min], a[i] if min != i
  end

  a
end
