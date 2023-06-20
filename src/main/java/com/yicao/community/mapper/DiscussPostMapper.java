package com.yicao.community.mapper;

import com.yicao.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

    // 分页查询帖子,userid为0时不考虑userid
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

    /**
     * @Param 用于给参数取别名 如果只有一个参数，并且在<if>里使用，则必须加别名 否则会报错
     * @param userId
     * @return
     */
    int selectDiscussPostRows(@Param("userId") int userId);
}
