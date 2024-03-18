package com.kreitek.editor;

public class Application {

    public static void main(String[] args) {
        CareTaker careTaker = new CareTaker();
        EditorFactory editorFactory = new EditorFactory();
        Editor editor = editorFactory.getEditor();
        editor.run();

    }

}
