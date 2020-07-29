This README is written by Mi Yon Kim and referred from Project4 AppClasses document.
Usage: java edu.pdx.cs410J.miyon.Project4 [options] <args>
args are (in this order):
   customer: Person whose phone bill we’re modeling
   callerNumber: Phone number of caller
   calleeNumber: Phone number of person who was called
   start: Date and time call began (12-hour time). These are three separate arguments.
   end: Date and time call ended (12-hour time). These are three separate arguments.
options are (options may appear in any order):
   -host hostname: Host computer on which the server runs
   -port port: Port on which the server is listening
   -search: PHone calls should be searched for
   -print: Prints a description of the new phone call
   -README: Prints a README for this project and exits
Date and time should be in the format: mm/dd/yyyy hh:mm am/pm (12-hour time)
