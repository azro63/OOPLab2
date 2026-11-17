import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;

public class Moped implements Vehicle {

    private String mark;
    private LinkedList<Model> models = new LinkedList<Model>();

    public Moped(String mark, int length) {
        this.mark = mark;
        for (int i = 0; i < length; i++) {
            models.add(new Model("moped" + (i + 1), Math.random() * 100000));
        }
    }

    @Override public String getMark() { return mark; }
    @Override public void setMark(String mark) { this.mark = mark; }

    private Model findModel(String modelName) {
        for (Model m : models) {
            if (m.getName().equals(modelName)) return m;
        }
        return null;
    }

    @Override
    public String[] getModelsNames() {
        String[] result = new String[models.size()];
        int i = 0;
        for (Model m : models) {
            result[i] = m.getName();
            i++;
        }
        return result;
    }

    @Override
    public double[] getModelsCosts() {
        double[] result = new double[models.size()];
        int i = 0;
        for (Model m : models) {
            result[i] = m.getCost();
            i++;
        }
        return result;
    }

    @Override
    public double getModelCost(String modelName) throws NoSuchModelNameException {
        Model m = findModel(modelName);
        if (m == null) throw new NoSuchModelNameException(modelName);
        return m.getCost();
    }

    @Override
    public void setModelCost(String modelName, double modelCost)
            throws NoSuchModelNameException {
        if (modelCost < 0 || Double.isNaN(modelCost)) {
            throw new ModelPriceOutOfBoundsException(modelCost);
        }
        Model m = findModel(modelName);
        if (m == null) throw new NoSuchModelNameException(modelName);
        m.setCost(modelCost);
    }

    @Override
    public void addNewModel(String modelName, double modelCost)
            throws DuplicateModelNameException {
        if (modelCost < 0 || Double.isNaN(modelCost)) {
            throw new ModelPriceOutOfBoundsException(modelCost);
        }
        if (findModel(modelName) != null) throw new DuplicateModelNameException(modelName);
        models.add(new Model(modelName, modelCost));
    }

    @Override
    public void removeOldModel(String modelName) throws NoSuchModelNameException {
        Model m = findModel(modelName);
        if (m == null) throw new NoSuchModelNameException(modelName);
        models.remove(m);
    }

    @Override public int getModelsLength() { return models.size(); }

    @Override
    public void setModelName(String oldName, String newName)
            throws NoSuchModelNameException, DuplicateModelNameException {
        if (findModel(newName) != null) throw new DuplicateModelNameException(newName);
        Model m = findModel(oldName);
        if (m == null) throw new NoSuchModelNameException(oldName);
        m.setName(newName);
    }

    private static class Model implements Serializable, Cloneable {
        private String name;
        private double cost;
        public Model(String name, double cost) { this.name = name; this.cost = cost; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public double getCost() { return cost; }
        public void setCost(double cost) { this.cost = cost; }
        @Override
        public Model clone() {
            try {
                return (Model) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("mark='")
                .append(mark)
                .append("', models=[");
        boolean first = true;
        for (Model m : models) {
            if (!first) { sb.append(", "); }
            first = false;
            sb.append("{name=")
                    .append(m.getName())
                    .append("} {cost=")
                    .append(m.getCost())
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

        String[] names1 = getModelsNames();
        String[] names2 = other.getModelsNames();
        double[] costs1 = getModelsCosts();
        double[] costs2 = other.getModelsCosts();

        for (int i = 0; i < names1.length; i++) {
            if (!Objects.equals(names1[i], names2[i]) || costs1[i] != costs2[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(mark);
        for (Model m : models) {
            result = 31 * result + Objects.hashCode(m.getName());
            result = 31 * result + Double.hashCode(m.getCost());
        }
        return result;
    }

    @Override
    public Vehicle clone() {
        try {
            Moped copiedMoped = (Moped) super.clone();
            copiedMoped.models = new LinkedList<Model>();
            for (Model m : models) {
                copiedMoped.models.add(m.clone());
            }
            return copiedMoped;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
