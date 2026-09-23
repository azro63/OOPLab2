public class DuplicateModelNameException extends Exception {
    private final String modelName;

    public DuplicateModelNameException(String modelName) {
        super("Модель с именем \"" + modelName + "\" уже существует");
        this.modelName = modelName;
    }

    public String getModelName() {
        return modelName;
    }
}