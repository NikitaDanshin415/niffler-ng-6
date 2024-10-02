package guru.qa.niffler.api.github;

import com.fasterxml.jackson.databind.JsonNode;
import guru.qa.niffler.api.spend.SpendApi;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GitHubApiClient {

    HttpLoggingInterceptor interceptor;

    private final Retrofit retrofit;

    private static final String GH_TOKEN = "GH_TOKEN";
    private final GitHubApi gitHubApi;

    public GitHubApiClient() {
        interceptor  = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        retrofit= new Retrofit.Builder()
                .baseUrl(Config.getInstance().githubUrl())
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build();

        gitHubApi = retrofit.create(GitHubApi.class);
    }

    @SneakyThrows
    public String issueState(String issueNumber) throws IOException {
        JsonNode response = gitHubApi
                .issue("Bearer " + System.getenv(GH_TOKEN), issueNumber)
                .execute()
                .body();

        return Objects.requireNonNull(response).get("state").asText();
    }


}
