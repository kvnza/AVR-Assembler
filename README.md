# JAssembler [AVR]  
#### A basic AVR assembler developed in Java.  
Not intended for assembling actual instructions, moreso, for understanding how AVR assembly statements are converted to machine code. Currently only supports the following operations:
  - LDI (load immediate)
  - MOV (move between registers)
  - AND (bitwise AND)
  - OR (bitwise OR)
  - COM (inversion - one's complement)
  - EOR (bitwise XOR)
  - ADD (addition)
  - SUB (subtraction)
  - NEG (negation - two's complement)

#### A command-line interface for ease-of-use.
Designed to be used via your system's command-line interface. Commands inclue:
  - 'help' : Provides the user with a list of valid commands.
  - 'assemble [file]' : Assembles a '.asm' file to the same directory.
  - 'read [file]' : Reads a '.txt' file (intended for assembled files but can technically be used for any '.txt' file).