import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> data = new ArrayList<String>();
    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Shambhavi's search engine");
        } 
        else if (url.getPath().contains("/search")) {
            ArrayList<String> resultList=new ArrayList<String>();
            String[] parameter = url.getQuery().split("=");
                if (parameter[0].equals("s")) {
                    for  (int i=0; i<data.size(); i++){
                        if (data.get(i).contains(parameter[1]) ){
                            resultList.add(data.get(i));
                        }
                    }
                    return String.format("Found: %s", resultList.toString());
                }
                    
            return String.format("No words found");
        }
        

        else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s") ) {
                    if (!data.contains(parameters[1])){
                        data.add(parameters[1]);
                        return String.format("Added! "+parameters[1]);
                    }
                
                    else{
                        return "already add";
                    }
                }
            }
            return "404 Not Found!";
        }
    }
}

class Search {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
