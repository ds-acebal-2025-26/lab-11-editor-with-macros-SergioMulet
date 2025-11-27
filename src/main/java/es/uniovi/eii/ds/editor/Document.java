package es.uniovi.eii.ds.editor;

public class Document {

    StringBuilder text = new StringBuilder();

    public Document(StringBuilder text) {
        this.text = text;
    }

    public void append(String[] args) {
        for (String word : args) {
            text.append(" ").append(word);
        }
    }

    public void replace(String[] args) {
        String find = args[0];
        String replace = args[1];
        text = new StringBuilder(text.toString().replace(find, replace));
    }

    public void delete() {
        int indexOfLastWord = text.toString().trim().lastIndexOf(" ");
        if (indexOfLastWord == -1)
            text = new StringBuilder("");
        else
            text.setLength(indexOfLastWord);
    }
}
