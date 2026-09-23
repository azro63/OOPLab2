import java.util.Arrays;

public class Motorcycle implements Vehicle {
    private String mark;
    private int size = 0;
    private Model head;
    private long lastModified;

    public Motorcycle(String mark, int size) {
        this.mark = mark;
        head = new Model();
        this.lastModified = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            try {
                this.addNewModel(String.format("moto%d", i + 1), Math.random() * 500000);
            } catch (DuplicateModelNameException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    @Override public String getMark() { return mark; }
    @Override public void setMark(String mark) {
        this.mark = mark;
        this.lastModified = System.currentTimeMillis();
    }

    @Override
    public String[] getModelsNames() {
        String[] result = new String[0];
        Model p = head.getNext();
        while (p != head) {
            result = Arrays.copyOf(result, result.length + 1);
            result[result.length - 1] = p.getName();
            p = p.getNext();
        }
        return result;
    }

    @Override
    public double[] getModelsCosts() {
        double[] result = new double[0];
        Model p = head.getNext();
        while (p != head) {
            result = Arrays.copyOf(result, result.length + 1);
            result[result.length - 1] = p.getCost();
            p = p.getNext();
        }
        return result;
    }

    @Override
    public double getModelCost(String modelName) throws NoSuchModelNameException {
        Model p = head.getNext();
        while (p != head && !p.name.equals(modelName)) p = p.getNext();
        if (p == head) throw new NoSuchModelNameException(modelName);
        return p.getCost();
    }

    @Override
    public void setModelCost(String modelName, double modelCost)
            throws NoSuchModelNameException {
        if(modelCost < 0 || Double.isNaN(modelCost)) throw new ModelPriceOutOfBoundsException(modelCost);
        Model p = head.getNext();
        while (p != head && !p.name.equals(modelName)) p = p.getNext();
        if (p == head) throw new NoSuchModelNameException(modelName);
        p.setCost(modelCost);//price
        this.lastModified = System.currentTimeMillis();
    }

    @Override
    public void addNewModel(String modelName, double modelCost)
            throws DuplicateModelNameException {
        if(modelCost < 0 || Double.isNaN(modelCost)) throw new ModelPriceOutOfBoundsException(modelCost);
        Model p = head.getNext();
        while (p != head) {
            if (p.name.equals(modelName)) {
                throw new DuplicateModelNameException(modelName);
            }
            p = p.getNext();
        }
        Model newModel = new Model(modelName, modelCost);
        newModel.setNext(head);
        newModel.setPrev(head.getPrev());
        head.getPrev().setNext(newModel);
        head.setPrev(newModel);
        size++;
        this.lastModified = System.currentTimeMillis();
    }

    @Override
    public void removeOldModel(String modelName) throws NoSuchModelNameException {
        Model p = head.getNext();
        while (p != head && !p.name.equals(modelName)) p = p.getNext();
        if (p == head) throw new NoSuchModelNameException(modelName);
        p.getPrev().setNext(p.getNext());
        p.getNext().setPrev(p.getPrev());
        size--;
        this.lastModified = System.currentTimeMillis();
    }

    @Override public int getModelsLength() { return size; }

    @Override public void setModelName(String oldName, String newName)
            throws NoSuchModelNameException, DuplicateModelNameException
    {
        Model m = null;
        Model p = head.getNext();
        while (p != head  )//
        {
            if(p.getName().equals(newName)) { throw new DuplicateModelNameException(newName); }
            if (p.name.equals(oldName)) {
                m = p;

            }
            p = p.getNext();
        }
        if (m == null) throw new NoSuchModelNameException(oldName);
        m.setName(newName);
    }

    private class Model {
        String name = null;
        double cost = Double.NaN;
        Model prev = null;
        Model next = null;

        public Model(String name, double cost) { this.name = name; setCost(cost); }
        public Model() { this.setPrev(this); this.setNext(this); } // head

        public void setName(String name) { this.name = name; }
        public String getName() { return name; }

        public void setCost(double cost) {
            this.cost = cost;
        }
        public double getCost() { return cost; }

        public void setNext(Model next) { this.next = next; }
        public Model getNext() { return next; }
        public void setPrev(Model prev) { this.prev = prev; }
        public Model getPrev() { return prev; }
    }
}