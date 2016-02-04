
public class HttpClientParc {
    private final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) throws Exception {
        //HttpClientParc http = new HttpClientParc();
        //System.out.println("Testing 1 - Send Http GET request");
        //http.sendGet();
        int i,j;
        double lat,lon;
        int counter = 1;
        System.out.println("\nTesting 2 - Send Http POST request");
        for(i=0; i<1; i++){
          for(j=0; j<1; j++) {
              lon = 30.46664719 + (j*0.0004901157);
              lat = 50.38929919 + (i*0.0003122835);
              randomData data = new randomData();
              String Category = data.getcategory();
              PostRequest postRequest = new PostRequest(lat, lon, Category);
              postRequest.sendPost();
              PostOffers postOffer = new PostOffers(counter, Category);
              postOffer.sendPost();
              counter++;
            }
        }
        //http.sendPost();

        System.out.println("HttpClientParc test!");
    }


    }
