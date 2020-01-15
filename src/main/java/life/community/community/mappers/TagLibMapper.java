package life.community.community.mappers;

import life.community.community.entity.TagLib;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TagLibMapper {

    @Insert("insert into taglib (type, tagname) values (#{ type }, #{ tagName })")
    void addNewTag(TagLib tagLib);

    @Select("select * from taglib")
    List<TagLib> getAllTag();

}
