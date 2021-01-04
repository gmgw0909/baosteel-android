package com.meetutech.baosteel.http.api;

//**********************************************************
// Assignment: MeetU-OS
// Package:com.meetutech.baosteel.http.api
// Author: culm at 2017-04-26
//*********************************************************

import com.meetutech.baosteel.common.HTTPConstant;
import com.meetutech.baosteel.http.body.AnalysisDataBody;
import com.meetutech.baosteel.http.body.BindDevicesInfoBody;
import com.meetutech.baosteel.http.body.ExperimentApprovalBody;
import com.meetutech.baosteel.http.body.ForgotPwdForm;
import com.meetutech.baosteel.http.body.PostMessageBody;
import com.meetutech.baosteel.http.body.PostTargetEvaluationBody;
import com.meetutech.baosteel.http.body.SnapshotDataBody;
import com.meetutech.baosteel.http.result.AllPartResult;
import com.meetutech.baosteel.http.result.AllPartResult2;
import com.meetutech.baosteel.http.result.CommonListResult;
import com.meetutech.baosteel.http.result.CommonObjectResult;
import com.meetutech.baosteel.http.result.CommonResult;
import com.meetutech.baosteel.http.result.ExperimentCamera;
import com.meetutech.baosteel.http.result.HtmlConfigResult;
import com.meetutech.baosteel.http.result.IntroBannerData;
import com.meetutech.baosteel.http.result.LoginResult;
import com.meetutech.baosteel.http.result.OnePartResult;
import com.meetutech.baosteel.http.result.UploadFileResult;
import com.meetutech.baosteel.http.result.UserInfoResult;
import com.meetutech.baosteel.http.result.VariableData;
import com.meetutech.baosteel.model.data.Project;
import com.meetutech.baosteel.model.form.LoginForm;
import com.meetutech.baosteel.model.http.CameraThumbnail;
import com.meetutech.baosteel.model.http.ExperimentInfo;
import com.meetutech.baosteel.model.http.Furnace;
import com.meetutech.baosteel.model.http.FurnaceVariable;
import com.meetutech.baosteel.model.http.Infos;
import com.meetutech.baosteel.model.http.MessageObject;
import com.meetutech.baosteel.model.http.NozzleVariable;
import com.meetutech.baosteel.model.http.ProjectDetails;
import com.meetutech.baosteel.model.http.PushNotification;
import com.meetutech.baosteel.model.http.Snapshot;
import com.meetutech.baosteel.model.http.TargetEvaluations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.QueryMap;
import retrofit.mime.TypedFile;

public interface ApiService {
    @POST("/api/login")
    public void login(@Body LoginForm body, Callback<CommonObjectResult<LoginResult>> cb);

    @POST("/Accounts/passwordReset")
    void passwordReset(@Body ForgotPwdForm body,
                       Callback<CommonObjectResult<Object>> cb);

    @GET("/Accounts/{id}")
    public void getUserInfo(@Path("id") String userId, @QueryMap SortedMap<String, String> body,
                            Callback<UserInfoResult> cb);

    @POST("/Accounts/{id}/devices")
    public void postBindDevices(@Path("id") String userId, @QueryMap SortedMap<String, String> params,
                                @Body BindDevicesInfoBody body, Callback<CommonObjectResult<Object>> callback);

    //@GET("/Configs") public void getAllConfig(@)
    @GET("/Configs")
    public void getHtmlConfigs(@QueryMap SortedMap<String, String> body,
                               Callback<HtmlConfigResult> callback);

    @GET("/Infos/s/{key}")
    public void getInfoByKey(@Path("key") String key, @QueryMap SortedMap<String, String> body,
                             Callback<CommonObjectResult<Infos<Infos.Html>>> callback);

    @GET("/Projects")
    public void getProjectList(@QueryMap SortedMap<String, String> body,
                               Callback<CommonListResult<Project>> callback);

    @GET("/Projects/{id}/projectInfos")
    public void getProjectInfo(@Path("id") String projectId, @QueryMap SortedMap<String, String> body,
                               Callback<CommonListResult<ProjectDetails>> callback);

    @GET("/Projects/{id}/furnaces")
    public void getProjectFurance(@Path("id") String projectId,
                                  @QueryMap SortedMap<String, String> body, Callback<CommonListResult<Furnace>> callback);

    @GET("/Infos/s/intro_banner")
    public void getIntroBanner(@QueryMap SortedMap<String, String> body,
                               Callback<CommonObjectResult<IntroBannerData>> callback);

    @GET("/Projects/{id}/experiments")
    public void getProjectExperiment(@Path("id") String furnaceId,
                                     @QueryMap SortedMap<String, String> body,
                                     Callback<CommonListResult<ExperimentInfo>> callback);

