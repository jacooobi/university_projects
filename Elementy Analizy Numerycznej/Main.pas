unit Main;

interface

uses
  Winapi.Windows, Winapi.Messages, System.SysUtils, System.Variants, System.Classes, Vcl.Graphics,
  Vcl.Controls, Vcl.Forms, Vcl.Dialogs, Vcl.ComCtrls, Vcl.StdCtrls, IntervalArithmetic32and64,
  Vcl.Grids, Vcl.ValEdit, Vcl.ExtCtrls, uTExtendedX87, UnitJacobi, UnitIntervalJacobi, System.Math;
type extended = TExtendedX87;

type
  TmainForm = class(TForm)
    variablesBox: TGroupBox;
    resultBox: TGroupBox;
    controlBox: TGroupBox;
    newEquationButton: TButton;
    resultButton: TButton;
    clearButton: TButton;
    equationList: TListView;
    outcomeList: TListView;
    variableEditBox: TGroupBox;
    variableEditControlBox: TGroupBox;
    variableEditConfirm: TButton;
    variableEditCancel: TButton;
    variableEditListEditor: TValueListEditor;
    settingsBox: TRadioGroup;
    modeI: TRadioButton;
    modeII: TRadioButton;
    modeIII: TRadioButton;
    variableEditListInterval: TGroupBox;
    variableEditIntervalGrid: TStringGrid;

    procedure clearButtonClick(Sender: TObject);
    procedure newEquationButtonClick(Sender: TObject);
    procedure resultButtonClick(Sender: TObject);
    procedure equationListDblClick(Sender: TObject);
    procedure variableEditCancelClick(Sender: TObject);
    procedure variableEditConfirmClick(Sender: TObject);
    procedure modeIClick(Sender: TObject);
    procedure modeIIClick(Sender: TObject);
    procedure modeIIIClick(Sender: TObject);
  private
    { Private declarations }
  public
    { Public declarations }
  end;

var
  mainForm: TmainForm;
  variableCount: integer;
  variableArray: matrix;
  vectorArray: vector;
  intervalTempVecArray: intervalVector;
  intervalTempVarArray: intervalMatrix;
  intervalVariableArray: intervalMatrix;
  intervalVectorArray: intervalVector;
  intervalTempResArray: intervalVector;
  mode: integer;
  maxIter: integer;
  tempIter, tempEps: interval;
  eps: Extended;

implementation

{$R *.dfm}
{$R+}

procedure TmainForm.resultButtonClick(Sender: TObject);
var
  i,j,iter: integer;
  resultArray: vector;
  resultIntervalArray: intervalVector;
  st:integer;
  l,r:string;
  text: string;
begin
  //eps:=1e-14;
  iter:=0;
  if mode=0 then begin
    setLength(variableArray, variableCount+1, variableCount+1);
    setLength(vectorArray, variableCount+1);
    setLength(resultArray, variableCount+1);
    for i := 0 to variableCount-1 do begin
      for j := 4 to variableCount+3 do
          variableArray[i+1][j-3]:=equationList.Items[j].SubItems[i].ToExtended();
      resultArray[i+1]:=equationList.Items[2].SubItems[i].ToExtended();
      vectorArray[i+1]:=equationList.Items[3].SubItems[i].ToExtended();
    end;
    maxIter:=equationList.Items[0].SubItems[0].ToInteger();
    eps:=equationList.Items[1].SubItems[0].ToExtended();

    Jacobi(variableCount, variableArray, vectorArray, maxIter, eps, resultArray, iter, st);
    if((st=0) or (st=3)) then begin
    for i := variableCount downto 1 do begin
      str(resultArray[i],text);
      outcomeList.Items[i-1].SubItems[0]:=text;
      end;
    str(iter, text);
    outcomeList.Items[0].SubItems[2]:=text;
    str(st, text);
    outcomeList.Items[0].SubItems[3]:=text;
    end else if(st=1) then begin
      MessageBox(Handle,'Liczba zmiennych jest mniejsza od zera!', 'Uwaga', MB_OK);
    end else if(st=2) then begin
      MessageBox(Handle,'Macierz wspó³czynników jest osobliwa!', 'Uwaga', MB_OK);
    end;
   end else if((mode=1) or (mode=2)) then begin
     setLength(intervalVariableArray, variableCount+1, variableCount+1);
     setLength(intervalVectorArray, variableCount+1);
     setLength(resultIntervalArray, variableCount+1);
     for i := 0 to variableCount-1 do
     begin
       for j := 0 to variableCount-1 do begin
         intervalVariableArray[i+1][j+1]:=intervalTempVarArray[i][j];
       end;
       intervalVectorArray[i+1]:=intervalTempVecArray[i];
       resultIntervalArray[i+1]:=intervalTempResArray[i];
     end;
     IntervalJacobi(variableCount, intervalVariableArray, intervalVectorArray, maxIter, eps, resultIntervalArray, iter, st);
     if((st=0) or (st=3)) then begin
      for i := variableCount downto 1 do begin
        iends_to_strings(resultIntervalArray[i],l,r);
        outcomeList.Items[i-1].SubItems[0]:=l+';'+r;
        str(int_width(resultIntervalArray[i]),text);
        outcomeList.Items[i-1].SubItems[1]:=text;
      end;
      str(iter,text);
      outcomeList.Items[0].SubItems[2]:=text;
      str(st, text);
      outcomeList.Items[0].SubItems[3]:=text;
     end else if(st=1) then begin
      MessageBox(Handle,'Liczba zmiennych jest mniejsza od zera!', 'Uwaga', MB_OK);
     end else if(st=2) then begin
      MessageBox(Handle,'Macierz wspó³czynników jest osobliwa!', 'Uwaga', MB_OK);
     end;
   end;
