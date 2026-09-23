public class NoSuchModelNameException extends Exception {
    private final String modelName;

    public NoSuchModelNameException(String modelName) {
        super("Модель с именем \"" + modelName + "\" не найдена");
        this.modelName = modelName;
    }

    public String getModelName() {
        return modelName;
    }
}