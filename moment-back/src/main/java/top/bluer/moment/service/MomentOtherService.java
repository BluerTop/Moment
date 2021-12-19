package top.bluer.moment.service;

import org.springframework.web.multipart.MultipartFile;
import top.bluer.moment.entity.vo.MomentUnsplashListVo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @program: moment-back
 * @description: 片刻-第三方服务接口
 * @author: bluer
 * @date: 2021-11-27 23:30
 * codes: 扁鹊
 **/
public interface MomentOtherService {

    /**
     * @description: 通用上传文件方法
     * @date: 2021/11/27 23:32
     * codes: 扁鹊
     **/
    ArrayList<String> uploadFile(MultipartFile file, String describe);

    /**
     * @description: 一言
     * @date: 2021/11/29 19:34
     * codes: 扁鹊
     **/
    HashMap<String, Object> oneWord();

    /**
     * @description: 获取unsplash随机图片
     * @date: 2021/12/11 13:51
     * @codes: 扁鹊
     **/
    ArrayList<MomentUnsplashListVo> photos(String index) ;

}
