import java.util.Arrays;

public class Main {
    public static void main(String[] args)  throws DuplicateModelNameException, NoSuchModelNameException{
        Vehicle[] vehicles = {
                new Auto("Lada", 3),
                new Motorcycle("Honda", 4)
        };

        for (Vehicle v : vehicles) {
            System.out.println("========================================");
            VehicleUtils.printModels(v);
            VehicleUtils.printModelCosts(v);
            VehicleUtils.printAverageModelCost(v);
            v.addNewModel("A", 100);
            v.addNewModel("B", 100);
            v.setModelName("B1", "A1");
           // v.removeOldModel("B");
            VehicleUtils.printModels(v);
            VehicleUtils.printModelCosts(v);
        }

      /*  System.out.println("========================================");
        Vehicle v = vehicles[0];
        Vehicle v1 = vehicles[1];
        //auto dubl
        try {
            v.addNewModel("Iskra", 5000000);
        } catch (DuplicateModelNameException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        VehicleUtils.printModels(v);
        VehicleUtils.printAverageModelCost(v);

        try {
            v.addNewModel("Iskra", 5000000);
        } catch (DuplicateModelNameException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
//auto !exist
        try {
            v.getModelCost("asdf1");
        } catch (NoSuchModelNameException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
//moto dubl
        try {
            v1.addNewModel("Honda", 5000000);
        } catch (DuplicateModelNameException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        try {
            v1.addNewModel("Honda", 5000000);
        } catch (DuplicateModelNameException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
//
        try
        {
            v.setModelCost("Iskra", -100);
        }
        catch(ModelPriceOutOfBoundsException e)
        {
            System.out.println("Ошибка: " + e.getMessage());
        }
        try
        {
            v.setModelCost("model1", -100);
        }
        catch(ModelPriceOutOfBoundsException e)
        {
            System.out.println("Ошибка: " + e.getMessage());
        }
//moto !exist
        try {
            v1.getModelCost("asdf2");
        } catch (NoSuchModelNameException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
//auto remove !exist
        try
        {
            v.removeOldModel("Iskra");
        } catch (NoSuchModelNameException e)
        {
            System.out.println("Ошибка: " + e.getMessage());
        }

        try
        {
            v.removeOldModel("Iskra");
        } catch (NoSuchModelNameException e)
        {
            System.out.println("Ошибка: " + e.getMessage());
        }
//auto dubl
        try
        {
            v.setModelName("model1", "MODEL");
        } catch (DuplicateModelNameException e)
        {
            System.out.println("Ошибка: " + e.getMessage());
        }

        try
        {
            v.setModelName("model1", "MODEL");
        } catch (DuplicateModelNameException e)
        {
            System.out.println("Ошибка: " + e.getMessage());
        }//
//moto !exist
        try
        {
            v1.removeOldModel("Honda");
        } catch (NoSuchModelNameException e)
        {
            System.out.println("Ошибка: " + e.getMessage());
        }

        try
        {
            v.removeOldModel("Honda");
        } catch (NoSuchModelNameException e)
        {
            System.out.println("Ошибка: " + e.getMessage());
        }
//moto
        try
        {
            v1.setModelName("moto2", "moto3");
            VehicleUtils.printModels(v1);
        } catch (NoSuchModelNameException e)
        {
            System.out.println("Ошибка!: " + e.getMessage());
        }

        try
        {
            v1.setModelName("moto1", "moto3");
            VehicleUtils.printModels(v1);
        } catch (DuplicateModelNameException e)
        {
            System.out.println("Ошибка!!!!: " + e.getMessage());
        }
//del, setmodel*/

    }
}