package de.dfki.vsm.sfsl.parser;

import java_cup.runtime.Symbol;

%%
%unicode
%implements java_cup.runtime.Scanner
%function next_token
%type java_cup.runtime.Symbol
%class _SFSLScanner_
%public
%char
%line

alpha=[a-zA-Z\u00e4\u00c4\u00f6\u00d6\u00fc\u00dc\u00df\u0040\u00b5\u00C0\u00C1\u00C2\u00C6\u00C7\u00C8\u00C9\u00CA\u00CB\u00CE\u00CF\u00D4\u00D6\u00E0\u00E2\u00E6\u00E7\u00E8\u00E9\u00EA\u00EB\u00EE\u00EF\u00F4\u00F6\u00FB\u00FC\u00FF]
digit   = [0-9]
special = ['!?$%&#/=~_:;,]|\\|\.|\+|\*|\-|\||\[|\]|\(|\)
win_new = (\n)
mac_new = (\r)
uni_new = (\r\n)
newline = ({win_new}|{uni_new}|{mac_new})
white   = ({newline}|([\ \t\f\b]))
escape  = (\\)
//char    = (\'({alpha}|{digit}|{special})\')
//ident   = (({alpha}|_)({alpha}|{digit}|_)*)
string  = (\"({alpha}|{digit}|{special}|{white})*\")


%%
"PlayDialogueAct" { return new Symbol(_SFSLToken_.PLAY_DIALOG_ACT); }
"PlaySceneGroup" { return new Symbol(_SFSLToken_.PLAY_SCENE_GROUP); }
"HistoryFlatClear" { return new Symbol(_SFSLToken_.HISTORY_FLAT_CLEAR); }
"HistoryDeepClear" { return new Symbol(_SFSLToken_.HISTORY_DEEP_CLEAR); }
//"HistoryContains" { return new Symbol(_SFSLToken_.HISTORY_CONTAINS); }
"HistoryValueOf" { return new Symbol(_SFSLToken_.HISTORY_VALUE_OF); }
"HistorySetDepth" { return new Symbol(_SFSLToken_.HISTORY_SET_DEPTH); }
"HistoryRunTime" { return new Symbol(_SFSLToken_.HISTORY_RUNTIME); }

"get" { return new Symbol(_SFSLToken_.GET); }
"remove" { return new Symbol(_SFSLToken_.REMOVE); }
"random" { return new Symbol(_SFSLToken_.RANDOM); }
"addFirst" { return new Symbol(_SFSLToken_.ADDFIRST); }
"addLast" { return new Symbol(_SFSLToken_.ADDLAST); }
"removeFirst" { return new Symbol(_SFSLToken_.REMOVEFIRST); }
"removeLast" { return new Symbol(_SFSLToken_.REMOVELAST); }
"first" { return new Symbol(_SFSLToken_.FIRST); }
"last" { return new Symbol(_SFSLToken_.LAST); }
"clear" { return new Symbol(_SFSLToken_.CLEAR); }
"size" { return new Symbol(_SFSLToken_.SIZE); }
"empty" { return new Symbol(_SFSLToken_.EMPTY); }
"contains" { return new Symbol(_SFSLToken_.CONTAINS); }

"in" { return new Symbol(_SFSLToken_.IN); }
"query" { return new Symbol(_SFSLToken_.QUERY); }
"timeout" { return new Symbol(_SFSLToken_.TIMEOUT); }

"list" { return new Symbol(_SFSLToken_.LIST); }
"struct" { return new Symbol(_SFSLToken_.STRUCT); }

"true" { return new Symbol(_SFSLToken_.BOOLEAN, new java.lang.Boolean(yytext())); }
"false" { return new Symbol(_SFSLToken_.BOOLEAN, new java.lang.Boolean(yytext())); }
"null"  { return new Symbol(_SFSLToken_.NULL); }
"new"   { return new Symbol(_SFSLToken_.NEW); }


"int"  { return new Symbol(_SFSLToken_.TINT, new java.lang.String(yytext())); }
"short"   { return new Symbol(_SFSLToken_.TSHORT, new java.lang.String(yytext())); }
"long"  { return new Symbol(_SFSLToken_.TLONG, new java.lang.String(yytext())); }
"float"   { return new Symbol(_SFSLToken_.TFLOAT, new java.lang.String(yytext())); }
"double"  { return new Symbol(_SFSLToken_.TDOUBLE, new java.lang.String(yytext())); }
"bool"   { return new Symbol(_SFSLToken_.TBOOL, new java.lang.String(yytext())); }
"char"   { return new Symbol(_SFSLToken_.TCHAR, new java.lang.String(yytext())); }
"string"   { return new Symbol(_SFSLToken_.TSTRING, new java.lang.String(yytext())); }


[0-9]+\.[0-9]+ { return new Symbol(_SFSLToken_.FLOAT, new java.lang.Float(yytext())); }
[0-9]+ { return new Symbol(_SFSLToken_.INTEGER, new java.lang.Integer(yytext())); }
//\"([a-zA-Z]|[_-])([a-zA-Z0-9]|[ _-])*([a-zA-Z0-9])*\" { return new Symbol(_SFSLToken_.STRING, new java.lang.String(yytext())); }
//\"([a-zA-Z0-9]|[ _-=.!>():'])*\" { return new Symbol(_SFSLToken_.STRING, new java.lang.String(yytext())); }
{string}        { return new Symbol(_SFSLToken_.STRING, new java.lang.String(yytext())); }

[a-zA-Z_][a-zA-Z0-9_]* { return new Symbol(_SFSLToken_.IDENTIFIER, new java.lang.String(yytext())); }

"+" { return new Symbol(_SFSLToken_.PLUS); }
"-" { return new Symbol(_SFSLToken_.MINUS); }
"*" { return new Symbol(_SFSLToken_.TIMES); }
"/" { return new Symbol(_SFSLToken_.DIV); }
"&&" { return new Symbol(_SFSLToken_.AND); }
"||" { return new Symbol(_SFSLToken_.OR); }
"==" { return new Symbol(_SFSLToken_.EQUALEQUAL); }
"!=" { return new Symbol(_SFSLToken_.NOTEQUAL); }
"<" { return new Symbol(_SFSLToken_.LESS); }
">" { return new Symbol(_SFSLToken_.GREATER); }
"<=" { return new Symbol(_SFSLToken_.LESSEQUAL); }
">=" { return new Symbol(_SFSLToken_.GREATEREQUAL); }
"!" { return new Symbol(_SFSLToken_.NOT); }
"(" { return new Symbol(_SFSLToken_.LPAREN); }
")" { return new Symbol(_SFSLToken_.RPAREN); }
"[" { return new Symbol(_SFSLToken_.LBRACK); }
"]" { return new Symbol(_SFSLToken_.RBRACK); }
"{" { return new Symbol(_SFSLToken_.LBRACE); }
"}" { return new Symbol(_SFSLToken_.RBRACE); }
"," { return new Symbol(_SFSLToken_.COMMA); }
"." { return new Symbol(_SFSLToken_.DOT); }
"?" { return new Symbol(_SFSLToken_.QUESTION); }
":" { return new Symbol(_SFSLToken_.COLON); }
"=" { return new Symbol(_SFSLToken_.EQUAL); }

[ \t\r\n\f] { /* ignore white space. */ }
. { System.err.println("Illegal character: "+yytext()); }
