program RownaniaJacobi;

uses
  Vcl.Forms,
  Main in 'Main.pas' {mainForm},
  IntervalArithmetic32and64 in 'IntervalArithmetic32and64.pas',
  uTExtendedX87 in 'uTExtendedX87.pas',
  UnitJacobi in 'UnitJacobi.pas',
  UnitIntervalJacobi in 'UnitIntervalJacobi.pas';

{$R *.res}

begin
  Application.Initialize;
  Application.MainFormOnTaskbar := True;
  Application.CreateForm(TmainForm, mainForm);
  Application.Run;
end.
