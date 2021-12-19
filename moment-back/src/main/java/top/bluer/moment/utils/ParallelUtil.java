package top.bluer.moment.utils;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;
import top.bluer.moment.entity.vo.MomentDynamicAndUserVo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;

/**
 * @program: moment-back
 * @description: 并行工具类
 * @author: bluer
 * @date: 2021-12-11 23:20
 * codes: 扁鹊
 **/
public class ParallelUtil {

    @FunctionalInterface
    public interface ParallelFunction<T> extends Supplier<T> {
    }

    @Data
    @Accessors(chain = true)
    public static class ParallelJob<T> {
        private ParallelFunction<T> function;
        private T resutl;
    }

    public static void execute(@NonNull ParallelJob... jobs) {
        Arrays.stream(jobs).parallel().forEach(job -> job.setResutl(job.getFunction().get()));
    }

    public static void execute(ArrayList<ParallelJob<MomentDynamicAndUserVo>> parallelJobs) {
        parallelJobs.stream().parallel().forEach(job -> job.setResutl(job.getFunction().get()));
    }
}
