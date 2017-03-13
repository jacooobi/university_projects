module ApplicationHelper

  def sorting_direction(name)
    if params['name'] == name && params['order'] == 'asc'
      'desc'
    else
      'asc'
    end
  end
end
