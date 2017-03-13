unit MyColorButton;

{$mode objfpc}{$H+}

interface

uses SysUtils, Classes, Controls, Forms, extctrls, Dialogs, Graphics, LCLIntf;

type

  { TMyColorButton }

  TMyColorButton = class(TPanel)
  private
   { Private declarations }
    procedure OnMouseDown(Sender: TObject; Button: TMouseButton; Shift: TShiftState; X, Y: Integer);
    function RandomHexColor(): TColor;
  protected
   { Protected declarations }
    constructor Create(TheOwner: TComponent); override;
    destructor Destroy; override;
    procedure MouseDown(Button: TMouseButton; Shift: TShiftState; X, Y: Integer);override;
  public
  { Public declarations }
  published
  { Published declarations }
  end;

procedure Register;

implementation

procedure Register;
begin
  RegisterComponents('Misc', [TMyColorButton]);
end;

{ TMyColorButton }

function TMyColorButton.RandomHexColor(): TColor;
var
  Red, Green, Blue : Integer;
begin
  Red    := Random(256);
  Green  := Random(256);
  Blue   := Random(256);
  Result := RGB(Red, Green, Blue);
end;

procedure TMyColorButton.OnMouseDown(Sender: TObject; Button: TMouseButton;
  Shift: TShiftState; X, Y: Integer);
begin
  MouseDown(Button, Shift, X, Y);
end;

constructor TMyColorButton.Create(TheOwner: TComponent);
begin
  inherited Create(TheOwner);
  Color := RandomHexColor
end;

destructor TMyColorButton.Destroy;
begin
  inherited Destroy;
end;


procedure TMyColorButton.MouseDown(Button: TMouseButton; Shift: TShiftState; X, Y: Integer);
begin
  inherited MouseDown(Button, Shift, X, Y);
  Color := RandomHexColor();
end;
end.
