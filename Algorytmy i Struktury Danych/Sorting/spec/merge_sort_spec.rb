require_relative '../lib/merge_sort'

RSpec.describe 'Merge Sort' do
  let(:array) { [8,9,2,3,4,1,0,14] }
  let(:odd_array) { [8,9,2,3,4,1,12,0,14] }

  it 'returns false, instead of array' do
    expect(merge_sort('str')).to eq false
    expect(merge_sort([1, 2, 3, '4'])).to eq false
    expect(merge_sort(1)).to eq false
  end

  it 'returns sorted array' do
    expect(merge_sort(array)).to eq [0,1,2,3,4,8,9,14]
    expect(merge_sort(odd_array)).to eq [0,1,2,3,4,8,9,12,14]
  end

  it 'returned array has same length' do
    expect(merge_sort(array).length).to eq array.length
  end
end
