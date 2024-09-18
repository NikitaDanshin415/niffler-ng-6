package guru.qa.niffler.api;

import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.CurrencyValues;
import guru.qa.niffler.model.SpendJson;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface SpendApi {

    @POST("internal/spends/add")
    Call<SpendJson> addSpend(@Body SpendJson spend);

    @GET("internal/spends/all")
    Call<ArrayList<SpendJson>> getSpends(
            @Query("username") String username,
            @Query("filterCurrency") CurrencyValues filterCurrency,
            @Query("from") Date from,
            @Query("to") Date to
    );

    @GET("internal/spends/{id}")
    Call<SpendJson> getSpend(
            @Path("id") UUID id,
            @Query("username") String username
    );

    @PATCH("internal/spends/edit")
    Call<SpendJson> editSpend(@Body SpendJson spend);

    @DELETE("internal/spends/remove")
    Call<Void> removeSpend(
            @Query("username") String username,
            @Query("ids") List<UUID> ids
    );

    @POST("internal/categories/add")
    Call<CategoryJson> addCategory(@Body CategoryJson category);

    @PATCH("internal/categories/update")
    Call<CategoryJson> editCategory(@Body CategoryJson category);

    @GET("internal/categories/all")
    Call<List<CategoryJson>> getCategories(
            @Query("username") String username,
            @Query("excludeArchived") Boolean excludeArchived);

}
