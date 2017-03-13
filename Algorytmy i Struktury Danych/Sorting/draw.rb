require 'gruff'

def theme
  {
    colors: ['#25a933', '#ffcf0d', '#ff9900', '#0c78cc'],
    marker_color: '#dddddd',
    font_color: 'black',
    background_colors: 'white'
  }
end

def four_algs
  g = Gruff::Line.new
  g.line_width = 2
  g.dot_radius = 3
  g.y_axis_label = 'czas'
  g.x_axis_label = 'ilosc elementow'
  g.marker_font_size = 15.0
  g.theme = theme
  g.dataxy('Selection Sort', [[1, 1], [3, 2], [4, 3], [5, 4], [6, 4], [10, 3]])
  g.dataxy('Merge Sort', [[1, 1], [3, 1], [4, 2], [5, 2], [7, 3], [9, 3]])
  g.dataxy('Shell Sort', [[1, 1], [2, 2], [5, 6], [11, 11]])
  g.dataxy('Quick Sort', [[1, 1], [2, 3], [5, 8], [11, 21], [11, 22.5], [11, 19.5]])
  g.labels = {0 => '25 000', 2 => '50 000', 4 => '75 000', 6 => '100 000', 8 => '125 000', 10 => '150 000'}
  g.write("4_algs.png")
end

def single_alg
  %w(selection merge shell quick).each do |alg|
    g = Gruff::Line.new
    g.title = alg.capitalize
    g.line_width = 2
    g.dot_radius = 3
    g.y_axis_label = 'czas'
    g.x_axis_label = 'ilosc elementow'
    g.marker_font_size = 15.0
    g.theme = theme
    g.dataxy('dane rosnace', [[1, 1], [3, 2], [4, 3], [5, 4], [6, 4], [10, 3]])
    g.dataxy('dane malejace', [[1, 1], [3, 1], [4, 2], [5, 2], [7, 3], [9, 3]])
    g.dataxy('dane losowe', [[1, 1], [2, 2], [5, 6], [11, 11]])
    g.labels = {0 => '25 000', 2 => '50 000', 4 => '75 000', 6 => '100 000', 8 => '125 000', 10 => '150 000'}
    g.write("1_#{alg}.png")
  end
end

four_algs
single_alg
