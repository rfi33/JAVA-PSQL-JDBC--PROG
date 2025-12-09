package ProductManagement.com;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DataRetriever dataRetriever = new DataRetriever();
        testGetAllCategories(dataRetriever);
        Main.testGetProductList(dataRetriever);
        testGetProductsByCriteria(dataRetriever);
        testGetProductsByCriteria2(dataRetriever);
    }

    public static void testGetAllCategories(DataRetriever dataRetriever) {
        List<Category> categories = dataRetriever.getAllCategories();

        System.out.println("Voici toute les categories:");
        for (Category category : categories) {
            System.out.println("  - " + category.getName());
        }
        System.out.println();
        System.out.println("Total de categories:"+categories.size());
    }


    public static void testGetProductList(DataRetriever dataRetriever) {
        List<Product> products = dataRetriever.getProductList(1, 10);
        System.out.println();
        System.out.println("Le nombre de produit recuperer sur cette page  "+ products.size());
        for (Product product : products) {
            System.out.println("  - " + product.getName());
        }
        System.out.println();
        List<Product> products2 = dataRetriever.getProductList(1,5 );
        System.out.println("Le nombre de produit recuperer sur cette page  "+ products2.size());
        for (Product product : products2){
            System.out.println(" -" + product.getName());
        }
        System.out.println();
        List<Product> products3 = dataRetriever.getProductList(1,3 );
        System.out.println("Le nombre de produit recuperer sur cette page  "+ products3.size());
        for (Product product : products3){
            System.out.println(" -" + product.getName());
        }
        System.out.println();
        List<Product> products4 = dataRetriever.getProductList(2,2 );
        System.out.println("Le nombre de produit recuperer sur cette page  "+ products4.size());
        for (Product product : products4){
            System.out.println(" -" + product.getName());
        }
    }

    private static void testGetProductsByCriteria(DataRetriever dataRetriever) {
        List<Product> products = dataRetriever.getProductsByCriteria("Dell",null,null,null);
        System.out.println();
        for (Product product :products){
            System.out.println(" -"+ product.getName() + " | " + product.getCategory().getName() + " | " + product.getCreationDatetime());
        }
        List<Product> products2 = dataRetriever.getProductsByCriteria(null,"info",null,null);
        System.out.println();
        for (Product product :products2){
            System.out.println(" -"+ product.getName() + " | " + product.getCategory().getName() + " | " + product.getCreationDatetime());
        }
        List<Product> products3 = dataRetriever.getProductsByCriteria("iPhone","mobile",null,null);
        System.out.println();
        for (Product product :products3){
            System.out.println(" -"+ product.getName() + " | " + product.getCategory().getName() + " | " + product.getCreationDatetime());
        }



        Instant dateMin1 = LocalDateTime.of(2024, 2, 1, 0, 0, 0)
                .atZone(ZoneId.systemDefault())
                .toInstant();

        Instant dateMax1 = LocalDateTime.of(2024, 3, 1, 0, 0, 0)
                .atZone(ZoneId.systemDefault())
                .toInstant();

        List<Product> products4 = dataRetriever.getProductsByCriteria(null,null,dateMin1,dateMax1);
        System.out.println();
        for (Product product :products4){
            System.out.println(" -"+ product.getName() + " | " + product.getCategory().getName() + " | " + product.getCreationDatetime());
        }

        List<Product> products5 = dataRetriever.getProductsByCriteria("Samsung","bureau",null,null);
        System.out.println();
        for (Product product : products5){
            System.out.println(" -"+ product.getName() + " | " + product.getCategory().getName() + " | " + product.getCreationDatetime());
        }

        List<Product> products6 = dataRetriever.getProductsByCriteria("Sony", "informatique", null , null);
        System.out.println();
        for (Product product : products6) {
            System.out.println(" -"+ product.getName() + " | " + product.getCategory().getName() + " | " + product.getCreationDatetime());
        };

        Instant dateMin2 = LocalDateTime.of(2024, 1, 1, 0, 0, 0)
                .atZone(ZoneId.systemDefault())
                .toInstant();

        Instant dateMax2 = LocalDateTime.of(2024, 12, 1, 0, 0, 0)
                .atZone(ZoneId.systemDefault())
                .toInstant();
        List<Product> products7 = dataRetriever.getProductsByCriteria(null, "audio", dateMin2 , dateMax2);
        System.out.println();
        for (Product product : products7) {
            System.out.println(" -"+ product.getName() + " | " + product.getCategory().getName() + " | " + product.getCreationDatetime());
        };


        List<Product> products8 = dataRetriever.getProductsByCriteria(null, null, null , null);
        System.out.println();
        for (Product product : products8) {
            System.out.println(" -"+ product.getName() + " | " + product.getCategory().getName() + " | " + product.getCreationDatetime());
        };

    }
    public static void testGetProductsByCriteria2(DataRetriever dataRetriever){
        List<Product> products = dataRetriever.getProductsByCriteria2(null,null,null,null,1,10);
        System.out.println();
        for (Product product : products) {
            System.out.println(" -"+ product.getName() + " | " + product.getCategory().getName() + " | " + product.getCreationDatetime());
        };

        List<Product> products2 = dataRetriever.getProductsByCriteria2("Dell",null,null,null,1,5);
        System.out.println();
        for (Product product : products2) {
            System.out.println(" -"+ product.getName() + " | " + product.getCategory().getName() + " | " + product.getCreationDatetime());
        };
        List<Product> products3 = dataRetriever.getProductsByCriteria2(null,"Informatique",null,null,1,10);
        System.out.println();
        for (Product product : products3) {
            System.out.println( product.getId()+"- "+ product.getName() + " | " + product.getCategory().getName() + " | " + product.getCreationDatetime());
        };

    }
}
