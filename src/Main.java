import java.io.*;
import java.lang.reflect.*;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("==================Задание 1 (рефлексия)======================");
        if (args.length >= 2) {
            callMethod(args);
        } else {
            System.out.println("Параметры: класс метод [параметры метода]");
            System.out.println("Пример: Auto setModelCost model1 5000");
        }

        Vehicle[] vehicles = {
                new Auto("Lada", 3),
                new Motorcycle("Honda", 4),
                new Scooter("Yamaha", 3),
                new Quadbike("Polaris", 2),
                new Moped("Alpha", 3)
        };

        for (int k = 0; k < vehicles.length; k++) {
            Vehicle v = vehicles[k];
            System.out.println("========================================");
            VehicleUtils.printModels(v);
            VehicleUtils.printAverageModelCost(v);

            Vehicle created = VehicleUtils.createVehicle("Test", 2, v);
            System.out.println("Задание 2, создан через рефлексию: " + created);

            String txtFile = "vehicle" + k + ".txt";
            try (FileWriter fw = new FileWriter(txtFile)) {
                VehicleUtils.writeVehicle(v, fw);
            }
            Vehicle fromTxt;
            try (FileReader fr = new FileReader(txtFile)) {
                fromTxt = VehicleUtils.readVehicle(fr);
            }
            System.out.println("Задание 7, прочитано из файла " + txtFile + ": " + fromTxt);
            System.out.println("Совпадает с исходным: " + v.equals(fromTxt));

            Vehicle copy = v.clone();
            System.out.println("Клон равен оригиналу: " + v.equals(copy));
            copy.setModelCost(copy.getModelsNames()[0], 1.5);
            System.out.println("После изменения клона равен оригиналу: " + v.equals(copy));
        }

        System.out.println("==================Задание 6 (среднее)======================");
        System.out.println("По всем ТС: " + VehicleUtils.getAverageModelCost(vehicles));
        System.out.println("По двум ТС: " + VehicleUtils.getAverageModelCost(vehicles[0], vehicles[2]));
        System.out.println("Без аргументов: " + VehicleUtils.getAverageModelCost());

        System.out.println("==================System.out (printf)======================");
        VehicleUtils.writeVehicle(vehicles[2], new OutputStreamWriter(System.out));
    }

    private static void callMethod(String[] args) {
        try {
            Class<?> c = Class.forName(args[0]);
            Constructor<?> constructor = c.getConstructor(String.class, int.class);
            Object obj = constructor.newInstance("Lada", 3);
            System.out.println("До вызова: " + obj);

            Method method = null;
            Method[] methods = c.getMethods();
            for (int i = 0; i < methods.length; i++) {
                if (methods[i].getName().equals(args[1])
                        && methods[i].getParameterTypes().length == args.length - 2) {
                    method = methods[i];
                }
            }
            if (method == null) {
                System.out.println("Метод не найден");
                return;
            }

            Class<?>[] types = method.getParameterTypes();
            Object[] params = new Object[types.length];
            for (int i = 0; i < types.length; i++) {
                if (types[i] == double.class) {
                    params[i] = Double.valueOf(args[i + 2]);
                } else if (types[i] == int.class) {
                    params[i] = Integer.valueOf(args[i + 2]);
                } else {
                    params[i] = args[i + 2];
                }
            }

            Object res = method.invoke(obj, params);
            if (res != null) {
                System.out.println("Результат: " + res);
            }
            System.out.println("После вызова: " + obj);
        } catch (ClassNotFoundException e) {
            System.out.println("Класс не найден");
        } catch (NoSuchMethodException e) {
            System.out.println("Нет конструктора с параметрами String и int");
        } catch (InstantiationException e) {
            System.out.println("Не удалось создать объект");
        } catch (IllegalAccessException e) {
            System.out.println("Метод недоступен");
        } catch (InvocationTargetException e) {
            System.out.println("При вызове возникло исключение: " + e.getCause().getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат числа");
        }
    }
}
