package techease.com.seaweb.Activities.Utils;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import techease.com.seaweb.Activities.Models.BoatDetail.BoatDetailResponseModel;
import techease.com.seaweb.Activities.Models.BookingBoatResponseModel;
import techease.com.seaweb.Activities.Models.Chat.InboxResponseModel;
import techease.com.seaweb.Activities.Models.Chat.MessageDetailResponseModel;
import techease.com.seaweb.Activities.Models.Chat.PriceRequestResponseModel;
import techease.com.seaweb.Activities.Models.SearchBoatsFileModel;
import techease.com.seaweb.Activities.Models.Trip.AllTripsResponseModel;
import techease.com.seaweb.Activities.Models.BoatTypeResponseModel;
import techease.com.seaweb.Activities.Models.BookedBoatsResponseModel;
import techease.com.seaweb.Activities.Models.CodeResponseModel;
import techease.com.seaweb.Activities.Models.FavrtResponseModel;
import techease.com.seaweb.Activities.Models.ForgotPassResponseModel;
import techease.com.seaweb.Activities.Models.GetAllPlacesResponseModel;
import techease.com.seaweb.Activities.Models.LoginResponseModel;
import techease.com.seaweb.Activities.Models.RegisterResponseModel;
import techease.com.seaweb.Activities.Models.ResetPassResponseModel;
import techease.com.seaweb.Activities.Models.SearchedBoatsResponseModel;
import techease.com.seaweb.Activities.Models.SocialLoginResponseModel;
import techease.com.seaweb.Activities.Models.SuggestedPlacesResponseModel;
import techease.com.seaweb.Activities.Models.Trip.BookedTripsResponseModel;
import techease.com.seaweb.Activities.Models.Trip.TripBookingResponseModel;
import techease.com.seaweb.Activities.Models.Trip.FavrtTripsResponseModel;
import techease.com.seaweb.Activities.Models.Trip.MakeFavrtTripResponseModel;
import techease.com.seaweb.Activities.Models.Trip.TripDetailResponseModel;

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
    @POST("App/getFavoriteBoatData")
    Call<FavrtResponseModel> favrtBooking(@Field("userid") String userId);

    @FormUrlEncoded
    @POST("App/getFavoriteTripData")
    Call<FavrtTripsResponseModel> getFavrtTrips(@Field("userid") String userId);



    @GET("App/getAllLocations")
    Call<GetAllPlacesResponseModel> getAllPlaces();



    @GET("App/getSuggestionLocations")
    Call<SuggestedPlacesResponseModel> getSuggestedPlaces();

    @GET("App/getBoatTypes")
    Call<BoatTypeResponseModel> getBoatTypes();

//
//    @FormUrlEncoded
//    @POST("App/bookboat")
//    Call<BoatBookingResponseModel> boatBooking(@Field("pro_id") String pro_id,
//                                               @Field("user_id") String userid,
//                                               @Field("start_date") String start_date,
//                                               @Field("end_date") String end_date,
//                                               @Field("adults") String adults,
//                                               @Field("childs") String childs,
//                                               @Field("price") String price,
//                                               @Field("whole_booking") String whole_booking,
//                                               @Field("trans_code") String trans_code,
//                                               @Field("message") String msg);


    @FormUrlEncoded
        @POST("App/searchBoat")
    Call<SearchedBoatsResponseModel> getSearchedBoats(@Field("location") String location,
                                                      @Field("type") String type,
                                                      @Field("start_date") String start_date,
                                                      @Field("end_date") String end_date);





    @FormUrlEncoded
    @POST("App/getBookings")
    Call<BookedBoatsResponseModel> getBookedBoats(@Field("user_id") String userid,
                                                  @Field("type") String type);

    @FormUrlEncoded
    @POST("App/getBookings")
    Call<BookedTripsResponseModel> getBookedTrips(@Field("user_id") String userid,
                                                  @Field("type") String type);

    @FormUrlEncoded
    @POST("App/getBoatDetails")
    Call<BoatDetailResponseModel> getBoatDetail(@Field("id") String id,
                                                @Field("userid") String userid);


    @FormUrlEncoded
    @POST("App/getTripDetails")
    Call<TripDetailResponseModel> getTripDetails(@Field("id") String id,
                                                 @Field("userid") String userid);

    @FormUrlEncoded
    @POST("App/favorite_boat")
    Call<MakeFavrtTripResponseModel> makeItFavrt(@Field("pro_id") String id,
                                                    @Field("userid") String userid,
                                                 @Field("type") String type);

    @FormUrlEncoded
    @POST("App/requestusers")
    Call<InboxResponseModel> getInbox(@Field("userid") String userid);

    @FormUrlEncoded
    @POST("App/requestusers")
    Call<MessageDetailResponseModel> getMessageDetails(@Field("userid") String userid,
                                                       @Field("clientid") String ownerid);

    @FormUrlEncoded
    @POST("App/request")
    Call<PriceRequestResponseModel> sentRequest(@Field("user_id") String userid,
                                                @Field("message") String msg,
                                                @Field("owner_id") String owner_id,
                                                @Field("pro_id") String pro_id);



    @FormUrlEncoded
    @POST("App/bookboat")
    Call<BookingBoatResponseModel> bookBoat(@Field("user_id") String userid,
                                            @Field("pro_id") String proid,
                                            @Field("start_date") String sdate,
                                            @Field("end_date") String edate,
                                            @Field("trans_code") String code,
                                            @Field("price_total") String total,
                                            @Field("type") String boat,
                                            @Field("message") String msg);


    @FormUrlEncoded
    @POST("App/bookboat")
    Call<TripBookingResponseModel> bookTrip(@Field("user_id") String userid,
                                            @Field("pro_id") String proid,
                                            @Field("adults") String adults,
                                            @Field("childs") String childs,
                                            @Field("seats") String code,
                                            @Field("price_total") String total,
                                            @Field("type") String boat,
                                            @Field("message") String msg,
                                            @Field("trans_code") String transCode);


    @GET("App/getTripData")
    Call<AllTripsResponseModel> getAllTrips();



}
