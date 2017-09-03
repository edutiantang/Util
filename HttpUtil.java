public class HttpUtil {

    private static final int connectTimeoutMillis = 8000;
    private static final int readTimeoutMillis = 8000;

    public static String get(String url, Map<String, String> requestHeader) throws Exception {

        HttpURLConnection connection = null;
        StringBuilder response = new StringBuilder();
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            setConnection(connection, "GET");
            addRequestHeader(connection, requestHeader);
            getResponseBody(connection, response);

        } catch (Exception e) {
            Log.e("http", "GET failed. The error message is " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return response.toString();
    }

    public static String post(String url, Map<String, String> requestHeader, String requestBody) throws Exception {

        HttpURLConnection connection = null;
        StringBuilder response = new StringBuilder();
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            setConnection(connection, "POST");
            addRequestHeader(connection, requestHeader);
            addRequestBody(requestBody, connection);
            getResponseBody(connection, response);

        } catch (Exception e) {
            Log.e("http", "POST failed. The error message is " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return response.toString();
    }

    public static String put(String url, Map<String, String> requestHeader, String requestBody) throws Exception {

        HttpURLConnection connection = null;
        StringBuilder response = new StringBuilder();
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            setConnection(connection, "PUT");
            addRequestHeader(connection, requestHeader);
            addRequestBody(requestBody, connection);
            getResponseBody(connection, response);

        } catch (Exception e) {
            Log.e("http", "PUT failed. The error message is " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return response.toString();
    }

    private static void setConnection(HttpURLConnection connection, String method) throws ProtocolException {
        connection.setRequestMethod(method);
        connection.setConnectTimeout(connectTimeoutMillis);
        connection.setReadTimeout(readTimeoutMillis);
        connection.setDoInput(true);
        connection.setDoOutput(true);
    }


    private static void addRequestHeader(HttpURLConnection connection, Map<String, String> requestHeader) {
        if (requestHeader != null && !requestHeader.isEmpty()) {
            Set<Map.Entry<String, String>> setHeader = requestHeader
                    .entrySet();
            for (Iterator<Map.Entry<String, String>> it = setHeader.iterator(); it
                    .hasNext();) {
                Map.Entry<String, String> entry = it.next();
                connection.addRequestProperty(entry.getKey(), entry.getValue());
            }
        }
    }

    private static void addRequestBody(String requestBody, HttpURLConnection connection) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
        dataOutputStream.writeBytes(requestBody);
    }

    private static void getResponseBody(HttpURLConnection connection, StringBuilder response) throws IOException {
        InputStream in = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
    }

    /*public static void main(String[] args) {

        Map<String, String> requestHeader = new HashMap<>();
        requestHeader.put("Accept", "application/json");
        requestHeader.put("Content-Type", "application/json");
        try {
            System.out.println(get("http://localhost:8080/api/1",requestHeader));
            System.out.println(post("http://localhost:8080/api/1",requestHeader,
                    "{\n" +
                            "    \"name\": \"name\"\n" +
                            "}"));
            System.out.println(put("http://localhost:8080/api/1",requestHeader,
                    "{\n" +
                    "    \"name\": \"name\"\n" +
                    "}"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}