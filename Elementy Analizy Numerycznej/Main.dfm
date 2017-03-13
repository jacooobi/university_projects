object mainForm: TmainForm
  Left = 49
  Top = 165
  BorderStyle = bsDialog
  Caption = 'Jacobi - rozwi'#261'zywanie uk'#322'ad'#243'w rowna'#324
  ClientHeight = 421
  ClientWidth = 1192
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'Tahoma'
  Font.Style = []
  OldCreateOrder = False
  Position = poDesigned
  PixelsPerInch = 96
  TextHeight = 13
  object variableEditControlBox: TGroupBox
    Left = 392
    Top = 8
    Width = 290
    Height = 57
    Caption = 'Edycja'
    TabOrder = 4
    Visible = False
    object variableEditConfirm: TButton
      Left = 52
      Top = 20
      Width = 85
      Height = 25
      Caption = 'Zapisz zmiany'
      TabOrder = 0
      OnClick = variableEditConfirmClick
    end
    object variableEditCancel: TButton
      Left = 160
      Top = 20
      Width = 75
      Height = 25
      Caption = 'Anuluj'
      TabOrder = 1
      OnClick = variableEditCancelClick
    end
  end
  object variableEditListInterval: TGroupBox
    Left = 8
    Top = 71
    Width = 674
    Height = 343
    Caption = 'Edytuj zmienn'#261
    TabOrder = 9
    Visible = False
    object variableEditIntervalGrid: TStringGrid
      Left = 7
      Top = 16
      Width = 664
      Height = 320
      ColCount = 3
      DefaultColWidth = 100
      RowCount = 2
      Options = [goFixedVertLine, goFixedHorzLine, goVertLine, goHorzLine, goRangeSelect, goEditing]
      TabOrder = 0
    end
  end
  object variableEditBox: TGroupBox
    Left = 8
    Top = 71
    Width = 674
    Height = 343
    Caption = 'Edytuj zmienn'#261
    TabOrder = 3
    Visible = False
    object variableEditListEditor: TValueListEditor
      Left = 5
      Top = 24
      Width = 666
      Height = 305
      Hint = 'Podaj warto'#347'ci zmiennej dla ka'#380'dego jej wyst'#261'pienia.'
      ParentShowHint = False
      ShowHint = True
      TabOrder = 0
      ColWidths = (
        150
        510)
    end
  end
  object resultBox: TGroupBox
    Left = 423
    Top = 71
    Width = 761
    Height = 343
    Caption = 'Rozwi'#261'zanie'
    TabOrder = 1
    object outcomeList: TListView
      Left = 3
      Top = 16
      Width = 755
      Height = 324
      Hint = 'Wci'#347'nij przycisk "Oblicz" aby rozwi'#261'za'#263' uk'#322'ad.'
      Columns = <
        item
          Caption = '   Zmienne'
          MaxWidth = 75
          MinWidth = 75
          Width = 75
        end
        item
          AutoSize = True
          Caption = 'Warto'#347'ci'
        end
        item
          Caption = 'Szeroko'#347#263
          MinWidth = 75
          Width = 160
        end
        item
          Caption = 'Iteracje'
          MaxWidth = 80
          MinWidth = 1
          Width = 55
        end
        item
          Caption = 'Status'
          MaxWidth = 50
          MinWidth = 50
        end>
      ColumnClick = False
      ReadOnly = True
      RowSelect = True
      ParentShowHint = False
      ShowHint = True
      TabOrder = 0
      ViewStyle = vsReport
    end
  end
  object variablesBox: TGroupBox
    Left = 8
    Top = 71
    Width = 409
    Height = 343
    Caption = 'Dane'
    TabOrder = 0
    object equationList: TListView
      Left = 5
      Top = 16
      Width = 401
      Height = 324
      Hint = 'Kliknij dwukrotnie na wybran'#261' zmienn'#261' aby j'#261' edytowa'#263'.'
      Columns = <
        item
          Caption = '   Zmienne'
          MaxWidth = 1000
          Width = 140
        end>
      ColumnClick = False
      RowSelect = True
      ParentShowHint = False
      ShowHint = True
      TabOrder = 0
      ViewStyle = vsReport
      OnDblClick = equationListDblClick
    end
  end
  object controlBox: TGroupBox
    Left = 382
    Top = 8
    Width = 297
    Height = 57
    Caption = 'Ustawienia'
    TabOrder = 2
    object newEquationButton: TButton
      Left = 10
      Top = 20
      Width = 85
      Height = 25
      Caption = 'Nowe r'#243'wnanie'
      TabOrder = 0
      OnClick = newEquationButtonClick
    end
    object resultButton: TButton
      Left = 101
      Top = 20
      Width = 109
      Height = 25
      Caption = 'Znajd'#378' rozwi'#261'zanie'
      Enabled = False
      TabOrder = 1
      OnClick = resultButtonClick
    end
    object clearButton: TButton
      Left = 216
      Top = 20
      Width = 69
      Height = 25
      Caption = 'Resetuj'
      Enabled = False
      TabOrder = 2
      OnClick = clearButtonClick
    end
  end
  object settingsBox: TRadioGroup
    Left = 8
    Top = 8
    Width = 337
    Height = 57
    Caption = 'Rodzaj arytmetyki'
    TabOrder = 5
  end
  object modeI: TRadioButton
    Left = 13
    Top = 32
    Width = 113
    Height = 17
    Caption = 'Zmiennopozycyjna'
    Checked = True
    TabOrder = 6
    TabStop = True
    OnClick = modeIClick
  end
  object modeII: TRadioButton
    Left = 132
    Top = 32
    Width = 113
    Height = 17
    Caption = 'Przedzia'#322'owa I'
    TabOrder = 7
    OnClick = modeIIClick
  end
  object modeIII: TRadioButton
    Left = 225
    Top = 32
    Width = 113
    Height = 17
    Caption = 'Przedzia'#322'owa II'
    TabOrder = 8
    OnClick = modeIIIClick
  end
end
