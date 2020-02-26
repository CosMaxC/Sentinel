package tk.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @program: sentinel-parent
 * @description:
 * @author: Cosmax
 * @create: 2020/02/26 13:10
 */
public interface MyMapper<T> extends MySqlMapper<T>, Mapper<T> {
}
