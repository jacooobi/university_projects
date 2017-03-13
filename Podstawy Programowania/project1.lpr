program project1;

{$mode objfpc}{$H+}

uses
  {$IFDEF UNIX}{$IFDEF UseCThreads}
  cthreads,
  {$ENDIF}{$ENDIF}
  Interfaces, // this includes the LCL widgetset
  Forms, Main, FindClient, LinkedList, dialogs, Concert, AddClient, EditClient, Windows;

{$R *.res}
var
  Mutex : THandle;

begin
  Mutex := CreateMutex(nil, True, 'OneInstance');
  if (GetLastError = ERROR_ALREADY_EXISTS) then
  begin
    MessageDlg('Program jest juz wlaczony', TMsgDlgType.mtError, [mbOK], 0);
    halt;
  end
  else
  begin
    RequireDerivedFormResource := True;
    Application.Initialize;
    Application.CreateForm(TMainForm, MainForm);
    MainForm.List := TSinglyLinkedList.Create;
    MainForm.List.LoadFromFile();
    Application.Run;
  end;
end.

