library validator;

{$mode objfpc}{$H+}

uses
  Classes, SysUtils, regexpr, strings
  { you can add units after this };

function ValidateCity(City: String): boolean;
begin
  result := false;
  if City = 'Poznan' then
     result := true;
  if City = 'Warszawa' then
     result := true;
  if City = 'Wroclaw' then
     result := true;
  if City = 'Krakow' then
     result := true;
  if City = 'Szczecin' then
     result := true;
end;

function ValidateNumbers(Sold,Price,Total: String): boolean;
var
  code : integer;
  _sold,_price,_total : real;
begin
  result := true;
  val(Sold, _sold, code);
  if code <> 0 then
     result := false;
  val(Price, _price, code);
  if code <> 0 then
     result := false;
  val(Total, _total, code);
  if code <>0 then
     result := false;
  if result = false then
    exit(result);
  if _sold < 0 then
    result := false;
  if _price < 0 then
    result := false;
  if _total < 0 then
    result := false;
end;

function ValidatePresence(Name,Date,City,Sold,Price,Total: String): boolean;
begin
  result := true;
  if length(Name) = 0 then
     result := false;
  if length(Date)= 0 then
     result := false;
  if length(City)= 0 then
     result := false;
  if length(Sold)= 0 then
     result := false;
  if length(Price)= 0 then
     result := false;
  if length(Total)= 0 then
     result := false;
end;

exports
  ValidatePresence, ValidateNumbers, ValidateCity;

begin
end.

