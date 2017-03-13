require_relative '../lib/counting_sort'
require_relative '../lib/shared'

RSpec.describe 'Counting sort' do
  let(:array) { [8,9,2,3,4,1,0,14] }
  let(:big_array) { generate_numbers(10000) }

  it 'returns false, instead of array' do
    expect(counting_sort_asc('str')).to eq false
    expect(counting_sort_asc([1, 2, 3, '4'])).to eq false
    expect(counting_sort_asc(1)).to eq false

    expect(counting_sort_desc('str')).to eq false
    expect(counting_sort_desc([1, 2, 3, '4'])).to eq false
    expect(counting_sort_desc(1)).to eq false
  end

  it 'returns sorted (descending order) array' do
    expect(counting_sort_desc(array)).to eq [14,9,8,4,3,2,1,0]
  end

  it 'returned array (descending order) has same length' do
    expect(counting_sort_desc(array).length).to eq array.length
  end

  it 'returns big sorted (descending order) array' do
    expect(counting_sort_desc(big_array)).to eq big_array.sort!.reverse
  end

  it 'returns sorted array' do
    expect(counting_sort_asc(array)).to eq [0,1,2,3,4,8,9,14]
  end

  it 'returns big sorted array' do
    expect(counting_sort_asc(big_array)).to eq big_array.sort!
  end

  it 'returned big array has same length' do
    expect(counting_sort_asc(big_array).length).to eq big_array.length
  end

  it 'returned array has same length' do
    expect(counting_sort_asc(array).length).to eq array.length
  end
end
