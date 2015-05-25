# U_VerteilteSysteme
Exercises for distributed systems for the university and contains 
a runner for the exercises called 'Shell'.

# Running the Shell
	
	ch U_VerteilteSysteme
	./gradlew -q run

# Running the shell inside an IDE

## IDEA

In order to run the Shell inside IDEA you have to 
add a run/debug configuration with these settings:

	* Configuration Type: Application
 	* Name: Shell
 	* Main Class: de.htw.vs.Shell
 	* VM Options: -Djline.terminal=org.springframework.shell.core.IdeTerminal


 

