package com.example.canma.eurekaswe.data.eventBusEvents;

import com.example.canma.eurekaswe.data.CellData;
import com.example.canma.eurekaswe.data.Selector;

import java.util.List;

/**
 * Created by canma on 12/12/2017.
 */

public class BussedToHighlight {


    public BussedToHighlight(List<Selector> selectors,int altCellPos) {
        this.selectors = selectors;
        this.altCellPos=altCellPos;
    }
public int altCellPos;

    public List<Selector> selectors;
}
