package com.leesungjae.tourist_hub_batch.config;//package com.leesungjae.tourist_hub_batch.job;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.leesungjae.tourist_hub_batch.entity.Address;
import com.leesungjae.tourist_hub_batch.entity.HashTag;
import com.leesungjae.tourist_hub_batch.entity.TouristAttraction;
import com.leesungjae.tourist_hub_batch.entity.TouristImage;
import com.leesungjae.tourist_hub_batch.enums.AreaEnum;
import com.leesungjae.tourist_hub_batch.tourist_attraction.repository.TouristAttractionRepository;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Configuration
public class TouristConfig extends DefaultBatchConfiguration {
    Logger logger = LoggerFactory.getLogger(TouristConfig.class);

    @Autowired
    TouristAttractionRepository touristAttractionRepository;

//    @Autowired
//    TouristStep touristStep;

//    @Bean
//    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager, Tasklet tasklet) {
//        logger.info("Building step");
//        return new StepBuilder("myTasklet", jobRepository)
//                .tasklet(tasklet, transactionManager)
//                .build();
//
//    }

//    @Bean
//    public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager,Tasklet tasklet) {
//
//
//        return new JobBuilder("myJob", jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .start(touristStep.touristStep(jobRepository, transactionManager,tasklet))
//                .build();
//    }


