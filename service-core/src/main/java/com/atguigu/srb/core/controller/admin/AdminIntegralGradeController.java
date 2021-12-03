package com.atguigu.srb.core.controller.admin;

import com.atguigu.common.exception.BusinessException;
import com.atguigu.common.result.ResponseEnum;
import com.atguigu.common.result.Result;
import com.atguigu.srb.core.pojo.entity.IntegralGrade;
import com.atguigu.srb.core.service.IntegralGradeService;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author ozq
 * @Date 2021/11/8 22:33
 * @Description
 */
@Api(tags = "积分等级管理")
@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/admin/core/integralGrade")
public class AdminIntegralGradeController {

    @Resource
    private IntegralGradeService integralGradeService;

    @ApiOperation(value ="积分等级列表" )
    @GetMapping("/list")
    public Result listAll(){
        log.info("hi i'm helen");
        log.warn("warning!!!");
        log.error("it's a error");
        List<IntegralGrade> list = integralGradeService.list();
        return Result.ok().data("list", list).message("查询成功");
    }

    @ApiOperation(value ="根据id删除积分等级" )
    @DeleteMapping("/remove/{id}")
    public Result deleteById(@PathVariable("id") Integer id){
        boolean result = integralGradeService.removeById(id);
        if(result == true){
            return Result.ok().message("删除成功");
        }else{
            return Result.error().message("删除失败");
        }
    }

    @ApiOperation(value = "保存积分等级")
    @PostMapping("/save")
    public Result save( @ApiParam(value = "积分等级对象", required = true)
                                     @RequestBody IntegralGrade integralGrade){
        if(integralGrade.getBorrowAmount() == null){

            throw new BusinessException(ResponseEnum.BORROW_AMOUNT_NULL_ERROR);
        }

        boolean saveResult = integralGradeService.save(integralGrade);
        if(saveResult){
           return Result.ok().message("保存成功");
        }else{
            return Result.error().message("保存失败");
        }

    }

    @ApiOperation(value = "更新积分等级")
    @PutMapping("/update")
    public Result updateById( @ApiParam(value = "积分等级对象", required = true)
                                     @RequestBody IntegralGrade integralGrade){

        boolean saveResult = integralGradeService.updateById(integralGrade);
        if(saveResult){
            return Result.ok().message("更新成功");
        }else{
            return Result.error().message("更新失败");
        }

    }



}


