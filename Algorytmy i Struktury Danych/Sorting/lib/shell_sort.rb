def shell_sort(a)
  h = 1
  h = 3 * h + 1 until h > a.length / 3

  while h >= 1
    for i in h...a.length
      j = i
      while j >= h && a[j] < a[j-h]
        a[j], a[j-h] = a[j-h], a[j]
        j -= h
      end
    end

    h /= 3
  end

  a
end


