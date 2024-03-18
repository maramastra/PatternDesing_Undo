package com.kreitek.editor.commands;

import com.kreitek.editor.CareTaker;
import com.kreitek.editor.Command;
import com.kreitek.editor.Memento;


import java.util.ArrayList;

public class UndoCommand implements Command {
    private CareTaker careTaker;

    public UndoCommand(CareTaker careTaker)
    {
        this.careTaker = careTaker;
    }



    @Override
    public void execute(ArrayList<String> documentLines) {
        Memento memento = careTaker.pop();
        if (memento != null) {
            documentLines.clear();
            documentLines.addAll((ArrayList<String>) memento.getState().get("documentLines"));
        }
    }
}


