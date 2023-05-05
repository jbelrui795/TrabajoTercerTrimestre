import java.util.Comparator;
import java.util.Objects;

public class Drug implements Comparator<Drug> {
    private int id;
    private String drugName;
    private String drugCompany;
    private String drugGeneric;
    private DrugType type;
    public Drug(int id, String drugName, String drugCompany, String drugGeneric){
        this.id = id;
        this.drugName = drugName;
        this.drugCompany = drugCompany;
        this.drugGeneric = drugGeneric;
    }
    public int hashCode(){
        return Objects.hash(drugName, id);
    }
    public int compare(Drug arg0, Drug arg1){

    }
}
