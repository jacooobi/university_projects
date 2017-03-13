unit LinkedList;

{$mode objfpc}{$H+}

interface

uses
  Classes, SysUtils, dialogs, Grids;

type
  PElement = ^TElement;
  TElement = record
    Name, City, Date : string[25];
    TicketsSold, TIcketPrice, TotalExpenses : Integer;
    Next : PElement;
end;

type
  TSinglyLinkedList = class
    private
      First    : PElement;
      Last     : PElement;
      ListCount    : Integer;
    public
      constructor Create;
      destructor Destroy; override;
      procedure Add(Name, Date, City : String; TicketsSold, TicketPrice, TotalExpenses: Integer);
      procedure Clear;
      procedure Delete(Index: Integer);
      function Find(SearchBy, KeyName : String): TSinglyLinkedList;
      procedure LoadFromFile();
      procedure SaveToFile();
      procedure ShowOn(StringGrid : TStringGrid);
      procedure Sort();
      function GetElement(Index: Integer): PElement;
      property Count : Integer read ListCount;
  end;
  operator = (Element1, Element2 : TElement)  b : Boolean;
  operator >= (Element1, Element2 : TElement)  b : Boolean;
  operator < (Element1, Element2 : TElement)  b : Boolean;

implementation

constructor TSinglyLinkedList.Create;
begin
  inherited Create();
  First := nil;
  Last := nil;
  ListCount := 0;
end;

destructor TSinglyLinkedList.Destroy;
begin
  Clear();
  inherited Destroy();
end;

function TSinglyLinkedList.GetElement(Index: Integer): PElement;
var
  Temp: PElement;
begin
  Temp := First;
  while Index > 0 do
  begin
    Temp := Temp^.Next;
    Dec(Index);
  end;
  Result := Temp;
end;

procedure TSinglyLinkedList.Add(Name, Date, City : String; TicketsSold, TicketPrice, TotalExpenses: Integer);
var
  Tmp : PElement;
begin
  New(Tmp);
  Tmp^.Next := nil;
  Tmp^.Name := Name;
  Tmp^.Date := Date;
  Tmp^.City := City;
  Tmp^.TicketsSold := TicketsSold;
  Tmp^.TicketPrice := TicketPrice;
  Tmp^.TotalExpenses := TotalExpenses;

  if First = nil then
    begin
      First := Tmp;
      Last := First;
    end
  else
    begin
      Last^.Next := Tmp;
      Last := Tmp;
    end;
  Inc(ListCount);
end;

procedure TSinglyLinkedList.Delete(Index : Integer);
var
  Temp, Previous: PElement;
  K: Integer;
begin
  K := 1;
  Temp := First;
  if Index = 1 then
  begin
    First := First^.Next;
    Dispose(Temp);
  end;

  if ListCount = 1 then
  begin
    First := nil;
    Last := nil;
  end;

  if Index > 1 then
  begin
    Previous := Temp;
    while Index <> K do
    begin
      Previous := Temp;
      Temp := Temp^.Next;
      K := K + 1;
    end;
    Previous^.Next := Temp^.Next;
    Dispose(Temp);
    if Previous^.Next = nil then
      Last := Previous;
  end;
end;

procedure TSinglyLinkedList.Clear;
var
  ToDelete : PElement;
begin
  while First <> nil do
  begin
    ToDelete := First;
    First := First^.Next;
    Dispose(ToDelete);
  end;
  Last := nil;
  ListCount := 0;
end;

procedure TSinglyLinkedList.ShowOn(StringGrid : TStringGrid);
var
  I: Integer;
  Temp: PElement;
begin
  Temp := First;
  if Temp = nil then
    Exit;
  I := 1;
  while Temp <> nil do
  begin
    with StringGrid do
    begin
      Cells[1, I] := Temp^.Name;
      Cells[2, I] := Temp^.Date;
      Cells[3, I] := Temp^.City;
      Cells[4, I] := IntToStr(Temp^.TicketPrice);
      Cells[5, I] := IntToStr(Temp^.TicketsSold);
      Cells[6, I] := IntToStr(Temp^.TotalExpenses);
    Inc(I);
    end;

    Temp := Temp^.Next;
  end;