end;

procedure TmainForm.variableEditCancelClick(Sender: TObject);
begin
  modeI.Enabled:=true;
  modeII.Enabled:=true;
  modeIII.Enabled:=true;
  variablesBox.Visible:=true;
  resultBox.Visible:=true;
  controlBox.Visible:=true;
  variableEditBox.Visible:=false;
  variableEditControlBox.Visible:=false;
  variableEditListInterval.Visible:=false;
  settingsBox.Enabled:=true;
end;

procedure TmainForm.variableEditConfirmClick(Sender: TObject);
var
  i: integer;
  tmp: interval;
begin
  if(mode=0) then begin
    for i := 0 to variableCount-1 do begin
      equationList.Selected.SubItems[i]:=variableEditListEditor.Values['Równanie '+FloatToStr(i+1)];
    end;
    variableEditBox.Visible:=false;
  end else if(mode=1) then begin
    if (equationList.Selected.Index>1) then begin
      for i := 0 to variableCount-1 do begin
        tmp:=int_read(variableEditListEditor.Values['Równanie '+FloatToStr(i+1)]);
        if(equationList.Selected.Index=2) then begin
          intervalTempResArray[i]:= tmp;
        end else if (equationList.Selected.Index=3) then begin
          intervalTempVecArray[i]:=tmp;
        end else if (equationList.Selected.Index>3) then begin
          intervalTempVarArray[i][equationList.Selected.Index-4]:=tmp;
        end;
        equationList.Selected.SubItems[i]:=variableEditListEditor.Values['Równanie '+FloatToStr(i+1)];
      end;
    end else if(equationList.Selected.Index=0) then begin
      tempIter:=int_read(variableEditListEditor.Values['Równanie 1']   );
      maxIter := Floor(tempIter.a);
      equationList.Selected.SubItems[0]:=variableEditListEditor.Values['Równanie 1'];
    end else if(equationList.Selected.Index=1) then begin
      tempEps:=int_read(variableEditListEditor.Values['Równanie 1']);
      eps:=Floor(tempEps.a);
      equationList.Selected.SubItems[0]:=variableEditListEditor.Values['Równanie 1'];
    end;
    variableEditBox.Visible:=false;
  end else if(mode=2) then begin
    if (equationList.Selected.Index>1) then begin
      for i := 0 to variableCount-1 do begin
        tmp.a:=left_read(variableEditIntervalGrid.Cells[1,i+1]);
        tmp.b:=right_read(variableEditIntervalGrid.Cells[2,i+1]);
        if(equationList.Selected.Index=2) then begin
          intervalTempResArray[i]:=tmp;
        end else if (equationList.Selected.Index=3) then begin
          intervalTempVecArray[i]:=tmp;
        end else if (equationList.Selected.Index>3) then begin
          intervalTempVarArray[i][equationList.Selected.Index-4]:=tmp;
        end;
        equationList.Selected.SubItems[i]:=variableEditIntervalGrid.Cells[1,i+1]+';'+variableEditIntervalGrid.Cells[2,i+1];
      end;
    end else if(equationList.Selected.Index=0) then begin
      maxIter:=Floor(left_read(variableEditIntervalGrid.Cells[1,1]));
      equationList.Selected.SubItems[0]:=variableEditIntervalGrid.Cells[1,1];
    end else if(equationList.Selected.Index=1) then begin
      eps:=Floor(left_read(variableEditIntervalGrid.Cells[1,1]));
      equationList.Selected.SubItems[0]:=variableEditIntervalGrid.Cells[1,1];
    end;
    variableEditListInterval.Visible:=false;
  end;
  modeI.Enabled:=true;
  modeII.Enabled:=true;
  modeIII.Enabled:=true;
  variablesBox.Visible:=true;
  resultBox.Visible:=true;
  variableEditControlBox.Visible:=false;
  controlBox.Visible:=true;
  settingsBox.Enabled:=true;
end;

procedure TmainForm.clearButtonClick(Sender: TObject);
begin
  newEquationButton.Enabled:=false;
  clearButton.Enabled:=false;
  resultButton.Enabled:=false;
  equationList.Clear;
  equationList.Columns.Clear;

  equationList.Columns.Add;
  equationList.Column[0].Caption:='   Zmienne';
  equationList.Column[0].width:=140;
  equationList.Column[0].maxWidth:=140;
  equationList.Column[0].minWidth:=140;

  outcomeList.Items.Clear;

  variableCount:=0;
  eps:=0;
  maxIter:=0;
  intervalTempVarArray:=nil;
  newEquationButton.Enabled:=true;
