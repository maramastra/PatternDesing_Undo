package com.kreitek.editor;

import java.util.ArrayList;
import java.util.List;

public class CareTaker {
    private List<Memento> mementos = new ArrayList<>();
   public void push(Memento memento){
        mementos.add(memento);
    }
    public Memento pop(){
        if (!mementos.isEmpty()) {
            return mementos.remove(mementos.size() - 1);

        }
        return null;
    }

    public Memento getMemento(int index) {
        return mementos.get(index);
    }
    public boolean isEmpty() {
        return mementos.isEmpty();
    }

    public int size() {
        return mementos.size();
    }
}
