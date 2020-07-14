package com.patanjali.patanjaliiasclasses.retrofit_service;

import com.patanjali.patanjaliiasclasses.model.ClassData;
import com.patanjali.patanjaliiasclasses.model.LiveclassModel;
import com.patanjali.patanjaliiasclasses.model.MorningClas_Model;
import com.patanjali.patanjaliiasclasses.model.Response_Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiService {
    @POST("Payment/PATANJALI@09765432")
    @FormUrlEncoded
    public Call<List<Response_Model>> ValidateMobile(
            @Header("Authorization")String header,
            @FieldMap Map<String,String> param);

   @POST("Onlinecourses/PATANJALI@09765432")
    @FormUrlEncoded
    public Call<ArrayList<ClassData>> getMyLibrary(
            @Header("Authorization")String header,
            @FieldMap Map<String,String> param);

    @GET("Liveclasses/PATANJALI@09765432/7060512022")
    @FormUrlEncoded
    public Call<ArrayList<LiveclassModel>> getMyLibrary1(
            @Header("Authorization")String header);


}
