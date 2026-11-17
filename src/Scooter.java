import java.util.HashMap;
import java.util.Objects;

public class Scooter implements Vehicle {

    private String mark;
    private HashMap<String, Double> models = new HashMap<String, Double>();

    public Scooter(String mark, int length) {
        this.mark = mark;
        for (int i = 0; i < length; i++) {
            models.put("scooter" + (i + 1), Math.random() * 300000);
        }
    }

    @Override public String getMark() { return mark; }
    @Override public void setMark(String mark) { this.mark = mark; }

    @Override
    public String[] getModelsNames() {
        String[] result = new String[models.size()];
        int i = 0;
        for (String name : models.keySet()) {
            result[i] = name;
            i++;
        }
        return result;
    }

    @Override
    public double[] getModelsCosts() {
        double[] result = new double[models.size()];
        int i = 0;
        for (double cost : models.values()) {
            result[i] = cost;
            i++;
        }
        return result;
    }

    @Override
    public double getModelCost(String modelName) throws NoSuchModelNameException {
        Double cost = models.get(modelName);
        if (cost == null) throw new NoSuchModelNameException(modelName);
        return cost;
    }

    @Override
    public void setModelCost(String modelName, double modelCost)
            throws NoSuchModelNameException {
        if (modelCost < 0 || Double.isNaN(modelCost)) {
            throw new ModelPriceOutOfBoundsException(modelCost);
        }
        if (!models.containsKey(modelName)) throw new NoSuchModelNameException(modelName);
        models.put(modelName, modelCost);
    }

    @Override
    public void addNewModel(String modelName, double modelCost)
            throws DuplicateModelNameException {
        if (modelCost < 0 || Double.isNaN(modelCost)) {
            throw new ModelPriceOutOfBoundsException(modelCost);
        }
        if (models.containsKey(modelName)) throw new DuplicateModelNameException(modelName);
        models.put(modelName, modelCost);
    }

    @Override
    public void removeOldModel(String modelName) throws NoSuchModelNameException {
        if (!models.containsKey(modelName)) throw new NoSuchModelNameException(modelName);
        models.remove(modelName);
    }

    @Override public int getModelsLength() { return models.size(); }

    @Override
    public void setModelName(String oldName, String newName)
            throws NoSuchModelNameException, DuplicateModelNameException {
        if (models.containsKey(newName)) throw new DuplicateModelNameException(newName);
        if (!models.containsKey(oldName)) throw new NoSuchModelNameException(oldName);
        double cost = models.remove(oldName);
        models.put(newName, cost);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("mark='")
                .append(mark)
                .append("', models=[");
        boolean first = true;
        for (String name : models.keySet()) {
            if (!first) { sb.append(", "); }
            first = false;
            sb.append("{name=")
                    .append(name)
                    .append("} {cost=")
                    .append(models.get(name))
                    .append("}");
        }
        sb.append("]}");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Vehicle)) return false;
        Vehicle other = (Vehicle) obj;
        if (!Objects.equals(mark, other.getMark())) return false;
        if (getModelsLength() != other.getModelsLength()) return false;

        String[] names = other.getModelsNames();
        double[] costs = other.getModelsCosts();
        for (int i = 0; i < names.length; i++) {
            Double cost = models.get(names[i]);
            if (cost == null || cost != costs[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return 31 * Objects.hashCode(mark) + models.hashCode();
    }

    @Override
    public Vehicle clone() {
        try {
            Scooter copiedScooter = (Scooter) super.clone();
            copiedScooter.models = new HashMap<String, Double>(models);
            return copiedScooter;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
