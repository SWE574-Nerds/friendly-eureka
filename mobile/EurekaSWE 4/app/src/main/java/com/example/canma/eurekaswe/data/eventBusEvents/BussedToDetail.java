package com.example.canma.eurekaswe.data.eventBusEvents;

import com.example.canma.eurekaswe.data.CellData;

/**
 * Created by suzanece on 12/12/2017.
 */

public class BussedToDetail {

    public BussedToDetail(CellData cellData) {
        this.cellData = cellData;
    }

    public CellData cellData;
}
