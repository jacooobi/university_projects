unit FindClient;

{$mode objfpc}{$H+}

interface

uses
  Classes, SysUtils, FileUtil, Forms, Controls, Graphics, Dialogs, ExtCtrls,
  StdCtrls, linkedlist;

type

  { TSearchForm }

  TSearchForm = class(TForm)
    SearchButton: TButton;
    Edit1: TEdit;
    NameRButton: TRadioButton;
    CityRButton: TRadioButton;
    RadioGroup1: TRadioGroup;
    procedure SearchButtonClick(Sender: TObject);
  private
    { private declarations }
  public
    { public declarations }
    constructor LoadList(list : TSinglyLinkedList);
  private
    List : TSinglyLinkedList;
  end;

var
  SearchForm: TSearchForm;

implementation

uses Main;
{$R *.lfm}

procedure TSearchForm.SearchButtonClick(Sender: TObject);
var
  SearchCryteria, Word : String;
  ResultList : TSinglyLinkedList;
  I,J : Integer;
begin
  Word := Edit1.Text;
  if Word = '' then
  begin
    ShowMessage('Musisz wypelnic pole!');
    Exit;
  end;
  if NameRButton.checked = true then
    SearchCryteria := 'name'
  else
    SearchCryteria := 'city';
  ResultList := List.Find(SearchCryteria, Word);
  showMessage(IntToStr(ResultList.Count) + ' rezultat(ow)');
  for i:= 1 to 6 do
      for j:= 1 to 50 do
        MainForm.StringGrid.Cells[i,j] := '';
  ResultList.ShowOn(MainForm.StringGrid);
  Close;
end;

constructor TSearchForm.LoadList(list : TSinglyLinkedList);
begin
  Self.List := list;
  Create(nil);
end;



end.

