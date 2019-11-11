package com.mpt.databasehandler;

public class Selector {
    private String tableTitle;
    private String columnTitle;
    private String mask;

    public Selector(String tableTitle, String columnTitle) {
        this.tableTitle = tableTitle;
        this.columnTitle = columnTitle;
    }

    public Selector(String tableTitle, String columnTitle, String mask) {
        this.tableTitle = tableTitle;
        this.columnTitle = columnTitle;
        this.mask = mask;
    }


    String getTableTitle() {
        return tableTitle;
    }

    String getColumnTitle() {
        return columnTitle;
    }

    String getMask(){
        return mask;
    }

    private String getSelectorWithMask(){
        return Model.quotes+tableTitle+Model.quotes+'.'+Model.quotes+columnTitle+Model.quotes+" AS "+Model.quotes+mask+Model.quotes;
    }
    private String getSelectorWithOutMask(){
        return Model.quotes+tableTitle+Model.quotes+'.'+Model.quotes+columnTitle+Model.quotes;
    }

    String getSelector(){
        return hasMask()?getSelectorWithMask():getSelectorWithOutMask();
    }
    String getColumnName(){
        return !hasMask()?getColumnTitle():getMask();
    }

    boolean hasMask(){
        return mask != null;
    }
}
