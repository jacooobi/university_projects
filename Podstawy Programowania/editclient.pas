unit EditClient;

{$mode objfpc}{$H+}

interface

uses
  Classes, SysUtils, FileUtil, Forms, Controls, Graphics, Dialogs, StdCtrls,
  LinkedList;

type

  { TEditForm }

  TEditForm = class(TForm)
    SaveButton: TButton;
    Nickname, Date, City, TicketPrice, TicketsSold, TotalExpenses: TEdit;
    TitleLabel: TLabel;
    constructor LoadList(List : TSinglyLinkedList; I : Integer);
    procedure LoadFields(El : PElement);
    procedure SaveButtonClick(Sender: TObject);
  private
    { private declarations }
  public
    { public declarations }
    List : TSinglyLinkedList;
    Index : Integer;
  end;

var
  EditForm: TEditForm;

implementation
uses Main;
{$R *.lfm}

constructor TEditForm.LoadList(List : TSinglyLinkedList; I: Integer);
begin
  Self.List := List;
  Self.Index := I;
  Create(nil);
end;

procedure TEditForm.LoadFields(El : PElement);
begin
  Nickname.Text      := El^.Name ;
  Date.Text          := El^.Date ;
  City.Text          := El^.City ;
  TicketPrice.Text   := IntToStr(El^.TicketPrice) ;
  TicketsSold.Text   := IntToStr(El^.TicketsSold) ;
  TotalExpenses.Text := IntToStr(El^.TotalExpenses) ;
end;

procedure TEditForm.SaveButtonClick(Sender: TObject);
var
  El : PElement;
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

  El := List.GetElement(Index-1);
  El^.Name          := Nickname.Text;
  El^.Date          := Date.Text;
  El^.City          := City.Text;
  El^.TicketPrice   := StrToInt(TicketPrice.Text);
  El^.TicketsSold   := StrToInt(TicketsSold.Text);
  El^.TotalExpenses := StrToInt(TotalExpenses.Text);
  Close;
end;

end.

