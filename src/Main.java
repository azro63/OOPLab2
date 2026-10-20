import java.io.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        Vehicle[] vehicles = {
                new Auto("Lada", 3),
                new Motorcycle("Honda", 4)
        };
        String[] types = {Auto.class.getName(), Motorcycle.class.getName()};

        for (int k = 0; k < vehicles.length; k++) {
            Vehicle v = vehicles[k];
            System.out.println("========================================");
            VehicleUtils.printModels(v);
            VehicleUtils.printModelCosts(v);
            VehicleUtils.printAverageModelCost(v);

            String binFile = "vehicle" + k + ".bin";
            try (FileOutputStream fos = new FileOutputStream(binFile)) {
                VehicleUtils.outputVehicle(v, fos);
            }
            Vehicle fromBin;
            try (FileInputStream fis = new FileInputStream(binFile)) {
                fromBin = VehicleUtils.inputVehicle(fis);
            }
            System.out.println("(Байтовый поток) прочитано из файла " + binFile + ":");
            VehicleUtils.printModels(fromBin);
            System.out.println("Совпадает с исходным: " + isSame(v, fromBin));

            String txtFile = "vehicle" + k + ".txt";
            try (FileWriter fw = new FileWriter(txtFile)) {
                VehicleUtils.writeVehicle(v, fw);
            }
            Vehicle fromTxt;
            try (FileReader fr = new FileReader(txtFile)) {
                fromTxt = VehicleUtils.readVehicle(fr);
            }
            System.out.println("(Символьный поток) прочитано из файла " + txtFile + ":");
            VehicleUtils.printModels(fromTxt);
            System.out.println("Совпадает с исходным: " + isSame(v, fromTxt));

            System.out.println("(System.out) запись символьным потоком:");
            VehicleUtils.writeVehicle(v, new OutputStreamWriter(System.out));

            String serFile = "vehicleSer" + k + ".txt";
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(serFile))) {
                oos.writeObject(v);
            }
            Vehicle fromSer;
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(serFile))) {
                fromSer = (Vehicle) ois.readObject();
            }
            System.out.println("(Сериализация) прочитано из файла " + serFile + ":");
            VehicleUtils.printModels(fromSer);
            System.out.println("Совпадает с исходным: " + isSame(v, fromSer));
        }

        System.out.println("==================Выше код предыдущей лабы======================");
        /*System.out.println("Введите транспортное средство (тип тс, марка, кол-во моделей, затем название и цена каждой модели):");
        Vehicle fromStdin = VehicleUtils.readVehicle(new InputStreamReader(System.in));
        System.out.println("Считано из System.in:");
        VehicleUtils.printModels(fromStdin);*/
        Vehicle auto = vehicles[0].clone();
        Vehicle moto = vehicles[1].clone();
        System.out.println("==================Проверки======================");
        System.out.println("Авто хэш " + vehicles[0].hashCode());
        System.out.println(vehicles[0].toString());
        System.out.println("Мото хэш " + vehicles[1].hashCode());
        System.out.println(vehicles[1].toString());
        System.out.println("Авто клон хэш " + auto.hashCode());
        System.out.println("Клон авто " + auto.toString());
        System.out.println("Сравнение клона и орига авто " + auto.equals(vehicles[0]));
        System.out.println("Сравнение хэшей клона и орига авто " + (vehicles[0].hashCode() == auto.hashCode()));
        System.out.println("Клон мото " + moto.toString());
        System.out.println("Мото клон хэш " + moto.hashCode());
        System.out.println("Сравнение клона и орига мото " + moto.equals(vehicles[1]));
        System.out.println("Сравнение хэшей клона и орига мото " + (vehicles[1].hashCode() == moto.hashCode()));
        System.out.println("==================Проверка на правильное копирование======================");
        vehicles[0].addNewModel("MARK", 23525.343);
        vehicles[1].addNewModel("MARK", 23525.343);
        System.out.println("Авто хэш " + vehicles[0].hashCode());
        System.out.println(vehicles[0].toString());
        System.out.println("Мото хэш " + vehicles[1].hashCode());
        System.out.println(vehicles[1].toString());
        System.out.println("Авто клон хэш " + auto.hashCode());
        System.out.println("Клон авто " + auto.toString());
        System.out.println("Сравнение клона и орига авто " + auto.equals(vehicles[0]));
        System.out.println("Сравнение хэшей клона и орига авто " + (vehicles[0].hashCode() == auto.hashCode()));
        System.out.println("Клон мото " + moto.toString());
        System.out.println("Мото клон хэш " + moto.hashCode());
        System.out.println("Сравнение клона и орига мото " + moto.equals(vehicles[1]));
        System.out.println("Сравнение хэшей клона и орига мото " + (vehicles[1].hashCode() == moto.hashCode()));
    }

    private static boolean isSame(Vehicle a, Vehicle b) {
        return a.getMark().equals(b.getMark())
                && Arrays.equals(a.getModelsNames(), b.getModelsNames())
                && Arrays.equals(a.getModelsCosts(), b.getModelsCosts());
    }
}
