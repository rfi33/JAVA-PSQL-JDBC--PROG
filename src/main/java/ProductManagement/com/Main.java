package ProductManagement.com;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DataRetriever dataRetriever = new DataRetriever();
        testGetAllCategories(dataRetriever);
        Main.testGetProductList(dataRetriever);
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
}
