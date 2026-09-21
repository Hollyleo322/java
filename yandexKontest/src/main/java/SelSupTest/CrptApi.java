package SelSupTest;

import SelSupTest.CrptApi.Document.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.Deque;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class CrptApi {
  @Getter
  public static class Document {
    @Getter
    public static class Description {
      private final String participantInn;
      public Description(String inn) {
        this.participantInn = inn;
      }
    }
    @AllArgsConstructor
    @Getter
    public static class Product {
      private String certificate_document;
      private Date certificate_document_date;
      private String certificate_document_number;
      private String owner_inn;
      private String producer_inn;
      private Date production_date;
      private String tnved_code;
      private String uit_code;
      private String uitu_code;
    }
    private final Description description;
    private final String doc_id;
    private final String doc_status;
    private final String doc_type;
    private final boolean importRequest;
    private final String owner_inn;
    private final String participant_owner;
    private final Date production_date;
    private final String production_type;
    private final Product product;
    private final Date reg_date;
    private final String reg_number;
    public Document(String inn, String doc_id, String doc_status, String doc_type, boolean importRequest, String owner_inn, String participant_owner, Date productionDate, String production_type, Product product, Date reg_date, String reg_number) {
      this.description = new Description(inn);
      this.doc_id = doc_id;
      this.doc_status = doc_status;
      this.doc_type = doc_type;
      this.importRequest = importRequest;
      this.owner_inn = owner_inn;
      this.participant_owner = participant_owner;
      this.production_date = productionDate;
      this.production_type = production_type;
      this.product = product;
      this.reg_date = reg_date;
      this.reg_number = reg_number;
    }
  }
  @AllArgsConstructor
  @Getter
  public static class QueryBody {
    private String document_format;
    private String product_document;
    private String product_group;
    private String signature;
    private String type;
  }
  private final TimeUnit period;
  private final int requestLimit;
  private final Deque<HttpRequest> dequeRequests;
  private final AtomicInteger counter;
  private final HttpClient httpClient;
  private final AtomicReference<LocalDateTime> borderTime;
  private final ScheduledExecutorService scheduledExecutorService;

  public CrptApi(TimeUnit period, int requestLimit) {
    this.period = period;
    this.requestLimit = requestLimit;
    dequeRequests = new ConcurrentLinkedDeque<>();
    counter = new AtomicInteger(0);
    httpClient = HttpClient.newHttpClient();
    borderTime = new AtomicReference<>(LocalDateTime.now());
    scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    scheduledExecutorService.scheduleAtFixedRate(this::sendRequestsFromDeque, 0,1, period);
    Runtime.getRuntime().addShutdownHook(new Thread(scheduledExecutorService::shutdownNow));
  }
  public int createDocument(Document document, String sign){
    int result = 0;
    ObjectMapper objectMapper = new ObjectMapper();
    String requestBodyDocument = "";
    String type = "T-Shirts";
    try {
      requestBodyDocument = objectMapper.writeValueAsString(document);
      QueryBody queryBody = new QueryBody("MANUAL", Base64.getEncoder().encodeToString(requestBodyDocument.getBytes()), "1", sign, "LP_INTRODUCE_GOODS");
      requestBodyDocument = objectMapper.writeValueAsString(queryBody);

    } catch (JsonProcessingException e) {
      System.out.println(e.getMessage());
    }
    HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/api/v3/lk/documents/create/" + type)).header("Content-Type", "application/json").POST(BodyPublishers.ofString(requestBodyDocument)).build();
    if (counter.get() >= requestLimit) {
      dequeRequests.add(request);
      result = 1;
    } else {
      try {
        var response = httpClient.send(request, BodyHandlers.ofString());
        result = response.statusCode();
        counter.incrementAndGet();
      } catch (Exception e) {
        System.out.println(e.getClass() + " " + e.getMessage());
      }
    }
    return result;
  }
  private void sendRequestsFromDeque() {
    counter.set(0);
    if (dequeRequests.isEmpty()) {
      return;
    }
    while (!dequeRequests.isEmpty() && counter.get() < requestLimit) {
      try {
        httpClient.send(dequeRequests.pop(), BodyHandlers.ofString());
        counter.incrementAndGet();
      } catch (Exception e) {
        System.out.println(e.getClass() + " " +  e.getMessage());
      }
    }
  }
}