    @Bean
    public Tasklet tasklet() {

        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

                List<String> seoulCotIds = getCotIds(AreaEnum.SEOUL);
                List<String> incheonCotIds = getCotIds(AreaEnum.INCHEON);
                List<String> gyeonggiCotIds = getCotIds(AreaEnum.GYEONGGI);

                saveTouristInfos(seoulCotIds);
                System.out.println("jobListenerTaskLet Spring Batch");
                saveTouristInfos(incheonCotIds);
                System.out.println("jobListenerTaskLet Spring Batch");
                saveTouristInfos(gyeonggiCotIds);
                System.out.println("jobListenerTaskLet Spring Batch");

                return RepeatStatus.FINISHED;
            }
        };
    }

    private void saveTouristInfos(List<String> cotIds) throws IOException, ParseException {
        for(int i = 0; i < cotIds.size(); i++){

            try{
                List<TouristImage> touristImages = new ArrayList<>();
                List<HashTag> hashTags = new ArrayList<>();
                List<Address> addressList = new ArrayList<>();
                TouristAttraction touristAttraction = new TouristAttraction();

                JsonNode tourInfo = getTouristInfo(cotIds.get(i));
                JsonNode detail = tourInfo.path("detail");
                JsonNode images = tourInfo.path("image");
                List<String> tags = Arrays.asList(Optional.ofNullable(detail.get("tagName").asText().split("\\|")).orElse(new String[]{}));

                /** address input **/
                Address address = new Address(Optional.ofNullable(detail.get("areaCode").asInt()).orElse(99), Optional.ofNullable(detail.get("sigugunCode").asInt()).orElse(99));
                address.setTouristAttraction(touristAttraction);
                addressList.add(address);

                /** hashtags input **/
                for(String tag : tags) {
                    HashTag hashTag = new HashTag();
                    hashTag.setName(tag);
                    hashTag.setTouristAttraction(touristAttraction);
                    hashTags.add(hashTag);
                }

                /** images input **/
                for(JsonNode image : images){
                    TouristImage touristImage = new TouristImage();
                    touristImage.setPath(image.get("imgPath").asText());
                    touristImage.setTouristAttraction(touristAttraction);
                    touristImages.add(touristImage);
                }

                /** detail input **/
                touristAttraction.setTitle(Optional.ofNullable(detail.get("title").asText()).orElse(""));
                touristAttraction.setOverview(Optional.ofNullable(detail.get("overView").asText()).orElse(""));
                touristAttraction.setInfoCenterCall(Optional.ofNullable(detail.get("infoCenter").asText()).orElse(""));
                touristAttraction.setLike(Optional.ofNullable(detail.get("conLike").asLong()).orElse(0L));
                touristAttraction.setRestDate(Optional.ofNullable(detail.get("infoCenter").asText()).orElse(""));
                touristAttraction.setHomepage(Optional.ofNullable(detail.get("homepage").asText()).orElse(""));
                touristAttraction.setAddr(Optional.ofNullable(detail.get("addr1").asText()).orElse(""));
                touristAttraction.setMapLatt(Optional.ofNullable(detail.get("mapX").asText()).orElse(""));
                touristAttraction.setMapLngt(Optional.ofNullable(detail.get("mapY").asText()).orElse(""));
                touristAttraction.setTouristImages(touristImages);
                touristAttraction.setHashTags(hashTags);
                touristAttraction.setAddressList(addressList);

                touristAttractionRepository.save(touristAttraction);

                System.out.println("running... (" + i + "/" + cotIds.size() + ")");
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    private JsonNode getTouristInfo(String cotId) throws IOException, ParseException {

        JsonNode jsonNode = JsonNodeFactory.instance.objectNode();

        HttpPost httpPost = new HttpPost("https://korean.visitkorea.or.kr/call");

        List<BasicNameValuePair> postParams = new ArrayList<>();
        postParams.add(new BasicNameValuePair("cmd", "TOUR_CONTENT_BODY_DETAIL"));
        postParams.add(new BasicNameValuePair("locationx", "0"));
        postParams.add(new BasicNameValuePair("cotId", cotId));
        postParams.add(new BasicNameValuePair("locationy", "0"));
        postParams.add(new BasicNameValuePair("stampId", "1589345b-b030-11ea-b8bd-020027310001"));

        //Post 방식인 경우 데이터를 Request body message에 전송
        HttpEntity postEntity = new UrlEncodedFormEntity(postParams);
        httpPost.setEntity(postEntity);

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = httpClient.execute(httpPost);

        if (response.getCode() == 200) {

            ObjectMapper objectMapper = new ObjectMapper();
            String result = EntityUtils.toString(response.getEntity());

            jsonNode = objectMapper.readTree(result)
                                            .path("body");


        }

        return jsonNode;

    }

    private List<String> getCotIds(AreaEnum areaEnum) throws IOException, ParseException {


        List<String> cotIds = new ArrayList<String>();

        HttpPost httpPost = new HttpPost("https://korean.visitkorea.or.kr/call");

        List<BasicNameValuePair> postParams = new ArrayList<>();
        postParams.add(new BasicNameValuePair("cmd", "TOUR_CONTENT_LIST_VIEW"));
        postParams.add(new BasicNameValuePair("month", "All"));
        postParams.add(new BasicNameValuePair("areaCode", String.valueOf(areaEnum.getCode())));
        postParams.add(new BasicNameValuePair("sigunguCode", "All"));
        postParams.add(new BasicNameValuePair("tagId", "All"));
        postParams.add(new BasicNameValuePair("sortkind", "1"));
        postParams.add(new BasicNameValuePair("locationx", "0"));
        postParams.add(new BasicNameValuePair("locationy", "0"));
        postParams.add(new BasicNameValuePair("page", "1"));
        postParams.add(new BasicNameValuePair("cnt", "4000"));
        postParams.add(new BasicNameValuePair("typeList", "Tour"));
        postParams.add(new BasicNameValuePair("stampId", "1589345b-b030-11ea-b8bd-020027310001"));


        //Post 방식인 경우 데이터를 Request body message에 전송
        HttpEntity postEntity = new UrlEncodedFormEntity(postParams);
        httpPost.setEntity(postEntity);

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = httpClient.execute(httpPost);



        if (response.getCode() == 200) {

            ObjectMapper objectMapper = new ObjectMapper();

            String result = EntityUtils.toString(response.getEntity());

            JsonNode jsonNode = objectMapper.readTree(result)
                                            .path("body")
                                            .path("result");
            cotIds = jsonNode.findValuesAsText("cotId");

        }

        return cotIds;


    }
}
