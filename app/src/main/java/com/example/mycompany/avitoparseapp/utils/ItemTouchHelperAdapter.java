package com.example.mycompany.avitoparseapp.utils;

public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);

    void onItemsSwiped(int position);
}