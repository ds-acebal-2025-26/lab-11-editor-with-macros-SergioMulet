package es.uniovi.eii.ds.commands;

import es.uniovi.eii.ds.editor.Document;

public class DeleteCommand implements UserCommand {

    private Document document;

    public DeleteCommand(Document document) {
        this.document = document;
    }   
    
    @Override
    public void execute() {
        document.delete();
    }

}
