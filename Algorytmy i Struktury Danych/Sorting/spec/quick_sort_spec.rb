require_relative '../lib/quick_sort'

RSpec.describe 'Shell Sort' do
  let(:array) { [8,9,2,3,4,1,0,14] }

  it 'returns sorted array' do
    expect(quick_sort(array)).to eq [0,1,2,3,4,8,9,14]
  end

  it 'returned array has same length' do
    expect(quick_sort(array).length).to eq array.length
  end
end
