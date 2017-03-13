unit Main;

{$mode objfpc}{$H+}

interface

uses
  Classes, SysUtils, FileUtil, Forms, Controls, Graphics, Dialogs, StdCtrls,
  ExtCtrls, Grids, FindClient, LinkedList, AddClient, EditClient, MyColorButton;

type

  { TMainForm }

  TMainForm = class(TForm)
    SortButton: TButton;
    AddButton: TButton;
    EditButton: TButton;
    DeleteButton: TButton;
    FileSaveButton: TButton;
    FileReadButton: TButton;
    SearchButton: TMyColorButton;
    StringGrid: TStringGrid;
    procedure SortButtonClick(Sender: TObject);
    procedure AddButtonClick(Sender: TObject);
    procedure EditButtonClick(Sender: TObject);
    procedure DeleteButtonClick(Sender: TObject);
    procedure FileSaveButtonClick(Sender: TObject);
    procedure FileReadButtonClick(Sender: TObject);
    procedure FormCreate(Sender: TObject);
    procedure SearchButtonClick(Sender: TObject);
  private
    { private declarations }
  public
    { public declarations }
    List: TSinglyLinkedList;
  end;

function ValidatePresence(Name,Date,City,Sold,Price,Total: String): boolean; external 'validator.dll';
function ValidateNumbers(Sold,Price,Total: String): boolean; external 'validator.dll';
function ValidateCity(City: String): boolean; external 'validator.dll';

var
  MainForm: TMainForm;

implementation

{$R *.lfm}

{ TMainForm }

procedure TMainForm.FormCreate(Sender: TObject);
begin
  Self.List := TSinglyLinkedList.Create;
  List.LoadFromFile();
  List.ShowOn(StringGrid);
end;

procedure TMainForm.SearchButtonClick(Sender: TObject);
var
  Form : TSearchForm;
begin
  if List.Count = 0 then
    Exit;
  Form := TSearchForm.LoadList(List);
  Form.showModal;
end;

procedure TMainForm.AddButtonClick(Sender: TObject);
var
  Form : TAddForm;
begin
  Form := TAddForm.LoadList(List);
  Form.ShowModal;
  List.ShowOn(StringGrid);
  Form.Free;
end;

procedure TMainForm.SortButtonClick(Sender: TObject);
begin
  List.Sort();
  List.ShowOn(StringGrid);
end;

procedure TMainForm.EditButtonClick(Sender: TObject);
var
  Form : TEditForm;
  i : integer;
  el : PElement;
begin
  i := StringGrid.Row;
  Form := TEditForm.LoadList(List, i);
  el := List.GetElement(i-1);
  Form.loadfields(el);
  Form.showModal;
  List.ShowOn(StringGrid);
  Form.free;
end;

procedure TMainForm.DeleteButtonClick(Sender: TObject);
var
  bs,i,j: integer;
begin
  if List.Count = 0 then
    Exit;
  bs := MessageDlg('Usunac zaznaczony rekord?', mtConfirmation,
    mbOKCancel, 0);
  if bs = mrOK then
  begin
    List.Delete(StringGrid.Row);
    for i:= 1 to 6 do
      for j:= 1 to 50 do
        StringGrid.Cells[i,j] := '';
    List.ShowOn(StringGrid);
  end;
end;

procedure TMainForm.FileSaveButtonClick(Sender: TObject);
begin
  List.savetoFile;
end;

procedure TMainForm.FileReadButtonClick(Sender: TObject);
begin
  List.LoadFromFile();
  List.ShowOn(StringGrid);
end;

end.

