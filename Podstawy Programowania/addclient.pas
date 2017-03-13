unit AddClient;

{$mode objfpc}{$H+}

interface

uses
  Classes, SysUtils, FileUtil, Forms, Controls, Graphics, Dialogs,
  StdCtrls, ExtCtrls, Grids, LinkedList;

type

  { TAddForm }

  TAddForm = class(TForm)
    Button1: TButton;
    TitleLabel: TLabel;
    Nickname, Date, City, TicketPrice, TicketsSold, TotalExpenses: TEdit;
    constructor LoadList(List : TSinglyLinkedList);
    procedure Button1Click(Sender: TObject);
  private
    List : TSinglyLinkedList;
    { private declarations }
  public
    { public declarations }
  end;

var
  AddForm: TAddForm;

implementation

uses Main;
{$R *.lfm}

{ TAddForm }

constructor TAddForm.LoadList(List : TSinglyLinkedList);
begin
  Self.List := List;
  Create(nil);
end;

procedure TAddForm.Button1Click(Sender: TObject);
var
  EmptyFields: boolean;
begin
  if ValidatePresence(Nickname.Text, Date.Text, City.Text, TicketPrice.Text, TicketsSold.Text, TotalExpenses.Text) = false then
  begin
    showMessage('Wszystkie pola nie zostaly wypelnione!');
    exit;
  end;
  if ValidateNumbers(TicketPrice.Text, TicketsSold.Text, TotalExpenses.Text) = false then
  begin
    showMessage('Ostatnie trzy pola musza byc dodatnimi liczbami!');
    exit;
  end;
  if ValidateCity(City.Text) = false then
  begin
    showMessage('Niepoprawne miasto. Wybierz Poznan,Warszawe,Wroclaw,Krakow lub Szczecin');
    exit;
  end;

  List.Add(Nickname.Text, Date.Text, City.Text, StrToInt(TicketPrice.Text), StrToInt(TicketsSold.Text), StrToInt(TotalExpenses.Text));
  Close;
end;

end.

