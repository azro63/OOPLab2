import java.io.*;

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
        if(vehicle.getClass().getName().equals(Auto.class.getName())) {
            System.out.println("Автомобиль: " + vehicle.getMark());
        }else if(vehicle.getClass().getName().equals(Motorcycle.class.getName())) {
            System.out.println("Мотоцикл: " + vehicle.getMark());
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
        pw.println(v.getClass().getName());
        pw.println(v.getMark());
        pw.println(names.length);
        for (int i = 0; i < names.length; i++) {
            pw.println(names[i]);
            pw.println(costs[i]);
        }
        pw.flush();
    }

    public static Vehicle readVehicle(Reader in) throws IOException, DuplicateModelNameException {
        BufferedReader br = new BufferedReader(in);
        String classType = br.readLine();
        String mark = br.readLine();
        int n = Integer.parseInt(br.readLine().trim());
        Vehicle v = createVehicle(mark, classType);
        for (int i = 0; i < n; i++) {
            String model = br.readLine();
            double cost = Double.parseDouble(br.readLine().trim());
            v.addNewModel(model, cost);
        }
        return v;
    }

    private static Vehicle createVehicle(String mark, String classType) throws IOException {
        Vehicle v = null;
        if(classType.equals(Auto.class.getName())) {
            v = new Auto(mark, 0);
        } else if (classType.equals(Motorcycle.class.getName())) {
            v = new Motorcycle(mark, 0);
        }else{
            throw new IOException("Неверно указан тип ТС");
        }
        return v;
    }
}
