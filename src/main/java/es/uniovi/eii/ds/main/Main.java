package es.uniovi.eii.ds.main;

import java.io.*;
import java.util.Arrays;

import es.uniovi.eii.ds.editor.Editor;

public class Main {

	private Editor editor = new Editor();

    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
	// Represents the document of the editor.
	StringBuilder text = new StringBuilder();

    public static void main(String[] args) {
        new Main().run();
    }
	
	// Main program loop.
    public void run() {
		drawLogo();
		showHelp();

		while (true) {
			UserCommand command = promptUser();
			String[] args = command.args;

			switch (command.name) {
				case "open" -> editor.open(args);
				case "insert" -> { 
					editor.insert(args);
				}
				case "delete" -> {
					editor.delete();
				}
				case "replace" -> editor.replace(args);
				case "help" -> showHelp();
				case "record" -> {
					String macroName = args[0];
					editor.record(macroName);
				}
				case "stop" -> { 
					editor.stop();
				}
				case "execute" -> {
					String macroName = args[0];
					editor.execute(macroName);
				}
				default -> {
					System.out.println("Unknown command");
					continue;
				}
			}
		}
	}

	//$-- Some individual user commands that do a bit more work ---------------


	//$-- Auxiliary methods ---------------------------------------------------

	// YOU DON'T NEED TO UNDERSTAND OR MODIFY THE CODE BELOW THIS LINE

	private record UserCommand(String name, String[] args) {}

    // Prompts the user and reads a line of input and returns it as a record with
	// the command and its arguments. If EOF is reached (i.e., there are nothing to
	// read), an error occurs or the user types "exit", the program exits. If there
	// are no arguments, the args array is empty.
	//
	// Example:
	//
	//   > insert "no quiero acordarme" --> returns UserInput("insert", ["no", "quiero", "acordarme"])
	//	 > delete                       --> returns UserInput("delete", [])
	//
	private UserCommand promptUser() {
		while (true) {
            System.out.print("> ");
            try {
                String line = in.readLine();
				if (line == null) System.exit(0);
				if (line.equals("exit")) exit();
				if (line.isBlank()) continue;
				String[] parts = line.split("\\s+");
				return new UserCommand(parts[0], Arrays.copyOfRange(parts, 1, parts.length));
            } catch (IOException e) {
                System.out.println("Error reading input");
				System.exit(2);
			}
		}
    }

	private void exit() {
		System.out.println("Goodbye!");
		System.exit(0);
	}	

	private void drawLogo() {
		System.out.println(LOGO);
	}

	private void showHelp() {
		System.out.println(HELP);
	}

	private static final String LOGO = """

			███╗   ███╗ █████╗  ██████╗████████╗███████╗██╗  ██╗
			████╗ ████║██╔══██╗██╔════╝╚══██╔══╝██╔════╝╚██╗██╔╝
			██╔████╔██║███████║██║        ██║   █████╗   ╚███╔╝ 
			██║╚██╔╝██║██╔══██║██║        ██║   ██╔══╝   ██╔██╗ 
			██║ ╚═╝ ██║██║  ██║╚██████╗   ██║   ███████╗██╔╝ ██╗
			╚═╝     ╚═╝╚═╝  ╚═╝ ╚═════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝
			""";

	private static final String HELP = """
			┌──────────────────────┬─────────────────────────────────────────────┐
			│ open <file>          │                                             │
			│ insert <text>        │ append text to the end                      │
			│ delete               │ delete the last word                        │
			│ replace <a> <b>      │ replace <a> with <b> in the whole document  │
			├──────────────────────┼─────────────────────────────────────────────┤
			│ record <macro>       │ start recording a macro                     │
			│ stop                 │ stop recording                              │
			│ execute <macro>      │ execute the specified macro                 │
			├──────────────────────┼─────────────────────────────────────────────┤
			│ help                 │                                             │
			│ exit                 │                                             │
			└──────────────────────┴─────────────────────────────────────────────┘
			""";
}
