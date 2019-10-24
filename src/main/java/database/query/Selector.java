package database.query;

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


    String getSelectorWithMask(){
        return '\"'+tableTitle+"\".\""+columnTitle+"\" AS \""+mask+'\"';
    }
    String getSelectorWithOutMask(){
        return '\"'+tableTitle+"\".\""+columnTitle+'\"';
    }

    public String getSelector(){
        return hasMask()?getSelectorWithMask():getSelectorWithOutMask();
    }
    public String getColumnName(){
        return !hasMask()?getColumnTitle():getMask();
    }



    boolean hasMask(){
        return mask != null;
    }

    @Override
    public String toString() {
        return "Selector{" +
                "tableTitle='" + tableTitle + '\'' +
                ", columnTitle='" + columnTitle + '\'' +
                ", mask='" + mask + '\'' +
                '}';
    }
}
