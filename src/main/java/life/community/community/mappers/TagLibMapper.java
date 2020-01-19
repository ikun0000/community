package life.community.community.mappers;


import life.community.community.entity.TagLib;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface TagLibMapper {

    /**
     * 获取所有标签
     * @return      标签列表
     */
    @Select("select * from taglib")
    List<TagLib> getAllTag();

}
