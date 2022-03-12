package unit_tests;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.hamcrest.Matchers;

import org.junit.jupiter.api.Test;
import utilities.GitHubUser;
import utilities.RetrieveUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TestClass extends BaseTests{

    @Test
    public void givenSuccessRegistration_whenUserInfoRetrieved_then200OK() throws IOException {
        //given
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost("http://localhost:8080/user/register");

        //when
        String json = "{\"name\":\"ssss\",\"password\":\"Pass\"}";
        StringEntity entity = new StringEntity(json);
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");

        //then
        CloseableHttpResponse response = client.execute(request);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();

    }
    @Test
    public void givenSuccessLogin_whenUserInfoRetrieved_thenTokenInPayload() throws IOException {
        //given
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost("http://localhost:8080/user/login");

        //when
        String json = "{\"login\":\"ssss\",\"password\":\"Pass\"}";
        StringEntity entity = new StringEntity(json);
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");

        //then
        CloseableHttpResponse response = client.execute(request);
        GitHubUser resource = RetrieveUtil.retrieveResourceFromResponse(response, GitHubUser.class);
        assertThat("ssss", Matchers.is( resource.getName() ) );
        assertThat(false,Matchers.equalTo(resource.getToken().isEmpty()));

        client.close();
    }
    @Test
    public void givenMessage_whenSendingMessage_then200OkSuccess() throws IOException {
        //given
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost("http://localhost:8080/user/msg");

        //when
        String json = "{\"name\":\"ssss\",\"message\":\"TESTMSG\"}";
        StringEntity entity = new StringEntity(json);
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Authorization", "Bearer_eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiLQmNCy0LDQvdC-0LIg0JLQu9Cw0LTQuNC80LjRgCDQktCw0YHQuNC70YzQtdCy0LjRhyIsImV4cCI6MTY0NzEwOTI4NiwiaWF0IjoxNjQ3MDczMjg2fQ.4mhJvvB0vxqAQu9f146U8IL0iNNP73TdfRYBLX2lDmrLr6i21ybYHVB4hSpGiefEeG77YWx9iQRUNX0GRA-QGg");
        request.setHeader("Content-Type", "application/json");

        //then
        CloseableHttpResponse response = client.execute(request);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();

    }
    @Test
    public void givenMessage_whenSendingHistoryMessage_thenRetrieveArrayOfMessages() throws IOException {
        //given
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost("http://localhost:8080/user/msg");

        //when
        String json = "{\"name\":\"ssss\",\"message\":\"history 5\"}";
        StringEntity entity = new StringEntity(json);
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Authorization", "Bearer_eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiLQmNCy0LDQvdC-0LIg0JLQu9Cw0LTQuNC80LjRgCDQktCw0YHQuNC70YzQtdCy0LjRhyIsImV4cCI6MTY0NzEwOTI4NiwiaWF0IjoxNjQ3MDczMjg2fQ.4mhJvvB0vxqAQu9f146U8IL0iNNP73TdfRYBLX2lDmrLr6i21ybYHVB4hSpGiefEeG77YWx9iQRUNX0GRA-QGg");
        request.setHeader("Content-Type", "application/json");

        //then
        CloseableHttpResponse response = client.execute(request);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();

    }



}
