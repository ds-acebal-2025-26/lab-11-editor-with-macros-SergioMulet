package es.uniovi.eii.ds.editor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.HashMap;

import es.uniovi.eii.ds.commands.*;

public class Editor {

    private Document document;
    private HashMap<String, MacroCommand> macros = new HashMap<>();
    private MacroCommand currentMacro;
    private boolean isRecording = false;

    public void open(String[] args) {
		if (!checkArguments(args, 1, "open <file>"))
			return;
		try {
			String filename = args[0];
			StringBuilder text = new StringBuilder(readFile(filename));
            document = new Document(text);
            this.printDocument();
		} catch (Exception e) {
			System.out.println("Document could not be opened");
		}
	}

    private String readFile(String filename) {
		InputStream in = getClass().getResourceAsStream("/" + filename);
		if (in == null)
			throw new IllegalArgumentException("File not found: " + filename);

		try (BufferedReader input = new BufferedReader(new InputStreamReader(in))) {
			StringBuilder result = new StringBuilder();
			String line;
			boolean firstLine = true;
			while ((line = input.readLine()) != null) {
				if (!firstLine)
					result.append(System.lineSeparator());
				result.append(line);
				firstLine = false;
			}
			return result.toString();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

    private void printDocument(){
        System.out.println(document.text.toString());
    }

    public void insert(String[] args){
        executeUserCommand (new InsertCommand(args, document));
    }

    public void delete(){
        executeUserCommand(new DeleteCommand(document));
    }

    public void replace(String[] args){
        if (!checkArguments(args, 2, "replace <find> <replace>"))
			return;
        executeUserCommand(new ReplaceCommand(args, document));
    }

    public void record(String macroName){
        isRecording = true;
        currentMacro = new MacroCommand(macroName);
    }

    public void stop(){
        if(!isRecording){
            System.out.println("No macro is being recorded");
            return;
        }
        isRecording = false;
        macros.put(currentMacro.getName(), currentMacro);
    }

    public void execute(String macroName){
        MacroCommand macro = macros.get(macroName);

        if(macro == null){
            System.out.println("Macro not found: " + macroName);
            return;
        }
        executeUserCommand(macro);
    }

    private boolean checkArguments(String[] args, int expected, String syntax) {
        if (args.length != expected) {
            System.out.println("Invalid number of arguments => " + syntax);
            return false;
        }
        return true;
    }

    private void executeUserCommand(UserCommand command) {
        if(isRecording){
            currentMacro.addCommand(command);
        }
        else{
            command.execute();
            this.printDocument();
        }
    }
}
