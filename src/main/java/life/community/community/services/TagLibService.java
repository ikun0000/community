package life.community.community.services;

import life.community.community.entity.TagLib;
import life.community.community.mappers.TagLibMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TagLibService {

    @Autowired
    private TagLibMapper tagLibMapper;


    public List<TagLib> getAllTag() {
        return tagLibMapper.getAllTag();
    }

}