end;

procedure TmainForm.equationListDblClick(Sender: TObject);
var
  i,j: integer;
  l,r: string;
begin
  if(equationList.SelCount=1) then begin
    if((mode=0) or (mode=1)) then begin
      variableEditBox.Caption:='Edytuj zmienn¹ ' + equationList.Selected.Caption;
      variableEditListEditor.Strings.Clear;
      for i := 0 to variableCount-1 do begin
        variableEditListEditor.InsertRow('Równanie ' + floatToStr(i+1),equationList.Selected.SubItems[i],true);
      end;
      variableEditBox.Visible:=true;
    end else if(mode=2) then begin
      variableEditIntervalGrid.RowCount:=variableCount+1;
      variableEditIntervalGrid.Cells[0,0]:=equationList.Selected.Caption;
      variableEditIntervalGrid.Cells[1,0]:='Lewy koniec';
      variableEditIntervalGrid.Cells[2,0]:='Prawy koniec';
      for i := 0 to variableCount-1 do begin
        variableEditIntervalGrid.Cells[0,i+1]:='Równanie ' + floatToStr(i+1);
        j:=Pos(';', equationList.Selected.SubItems[i]);
        if(j>0) then begin
        l:=Copy(equationList.Selected.SubItems[i], 0,Pos(';', equationList.Selected.SubItems[i]) - 1);
        r:=Copy(equationList.Selected.SubItems[i], Pos(';', equationList.Selected.SubItems[i])+1,equationList.Selected.SubItems[i].Length);
          variableEditIntervalGrid.Cells[1,i+1]:=l;
          variableEditIntervalGrid.Cells[2,i+1]:=r;
        end else begin
          variableEditIntervalGrid.Cells[1,i+1]:='0';
          variableEditIntervalGrid.Cells[2,i+1]:='0';
        end;
      end;
      variableEditListInterval.Visible:=true;
    end;
    modeI.Enabled:=false;
    modeII.Enabled:=false;
    modeIII.Enabled:=false;
    variablesBox.Visible:=false;
    controlBox.Visible:=false;
    resultBox.Visible:=false;
    variableEditControlBox.Visible:=true;
    settingsBox.Enabled:=false;
  end;
end;

procedure TmainForm.modeIClick(Sender: TObject);
begin
  if(mode<>0) then begin
    mode:=0;
    clearButtonClick(Sender);
  end;
end;

procedure TmainForm.modeIIClick(Sender: TObject);
begin
  if(mode<>1) then begin
    mode:=1;
    clearButtonClick(Sender);
  end;
end;

procedure TmainForm.modeIIIClick(Sender: TObject);
begin
  if(mode<>2) then begin
    mode:=2;
    clearButtonClick(Sender);
  end;
end;

procedure TmainForm.newEquationButtonClick(Sender: TObject);
var
  i, offset: integer;
begin
  offset:=3;
  if(variableCount=0) then begin
    clearButton.Enabled:=true;
    resultButton.Enabled:=true;
    for i := 0 to offset do
      equationList.Items.Add;
    equationList.Items[0].Caption:='Maksymalna liczba iteracji';
    equationList.Items[1].Caption:='Epsilon';
    equationList.Items[2].Caption:='Wektor pocz. przybli¿eñ';
    equationList.Items[3].Caption:='Wektor wyr. wolnych';
  end;
  inc(variableCount);
  equationList.Items.BeginUpdate;
  equationList.Columns.Add;
  equationList.Column[variableCount].Caption:='R'+floatToStr(variableCount);
  equationList.Column[variableCount].width:=55;

  equationList.Items.Add;
  equationList.Items[variableCount+offset].Caption:='X'+floatToStr(variableCount);
  equationList.Items[0].SubItems.Add('-');
  equationList.Items[1].SubItems.Add('0,0000000001');

  for i := 2 to variableCount+2 do begin
    if(mode<>2) then equationList.Items[i].SubItems.Add('0') else equationList.Items[i].SubItems.Add('0;0');
  end;

  for i := 2 to variableCount+1 do begin
    if(mode<>2) then equationList.items[variableCount+offset].SubItems.Add('0') else equationList.items[variableCount+3].SubItems.Add('0;0')
  end;
  equationList.Items.EndUpdate;
  outcomeList.Items.BeginUpdate;
  outcomeList.Items.Add;
  outcomeList.Items[variableCount-1].Caption:='X'+floatToStr(variableCount);
  outcomeList.Items[variableCount-1].SubItems.add('---');
  outcomeList.Items[variableCount-1].SubItems.add('---');
  outcomeList.Items[variableCount-1].SubItems.add('---');
  outcomeList.Items[variableCount-1].SubItems.add('---');
  outcomeList.Items.EndUpdate;
  if((mode=1) or (mode=2)) then
  begin
    setLength(intervalTempVarArray,variableCount,variableCount);
    setLength(intervalTempVecArray,variableCount);
    setLength(intervalTempResArray,variableCount);
  end;
end;
end.
