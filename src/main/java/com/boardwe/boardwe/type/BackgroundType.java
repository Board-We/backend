package com.boardwe.boardwe.type;

public enum BackgroundType {
    IMAGE{
        @Override
        public String toString(){
            return "IMAGE";
        }
    },
    COLOR{
        @Override
        public String toString(){
            return "COLOR";
        }
    };
}