    @GET("/Furnaces/{id}/experiments")
    public void getFuranceExperiment(@Path("id") String furnaceId,
                                     @QueryMap SortedMap<String, String> body,
                                     Callback<CommonListResult<ExperimentInfo>> callback);

    @GET("/Nozzles/{id}/variables")
    public void getNozzleVariables(@Path("id") String nozzleId,
                                   @QueryMap SortedMap<String, String> body,
                                   Callback<CommonListResult<NozzleVariable>> callback);

    //TopData
    @GET("/Experiments/{id}/top")
    public void getAllTopData(@Path("id") String experimentId,
                              @QueryMap SortedMap<String, String> body, Callback<CommonListResult<VariableData>> callback);

    @GET("/Experiments/{id}/top")
    public void getTopDataById(@Path("id") String experimentId,
                               @QueryMap SortedMap<String, String> body, Callback<CommonListResult<VariableData>> callback);

    @PUT("/Experiments/{id}/approvals/{aid}")
    public void putExperimentApprovals(@Path("id") String id, @Path("aid") String aid,
                                       @QueryMap SortedMap<String, String> auth, @Body ExperimentApprovalBody body,
                                       Callback<CommonObjectResult<Object>> callback);

    @GET("/Experiments/{id}/snapshots")
    public void getSnapshotList(@Path("id") String id, @QueryMap SortedMap<String, String> body,
                                Callback<CommonListResult<Snapshot>> cb);

    @POST("/Experiments/snapshots/query")
    public void querySnapshotData(@QueryMap SortedMap<String, String> params,
                                  @Body SnapshotDataBody body, Callback<CommonListResult<HashMap<String, String>>> cb);

    @POST("/Experiments/snapshots/query")
    public void analysisSnapshotData(@QueryMap SortedMap<String, String> params,
                                     @Body AnalysisDataBody body, Callback<CommonListResult<HashMap<String, String>>> cb);


    //Camera Management
    @GET("/Experiments/{id}/cameras")
    public void getExperimentCamera(@Path("id") String id, @QueryMap SortedMap<String, String> body,
                                    Callback<CommonListResult<ExperimentCamera>> cb);

    //Target
    @POST("/Targets/{targetId}/evaluations")
    public void postTargetEvaluation(
            @Path("targetId") String targetId, @QueryMap SortedMap<String, String> authBody,
            @Body PostTargetEvaluationBody body,
            Callback<CommonObjectResult<TargetEvaluations>> callback);

    @PUT("/Targets/{targetId}/evaluations/{evaluationId}")
    public void putTargetEvaluation(@Path("targetId") String targetId,
                                    @Path("evaluationId") String evaluationId, @QueryMap SortedMap<String, String> authBody,
                                    @Body PostTargetEvaluationBody body, Callback<CommonObjectResult<Object>> callback);

    //Furnace Variables
    @GET("/FurnaceVariables")
    public void getFurnaceVariables(@QueryMap SortedMap<String, String> body,
                                    Callback<CommonListResult<FurnaceVariable>> callback);

    //Chat
    @GET("/Experiments/{id}/chats")
    public void getChatMessageByExpId(@Path("id") String experimentId,
                                      @Header(HTTPConstant.HEADER_IF_NONE_MATCH) String eTag,
                                      @QueryMap SortedMap<String, String> body, Callback<CommonListResult<MessageObject>> callback);

    @POST("/Experiments/{id}/chats")
    public void postChatMessage(@Path("id") String experimentId,
                                @QueryMap SortedMap<String, String> authBody, @Body PostMessageBody body,
                                Callback<CommonObjectResult<MessageObject>> callback);

    @GET("/PushResults")
    public void getPushResults(@QueryMap SortedMap<String, String> body,
                               Callback<CommonListResult<PushNotification>> callback);

    //Camera Stream Loopback
    @GET("/Cameras/{id}/play")
    public void postCameraStream(@Path("id") String cid, @QueryMap SortedMap<String, String> body,
                                 Callback<CommonObjectResult<Object>> callback);

    //Camera Thumb
    @GET("/Infos/s/{id}")
    public void getCameraThumbnail(@Path("id") String id, @QueryMap SortedMap<String, String> body,
                                   Callback<CommonObjectResult<CameraThumbnail>> callback);

    //Static File Upload
    @Multipart
    @POST("/api/upload/{type}")
    public void uploadFile(@Path("type") String type,
                           @QueryMap SortedMap<String, String> authBody, @Part("file") TypedFile file,
                           Callback<CommonListResult<UploadFileResult>> callback);

    //综合画面
    @GET("/appApi/comprehensiveMenu")
    public void comprehensiveMenu(@QueryMap Map<String, Object> body, Callback<CommonResult<List<AllPartResult2>>> callback);

    //获取变量
    @GET("/appApi/getVariable")//name:大功率明火烧嘴
    public void getVariable(@QueryMap Map<String, String> body, Callback<CommonResult<OnePartResult>> callback);
}
