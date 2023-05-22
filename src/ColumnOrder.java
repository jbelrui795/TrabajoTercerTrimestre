public class ColumnOrder {
    private int index;
    private String order;

    /**
     * Constructor para crear instancia de ColumnOrder con todos sus datos
     * @param index si ponemos 1 seria ascendente y si ponemos -1 descendente
     * @param order para indicar el nombre de la columna por la que se quiere ordenar
     */
    public ColumnOrder(int index, String order){
        this.index = index;
        this.order = order;
    }
    public int getIndex(){
        return index;
    }
    public void setIndex(int index){
        this.index = index;
    }
    public String getOrder(){
        return order;
    }
    public void setOrder(String order){
        this.order = order;
    }
}