end;

procedure TSinglyLinkedList.SaveToFile();
var
  f: file of TElement;
  Temp: PElement;
begin
  try
    try
      Temp := First;
      AssignFile(f, 'file.dat');
      ReWrite(f);
      while Temp <> nil do
      begin
        Write(f, Temp^);
        Temp := Temp^.Next;
      end;
    finally
      CloseFile(f);
    end;
  except
    ShowMessage('Cannot save to file');
  end;
end;

procedure TSinglyLinkedList.LoadFromFile();
var
  F : file of TElement;
  Temp : TElement;
begin
  try
    try
      AssignFile(F, 'file.dat');
      Reset(F);
      while not EOF(F) do
      begin
        Read(F, Temp);
        Add(Temp.Name, Temp.Date, Temp.City, Temp.TicketPrice, Temp.TicketsSold, Temp.TotalExpenses);
      end;
    finally
      CloseFile(F);
    end;
  except
    ShowMessage('Cannot read from file');
  end;
end;

procedure TSinglyLinkedList.Sort();
var
  Length, I: Integer;
  Previous, Current, Next: PElement;
begin
  Length := Count;
  for I:= 0 to Length-1 do
  begin
    Previous := nil;
    Current := First;
    Next := Current^.Next;
    while Next <> nil do
    begin
      if (Current^ >= Next^) then
      begin
        if (Current^ = First^) then
          First := Next
        else
          Previous^.Next := Next;
        Current^.Next := Next^.Next;
        Next^.Next := Current;
        Previous := Next;
        Next := Current^.Next;
      end
      else
      begin
        Previous := Current;
        Current := Current^.Next;
        Next := Current^.Next;
      end;
    end;
  end;
end;

function TSinglyLinkedList.Find(SearchBy, KeyName : String): TSinglyLinkedList;
var
  TempList : TSinglyLinkedList;
  Temp : PElement;
  I : Integer;
begin
  TempList := TSinglyLinkedList.Create();
  I := Count;
  Temp := First;
  if SearchBy = 'name' then
  begin
    while i <> 0 do
    begin
      if Temp^.Name = KeyName then
      begin
        TempList.Add(Temp^.Name, Temp^.Date, Temp^.City, Temp^.TicketsSold, Temp^.TIcketPrice, Temp^.TotalExpenses);
      end;
      Dec(I);
      Temp := Temp^.Next;
    end;
  end
  else
  begin
    while i <> 0 do
    begin
      if Temp^.City = KeyName then
      begin
        TempList.Add(Temp^.Name, Temp^.Date, Temp^.City, Temp^.TicketsSold, Temp^.TIcketPrice, Temp^.TotalExpenses);
      end;
      Dec(I);
      Temp := Temp^.Next;
    end;
  end;
  Result := TempList;
end;

operator = (Element1, Element2 : TElement)  b : Boolean;
begin
  Result := true;
  if(Element1.Name <> Element2.Name) then
    Result := false;
  if(Element1.Date <> Element2.Date) then
    Result := false;
  if(Element1.City <> Element2.City) then
    Result := false;
  if(Element1.TIcketPrice <> Element2.TicketPrice) then
    Result := false;
  if(Element1.TicketsSold <> Element2.TicketsSold) then
    Result := false;
  if(Element1.TotalExpenses <> Element2.TotalExpenses) then
    Result := false;
end;

operator >= (Element1, Element2 : TElement)  b : Boolean;
var I:Integer;
begin
  I := CompareText(Element1.Name, Element2.Name);
  if(I = 0) then
    I := CompareText(Element1.City, Element2.City);
  if(I > 0 ) then
    Result := true
  else
    Result := false;
end;

operator < (Element1, Element2 : TElement)  b : Boolean;
var I:Integer;
begin
  I := CompareText(Element1.Name, Element2.Name);
  if(I = -1 ) then
    Result := false
  else
    Result := true;
end;

end.
