package techease.com.seaweb.Activities.Utils;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import techease.com.seaweb.Activities.Models.AddToFavrtResponseModel;
import techease.com.seaweb.Activities.Models.BoatDetailsResponseModel;
import techease.com.seaweb.Activities.Models.BoatsOnLocationResponseModel;
import techease.com.seaweb.Activities.Models.CodeResponseModel;
import techease.com.seaweb.Activities.Models.FavrtResponseModel;
import techease.com.seaweb.Activities.Models.ForgotPassResponseModel;
import techease.com.seaweb.Activities.Models.GetAllPlacesResponseModel;
import techease.com.seaweb.Activities.Models.LoginResponseModel;
import techease.com.seaweb.Activities.Models.RegisterResponseModel;
import techease.com.seaweb.Activities.Models.ResetPassResponseModel;
import techease.com.seaweb.Activities.Models.SocialLoginResponseModel;

public interface ApiService {

    @FormUrlEncoded
    @POST("register/sociallogin")
    Call<SocialLoginResponseModel> socialLogin(@Field("email") String email,
                                               @Field("name") String name,
                                               @Field("device_id") String device_id,
                                               @Field("provider_id") String provider_id,
                                               @Field("provider") String provider);

    @FormUrlEncoded
    @POST("register/register")
    Call<RegisterResponseModel> register(@Field("email") String email,
                                        @Field("first_name") String Fname,
                                         @Field("last_name") String Lname,
                                         @Field("type") String userType,
                                         @Field("area_name") String area,
                                         @Field("password") String password,
                                         @Field("device_id") String devie_id);
    @FormUrlEncoded
    @POST("register/login")
    Call<LoginResponseModel> login(@Field("email") String email,
                                   @Field("password") String password);

    @FormUrlEncoded
    @POST("register/forgot")
    Call<ForgotPassResponseModel> forgot(@Field("email") String email);

    @FormUrlEncoded
    @POST("register/CheckCode")
    Call<CodeResponseModel> checkCode(@Field("code") String code);

    @FormUrlEncoded
    @POST("register/Resetpassword")
    Call<ResetPassResponseModel> resetPassword(@Field("code") String code,
                                               @Field("password") String password);



    @FormUrlEncoded
    @POST("App/getBoatData")
    Call<BoatsOnLocationResponseModel> boatsOnLocation(@Field("location") String locId,
                                                     @Field("userid") String userId);

    @FormUrlEncoded
    @POST("App/getFavoriteBoatData")
    Call<FavrtResponseModel> favrtBooking(@Field("userid") String userId);



    @GET("App/getAllLocations")
    Call<GetAllPlacesResponseModel> getAllPlaces();


    @FormUrlEncoded
    @POST("App/getBoatDetails")
    Call<BoatDetailsResponseModel> getBoatDetails(@Field("id") String id,
                                                  @Field("userid") String userid);

    @FormUrlEncoded
    @POST("App/favorite_boat")
    Call<AddToFavrtResponseModel> addToFavrt(@Field("pro_id") String pro_id,
                                             @Field("userid") String userid);





}
