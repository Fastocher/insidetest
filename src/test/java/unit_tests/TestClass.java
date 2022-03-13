package unit_tests;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.hamcrest.Matchers;

import org.testng.annotations.Test;
import utilities.GitHubUser;
import utilities.RetrieveUtil;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TestClass extends BaseTests{

    private String token;

//    @Test
//    public void givenSuccessRegistration_whenUserInfoRetrieved_then200OK() throws IOException {
//        //given
//        CloseableHttpClient client = HttpClients.createDefault();
//        HttpPost request = new HttpPost("http://localhost:8080/user/register");
//
//        //when
//        String json = "{\"name\":\"ssss\",\"password\":\"Pass\"}";
//        StringEntity entity = new StringEntity(json);
//        request.setEntity(entity);
//        request.setHeader("Accept", "application/json");
//        request.setHeader("Content-type", "application/json");
//
//        //then
//        CloseableHttpResponse response = client.execute(request);
//        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
//        client.close();
//
//    }
    @Test
    public void givenSuccessLogin_whenUserInfoRetrieved_thenTokenInPayload() throws IOException {
        //given
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost("http://localhost:8080/user/login");

        //when
        String json = "{\"name\":\"ssss\",\"password\":\"Pass\"}";
        StringEntity entity = new StringEntity(json);
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");

        //then
        CloseableHttpResponse response = client.execute(request);
        GitHubUser resource = RetrieveUtil.retrieveResourceFromResponse(response, GitHubUser.class);
        assertThat("ssss", Matchers.is( resource.getName() ) );
        assertThat(false,Matchers.equalTo(resource.getToken().isEmpty()));

        token = resource.getToken().toString();

        client.close();
    }
    @Test(dependsOnMethods = "givenSuccessLogin_whenUserInfoRetrieved_thenTokenInPayload",priority = 1)
    public void givenMessage_whenSendingMessage_then200OkSuccess() throws IOException {
        //given
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost("http://localhost:8080/user/msg");

        //when
        String json = "{\"name\":\"ssss\",\"message\":\"TESTMSG\"}";
        StringEntity entity = new StringEntity(json);
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Authorization", "Bearer_"+ token);
        request.setHeader("Content-Type", "application/json");

        //then
        CloseableHttpResponse response = client.execute(request);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();

    }
    @Test(dependsOnMethods = "givenSuccessLogin_whenUserInfoRetrieved_thenTokenInPayload",priority = 2)
    public void givenMessage_whenSendingHistoryMessage_thenRetrieveArrayOfMessages() throws IOException {
        //given
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost("http://localhost:8080/user/msg");

        //when
        String json = "{\"name\":\"ssss\",\"message\":\"history 5\"}";
        StringEntity entity = new StringEntity(json);
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Authorization", "Bearer_"+ token);
        request.setHeader("Content-Type", "application/json");

        //then
        CloseableHttpResponse response = client.execute(request);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
        client.close();

    }



}
