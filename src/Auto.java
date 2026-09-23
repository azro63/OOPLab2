import java.util.Arrays;

public class Auto implements Vehicle {
    private String mark;
    private Model[] models;

    public Auto(String mark, int length) {
        this.mark = mark;
        models = new Model[length];
        for (int i = 0; i < length; i++) {
            models[i] = new Model("model" + (i + 1), Math.random() * 10000000);
        }
    }

    @Override public String getMark() { return mark; }
    @Override public void setMark(String mark) { this.mark = mark; }

    @Override
    public String[] getModelsNames() {
        String[] result = new String[models.length];
        for (int i = 0; i < result.length; i++) result[i] = models[i].getName();
        return result;
    }

    @Override
    public double[] getModelsCosts() {
        double[] result = new double[models.length];
        for (int i = 0; i < result.length; i++) result[i] = models[i].getCost();
        return result;
    }

    @Override
    public double getModelCost(String modelName) throws NoSuchModelNameException {
        int i = 0;
        while (i < models.length && !models[i].getName().equals(modelName)) i++;
        if (i >= models.length) throw new NoSuchModelNameException(modelName);
        return models[i].getCost();
    }

    @Override
    public void setModelCost(String modelName, double modelCost)
            throws NoSuchModelNameException {
        if (modelCost < 0 || Double.isNaN(modelCost)) {
            throw new ModelPriceOutOfBoundsException(modelCost);
        }
        int i = 0;
        while (i < models.length && !models[i].getName().equals(modelName)) i++;
        if (i >= models.length) throw new NoSuchModelNameException(modelName);
        models[i].setCost(modelCost);//!!!
    }

    @Override
    public void addNewModel(String modelName, double modelCost)
            throws DuplicateModelNameException, ModelPriceOutOfBoundsException {
        if(modelCost < 0 || Double.isNaN(modelCost)) {throw new ModelPriceOutOfBoundsException(modelCost);}
        for (Model m : models) {
            if (m.getName().equals(modelName)) {
                throw new DuplicateModelNameException(modelName);
            }
        }
        models = Arrays.copyOf(models, models.length + 1);
        models[models.length - 1] = new Model(modelName, modelCost);
    }

    @Override
    public void removeOldModel(String modelName) throws NoSuchModelNameException {
        int index = -1;
        for (int i = 0; i < models.length; i++) {
            if (models[i].getName().equals(modelName)) { index = i; break; }
        }
        if (index == -1) throw new NoSuchModelNameException(modelName);


        System.arraycopy(models, index + 1, models, index, models.length - index - 1);
        models = Arrays.copyOf(models, models.length - 1);
    }

    @Override public int getModelsLength() { return models.length; }

    @Override public void setModelName(String oldName, String newName)
            throws NoSuchModelNameException, DuplicateModelNameException
    {
        int index = -1;
        for (int i = 0; i < models.length; i++) {
            if(models[i].getName().equals(newName)) { throw new DuplicateModelNameException(newName); }
            if (models[i].getName().equals(oldName)) {
                index = i;
            }
        }
        if (index == -1) throw new NoSuchModelNameException(oldName);
        models[index].setName(newName);
    }

    private class Model {
        private String name;
        private double cost;
        public Model(String name, double cost) { this.name = name; setCost(cost); }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public double getCost() { return cost; }
        public void setCost(double cost) {

            this.cost = cost;
        }
    }
}