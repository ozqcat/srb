package com.atguigu.srb.core.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.srb.core.listener.ExcelDictDTOListener;
import com.atguigu.srb.core.pojo.dto.ExcelDictDTO;
import com.atguigu.srb.core.pojo.entity.Dict;
import com.atguigu.srb.core.mapper.DictMapper;
import com.atguigu.srb.core.service.DictService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author ozq
 * @since 2021-11-08
 */
@Slf4j
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Resource
    private RedisTemplate redisTemplate;

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void importData(InputStream inputStream) {
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        log.info("importData start");
        EasyExcel.read(inputStream, ExcelDictDTO.class, new ExcelDictDTOListener(baseMapper)).sheet().doRead();
        log.info("importData finished");
    }

    @Override
    public List<ExcelDictDTO> listDictData() {
        List<Dict> dictList = baseMapper.selectList(null);

        List<ExcelDictDTO> excelDictDTOList = new ArrayList<>(dictList.size());
        dictList.forEach( dict -> {
            ExcelDictDTO excelDictDTO = new ExcelDictDTO();
            BeanUtils.copyProperties(dict,excelDictDTO);
            excelDictDTOList.add(excelDictDTO);

        });
        return excelDictDTOList;
    }

    @Override
    public List<Dict> selectByParentId(Long parentId) {

        try {
            // 从Redis中取出数据，判断是否存在
            List<Dict> dictList = (List<Dict>)redisTemplate.opsForValue().get("srb:core:dictList");
            if( dictList != null) {
                // 不为空返回数据
                return dictList;
            }
        } catch (Exception e) {
            log.info("redis出现错误：" + ExceptionUtils.getStackTrace(e));
        }


        log.info("从数据库中取数据");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("parent_id",parentId);
        List<Dict> dictList = baseMapper.selectList(queryWrapper);
        dictList.forEach(dict -> {
            boolean hasChildren = this.hasChildren(dict.getId());
            dict.setHasChildren(hasChildren);

        } );

        try {
            log.info("将数据存入Redis中");
            // 将从数据库取出的数据，放入到redis
            redisTemplate.opsForValue().set("srb:core:dictList",dictList,15, TimeUnit.MINUTES);
        } catch (Exception e) {
            log.info("redis出现错误：" + ExceptionUtils.getStackTrace(e));
        }

        return dictList;
    }

    public boolean hasChildren(Long id){
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<Dict>().eq("parent_id", id);
        List<Dict> dicts = baseMapper.selectList(queryWrapper);
        if(dicts.size() > 0){
            return true;
        }
        return false;
    }
}
