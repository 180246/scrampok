lexer grammar PokerLexer;

options {
  language = Java;
}
 
Timestamp: Digit+;

PlayerAction:('c' | 'r' | 'f' | 'b' | 's' | 'm');
  
GameAction:('C');  
  
fragment
Letter :
  'a'..'z' |
  'A'..'Z';
  
fragment
Digit :
  '0'..'9';
  
Whitespace :
  (' ' | '\t' | '\f')+ {$channel=HIDDEN;};