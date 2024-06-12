package com.bootx.dao.impl;

import com.bootx.dao.TopicDao;
import com.bootx.entity.Topic;
import org.springframework.stereotype.Repository;


@Repository
public class TopicDaoImpl extends BaseDaoImpl<Topic,Long> implements TopicDao {
}
