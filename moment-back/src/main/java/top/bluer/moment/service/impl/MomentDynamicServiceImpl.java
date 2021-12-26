package top.bluer.moment.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import top.bluer.moment.common.exception.BizException;
import top.bluer.moment.config.SaTokenConfig;
import top.bluer.moment.entity.MomentDynamic;
import top.bluer.moment.entity.MomentFollow;
import top.bluer.moment.entity.MomentLikes;
import top.bluer.moment.entity.MomentUser;
import top.bluer.moment.entity.vo.MomentDynamicAndUserVo;
import top.bluer.moment.entity.dto.MomentDynamicDto;
import top.bluer.moment.mapper.MomentDynamicMapper;
import top.bluer.moment.mapper.MomentFollowMapper;
import top.bluer.moment.mapper.MomentLikesMapper;
import top.bluer.moment.service.MomentDynamicService;
import org.springframework.stereotype.Service;
import cn.hutool.core.bean.BeanUtil;
import top.bluer.moment.utils.HttpRequestUtil;
import top.bluer.moment.utils.ParallelUtil;
import top.bluer.moment.utils.RandomUtil;

import javax.annotation.Resource;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 片刻动态表(MomentDynamic)表服务实现类
 *
 * @author bluer
 * @since 2021-12-02 17:52:33
 */
@Service("momentDynamicService")
public class MomentDynamicServiceImpl implements MomentDynamicService {
    @Resource
    private MomentDynamicMapper momentDynamicMapper;
    @Resource
    private SaTokenConfig saTokenConfig;
    @Resource
    private MomentFollowMapper momentFollowMapper;
    @Resource
    private HttpRequestUtil httpRequestUtil;
    @Resource
    private MomentLikesMapper MomentLikesMapper;
    @Resource
    private MomentLikesMapper momentLikesMapper;

    /**
     * @description: 发布动态
     * @date: 2021/12/2 18:03
     * codes: 扁鹊
     **/
    @Override
    public MomentDynamic insert(MomentDynamicDto momentDynamicDto) {
        MomentDynamic momentDynamic = MomentDynamic.builder().build();
        BeanUtil.copyProperties(momentDynamicDto, momentDynamic);
        momentDynamic.setId(RandomUtil.getRanId());
        // 创建时间
        momentDynamic.setCreatTime(new Date());
        momentDynamic.setStatus("M");
        this.momentDynamicMapper.insert(momentDynamic);
        return momentDynamic;
    }

    /**
     * @description: 单条动态信息
     * @date: 2021/12/3 17:38
     * codes: 扁鹊
     **/
    @Override
    public MomentDynamicAndUserVo queryById(String id) {
        MomentDynamicAndUserVo item = momentDynamicMapper.queryById(id);
        // 可见范围：O-自己可见，F-粉丝可见，A-所有人可见，C-亲密关系可见
        MomentUser user = saTokenConfig.getUser();
        MomentLikes info = momentLikesMapper.getInfo(MomentLikes.builder().dynamicId(id).userId(user.getUserId()).status(0).build());
        item.setLikeStatus(Objects.nonNull(info));
        if (StpUtil.isLogin()) {
            String userId = saTokenConfig.getUser().getUserId();
            item.setFollowStatus(momentFollowMapper.count(MomentFollow.builder().followersId(userId).followedId(item.getUserId()).status(0).build()));
        } else {
            item.setFollowStatus(0);
        }
        if (item.getAuthority().equals("A")) {
            return dataProcessing(item);
        }
        if (Objects.isNull(StpUtil.getLoginIdDefaultNull())) {
            throw new BizException("无权操作");
        }
        if (Objects.equals(user.getUserId(), item.getUserId())) {
            return dataProcessing(item);
        }
        throw new BizException("无权操作");
    }

    /**
     * @description: 分页查询 公共
     * @date: 2021/12/3 17:23
     * codes: 扁鹊
     **/
    @Override
    public PageInfo<MomentDynamicAndUserVo> queryByPage(Integer page, Integer size) {
        PageHelper.startPage(page, size);
        List<MomentDynamicAndUserVo> items = this.momentDynamicMapper.queryAllByLimit(MomentDynamic.builder().authority("A").status("M").build());
        PageInfo<MomentDynamicAndUserVo> pageInfo = new PageInfo<>(items);
        items.forEach(this::dataProcessing);
        pageInfo.setList(items);
        return pageInfo;
    }

    /**
     * @description: 我的动态列表
     * @date: 2021/12/3 17:23
     * codes: 扁鹊
     **/
    @Override
    public List<MomentDynamicAndUserVo> queryByMy(Integer index) {
        String userId = saTokenConfig.getUser().getUserId();
        PageHelper.startPage(index, 10);
        List<MomentDynamicAndUserVo> items = this.momentDynamicMapper.queryAllByLimit(MomentDynamic.builder().userId(userId).status("M").build());
        ArrayList<ParallelUtil.ParallelJob<MomentDynamicAndUserVo>> parallelJobs = new ArrayList<>();
        items.forEach(item -> parallelJobs.add(new ParallelUtil.ParallelJob<MomentDynamicAndUserVo>().setFunction(() -> dataProcessing(item))));
        ParallelUtil.execute(parallelJobs);
        ArrayList<MomentDynamicAndUserVo> list = new ArrayList<>();
        parallelJobs.forEach(item -> list.add(item.getResutl()));
        list.forEach(item -> item.setFollowStatus(momentFollowMapper.count(MomentFollow.builder().followersId(userId).followedId(item.getUserId()).status(0).build())));
        return list;
    }

