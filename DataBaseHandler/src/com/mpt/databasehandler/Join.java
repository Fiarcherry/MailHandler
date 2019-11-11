package com.mpt.databasehandler;

public class Join {
    private JoinType type;

    private String foreignTable;
    private String primaryKey;
    private String primaryTable;
    private String foreignKey;

    private Character quotes;

    public Join(JoinType type, String foreignTable, String primaryKey, String primaryTable, String foreignKey, Character quotes) {
        this.type = type;
        this.foreignTable = foreignTable;
        this.primaryKey = primaryKey;
        this.primaryTable = primaryTable;
        this.foreignKey = foreignKey;
        this.quotes = quotes;
    }

    @Override
    public String toString() {
        return ' '+type.toString()+" JOIN "+ quotes +foreignTable+ quotes +" ON "+ quotes +foreignTable+ quotes +"."+ quotes +primaryKey+ quotes +" = "+ quotes +primaryTable+ quotes +"."+ quotes +foreignKey+ quotes +"";
    }
}
