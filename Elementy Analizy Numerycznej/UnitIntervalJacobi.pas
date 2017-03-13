unit UnitIntervalJacobi;

interface

uses System.SysUtils, System.Classes, Windows, System.Math, IntervalArithmetic32and64;

type
  intervalVector = array of Interval;
  intervalMatrix = array of array of Interval;

procedure IntervalJacobi (n  : Integer;
                  var a     : intervalMatrix;
                  var b     : intervalVector;
                  mit       : Integer;
                  eps       : Extended;
                  var x     : intervalVector;
                  var it,st : Integer);

implementation

function iabs(x : interval) : interval;
begin
  if (x.a <= 0) and (x.b >= 0) then
  begin
    iabs.a := 0;
    if x.b > -x.a then
      iabs.b := x.b
    else
      iabs.b := x.a

  end
  else if x.b < 0 then
  begin
    iabs.a := -x.b;
    iabs.b := -x.a;
  end
  else
  begin
    iabs.a := x.a;
    iabs.b := x.b;
  end;
end;

procedure IntervalJacobi (n : Integer;
                  var a     : intervalMatrix;
                  var b     : intervalVector;
                  mit       : Integer;
                  eps       : Extended;
                  var x     : intervalVector;
                  var it,st : Integer);

var i,ih,k,kh,khh,lz1,lz2 : Integer;
    max,r                 : Interval;
    cond                  : Boolean;
    x1                    : intervalVector;
begin
  if n<1
    then st:=1
    else begin
           setLength(x1, n+1);
           st:=0;
           cond:=true;
           for k:=1 to n do
             x1[k].a := 0;
             x1[k].b := 0;
           repeat
             lz1:=0;
             khh:=0;
             for k:=1 to n do
               begin
                 lz2:=0;
                 if (a[k,k].a <= 0) and (a[k,k].b >= 0)
                   then begin
                          kh:=k;
                          for i:=1 to n do
                            if (a[i,k].a <= 0) and (a[i,k].b >= 0)
                              then lz2:=lz2+1;
                          if lz2>lz1
                            then begin
                                   lz1:=lz2;
                                   khh:=kh
                                 end
                        end
               end;
             if khh=0
               then cond:=false
               else begin
                      max.a := 0;
                      max.b := 0;
                      for i:=1 to n do
                        begin
                          r := iabs(a[i,khh]);
                          if (r.a>max.b) and ((x1[i].a <= 0) and (x1[i].b >= 0))
                            then begin
                                   max:=r;
                                   ih:=i
                                 end
                        end;
                      if ((max.a<=0) and (max.b>=0))
                        then st:=2
                        else begin
                               for k:=1 to n do
                                 begin
                                   r:=a[khh,k];
                                   a[khh,k]:=a[ih,k];
                                   a[ih,k]:=r
                                 end;
                               r:=b[khh];
                               b[khh]:=b[ih];
                               b[ih]:=r;
                               x1[khh].a := 1;
                               x1[khh].b := 1;
                             end
                    end
           until not cond or (st=2);
           if not cond
             then begin
                    it:=0;
                    repeat
                      it:=it+1;
                      if it>mit
                        then begin
                               st:=3;
                               it:=it-1
                             end
                        else begin
                               for i:=1 to n do
                                 begin
                                   r:=b[i];
                                   for k:=1 to n do
                                     if k<>i
                                       then r:= isub(r, imul(a[i,k], x[k]));
                                   x1[i]:= idiv(r,a[i,i]);
                                 end;
                               cond:=true;
                               i:=0;
                               repeat
                                 i:=i+1;
                                 max:=iabs(x[i]);
                                 r:=iabs(x1[i]);
                                 if r.a>max.b
                                   then max:=r;
                                 if (max.a <> 0) and (max.b <> 0)
                                   then if (( idiv(iabs(isub(x[i],x1[i])), max).a >= eps)
                                   and (idiv(iabs(isub(x[i],x1[i])), max).b >= eps))
                                          then cond:=false;
                               until (i=n) or not cond;
                               for i:=1 to n do
                                 x[i]:=x1[i]
                             end
                    until (st=3) or cond
                  end
         end
end;

end.

