package top.bluer.moment.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.ObjectMetadata;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.bluer.moment.common.exception.BizException;
import top.bluer.moment.common.redis.CachePackage;
import top.bluer.moment.common.redis.MomentRedis;
import top.bluer.moment.entity.vo.MomentUnsplashListVo;
import top.bluer.moment.mapper.MomentOssMapper;
import top.bluer.moment.entity.MomentOss;
import top.bluer.moment.service.MomentOtherService;
import top.bluer.moment.utils.HttpRequestUtil;
import top.bluer.moment.utils.JasyptUtil;
import top.bluer.moment.utils.TemplateUtil;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

import static top.bluer.moment.common.redis.CachePackage.IMG_LIST_DATE;

/**
 * @program: moment-back
 * @description: 片刻-第三方服务实现类
 * @author: bluer
 * @date: 2021-11-28 11:04
 * codes: 扁鹊
 **/
@Service("momentOtherService")
public class MomentOtherServiceImpl implements MomentOtherService {

    @Resource
    private MomentOssMapper momentOssMapper;

    @Resource
    private JasyptUtil jasyptUtil;

    @Resource
    private MomentRedis momentRedis;

    @Resource
    private HttpRequestUtil httpRequestUtil;

    /**
     * @description: 通用上传文件方法
     * @date: 2021/11/28 11:41
     * codes: 扁鹊
     **/
    @Override
    public ArrayList<String> uploadFile(MultipartFile file, String describe) {
        if (describe.equals("P")) {
            if (file.getSize() > 20971520) {
                throw new BizException("图片限制为20M");
            }
        } else if (describe.equals("V")) {
            if (file.getSize() > 209715200) {
                throw new BizException("视频限制为200M");
            }
        }
        // 获取存储模板
        MomentOss momentOss = momentOssMapper.queryByDescribe(describe);
        String bucketName = momentOss.getBucketName();
        String key = momentOss.getUrlKey();
        String returnPrefix = momentOss.getReturnPrefix();
        // 创建上传文件的元信息，可以通过文件元信息设置HTTP header。
        ObjectMetadata meta = new ObjectMetadata();
        // 设置内容被下载时的名称。
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String s = UUID.randomUUID().toString();
        String fileName = key + format + '/' + s + "-" + file.getOriginalFilename();
        // 设置内容被下载时的名称。
        meta.setContentDisposition(fileName);
        // 设置上传文件的长度。如超过此长度，则会被截断，为设置的长度。如不足，则为上传文件的实际长度。
        meta.setContentLength(file.getSize());
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(momentOss.getEndpoint(), jasyptUtil.decrypt(momentOss.getAccessKeyId()), jasyptUtil.decrypt(momentOss.getAccessKeySecret()));

        // 上传文件。
        try {
            ossClient.putObject(bucketName, fileName, file.getInputStream(), meta);
            ossClient.setObjectAcl(bucketName, fileName, CannedAccessControlList.PublicRead);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 关闭OSSClient。
        ossClient.shutdown();
        final String res = returnPrefix + "/" + fileName;
        System.out.println(res);
        return new ArrayList<String>() {{
            add(res);
        }};
    }

    /**
     * @description: 一言
     * @date: 2021/11/29 19:34
     * codes: 扁鹊
     */
    @Override
    public HashMap<String, Object> oneWord() {
        String s = httpRequestUtil.sendGet("https://v1.hitokoto.cn/", null);
        return JSONObject.parseObject(s, HashMap.class);
    }

    /**
     * @description: 获取unsplash随机图片
     * @date: 2021/12/11 13:51
     * @codes: 扁鹊
     **/
    @Override
    public ArrayList<MomentUnsplashListVo> photos(String index) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date());
        String imgListDate = TemplateUtil.processTemplate(IMG_LIST_DATE, new HashMap<String, Object>(1) {{
            put("date", date);
            put("index", index);
        }});
        String s = momentRedis.get(imgListDate);
        if (Objects.isNull(s)) {
            s = httpRequestUtil.sendGet("https://api.unsplash.com/photos?page=" + index + "&per_page=30");
            momentRedis.set(CachePackage.builder().key(imgListDate).value(s).expire(Duration.ofHours(12L)).build());
        }
        List<Object> list = JSONObject.parseObject(s, List.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        ArrayList<MomentUnsplashListVo> finalItems = new ArrayList<>();
        list.forEach(item -> {
            MomentUnsplashListVo unsplashListVo = new MomentUnsplashListVo();
            JSONObject jsonObject = JSON.parseObject(item.toString());
            JSONObject user = JSON.parseObject(jsonObject.get("user").toString());
            // 昵称
            unsplashListVo.setUserName(user.get("name").toString());
            // 位置
            Object location = user.get("location");
            unsplashListVo.setLocation(Objects.nonNull(location) ? location.toString() : "Earth");
            String userAvatar = JSON.parseObject(user.get("profile_image").toString()).get("large").toString();
            // 头像
            unsplashListVo.setUserAvatar(userAvatar);
            String updated_at = jsonObject.get("updated_at").toString();
            try {
                // 时间
                unsplashListVo.setCreatTime(sdf.parse(updated_at));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            JSONObject urls = JSON.parseObject(jsonObject.get("urls").toString());
            // 文字数据
            Object description = jsonObject.get("description");
            Object alt_description = jsonObject.get("alt_description");
            unsplashListVo.setTextData(Objects.nonNull(description) ? Objects.nonNull(alt_description) ? description + alt_description.toString() : description.toString() : Objects.nonNull(alt_description) ? alt_description.toString() : "");
            // 图片数据
            unsplashListVo.setPictureData(urls.get("regular").toString());
            unsplashListVo.setWidth(jsonObject.get("width").toString());
            unsplashListVo.setHeight(jsonObject.get("height").toString());
            unsplashListVo.setHeight(jsonObject.get("height").toString());
            unsplashListVo.setColor(jsonObject.get("color").toString());
            unsplashListVo.setPictureDataList(new ArrayList<String>() {{
                add(urls.get("raw").toString());
                add(urls.get("full").toString());
                add(urls.get("regular").toString());
                add(urls.get("small").toString());
                add(urls.get("thumb").toString());
            }});
            // 点赞数
            unsplashListVo.setLikes(Integer.valueOf(jsonObject.get("likes").toString()));

            finalItems.add(unsplashListVo);
        });

        return finalItems;
    }
}
