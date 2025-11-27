package es.uniovi.eii.ds.commands;

import es.uniovi.eii.ds.editor.Document;

public class InsertCommand implements UserCommand {

    private String[] args;
    private Document document;

    public InsertCommand(String[] args, Document document) {
        this.args = args;
        this.document = document;
    }

    @Override
    public void execute() {
        document.append(args);
    }

}
