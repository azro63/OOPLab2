import java.io.Serializable;

public interface Vehicle extends Serializable{
    String getMark();
    void setMark(String mark);

    String[] getModelsNames();
    double[] getModelsCosts();

    double getModelCost(String modelName) throws NoSuchModelNameException;
    void setModelCost(String modelName, double modelCost) throws NoSuchModelNameException;

    void addNewModel(String modelName, double modelCost) throws DuplicateModelNameException;
    void removeOldModel(String modelName) throws NoSuchModelNameException;
    void setModelName(String oldName, String newName) throws NoSuchModelNameException, DuplicateModelNameException;
    int getModelsLength();
    Vehicle clone();
}