    /**
     * @description: 我的关注动态列表
     * @date: 2021/12/26 12:22
     * @codes: 扁鹊
     **/
    @Override
    public PageInfo<MomentDynamicAndUserVo> queryByMyFocus(Integer index) {
        String userId = saTokenConfig.getUser().getUserId();
        List<MomentFollow> list = momentFollowMapper.list(MomentFollow.builder().followersId(userId).status(0).build());
        if (list.size() <= 0) {
            return new PageInfo<>(new ArrayList<>());
        }
        List<String> collect = list.stream().map(MomentFollow::getFollowedId).collect(Collectors.toList());
        PageHelper.startPage(index, 10);
        List<MomentDynamicAndUserVo> items = momentDynamicMapper.queryByMyFocus(collect, new ArrayList<String>() {{
            add("F");
            add("A");
        }}, "M");
        PageInfo<MomentDynamicAndUserVo> pageInfo = new PageInfo<>(items);
        ArrayList<ParallelUtil.ParallelJob<MomentDynamicAndUserVo>> parallelJobs = new ArrayList<>();
        items.forEach(item -> parallelJobs.add(new ParallelUtil.ParallelJob<MomentDynamicAndUserVo>().setFunction(() -> dataProcessing(item))));
        ParallelUtil.execute(parallelJobs);
        List<MomentDynamicAndUserVo> finalItems = new ArrayList<>();
        parallelJobs.forEach(item -> finalItems.add(item.getResutl()));
        finalItems.forEach(item -> item.setFollowStatus(momentFollowMapper.count(MomentFollow.builder().followersId(userId).followedId(item.getUserId()).status(0).build())));
        pageInfo.setList(finalItems);
        return pageInfo;
    }

    /**
     * @description: 数据处理 - 图片、标签
     * @date: 2021/12/5 15:41
     * codes: 扁鹊
     **/
    private MomentDynamicAndUserVo dataProcessing(MomentDynamicAndUserVo item) {
        String pictureData = item.getPictureData();
        if (Objects.nonNull(pictureData)) {
            ArrayList<String> list = JSONObject.parseObject(pictureData, ArrayList.class);
            item.setPictureDataList(list);
            item.setPictureDataListSize(list.size());
            String s = list.get(0);
            // 获取图片参数 http://image-demo.oss-cn-hangzhou.aliyuncs.com/example.jpg?x-oss-process=image/info
            String info = httpRequestUtil.sendGet(s + "?x-oss-process=image/info", null);
            /*{
             "FileSize": {"value": "21839"},
             "Format": {"value": "jpg"},
             "ImageHeight": {"value": "267"},
             "ImageWidth": {"value": "400"},
             "ResolutionUnit": {"value": "1"},
             "XResolution": {"value": "1/1"},
             "YResolution": {"value": "1/1"}
             }*/
            JSONObject jsonObject = JSON.parseObject(info);
            item.setPictureData(s);
            item.setImageHeight(JSON.parseObject(jsonObject.get("ImageHeight").toString()).get("value").toString());
            item.setImageWidth(JSON.parseObject(jsonObject.get("ImageWidth").toString()).get("value").toString());
            // 获取图片主色调 https://image-demo.oss-cn-hangzhou.aliyuncs.com/example.jpg?x-oss-process=image/average-hue
            String color = httpRequestUtil.sendGet(s + "?x-oss-process=image/average-hue", null);
            /*{"RGB": "0x5c783b"}*/
            jsonObject = JSON.parseObject(color);
            String rgb = jsonObject.get("RGB").toString();
            String r = rgb.substring(2, rgb.length() - 4);
            String g = rgb.substring(4, rgb.length() - 2);
            String b = rgb.substring(6);
            int i1 = Integer.parseInt(r, 16);
            int i2 = Integer.parseInt(g, 16);
            int i3 = Integer.parseInt(b, 16);
            item.setImageColor("rgb(" + i1 + "," + i2 + "," + i3 + ")");
        }
        String label = item.getLabel();
        if (Objects.nonNull(label)) {
            ArrayList<String> parse = JSONObject.parseObject(label, ArrayList.class);
            item.setLabelList(parse);
        }
        int count = MomentLikesMapper.count(MomentLikes.builder().status(0).dynamicId(item.getId()).build());
        item.setLikes(count);
        return item;
    }

    /**
     * 修改数据
     *
     * @param momentDynamic 实例对象
     * @return 实例对象
     */
    @Override
    public MomentDynamic update(MomentDynamic momentDynamic) {
        this.momentDynamicMapper.update(momentDynamic);
//        return this.queryById(momentDynamic.getId());
        return null;
    }


    /**
     * @description: 删除动态/恢复动态
     * @date: 2021/12/2 18:18
     * codes: 扁鹊
     **/
    @Override
    public boolean delOrRec(String id, String type) {
        return momentDynamicMapper.delOrRec(id, type) > 0;
    }
}
