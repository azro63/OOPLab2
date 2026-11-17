import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.Scanner;

public class VehicleUtils {

    public static double getAverageModelCost(Vehicle... vehicles) {
        double sum = 0;
        int count = 0;
        for (Vehicle vehicle : vehicles) {
            for (double cost : vehicle.getModelsCosts()) {
                sum += cost;
                count++;
            }
        }
        if (count == 0) {
            return 0;
        }
        return sum / count;
    }

    public static void printModels(Vehicle vehicle) {
        if (vehicle instanceof Auto) {
            System.out.println("Автомобиль: " + vehicle.getMark());
        } else if (vehicle instanceof Motorcycle) {
            System.out.println("Мотоцикл: " + vehicle.getMark());
        } else if (vehicle instanceof Scooter) {
            System.out.println("Скутер: " + vehicle.getMark());
        } else if (vehicle instanceof Quadbike) {
            System.out.println("Квадроцикл: " + vehicle.getMark());
        } else if (vehicle instanceof Moped) {
            System.out.println("Мопед: " + vehicle.getMark());
        }
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

    private static void writeString(DataOutputStream dos, String s) throws IOException {
        byte[] b = s.getBytes();
        dos.writeInt(b.length);
        dos.write(b);
    }

    private static String readString(DataInputStream dis) throws IOException {
        int len = dis.readInt();
        byte[] b = new byte[len];
        dis.readFully(b);
        return new String(b);
    }

    public static void outputVehicle(Vehicle v, OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        String[] names = v.getModelsNames();
        double[] costs = v.getModelsCosts();
        writeString(dos, v.getClass().getName());
        writeString(dos, v.getMark());
        dos.writeInt(names.length);
        for (int i = 0; i < names.length; i++) {
            writeString(dos, names[i]);
            dos.writeDouble(costs[i]);
        }
        dos.flush();
    }

    public static Vehicle inputVehicle(InputStream in) throws IOException, DuplicateModelNameException {
        DataInputStream dis = new DataInputStream(in);
        String classType = readString(dis);
        String mark = readString(dis);
        int n = dis.readInt();
        Vehicle v = createVehicle(mark, classType);
        for (int i = 0; i < n; i++) {
            String model = readString(dis);
            double cost = dis.readDouble();
            v.addNewModel(model, cost);
        }
        return v;
    }

    public static void writeVehicle(Vehicle v, Writer out) {
        PrintWriter pw = new PrintWriter(out);
        String[] names = v.getModelsNames();
        double[] costs = v.getModelsCosts();
        pw.printf("%s %s %d%n", v.getClass().getName(), v.getMark(), names.length);
        for (int i = 0; i < names.length; i++) {
            pw.printf("%s %s%n", names[i], costs[i]);
        }
        pw.flush();
    }

    public static Vehicle readVehicle(Reader in) throws IOException, DuplicateModelNameException {
        Scanner sc = new Scanner(in);
        sc.useLocale(Locale.US);
        String classType = sc.next();
        String mark = sc.next();
        int n = sc.nextInt();
        Vehicle v = createVehicle(mark, classType);
        for (int i = 0; i < n; i++) {
            String model = sc.next();
            double cost = sc.nextDouble();
            v.addNewModel(model, cost);
        }
        return v;
    }

    public static Vehicle createVehicle(String mark, int length, Vehicle vehicle) {
        Class<? extends Vehicle> c = vehicle.getClass();
        try {
            Constructor<? extends Vehicle> constructor = c.getConstructor(String.class, int.class);
            return constructor.newInstance(mark, length);
        } catch (NoSuchMethodException e) {
            return null;
        } catch (InstantiationException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        }
    }

    private static Vehicle createVehicle(String mark, String classType) throws IOException {
        Vehicle v = null;
        if (classType.equals(Auto.class.getName())) {
            v = new Auto(mark, 0);
        } else if (classType.equals(Motorcycle.class.getName())) {
            v = new Motorcycle(mark, 0);
        } else if (classType.equals(Scooter.class.getName())) {
            v = new Scooter(mark, 0);
        } else if (classType.equals(Quadbike.class.getName())) {
            v = new Quadbike(mark, 0);
        } else if (classType.equals(Moped.class.getName())) {
            v = new Moped(mark, 0);
        } else {
            throw new IOException("Неверно указан тип ТС");
        }
        return v;
    }
}
