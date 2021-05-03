package com.github.PrzeBarCore.springlibraryworkshop.utils;

public enum BookState {
    AVAILABLE, BORROWED, RESERVED;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
