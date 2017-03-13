class FestivalsController < ApplicationController
  before_action :set_festival, only: [:show, :edit, :update, :destroy]

  # GET /festivals
  # GET /festivals.json
  def index
    if params[:query].present?
      @festivals = Festival.search(params[:query]).order(params[:order]).order(:id)
    else
      @festivals = Festival.order(params[:order]).order(:id)
    end
  end

  # GET /festivals/1
  # GET /festivals/1.json
  def show
    @tickets = @festival.tickets
    @concerts = @festival.concerts
    sql_statement = "SELECT zlicz_sprzedane_bilety(" + params[:id] + ");"
    @tickets_sold = ActiveRecord::Base.connection.execute(sql_statement).values[0][0].to_i
  end

  def increase_prices
    s_percentage = 10;
    n_percentage = 20;
    sql_statement = "SELECT podwyzka_cen_biletow(" + s_percentage.to_s + "," + n_percentage.to_s + ");"
    ActiveRecord::Base.connection.execute('BEGIN TRANSACTION ISOLATION LEVEL SERIALIZABLE;')
    ActiveRecord::Base.connection.execute(sql_statement)
    ActiveRecord::Base.connection.execute('COMMIT;')

    redirect_to festival_path(params[:id])
  end

  # GET /festivals/new
  def new
    @festival = Festival.new
  end

  # GET /festivals/1/edit
  def edit
  end

  # POST /festivals
  # POST /festivals.json
  def create
    @festival = Festival.new(festival_params)

    respond_to do |format|
      if @festival.save
        format.html { redirect_to @festival, notice: 'Festival was successfully created.' }
        format.json { render :show, status: :created, location: @festival }
      else
        format.html { render :new }
        format.json { render json: @festival.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /festivals/1
  # PATCH/PUT /festivals/1.json
  def update
    respond_to do |format|
      if @festival.update(festival_params)
        format.html { redirect_to @festival, notice: 'Festival was successfully updated.' }
        format.json { render :show, status: :ok, location: @festival }
      else
        format.html { render :edit }
        format.json { render json: @festival.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /festivals/1
  # DELETE /festivals/1.json
  def destroy
    @festival.destroy
    respond_to do |format|
      format.html { redirect_to festivals_url, notice: 'Festival was successfully destroyed.' }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_festival
      @festival = Festival.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def festival_params
      params.require(:festival).permit(:name, :edition, :start_date, :end_date, :id)
    end
end
