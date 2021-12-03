package com.atguigu.srb.core.service;

import com.atguigu.srb.core.pojo.entity.Dict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author ozq
 * @since 2021-11-08
 */
public interface DictService extends IService<Dict> {

    /**
     * 导入excel数据字典
     * @param inputStream
     */
    void importData(InputStream inputStream);

    /**
     * 导出数据字典数据
     * @return
     */
    List listDictData();

    List<Dict> selectByParentId(Long parentId);
}
