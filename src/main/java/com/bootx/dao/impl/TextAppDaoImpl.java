package com.bootx.dao.impl;

import com.bootx.dao.TextAppDao;
import com.bootx.entity.TextApp;
import org.springframework.stereotype.Repository;


@Repository
public class TextAppDaoImpl extends BaseDaoImpl<TextApp,Long> implements TextAppDao {
}
