public class VehicleUtils {

    public static double getAverageModelCost(Vehicle vehicle) {
        double[] costs = vehicle.getModelsCosts();
        if (costs.length == 0) {
            return 0;
        }
        double sum = 0;
        for (double cost : costs) {
            sum += cost;
        }
        return sum / costs.length;
    }

    public static void printModels(Vehicle vehicle) {
        System.out.println("Транспортное средство: " + vehicle.getMark());
        String[] names = vehicle.getModelsNames();
        double[] costs = vehicle.getModelsCosts();

        if (names.length == 0) {
            System.out.println("(моделей нет)");
            return;
        }
        for (int i = 0; i < names.length; i++) {
            System.out.printf("%s — %.2f%n", names[i], costs[i]);
        }
    }

    public static void printModelCosts(Vehicle vehicle) {
        System.out.println("Цены моделей для \"" + vehicle.getMark() + "\":");
        double[] costs = vehicle.getModelsCosts();

        if (costs.length == 0) {
            System.out.println("(моделей нет)");
            return;
        }
        for (double cost : costs) {
            System.out.printf(" %.2f%n", cost);
        }
    }

    public static void printAverageModelCost(Vehicle vehicle) {
        double avg = getAverageModelCost(vehicle);
        System.out.printf("Средняя цена модели для \"%s\": %.2f%n",
                vehicle.getMark(), avg);
    }
}