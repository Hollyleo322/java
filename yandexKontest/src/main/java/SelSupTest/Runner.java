package SelSupTest;

import SelSupTest.CrptApi.Document;
import SelSupTest.CrptApi.Document.Product;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Runner {

  public static void main(String[] args) {
    CrptApi crptApi = new CrptApi(TimeUnit.MINUTES, 1);
    Product product = new Product("qq", new Date(System.currentTimeMillis()), "322", "23450", "234", new Date(System.currentTimeMillis() + 60 * 1000), "322", "213", "105");
    Document document = new Document("123", "123", "1", "famous", false, "322", "big papa", new Date(System.currentTimeMillis()),"milk", product,new Date(System.currentTimeMillis() + 15 * 1000), "12456");
    for (int i = 0; i < 10; i++) {
        crptApi.createDocument(document,"152");
    }
    while (true) {

    }
  }
}
