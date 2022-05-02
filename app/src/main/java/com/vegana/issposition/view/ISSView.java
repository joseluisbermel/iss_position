package com.vegana.issposition.view;

import com.vegana.issposition.model.ResponseISS;

import java.util.ArrayList;

public interface ISSView {
    void getNextPositions(ArrayList<ResponseISS> response);
}
