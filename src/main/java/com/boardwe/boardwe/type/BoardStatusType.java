package com.boardwe.boardwe.type;

public enum BoardStatusType {
    BEFORE_WRITING{
        @Override
        public String toString(){
            return "BEFORE_WRITING";
        }
    },
    WRITING{
        @Override
        public String toString(){
            return "WRITING";
        }
    },
    BEFORE_OPEN{
        @Override
        public String toString(){
            return "BEFORE_OPEN";
        }
    },
    OPEN{
        @Override
        public String toString(){
            return "OPEN";
        }
    },
    CLOSED{
        @Override
        public String toString(){
            return "CLOSED";
        }
    };
}
